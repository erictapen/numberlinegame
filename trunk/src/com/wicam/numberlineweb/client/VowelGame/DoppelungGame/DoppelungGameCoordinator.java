package com.wicam.numberlineweb.client.VowelGame.DoppelungGame;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.GameTypeSelector;
import com.wicam.numberlineweb.client.VowelGame.AnimationTimer;
import com.wicam.numberlineweb.client.VowelGame.AnimationTimerTask;
import com.wicam.numberlineweb.client.VowelGame.ConsonantPoint2D;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;
import com.wicam.numberlineweb.client.VowelGame.EnemyMoveTask;
import com.wicam.numberlineweb.client.VowelGame.MovingConsonants;
import com.wicam.numberlineweb.client.VowelGame.SoundRetriever;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

/**
 * Coordinate of the doppelung game
 * 
 * @author shuber
 *
 */

public class DoppelungGameCoordinator extends GameCoordinator{

	protected DoppelungGameController controller;
	// boolean variable indicating if the short vowel game has been started
	private boolean shortVowelGameStarted = false; 
	// boolean variable to ensure that the feedback number will set only once
	private boolean feedbackNumberSet = false;
	// random number indicating which feedback should be drawn
	private int feedbackNumber = 0;
	// list of the current moving consonants
	private ArrayList<MovingConsonants> movingConsonantsList = new ArrayList<MovingConsonants>();
	private AnimationTimer aniTimer = new AnimationTimer();
	// SoundController for playing sound files
	private Timer updateMyPositionTimer;

	private static int POSITION_TIMER_INTERVALL = 80;


	// position of the enemy short vowel image
	private int enemyImageX = 270;
	private int enemyImageY = 330;


	public DoppelungGameCoordinator(GameCommunicationServiceAsync commServ, ChatCommunicationServiceAsync chatServ,
			Panel root, GameTypeSelector gs) {
		super(commServ, chatServ, root,gs);
	}

	/**
	 * returns the name of the game
	 */
	@Override
	public String getGameName() {

		return "Doppelungspiel";

	}


	@Override
	public void init() {
		gameSelector = new DoppelungGameSelector(this);
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

		GWT.log("doppelung game coordinator loaded.");

	}

	@Override
	protected void joinedGame(int playerID, int gameID) {
		super.joinedGame(playerID, gameID);
		this.playerID = playerID;

		//construct game
		createControllerAndView();
		DoppelungGameView gameView =  (DoppelungGameView) view;
		gameView.initializeMovingShortVowelImages(playerID);

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
	
	protected void createControllerAndView(){
		controller = new DoppelungGameController(this);
		this.view = new DoppelungGameView(numberOfPlayers, controller);
	}

	@Override
	protected void updateGame(GameState gameState) {
		super.updateGame(gameState);
		DoppelungGameState g = (DoppelungGameState) gameState;
		DoppelungGameView gameView =  (DoppelungGameView) view;
		//we already have the lates state
		if (g==null) return;


		switch (g.getState()) {
			// word played and vowel choice
		case 3:
			shortVowelGameStarted = false; // reset
			if (!g.getShowSoundFeedback(playerID)){
				feedbackNumberSet = false;
				gameView.showVowelChoice();
				
				if (g.getSoundTries(playerID) == 0) {
					gameView.playWord(SoundRetriever.getAudioElement(g.getCurWord(), false), g.getCurWord().getWordString());
				} else {
					gameView.playWord(SoundRetriever.getAudioElement(g.getCurWord(), true), g.getCurWord().getWordString());
				}
				
			}
			// sound feedback after choice
			else {
				if (g.getSoundTries(playerID) < 2){
					if (!feedbackNumberSet){
						if (g.hasCorrectlyAnswered(playerID))
							feedbackNumber = (int)(Math.random()*7);
						else
							feedbackNumber = (int)(Math.random()*4);
						feedbackNumberSet = true;
					}
					gameView.showSoundFeedback(g.hasCorrectlyAnswered(playerID), g.getCurWord().isShortVowel(), feedbackNumber);
					for (int i = 0; i < g.getPlayers().size(); i++){
						gameView.actualizePoints(i+1,g.getPlayerPoints(i+1),g.getPlayerName(i+1));
					}
				}
				else {
					gameView.clearGamePanel();
				}
			}
			for (int i = 0; i < g.getPlayers().size(); i++){
				gameView.actualizePoints(i+1,g.getPlayerPoints(i+1),g.getPlayerName(i+1));
			}
			break;
			// short vowel game
		case 5:
			feedbackNumberSet = false;

			if (g.getPlayerCount() > 1){
				
				POSITION_TIMER_INTERVALL = 80;
				
			}else{

				POSITION_TIMER_INTERVALL = 300;

			}

			if (!shortVowelGameStarted){
				this.controller.setArrowKeysEnabled(true);
				shortVowelGameStarted = true;

				startShortVowelGame(g.getCurWord(), g);

				enemyImageX = 270;
				enemyImageY = 330;				
				enemyMoveTask = new EnemyMoveTask(this);
				registerAniTask(enemyMoveTask);

				makeEnemyMove(enemyImageX, enemyImageY);
			}


			updateMovingConsonantsPosition(gameView, 
					this.movingConsonantsList, 
					g.getMovingConsonantsCoords(), 
					(POSITION_TIMER_INTERVALL+(int)averageLatency));

			if (g.getPlayerCount() > 1){

				this.enemyMoveTask.setToX(g.getPlayerPosX(playerID%2+1));
				this.enemyMoveTask.setToY(g.getPlayerPosY(playerID%2+1));
				int speedY = (int)((g.getPlayerPosY(playerID%2+1)-this.enemyImageY)/((POSITION_TIMER_INTERVALL+averageLatency)/(double)AnimationTimer.TIMER_SPEED));
				int speedX = (int)((g.getPlayerPosX(playerID%2+1)-this.enemyImageX)/((POSITION_TIMER_INTERVALL+averageLatency)/(double)AnimationTimer.TIMER_SPEED));
				this.enemyMoveTask.setSpaceSpeedX(speedX);
				this.enemyMoveTask.setSpaceSpeedY(speedY);

			}

			removeMarkedMc(g);

			for (int i = 0; i < g.getPlayers().size(); i++){
				gameView.actualizePoints(i+1,g.getPlayerPoints(i+1),g.getPlayerName(i+1));
			}


			break;
			// word enter
		case 6:
			if (!g.getShowWordFeedback(playerID)){
				gameView.showUserWordInput();
				feedbackNumberSet = false;
			}
			else {
				if (g.getWordTries(playerID) < 2){
					if (!feedbackNumberSet){
						if (g.hasCorrectlyAnswered(playerID))
							feedbackNumber = (int)(Math.random()*7);
						else
							feedbackNumber = (int)(Math.random()*4);
						feedbackNumberSet = true;
					}
					gameView.showWordFeedback(g.hasCorrectlyAnswered(playerID), g.getCurWord().getWordString(), feedbackNumber);
					for (int i = 0; i < g.getPlayers().size(); i++){
						gameView.actualizePoints(i+1,g.getPlayerPoints(i+1),g.getPlayerName(i+1));
					}
				}
				else
					gameView.clearGamePanel();

			}
			break;
		}

		openGame = g;

	}
	
	
	/**
	 * Sets user name in chat and sets points
	 * "Warte auf [other player name] is displayed
	 * 
	 */
	@Override
	protected void handleAwaitingStartState(GameState g){
		DoppelungGameView gameView =  (DoppelungGameView) view;
		if (this.numberOfPlayers > 1)
			chatC.setUserName(g.getPlayerName(this.playerID));
		for (int i = 0; i < g.getPlayers().size(); i++){
			gameView.actualizePoints(i+1,g.getPlayerPoints(i+1),g.getPlayerName(i+1));
		}
		if (g.isPlayerReady(this.playerID)){
			// other player ready ?

			gameView.clearGamePanel();
			gameView.showWaitingForOtherPlayer("Warte auf " + g.getPlayerName(playerID%2+1) + "!");

		}
		setRefreshRate(1000);
	}
	
	
	/**
	 * the refresh set is increased since the description is displayed
	 */
	@Override
	protected void handleWaitingForPlayersState(){
		setRefreshRate(2000);
	}
	
	
	/**
	 * points are displayed and "Warte auf zweiten Spieler..." is shown
	 */
	@Override
	protected void handleWaitingForOtherPlayersState(GameState g){
		DoppelungGameView gameView =  (DoppelungGameView) view;
		setRefreshRate(2000);
		for (int i = 0; i < g.getPlayers().size(); i++){
			gameView.actualizePoints(i+1,g.getPlayerPoints(i+1),g.getPlayerName(i+1));
		}
		if (g.isPlayerReady(this.playerID)){
			// other player ready ?

			gameView.clearGamePanel();
			gameView.showWaitingForOtherPlayer("Warte auf zweiten Spieler...");

		}
	}
	
	private void removeMarkedMc(DoppelungGameState g){
		if (movingConsonantsList != null){
			for (MovingConsonants mc: this.movingConsonantsList){
				ArrayList<ConsonantPoint2D> mcCoordsList = g.getMovingConsonantsCoords();
				if (mcCoordsList.get(mc.getId()).isRemoved()){
					mc.setRemoved(true);
					removeMovingConsonants(mc);
				}
			}
		}
	}

	/**
	 * Updates the coordinates of the moving consonants to which they should move
	 * 
	 * @param gameView		view of the game
	 * @param mcList		list of the moving consonants
	 * @param coordList		new coordinates
	 * @param timeInterval	refresh time intervall
	 */
	public void updateMovingConsonantsPosition(DoppelungGameView gameView, ArrayList<MovingConsonants> mcList, 
			ArrayList<ConsonantPoint2D> coordList, int timeInterval){
		int i = 0;
		if (mcList != null){
			for (MovingConsonants mc: mcList){
				if (coordList.get(i).getY()+200 >= 0){
					if (!mc.isStartedMoving()){
						gameView.showMovingConsonants(mc);
						gameView.setMcPosition(mc, coordList.get(i).getX(), coordList.get(i).getY());
						mc.setStartedMoving(true);
						mc.startMoving();
					}
					mc.setSpeed((int)((coordList.get(i).getY()-mc.getY())/(timeInterval/(double)AnimationTimer.TIMER_SPEED)));
					mc.moveTo(coordList.get(i).getY());
				}
				i++;
			}
		}
	}

	private void updateMyPosition() {


		long id = (long) Math.random() * 500000;

		super.pingTimes.put(id, System.currentTimeMillis());

		super.timeStamp = System.currentTimeMillis();


		((DoppelungGameCommunicationServiceAsync) commServ).updatePlayerPos(this.openGame.getId() + ":" + 
				this.playerID + ":" +
				((DoppelungGameView)view).getShortVowelImagePosition()[0] + ":" +
				((DoppelungGameView)view).getShortVowelImagePosition()[1] + ":" + id,
				updateCallback);


	}

	/**
	 * Registers a new AnimationTimerTask
	 * 
	 * @param t		the taskText to register
	 */
	public void registerAniTask(AnimationTimerTask t) {
		aniTimer.registerTask(t);
	}

	/*
	 * Animation tasks will later be registered in the animation timer.
	 * a taskText exists for every direction the player can move.
	 */
	private AnimationTimerTask updatePositionTask = new AnimationTimerTask() {

		@Override
		public void run() {
			updateMyPosition();
		}

	};


	private AnimationTimerTask moveLeftTask = new AnimationTimerTask() {

		@Override
		public void run() {
			((DoppelungGameView)view).moveStepLeft(true);
			//updateMyPosition();
		}

	};

	private AnimationTimerTask moveRightTask = new AnimationTimerTask() {

		@Override
		public void run() {

			((DoppelungGameView)view).moveStepRight(true);
			//updateMyPosition();
		}

	};

	private AnimationTimerTask moveUpTask = new AnimationTimerTask() {

		@Override
		public void run() {

			((DoppelungGameView)view).moveStepUp(true);
			//updateMyPosition();

		}

	};

	private AnimationTimerTask moveDownTask = new AnimationTimerTask() {

		@Override
		public void run() {

			((DoppelungGameView)view).moveStepDown(true);
			//updateMyPosition();

		}

	};

	// taskText for the enemy image
	private EnemyMoveTask enemyMoveTask;

	/**
	 * We only want a click to be registered ONCE.
	 * TODO: an array would be nice here
	 */

	private boolean keyUpDown = false;
	private boolean keyDownDown = false;
	private boolean keyLeftDown = false;
	private boolean keyRightDown = false;


	public void moveImageOnGamePanel(boolean up, int key){


		if (!up) {



			switch(key){

			case 1:

				if (!keyDownDown) {
					keyDownDown = true;

					registerAniTask(moveDownTask);

				}

				break;
			case 3:
				if (!keyRightDown) {
					keyRightDown = true;

					registerAniTask(moveRightTask);
				}

				break;
			case 2:
				if (!keyUpDown) {
					keyUpDown = true;

					registerAniTask(moveUpTask);
				}

				break;
			case 4:
				if (!keyLeftDown) {
					keyLeftDown = true;

					registerAniTask(moveLeftTask);
				}

				break;
			}
		}
		if (up) {


			switch(key){

			case 1:

				if (keyDownDown) {

					keyDownDown = false;
					moveDownTask.markForDelete();
				}

				break;
			case 3:
				if (keyRightDown) {

					keyRightDown = false;
					moveRightTask.markForDelete();
				}

				break;
			case 2:
				if (keyUpDown) {

					keyUpDown = false;
					moveUpTask.markForDelete();
				}

				break;
			case 4:
				if (keyLeftDown) {

					keyLeftDown = false;
					moveLeftTask.markForDelete();
				}

				break;
			}



		}

	}

	private void initializeMovingConsonantList(VowelGameWord word, DoppelungGameState g){

		int i = 0;
		Iterator<ConsonantPoint2D> it = g.getMovingConsonantsCoords().iterator();
		while(it.hasNext()){
			ConsonantPoint2D cp2D = it.next();
			MovingConsonants mc = new MovingConsonants(
					cp2D.getConsonant(), 
					this, 
					cp2D.getX(), 
					cp2D.getY(),
					i);
			this.movingConsonantsList.add(mc);
			i++;
		}

	}

	/**
	 * Sets a moving consonant to a new position
	 * 
	 * @param mc	the moving consonant
	 * @param x		new x-coordinate
	 * @param y		new y-coordinate
	 */
	public void setMovingConsonantsPosition(MovingConsonants mc, int x, int y){
		if (((DoppelungGameView) view).isOnCanvas(y)){
			((DoppelungGameView) view).setMcPosition(mc, x, y);
		}
		else{
			mc.setRemoved(true);
			removeMovingConsonants(mc);
		}
	}

	/**
	 * Set enemy image to a new position
	 * 
	 * @param x		new x-coordinate
	 * @param y		new y-coordinate
	 */
	public void moveEnemyTo(int x, int y){
		((DoppelungGameView) view).moveEnemyTo(x, y);
	}


	public void removeMovingConsonants(MovingConsonants mc){
		((DoppelungGameView) view).hideMovingConsonant(mc);
		// because of concurrent modification do not remove them
		//movingConsonantsList.remove(mc);
		boolean allRemoved = true;
		for (MovingConsonants mc2: movingConsonantsList)
			if (!mc2.removed())
				allRemoved = false;
		if (allRemoved){
			movingConsonantsList = new ArrayList<MovingConsonants>();
			//finished MovingConsonantsGame
			this.controller.setArrowKeysEnabled(false);
			endMovingConsonantsGame();
			this.enemyMoveTask.markForDelete();
			((DoppelungGameCommunicationServiceAsync) commServ).enableWordInput(Integer.toString(openGame.getId()) + ":" +  this.playerID, updateCallback);
		}
	}


	public void endMovingConsonantsGame() {

		moveLeftTask.markForDelete();
		moveRightTask.markForDelete();
		moveUpTask.markForDelete();
		moveDownTask.markForDelete();
		updatePositionTask.markForDelete();
		enemyMoveTask.markForDelete();

		keyUpDown = false;
		keyDownDown = false;
		keyLeftDown = false;
		keyRightDown = false;

		GWT.log("canceled updateMyPositionTimer, started standard update timer again..");
		updateMyPositionTimer.cancel();
		t.scheduleRepeating(200);


	}

	public void makeEnemyMove(int x, int y) {
		this.enemyImageX = x;
		this.enemyImageY = y;
		((DoppelungGameView)view).moveEnemyTo(enemyImageX, enemyImageY);
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
			((DoppelungGameCommunicationServiceAsync) commServ).updatePoints(
					openGame.getId() + ":" + 
					Integer.toString(playerID) + ":" + 
					mc.getConsonants() + ":" + 
					mc.getId(), 
					updateCallback);
			mc.setRemoved(true);
			removeMovingConsonants(mc);
		}
	}


	public void startShortVowelGame(VowelGameWord word, DoppelungGameState g){

		((DoppelungGameView)view).showShortVowelGame(this.playerID,
				this.numberOfPlayers, 
				270, 
				330);

		initializeMovingConsonantList(word, g);

		GWT.log("cancelled normal timer, starded updateMyPositionTimer...");

		t.cancel();

		updateMyPositionTimer = new Timer() {
			@Override
			public void run() {
				updateMyPosition();
			}
		};

		updateMyPositionTimer.scheduleRepeating(POSITION_TIMER_INTERVALL);

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

	public void wordEntered(String word){
		((DoppelungGameCommunicationServiceAsync) commServ).wordEntered(openGame.getId() + ":" + Integer.toString(playerID) + ":" + word, updateCallback);
	}

	public int getEnemyImageX() {
		return enemyImageX;
	}

	public int getEnemyImageY() {
		return enemyImageY;
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
