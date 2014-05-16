package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.GameTypeSelector;
import com.wicam.numberlineweb.client.Letris.AnimationTimer;
import com.wicam.numberlineweb.client.Letris.AnimationTimerTask;
import com.wicam.numberlineweb.client.Letris.LetrisGameModel.MovementDirection;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

/**
 * Coordinator of the LeTris game.
 * @author timfissler
 *
 */

public class LetrisGameCoordinator extends GameCoordinator {
	
	/**
	 * The controller of the LeTris game.
	 */
	protected LetrisGameController controller;
	/**
	 * The timer for movement animation.
	 */
	private AnimationTimer aniTimer = new AnimationTimer();
	/**
	 * The ratio of letters of a target word that don't belong to that word
	 * but are drawn from the outstanding words list.
	 */
	public static double STARTING_OUTSTANDING_LETTER_RATIO = 0.2;
	/**
	 * The ratio of all letter blocks of a target word that are not oriented correctly.
	 */
	public static double STARTING_ROTATED_LETTER_RATIO = 0.3;
	/**
	 * The time in milliseconds a block movement needs from one block to another.
	 */
	public static int STARTING_TIME_PER_BLOCK = 1000;
	/**
	 * The list of target words from that the current word is drawn randomly.
	 */
	private ArrayList<String> targetWords;
	/**
	 * The model of the LeTris game.
	 */
	private LetrisGameModel gameModel;

	public LetrisGameCoordinator(GameCommunicationServiceAsync commServ, ChatCommunicationServiceAsync chatServ,
			Panel root, GameTypeSelector gts) {
		super(commServ, chatServ, root,gts);
	}

	/**
	 * returns the name of the game
	 */
	@Override
	public String getGameName() {

		return "LeTris";

	}
	
	public LetrisGameState getGameState() {
		return (LetrisGameState) this.openGame;
	}


	@Override
	public void init() {
		// Get the target words from the server.
		LetrisGameCommunicationServiceAsync letrisCommServ = (LetrisGameCommunicationServiceAsync) commServ;
		letrisCommServ.getTargetWords(targetWordsCallback);
	}

	@Override
	protected void joinedGame(int playerID, int gameID) {
		super.joinedGame(playerID, gameID);
		this.playerID = playerID;

		//construct game
		createControllerAndView();
		LetrisGameView gameView =  (LetrisGameView) view;

		//construct an empty game-state with the given information
		LetrisGameState g = new LetrisGameState();
		g.setGameId(gameID);
		g.setState(-1);
		openGame = g;
		update();
		
		// Set up the game model.
//		gameModel = new LetrisGameModel(LetrisGameCoordinator.this,
//				STARTING_OUTSTANDING_LETTER_RATIO,
//				STARTING_ROTATED_LETTER_RATIO,
//				STARTING_TIME_PER_BLOCK, playerID);

		//clear the root panel and draw the game
		rootPanel.clear();
		rootPanel.add(gameView);

		if (numberOfPlayers > 1){
			addChatView();
		}
	}
	
	public ArrayList<String> getTargetWords() {
		return targetWords;
	}

	public void setTargetWords(ArrayList<String> targetWords) {
		this.targetWords = targetWords;
	}

	protected void createControllerAndView(){
		controller = new LetrisGameController(this);
		this.view = new LetrisGameView(numberOfPlayers, controller);
	}

	@Override
	protected void updateGame(GameState gameState) {
		super.updateGame(gameState);
		LetrisGameState g = (LetrisGameState) gameState;
		LetrisGameView gameView =  (LetrisGameView) view;
		//we already have the lates state
		if (g==null) return;


		switch (g.getState()) {
			// TODO Implement this.
			//started
		case 3:
			
			this.controller.setKeysEnabled(true);
			
//			updateViewIngame(g, gameView);
			
			gameModel.setupGameState();
//			gameModel.startMoving();
			
			GWT.log(((LetrisGameState)openGame).toString(playerID));

			break;

			//evaluation, who has won?
		case 5:
			
//			updateViewIngame(g, gameView);
				
			break;
			
			// for synchronization
		case 6:
			commServ.updateReadyness(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
			break;
		}

		openGame = g;

	}
	
	
	/**
	 * Sets user name in chat and sets points
	 * "Warte auf [other player name]" is displayed
	 * 
	 */
	@Override
	protected void handleAwaitingStartState(GameState g){
		LetrisGameView gameView =  (LetrisGameView) view;
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
		LetrisGameView gameView =  (LetrisGameView) view;
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
			// TODO Insert the method that updates the position of the current block.
//			updateMyPosition();
		}

	};


	private AnimationTimerTask moveLeftTask = new AnimationTimerTask() {

		@Override
		public void run() {
			((LetrisGameView)view).moveStepLeft(true);
			gameModel.moveLetterBlock(MovementDirection.LEFT);
			GWT.log("Move left");
		}

	};

	private AnimationTimerTask moveRightTask = new AnimationTimerTask() {

		@Override
		public void run() {
			((LetrisGameView)view).moveStepRight(true);
			gameModel.moveLetterBlock(MovementDirection.RIGHT);
			GWT.log("Move right");
		}

	};


	private AnimationTimerTask moveDownTask = new AnimationTimerTask() {

		@Override
		public void run() {
			((LetrisGameView)view).moveStepDown(true);
			gameModel.moveLetterBlock(MovementDirection.DOWN);
			GWT.log("Move down");
		}

	};

	private AnimationTimerTask dropTask = new AnimationTimerTask() {

		@Override
		public void run() {
			((LetrisGameView)view).moveStepDown(true);
			gameModel.moveLetterBlock(MovementDirection.DOWN);
			GWT.log("Drop");
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
	private boolean keySpaceDown = false;


	public void moveBlock(boolean up, int key){


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
					// TODO Implement rotation here.
//					registerAniTask(moveUpTask);
				}

				break;
			case 4:
				if (!keyLeftDown) {
					keyLeftDown = true;

					registerAniTask(moveLeftTask);
				}

				break;
			case 5:
				// TODO dropTask.markForDelete(); after block touched ground in GameModel.
				if (!keySpaceDown) {
					keySpaceDown = true;

					registerAniTask(dropTask);
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
					// TODO Implement rotation here.
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
	
	/**
	 * Sets a moving letter block to a new position
	 * 
	 * @param letterBlock	the moving letter block
	 */
	public void updateMovingLetterBlock(LetrisGameLetterBlock letterBlock){
		((LetrisGameView) view).updateMovingLetterBlock(letterBlock);
	}
	
	/**
	 * Compute the current number of points according to the given
	 * word and send them to the view for being displayed. Also
	 * show a kind of popup that again displays the correct word.
	 * @param correctLetters number of correct letters
	 */
	public void foundCorrectWord(String foundWord) {
		// TODO Implement this.
	}


	public void endGame() {

		moveLeftTask.markForDelete();
		moveRightTask.markForDelete();
		// TODO Implement rotation here.
		moveDownTask.markForDelete();
		updatePositionTask.markForDelete();
		dropTask.markForDelete();

		keyUpDown = false;
		keyDownDown = false;
		keyLeftDown = false;
		keyRightDown = false;
		keySpaceDown = false;

	}

	public void startButtonClicked(){
		if (!openGame.isPlayerReady(this.playerID)) {
			commServ.updateReadyness(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
		}
	}
	

	AsyncCallback<LetrisGameState> keyEventCallback = new AsyncCallback<LetrisGameState>() {

		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
		}

		@Override
		public void onSuccess(LetrisGameState result) {


		}

	};
	
	AsyncCallback<ArrayList<String>> targetWordsCallback = new AsyncCallback<ArrayList<String>>() {
		
		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
		}
		
		@Override
		public void onSuccess(ArrayList<String> targetWords) {
			setTargetWords(targetWords);
			
			// Initialize the game model.
			gameModel = new LetrisGameModel(LetrisGameCoordinator.this,
					STARTING_OUTSTANDING_LETTER_RATIO,
					STARTING_ROTATED_LETTER_RATIO,
					STARTING_TIME_PER_BLOCK, playerID);
			
			// Set up the game selector.
			gameSelector = new LetrisGameSelector(LetrisGameCoordinator.this);
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
			
			GWT.log("LeTris game coordinator loaded.");
		}
		
	};
}
