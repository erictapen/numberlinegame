package com.wicam.numberlineweb.client.DoppelungGame;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.GameTypeSelector;
import com.wicam.numberlineweb.client.Player;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

public class DoppelungGameCoordinator extends GameCoordinator{

	private DoppelungGameController controller;
	private boolean shortVowelGameStarted = false;
	private ArrayList<MovingConsonants> movingConsonantsList = new ArrayList<MovingConsonants>();
	private AnimationTimer aniTimer = new AnimationTimer();


	public DoppelungGameCoordinator(GameCommunicationServiceAsync commServ, ChatCommunicationServiceAsync chatServ,
			Panel root, GameTypeSelector gs) {
		super(commServ, chatServ, root,gs);
	}

	@Override
	public String getGameName() {

		return "Doppelung";

	}


	@Override
	public void init() {
		gameSelector = new DoppelungGameSelector((DoppelungGameCoordinator) this);
		rootPanel.add(gameSelector);

		t = new Timer() {
			public void run() {
				update();
			}
		};

		//main loop-timer
		t.scheduleRepeating(500);
		refreshGameList();

		GWT.log("doppelung game coordinator loaded.");

	}

	@Override
	public void openGame(GameState gameState) {
		this.numberOfPlayers = gameState.getMaxNumberOfPlayers();
		this.numberOfNPCs = gameState.getNumberOfMaxNPCs();
		DoppelungGameState g = (DoppelungGameState) gameState;
		commServ.openGame(g, gameOpenedCallBack);
	}

	@Override
	protected void joinedGame(int playerID, int gameID) {
		this.playerID = playerID;

		//construct game
		controller = new DoppelungGameController(this);
		this.view = new DoppelungGameView(numberOfPlayers, controller);
		DoppelungGameView gameView =  (DoppelungGameView) view;

		// TODO: add controller


		//construct an empty game-state with the given information
		DoppelungGameState g = new DoppelungGameState();
		g.setGameId(gameID);
		g.setState(-1);
		this.openGame = g;
		update();

		//clear the root panel and draw the game
		rootPanel.clear();
		rootPanel.add(gameView);

		if (this.numberOfPlayers > 1){
			this.addChatView();
		}

	}

	@Override
	protected void updateGame(GameState gameState) {
		DoppelungGameState g = (DoppelungGameState) gameState;
		DoppelungGameView gameView =  (DoppelungGameView) view;

		//we already have the lates state
		if (g==null) return;

		switch (g.getState()) {

		//game closed
		case -1:
			setRefreshRate(2000);
			//TODO: close game
			break;
			//awaiting players
		case 0:
			setRefreshRate(2000);
			break;
			//awaiting 2nd player
		case 1:
			setRefreshRate(2000);
			for (int i = 0; i < g.getPlayers().size(); i++){
				gameView.showPlayerName(i+1,g.getPlayerName(i+1));
			}
			// TODO: wait for second player
			break;
			//awaiting start
		case 2:
			if (this.numberOfPlayers > 1)
				chatC.setUserName(g.getPlayerName(this.playerID));
			for (int i = 0; i < g.getPlayers().size(); i++){
				gameView.showPlayerName(i+1,g.getPlayerName(i+1));
			}
			setRefreshRate(1000);
			break;

		case 21:
			shortVowelGameStarted = false; // reset
			setRefreshRate(200);
			//start is pending. I am ready!
			if (!openGame.isPlayerReady(this.playerID)) {
				commServ.updateReadyness(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
			}
			break;

			// word played and vowel choice
		case 3:
			gameView.showVowelChoice(g.getCurWord().getWord());
			break;
			// feedback after choice
		case 4:
			gameView.showFeedback(g.isCorrectAnswered());
			for (int i = 0; i < g.getPlayers().size(); i++){
				gameView.actualizePointsBar(i+1, g.getPlayerPoints(i+1));
			}
			break;
			// short vowel game
		case 5:
			if (!shortVowelGameStarted){
				shortVowelGameStarted = true;
				t.cancel();
				registerAniTask(new AnimationTimerTask() {
					
					
					@Override
					public void run() {
						update();
					}
					
				});

				startShortVowelGame(g.getCurWord());
			}
		
			makeEnemyMove(g.enemyMovingTo(((this.playerID) % 2)+1));

			for (int i = 0; i < g.getPlayers().size(); i++){
				gameView.actualizePointsBar(i+1, g.getPlayerPoints(i+1));
			}
			break;

		case 97:
			gameView.showEndScreen(g.getPlayerPoints(playerID));
			break;

		case 98:
			closeGame(g);
			break;

		case 99:
			// player has left the game

			Iterator<? extends Player> i = g.getPlayers().iterator();

			while (i.hasNext()) {

				Player current = i.next();

				if (current.hasLeftGame() && !openGame.getPlayers().get(g.getPlayers().indexOf(current)).hasLeftGame()) {
					// TODO: view left game
				}


			}


			break;
		}

	}

	public void registerAniTask(AnimationTimerTask t) {
		aniTimer.registerTask(t);
	}

	/*
	 * Animation tasks will later be registered in the animation timer.
	 * a task exists for every direction the player can move.
	 */

	private AnimationTimerTask moveLeftTask = new AnimationTimerTask() {

		@Override
		public void run() {

			((DoppelungGameView)view).moveStepLeft(true);
		}

	};

	private AnimationTimerTask moveRightTask = new AnimationTimerTask() {

		@Override
		public void run() {

			((DoppelungGameView)view).moveStepRight(true);
		}

	};

	private AnimationTimerTask moveUpTask = new AnimationTimerTask() {

		@Override
		public void run() {

			((DoppelungGameView)view).moveStepUp(true);

		}

	};

	private AnimationTimerTask moveDownTask = new AnimationTimerTask() {

		@Override
		public void run() {
			((DoppelungGameView)view).moveStepDown(true);

		}

	};
	
	private AnimationTimerTask moveLeftTaskEnemy = new AnimationTimerTask() {

		@Override
		public void run() {

			((DoppelungGameView)view).moveStepLeft(false);
		}

	};

	private AnimationTimerTask moveRightTaskEnemy = new AnimationTimerTask() {

		@Override
		public void run() {

			((DoppelungGameView)view).moveStepRight(false);
		}

	};

	private AnimationTimerTask moveUpTaskEnemy = new AnimationTimerTask() {

		@Override
		public void run() {

			((DoppelungGameView)view).moveStepUp(false);

		}

	};

	private AnimationTimerTask moveDownTaskEnemy = new AnimationTimerTask() {

		@Override
		public void run() {
			((DoppelungGameView)view).moveStepDown(false);

		}

	};


	/**
	 * We only want a click to be registered ONCE.
	 * TODO: an array would be nice here
	 */

	private boolean keyUpDown = false;
	private boolean keyDownDown = false;
	private boolean keyLeftDown = false;
	private boolean keyRightDown = false;


	public void moveImageOnGamePanel(KeyEvent event){
		int keyCode = event.getNativeEvent().getKeyCode();

		if (event instanceof KeyDownEvent) {



			switch(keyCode){

			case KeyCodes.KEY_DOWN:

				if (!keyDownDown) {
					keyDownDown = true;
					((DoppelungGameCommunicationServiceAsync) commServ).keyEvent(this.openGame.getId() + ":" + this.playerID + ":down:" + keyCode,keyEventCallback);

					registerAniTask(moveDownTask);

				}

				break;
			case KeyCodes.KEY_RIGHT:
				if (!keyRightDown) {
					keyRightDown = true;
					((DoppelungGameCommunicationServiceAsync) commServ).keyEvent(this.openGame.getId() + ":" + this.playerID + ":down:" + keyCode,keyEventCallback);

					registerAniTask(moveRightTask);
				}

				break;
			case KeyCodes.KEY_UP:
				if (!keyUpDown) {
					keyUpDown = true;
					((DoppelungGameCommunicationServiceAsync) commServ).keyEvent(this.openGame.getId() +":" + this.playerID + ":down:" + keyCode,keyEventCallback);

					registerAniTask(moveUpTask);
				}

				break;
			case KeyCodes.KEY_LEFT:
				if (!keyLeftDown) {
					keyLeftDown = true;
					((DoppelungGameCommunicationServiceAsync) commServ).keyEvent(this.openGame.getId() +":" + this.playerID + ":down:" + keyCode,keyEventCallback);

					registerAniTask(moveLeftTask);
				}

				break;
			}
		}
		if (event instanceof KeyUpEvent) {


			switch(keyCode){

			case KeyCodes.KEY_DOWN:

				if (keyDownDown) {
					((DoppelungGameCommunicationServiceAsync) commServ).keyEvent(this.openGame.getId() + ":" + this.playerID + ":up:" + keyCode,keyEventCallback);

					keyDownDown = false;
					moveDownTask.markForDelete();
				}

				break;
			case KeyCodes.KEY_RIGHT:
				if (keyRightDown) {
					((DoppelungGameCommunicationServiceAsync) commServ).keyEvent(this.openGame.getId() + ":" + this.playerID + ":up:" + keyCode,keyEventCallback);

					keyRightDown = false;
					moveRightTask.markForDelete();
				}

				break;
			case KeyCodes.KEY_UP:
				if (keyUpDown) {
					((DoppelungGameCommunicationServiceAsync) commServ).keyEvent(this.openGame.getId() +":" +  this.playerID + ":up:" + keyCode,keyEventCallback);

					keyUpDown = false;
					moveUpTask.markForDelete();
				}

				break;
			case KeyCodes.KEY_LEFT:
				if (keyLeftDown) {
					((DoppelungGameCommunicationServiceAsync) commServ).keyEvent(this.openGame.getId() + ":" +  this.playerID + ":up:" + keyCode,keyEventCallback);

					keyLeftDown = false;
					moveLeftTask.markForDelete();
				}

				break;
			}
			
			if (!keyDownDown && !keyUpDown && !keyLeftDown && !keyRightDown) ((DoppelungGameCommunicationServiceAsync) commServ).keyEvent(this.openGame.getId() + ":" +  this.playerID + ":stop:-1",keyEventCallback);


		}

	}

	private void initializeMovingConsonantList(DoppelungGameWord word){
		ArrayList<String> consonantPairList = DoppelungGameConsonantPairListCreater.createConsonantPairList(word.getConsonantPair(),5,10);
		int i = 0;
		for (String consonantPair: consonantPairList){
			MovingConsonants mc = new MovingConsonants(consonantPair, this, 50+i%9*50, -50);



			((DoppelungGameView) view).showMovingConsonants(i, mc);

			this.movingConsonantsList.add(mc);
			i++;
		}
	}


	public void setMovingConsonantsPosition(MovingConsonants mc, int x, int y){
		if (((DoppelungGameView) view).isOnCanvas(y)){
			((DoppelungGameView) view).setMcPosition(mc, x, y);
		}
		else{
			mc.setRemoved(true);
			removeMovingConsonants(mc);
		}
	}


	public void removeMovingConsonants(MovingConsonants mc){
		((DoppelungGameView) view).hideMovingConsonant(mc);
		movingConsonantsList.remove(mc);
		if (movingConsonantsList.isEmpty()){

			//finished MovingConsonantsGame

			endMovingConsonantsGame();
			((DoppelungGameView) view).showUserWordInput();
		}
	}


	public void endMovingConsonantsGame() {

		moveLeftTask.markForDelete();
		moveRightTask.markForDelete();
		moveUpTask.markForDelete();
		moveDownTask.markForDelete();

		keyUpDown = false;
		keyDownDown = false;
		keyLeftDown = false;
		keyRightDown = false;

	}

	public void makeEnemyMove(String to) {
		
		if (to == null) to="stop";

		if (to.equals("up")) {

			this.registerAniTask(moveUpTaskEnemy);
			
		}

		if (to.equals("down")) {

			this.registerAniTask(moveDownTaskEnemy);
			
		}

		if (to.equals("left")) {
			
			this.registerAniTask(moveLeftTaskEnemy);

		}

		if (to.equals("right")) {
			
			this.registerAniTask(moveRightTaskEnemy);

		}
		
		if (to.equals("stop")) {
			
			moveUpTaskEnemy.markForDelete();
			moveDownTaskEnemy.markForDelete();
			moveLeftTaskEnemy.markForDelete();
			moveRightTaskEnemy.markForDelete();
			
			
		}



	}


	/**
	 * TODO: this has to be done in the controller
	 * @param mc
	 */
	public void checkForCollision(MovingConsonants mc){

		int[] imgDimension = ((DoppelungGameView) view).getShortVowelImageDimension();

		int imgWidth = imgDimension[0];
		int imgHeight = imgDimension[1];

		int mcWidth = mc.getOffsetWidth();
		int mcHeight = mc.getOffsetHeight();

		int[] imgPosition = ((DoppelungGameView) view).getShortVowelImagePosition();

		int posXDiff = Math.abs(imgPosition[0] - mc.getX());
		int posYDiff = Math.abs(imgPosition[1] - mc.getY());

		if (posXDiff < imgWidth/2+mcWidth/2 && posYDiff < imgHeight/2+mcHeight/2){
			updatePoints(mc.getConsonants());
			mc.setRemoved(true);
			removeMovingConsonants(mc);
		}
	}


	public void startShortVowelGame(DoppelungGameWord word){

		((DoppelungGameView)view).showShortVowelGame(this.numberOfPlayers);
		initializeMovingConsonantList(word);



	}

	public void startButtonClicked(){
		if (!openGame.isPlayerReady(this.playerID)) {
			commServ.updateReadyness(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
		}
	}

	public void vowelButtonClicked(int buttonid){
		((DoppelungGameCommunicationServiceAsync)commServ).buttonClicked(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID) + ":"
				+ Integer.toString(buttonid), updateCallback);
	}

	public void updatePoints(String consonants){
		((DoppelungGameCommunicationServiceAsync) commServ).updatePoints(openGame.getId() + ":" + Integer.toString(playerID) + ":" + consonants, updateCallback);
	}

	public void wordEntered(String word){
		((DoppelungGameCommunicationServiceAsync) commServ).wordEntered(openGame.getId() + ":" + Integer.toString(playerID) + ":" + word, updateCallback);
	}


	AsyncCallback<DoppelungGameState> keyEventCallback = new AsyncCallback<DoppelungGameState>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(DoppelungGameState result) {


		}

	};
}
