package com.wicam.numberlineweb.client.MathDiagnostics;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.GameTypeSelector;
import com.wicam.numberlineweb.client.GameView;
import com.wicam.numberlineweb.client.MobileDeviceChecker;
import com.wicam.numberlineweb.client.MathDiagnostics.ChoiceTaskItemInformation;
import com.wicam.numberlineweb.client.MathDiagnostics.ChoiceReactionTask.AdditionView;
import com.wicam.numberlineweb.client.MathDiagnostics.ChoiceReactionTask.ChoiceReactionTaskController;
import com.wicam.numberlineweb.client.MathDiagnostics.ChoiceReactionTask.SubtractionView;
import com.wicam.numberlineweb.client.MathDiagnostics.NumberComparison.NumberComparisonController;
import com.wicam.numberlineweb.client.MathDiagnostics.NumberComparison.NumberComparisonView;
import com.wicam.numberlineweb.client.MathDiagnostics.NumberLine.NumberLineItem;
import com.wicam.numberlineweb.client.MathDiagnostics.NumberLine.NumberLineItemInformation;
import com.wicam.numberlineweb.client.MathDiagnostics.NumberLine.NumberLineTaskController;
import com.wicam.numberlineweb.client.MathDiagnostics.NumberLine.NumberLineTaskView;
import com.wicam.numberlineweb.client.MathDiagnostics.VerificationTask.MultiplicationView;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

public class MathDiagnosticsCoordinator extends GameCoordinator {
	
	private ArrayList<isItem> itemList = null;
	private Iterator<isItem> itemListIterator;
	private isItem curItem = null;
	private int itemType;
	private ArrayList<ItemInformation> keyCodeList = new ArrayList<ItemInformation>();
	private boolean started = false;
	private final int timeBetweenTrials = 1000;
	private Duration duration;
	
	public MathDiagnosticsCoordinator(GameCommunicationServiceAsync commServ,
			ChatCommunicationServiceAsync chatCommServ, Panel root,
			GameTypeSelector gts) {
		super(commServ, chatCommServ, root, gts);
	}

	@Override
	public void init() {
		gameSelector = new MathDiagnosticsSelector(this);
		rootPanel.add(gameSelector);

		t = new Timer() {
			@Override
			public void run() {
				update();
			}
		};

		//main loop-timer
		t.scheduleRepeating(500);
		refreshGameList();

		GWT.log("math diagnostics coordinator loaded.");
	}

	@Override
	protected void joinedGame(int playerID, int gameID) {
		super.joinedGame(playerID, gameID);
		this.playerID = playerID;
		itemType = ((MathDiagnosticsSelector)gameSelector).getTask();
			
		constructControllerAndView();
		
		//construct an empty game-state with the given information
		MathDiagnosticsGameState g = new MathDiagnosticsGameState();
		g.setGameId(gameID);
		g.setState(-1);
		this.openGame = g;
		
		MathDiagnosticsView gameView =  (MathDiagnosticsView) view;
		update();
		
		//clear the root panel and draw the game
		rootPanel.clear();
		rootPanel.add(gameView);
		
		// retrieve items
		((MathDiagnosticsCommonicationServiceAsync) commServ).retrieveItemList(gameID, itemType, retrieveItemsCallback);
	}
	
	private void constructControllerAndView(){
		if (itemType == ItemTypes.ADDITIONITEM || 
				itemType == ItemTypes.SUBTRACTIONITEM || 
				itemType == ItemTypes.MULTIPLICATIONITEM){
			
			ChoiceReactionTaskController controller = new ChoiceReactionTaskController(this);
			
			if (itemType == ItemTypes.ADDITIONITEM)
				this.view = new AdditionView(numberOfPlayers, controller);
			if (itemType == ItemTypes.SUBTRACTIONITEM)
				this.view = new SubtractionView(numberOfPlayers, controller);
			if (itemType == ItemTypes.MULTIPLICATIONITEM)
				this.view = new MultiplicationView(numberOfPlayers, controller);
		}
		
		if (itemType == ItemTypes.NUMBERLINEITEM){
			NumberLineTaskController controller = new NumberLineTaskController(this);
			this.view = new NumberLineTaskView(numberOfPlayers, controller);
		}
		
		if (itemType == ItemTypes.NUMBERCOMPARISON){
			NumberComparisonController controller = new NumberComparisonController(this);
			this.view = new NumberComparisonView(numberOfPlayers, controller);
		}
		((MathDiagnosticsView)view).initKeyboardDependentElements(!MobileDeviceChecker.checkForKeyboard());
	}
	
	@Override
	public String getGameName() {

		return "Mathediagnostik";

	}
	
	@Override
	protected void updateGame(GameState gameState) {
		super.updateGame(gameState);
		MathDiagnosticsGameState g = (MathDiagnosticsGameState) gameState;
		//we already have the lates state
		if (g==null) return;


		switch (g.getState()) {
		// present item
		case 3:
			if (itemList != null){
				if (!started){
					started = true;
					itemListIterator = itemList.iterator();
					presentNextItem();
					// we do not need to communicate with the server
					setRefreshRate(10000);
				}
			}
			// TODO: loading screen if item list not loaded
			else {
				setRefreshRate(100);
			}
			break;
		}

		openGame = g;

	}
	
	
	/**
	 * empty since there are no other players
	 */
	@Override
	protected void handleWaitingForPlayersState(){}
	@Override
	protected void handleAwaitingStartState(GameState gameState){}
	
	
	/**
	 * we don't need to wait for other players
	 * just increase refresh rate
	 */
	@Override
	protected void handleWaitingForOtherPlayersState(GameState g){
		setRefreshRate(1000);
	}
	
	
	@Override
	protected void handlePerformanceState(GameState gameState){
		MathDiagnosticsHighScoreView h = new MathDiagnosticsHighScoreView(gameState.getPlayers(),GameView.playerColors, 500);
		rootPanel.clear();
		h.init(rootPanel);
	}
	
	
	public void startButtonClicked(){
		if (!openGame.isPlayerReady(this.playerID)) {
			commServ.updateReadyness(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
		}
	}

	public void recordItemInformation(int response){
		ItemInformation itemInf = new ItemInformation();
		
		if (itemType == ItemTypes.ADDITIONITEM || 
				itemType == ItemTypes.NUMBERCOMPARISON ||
				itemType == ItemTypes.SUBTRACTIONITEM ||
				itemType == ItemTypes.MULTIPLICATIONITEM){
			itemInf = new ChoiceTaskItemInformation();
			((ChoiceTaskItemInformation)itemInf).setCorrect(response == curItem.getCorrectSolution());
		}
		
		if (itemType == ItemTypes.NUMBERLINEITEM){
			NumberLineItem item = (NumberLineItem)curItem;
			itemInf = new NumberLineItemInformation();
			int realPos = item.getLeftNumber() + (int) ((response) *  ((double)(item.getRightNumber() - item.getLeftNumber())/400));
			int deviation = Math.abs(item.getCorrectSolution() - realPos);
			((NumberLineItemInformation)itemInf).setDeviation(deviation);
			((NumberLineItemInformation)itemInf).setRelDeviation((double)deviation/(item.getRightNumber()-item.getLeftNumber()));
		}
		
		itemInf.setRt(duration.elapsedMillis());
		keyCodeList.add(itemInf);
	}
	
	public void presentNextItem(){
		((MathDiagnosticsPresentation) view).clearGamePanel();
		if (itemListIterator.hasNext()){
			curItem = itemListIterator.next();
			ShowNextItemTask showNextItemTask = new ShowNextItemTask(this, (MathDiagnosticsPresentation) view, curItem);
			showNextItemTask.schedule(timeBetweenTrials);
		}
		else {
			setRefreshRate(1000);
			ShowResultTask showResultTask = new ShowResultTask((MathDiagnosticsPresentation) view);
			showResultTask.schedule(timeBetweenTrials);
			((MathDiagnosticsCommonicationServiceAsync)commServ).sendItemInformationList(openGame.getId(), keyCodeList, dummyCallback);
		}
	}
	
	public void createNewDuration (){
		duration = new Duration();
	}

	public void mouseMovedTo(int x, int y) {
		if (view instanceof NumberLineTaskView)
			if (x>=175 && x<=575)	
				((NumberLineTaskView)view).setPointer(playerID, x-175);
		
	}

	AsyncCallback<ArrayList<isItem>> retrieveItemsCallback = new AsyncCallback<ArrayList<isItem>>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(ArrayList<isItem> result) {	
			itemList = result;
		}

	};
}
