package com.wicam.numberlineweb.client.VowelGame.DoppelungGame;

import java.util.ArrayList;
import java.util.Iterator;

import com.allen_sauer.gwt.voices.client.SoundController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.GameTypeSelector;
import com.wicam.numberlineweb.client.VowelGame.AnimationTimer;
import com.wicam.numberlineweb.client.VowelGame.AnimationTimerTask;
import com.wicam.numberlineweb.client.VowelGame.ConsonantPoint2D;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;
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
	private SoundController soundController = new SoundController();
	private Timer updateMcCoordsTimer;
	
	private Timer sendKeepAliveTimer;

	private static int POSITION_TIMER_INTERVALL = 80;


	// position of the enemy short vowel image

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
	protected void joinedGame(int playerID, int gameID) {
		super.joinedGame(playerID, gameID);
		this.playerID = playerID;
		
		this.preloadImageFiles();
		
		((DoppelungGameCommunicationServiceAsync) commServ).getSimpleWordList(Integer.toString(gameID), callback);
		
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
	
	AsyncCallback<ArrayList<VowelGameWord>> callback = new AsyncCallback<ArrayList<VowelGameWord>>() {

		@Override
		public void onFailure(Throwable caught) {
			
		}

		@Override
		public void onSuccess(ArrayList<VowelGameWord> result) {
			preloadSoundFiles(result);
		}
		
		
	};
	
	protected final void preloadSoundFiles(ArrayList<VowelGameWord> list) {
		
		
		for (VowelGameWord word : list) {
			
			SoundRetriever.preloadSound(word, false);
			SoundRetriever.preloadSound(word, true);
			
		}
		
	}
	
	protected void preloadImageFiles() {
		
		String[] imageFileNames = {"doppelungGame/feedback/beide_daumen.gif", "doppelungGame/feedback/beifall.gif",
				"doppelungGame/feedback/beifall_1.gif", "doppelungGame/feedback/jippie.gif", "doppelungGame/feedback/juchhu.gif",
				"doppelungGame/feedback/smile_1.gif", "doppelungGame/feedback/victory.gif", "doppelungGame/feedback/hantel.gif",
				"doppelungGame/feedback/hmm_big.gif", "doppelungGame/feedback/huch.gif", "doppelungGame/feedback/oops.gif",
				"doppelungGame/coins/coin_blue.png", "doppelungGame/coins/coin_red.png", "doppelungGame/coins/coin_ck.png",
				"doppelungGame/coins/coin_hl.png", "doppelungGame/coins/coin_hm.png", "doppelungGame/coins/coin_hn.png",
				"doppelungGame/coins/coin_ll.png", "doppelungGame/coins/coin_lt.png", "doppelungGame/coins/coin_mm.png",
				"doppelungGame/coins/coin_nn.png", "doppelungGame/coins/coin_pp.png", "doppelungGame/coins/coin_rr.png",
				"doppelungGame/coins/coin_rt.png", "doppelungGame/coins/coin_ss.png", "doppelungGame/coins/coin_tt.png"}; 
		
		for (String imageFileName : imageFileNames) 
			Image.prefetch(imageFileName);
		
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
					gameView.playWord(SoundRetriever.getSound(soundController, g.getCurWord(), false), g.getCurWord().getWordString());
				} else {
					gameView.playWord(SoundRetriever.getSound(soundController, g.getCurWord(), true), g.getCurWord().getWordString());
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

			}

			updateMovingConsonantsPosition(gameView, 
					this.getMovingConsonantsList(), 
					g.getMovingConsonantsCoords(), 
					(POSITION_TIMER_INTERVALL+(int)averageLatency));

			markCollectedMc(g);
				
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
	
	private void markCollectedMc(DoppelungGameState g){
		
		//Game state is already up to date
		if (g == null)
			return;
		
		if (getMovingConsonantsList() != null){
			for (MovingConsonants mc: this.getMovingConsonantsList()){
				ArrayList<ConsonantPoint2D> mcCoordsList = g.getMovingConsonantsCoords();
				
				if (mcCoordsList.get(mc.getId()).isCollected())
					mc.setCollected(mcCoordsList.get(mc.getId()).getCollectorPlayerID());
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
	

	private void sendKeepAlive() {

		long id = (long) Math.random() * 500000;

		super.pingTimes.put(id, System.currentTimeMillis());

		super.timeStamp = System.currentTimeMillis();
		
		if (this.view != null) {
			commServ.update(Integer.toString(this.openGame.getId()) + ":" + Integer.toString(this.playerID) + ":" + id, getMarkedMCCallback);
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
	 * @param t		the task to register
	 */
	public void registerAniTask(AnimationTimerTask t) {
		aniTimer.registerTask(t);
	}

	/*
	 * Animation tasks will later be registered in the animation timer.
	 * a task exists for every direction the player can move.
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
			this.getMovingConsonantsList().add(mc);
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

	public void removeMovingConsonants(MovingConsonants mc){
		((DoppelungGameView) view).hideMovingConsonant(mc);
		// because of concurrent modification do not remove them
		//movingConsonantsList.remove(mc);
		boolean allRemoved = true;
		for (MovingConsonants mc2: getMovingConsonantsList())
			if (!mc2.removed())
				allRemoved = false;
		if (allRemoved){
			
			setMovingConsonantsList(new ArrayList<MovingConsonants>());
			//finished MovingConsonantsGame
			this.controller.setArrowKeysEnabled(false);
			endMovingConsonantsGame();
			((DoppelungGameCommunicationServiceAsync) commServ).enableWordInput(Integer.toString(openGame.getId()) + ":" +  this.playerID, updateCallback);
		}
	}


	public void endMovingConsonantsGame() {

		moveLeftTask.markForDelete();
		moveRightTask.markForDelete();
		moveUpTask.markForDelete();
		moveDownTask.markForDelete();
		updatePositionTask.markForDelete();

		keyUpDown = false;
		keyDownDown = false;
		keyLeftDown = false;
		keyRightDown = false;

		this.sendKeepAliveTimer.cancel();
		
		this.updateMcCoordsTimer.cancel();

		if (this.openGame.getPlayerCount() == 1) {
			
			//Send update to server to inform about the new points
			((DoppelungGameCommunicationServiceAsync) commServ).setPlayerPoints(
					openGame.getId() + ":" + 
					Integer.toString(playerID) + ":" + 
					Integer.toString(this.openGame.getPlayerPoints(playerID)),
					updateCallback);
			
		}
		
		t.scheduleRepeating(200);
		
			
	}


	/**
	 * TODO: this has to be done in the controller
	 * @param mc
	 */
	public void checkForCollision(MovingConsonants mc){
		
		if (mc.isCollected())
			return;

		int[] imgDimension = ((DoppelungGameView) view).getShortVowelImageDimension();

		int imgWidth = imgDimension[0];
		int imgHeight = imgDimension[1];

		int mcWidth = mc.getOffsetWidth();
		int mcHeight = mc.getOffsetHeight();

		int[] imgPosition = ((DoppelungGameView) view).getShortVowelImagePosition();

		int posXDiff = Math.abs(imgPosition[0] - mc.getX());
		int posYDiff = Math.abs(imgPosition[1] - mc.getY());
		

		if (posXDiff < imgWidth/2+mcWidth/2 && posYDiff < imgHeight/2+mcHeight/2){
			
			
			if (this.openGame.getPlayerCount() > 1) {
				
				((DoppelungGameCommunicationServiceAsync) commServ).updatePoints(
						openGame.getId() + ":" + 
						Integer.toString(playerID) + ":" + 
						mc.getConsonants() + ":" + 
						mc.getId(), 
						updateCallback);
				
			}
			else {
				
				GameState g = this.updatePoints(
						openGame.getId() + ":" + 
						Integer.toString(playerID) + ":" + 
						mc.getConsonants() + ":" + 
						mc.getId());
				
				this.updateGame(g);
				
			}

//			mc.setRemoved(true);
			mc.setCollected(this.playerID);
//			removeMovingConsonants(mc);
			
		}
	}


	public void startShortVowelGame(VowelGameWord word, DoppelungGameState g){

		((DoppelungGameView)view).showShortVowelGame(this.playerID,
				this.numberOfPlayers, 
				270, 
				330);

		initializeMovingConsonantList(word, g);
		
		t.cancel();

		this.sendKeepAliveTimer = new Timer() {
			
			public void run() {
				
				sendKeepAlive();
				
			}
		};
		
		this.sendKeepAliveTimer.scheduleRepeating(500);
		
		updateMcCoordsTimer = new Timer() {
			public void run() {
				updateMcCoords(4);
			}
		};
		
		updateMcCoordsTimer.scheduleRepeating(40);
		
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

	AsyncCallback<DoppelungGameState> keyEventCallback = new AsyncCallback<DoppelungGameState>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(DoppelungGameState result) {


		}

	};
	
	public void updateMcCoords(int speed) {
		
		DoppelungGameState g = (DoppelungGameState) this.openGame;
		
		for(ConsonantPoint2D cp : g.getMovingConsonantsCoords()){
			cp.setY(cp.getY() + 4);
		}
		
		this.updateGame(g);
		
	}
	
	public GameState updatePoints(String ids) {
		int playerid = Integer.parseInt(ids.split(":")[1]);
		String consonants = ids.split(":")[2];
		int mcid =  Integer.parseInt(ids.split(":")[3]);

		int points = 0;
		
		DoppelungGameState g = (DoppelungGameState) this.openGame;

		if (consonants.equals(g.getCurWord().getConsonantPair()))
			points = g.hasCorrectlyAnswered(playerid)?2:1;
		else
			points = -1;

		int newPoints = g.getPlayerPoints(playerid) + points;
		if (newPoints < 0)
			newPoints = 0;

		g.setPlayerPoints(playerid, newPoints);
//		g.getMovingConsonantsCoords().get(mcid).setRemoved(true);
		g.getMovingConsonantsCoords().get(mcid).setCollected(true, this.playerID);
		g.setServerSendTime(System.currentTimeMillis());
		
		return g;
	}

	public ArrayList<MovingConsonants> getMovingConsonantsList() {
		return movingConsonantsList;
	}

	public void setMovingConsonantsList(ArrayList<MovingConsonants> movingConsonantsList) {
		this.movingConsonantsList = movingConsonantsList;
	}
	
	private AsyncCallback<GameState> getMarkedMCCallback = new AsyncCallback<GameState>() {

		@Override
		public void onFailure(Throwable caught) {


		}

		@Override
		public void onSuccess(GameState result) {

			if (result != null) {

				if (pingTimes.containsKey(result.getPingId())) {
					latency = System.currentTimeMillis() -  pingTimes.get(result.getPingId());

					GWT.log("ping: " + Long.toString(latency) + "ms (average: " + averageLatency + "ms)");
				}

				if (result.getServerSendTime() >= lastServerSendTime) {

					lastServerSendTime = result.getServerSendTime();
					updateMarkedMC(result);


				}else{

					GWT.log("Received an outlived game state, ignoring...");

				}

				lastTenLatencies.add(latency);
				if (lastTenLatencies.size()>10) lastTenLatencies.poll();
				calcAverageLatency();
			}

		}

	};


	protected void updateMarkedMC(GameState result) {

		this.markCollectedMc((DoppelungGameState) result);
		
	}
	
}
