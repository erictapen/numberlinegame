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
import com.wicam.numberlineweb.client.HighScoreView;
import com.wicam.numberlineweb.client.Player;
import com.wicam.numberlineweb.client.MathDiagnostics.Addition.AdditionController;
import com.wicam.numberlineweb.client.MathDiagnostics.Addition.AdditionView;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

public class MathDiagnosticsCoordinator extends GameCoordinator {
	
	private ArrayList<isItem> itemList = null;
	private Iterator<isItem> itemListIterator;
	private isItem curItem = null;
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
		gameSelector = new MathDiagnosticsSelector((MathDiagnosticsCoordinator) this);
		rootPanel.add(gameSelector);

		t = new Timer() {
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

		//construct game
		if (((MathDiagnosticsSelector)gameSelector).getTask() == ItemTypes.ADDITIONITEM){
			AdditionController controller = new AdditionController(this);
			this.view = new AdditionView(numberOfPlayers, controller);
		}
		
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
		((MathDiagnosticsCommonicationServiceAsync) commServ).retrieveItemList(gameID, ItemTypes.ADDITIONITEM, retrieveItemsCallback);
	}
	
	@Override
	public String getGameName() {

		return "Mathediagnostik";

	}
	
	@Override
	protected void updateGame(GameState gameState) {
		MathDiagnosticsGameState g = (MathDiagnosticsGameState) gameState;
		//we already have the lates state
		if (g==null) return;


		switch (g.getState()) {

		//game closed
		case -1:
			setRefreshRate(2000);
			//TODO: close game
			break;
			//awaiting start
		case 1: case 2:
			setRefreshRate(1000);
			break;
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
			// performance
		case 97:
			MathDiagnosticsHighScoreView h = new MathDiagnosticsHighScoreView(g.getPlayers(),GameView.playerColors, 500);
			rootPanel.clear();
			h.init(rootPanel);
			break;

		case 98:
			closeGame();
			break;

		case 99:
			// player has left the game

			Iterator<? extends Player> i = g.getPlayers().iterator();

			while (i.hasNext()) {

				Player current = i.next();

				int pid = g.getPlayers().indexOf(current)+1;
				GWT.log(Integer.toString(pid));
				GWT.log(Integer.toString(openGame.getPlayers().size()));

				if (current.hasLeftGame() && (openGame.getPlayers().size() >= pid &&!(openGame.getPlayers().get(pid-1).hasLeftGame()))) {
					super.showPlayerLeft(current.getName());
				}

			}


			break;
		}

		openGame = g;

	}
	
	public void startButtonClicked(){
		if (!openGame.isPlayerReady(this.playerID)) {
			commServ.updateReadyness(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
		}
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

	public void recordItemInformation(int keyCode){
		ItemInformation itemInf = new ItemInformation();
		boolean isCorrect = keyCode == curItem.getCorrectSolution();
		itemInf.setItemInformation(isCorrect, duration.elapsedMillis());
		keyCodeList.add(itemInf);
	}
	
	public void presentNextItem(){
		((MathDiagnosticsView) view).clearGamePanel();
		if (itemListIterator.hasNext()){
			curItem = itemListIterator.next();
			ShowNextItemTask showNextItemTask = new ShowNextItemTask(this, (MathDiagnosticsView) view, curItem);
			showNextItemTask.schedule(timeBetweenTrials);
		}
		else {
			setRefreshRate(1000);
			ShowResultTask showResultTask = new ShowResultTask((MathDiagnosticsView) view);
			showResultTask.schedule(timeBetweenTrials);
			((MathDiagnosticsCommonicationServiceAsync)commServ).sendKeyCodeList(openGame.getId(), keyCodeList, dummyCallback);
		}
	}
	
	public void createNewDuration (){
		duration = new Duration();
	}
}
