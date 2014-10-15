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
import com.wicam.numberlineweb.client.Letris.LetrisGameModel.RotationDirection;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

/**
 * Coordinator of the LeTris game.
 * @author timfissler
 *
 */
// TODO Check pseudo random target words. Or directly switch to single item retrieval from server.
// TODO Add letter block 'ghost' to display the horizontal position of the current block
// on the bottom of the playground.
// TODO Add multiplayer support.
/*
 *  TODO Add AI for NPC player.
 *  1. Estimate the target position of the current block with error probability x (e.g. 0.02).
 *  2. Move the current block to this position with time delay per movement.
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
     * but are drawn from the alphabet.
     */
    public static double STARTING_DISTRACTOR_LETTER_RATIO = 0.3;
	/**
	 * The ratio of all letter blocks of a target word that are not oriented correctly.
	 */
	public static double STARTING_ROTATED_LETTER_RATIO = 0;//0.3;
	/**
	 * The time in milliseconds a block movement needs from one block to another.
	 */
	public static int STARTING_TIME_PER_BLOCK = 1000;
	/**
	 * The list of target words from that the current word is drawn randomly.
	 */
	private ArrayList<VowelGameWord> targetWords;
	/**
	 * The model of the LeTris game.
	 */
	private LetrisGameModel gameModel;
	/**
	 * Indicates whether the game is paused or not.
	 */
	private boolean gamePaused;

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
	
	/**
	 * True, if the given letter block currently is drawn.
	 * @param letterBlock
	 * @return
	 */
	public boolean isDrawn(LetrisGameLetterBlock letterBlock) {
		LetrisGameView gameView = (LetrisGameView)view;
		return gameView.isDrawn(letterBlock);
	}


	@Override
	public void init() {
		gamePaused = false;
		
		// Setup animation tasks with delayed continuous running.
		int delay = 500;
		moveLeftTask.setDelayForContinuousRunning(delay);
		moveRightTask.setDelayForContinuousRunning(delay);
		moveDownTask.setDelayForContinuousRunning(delay);
		rotateTask.setDelayForContinuousRunning(delay);
		dropTask.setDelayForContinuousRunning(delay);
		
		// Get the target words from the server.
		LetrisGameCommunicationServiceAsync letrisCommServ = (LetrisGameCommunicationServiceAsync) commServ;
		letrisCommServ.getTargetWords(targetWordsCallback);
	}
	
	/**
	 * Set the target word that should be built by the player in the view.
	 */
	public void updateTargetWord() {
		// Update only if there is still a view.
		if (view != null) {
			LetrisGameView gameView = (LetrisGameView) view;
			gameView.updateTargetWord(getGameState().getCurrentWord());
		}
	}
	
	/**
	 * Set the next letter block that will be dropped after the current one.
	 * @param nextLetterBlock	the letter block to be shown
	 */
	public void updateNextBlock(LetrisGameLetterBlock nextLetterBlock) {
		// Update only if there is still a view.
		if (view != null) {
			LetrisGameView gameView = (LetrisGameView) view;
			gameView.updateNextBlock(nextLetterBlock);
		}
	}
	
	/**
	 * Update the given letter block in the view.
	 * @param letterBlock
	 */
	public void updateLetterBlock(LetrisGameLetterBlock letterBlock) {
		LetrisGameView gameView = (LetrisGameView) view;
		gameView.updateLetterBlock(letterBlock);
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

		//clear the root panel and draw the game
		rootPanel.clear();
		rootPanel.add(gameView);

		if (numberOfPlayers > 1){
			addChatView();
		}
	}
	
	public ArrayList<VowelGameWord> getTargetWords() {
		return targetWords;
	}

	public void setTargetWords(ArrayList<VowelGameWord> targetWords) {
		this.targetWords = targetWords;
	}

	protected void createControllerAndView(){
		controller = new LetrisGameController(this);
		this.view = new LetrisGameView(numberOfPlayers, controller, gameModel.getPlaygroundWidth(), gameModel.getPlaygroundHeight());
	}
	
	public LetrisGameModel getGameModel() {
		return gameModel;
	}
	
	/**
	 * Pushes the current game state to the server where it is
	 * used for updating the old game state.
	 */
	public void pushGameStateToServer() {
		// TODO Implement this with an RPC call.
	}
	
	/**
	 * Redraw the playground in the game view according to the current
	 * game state.
	 */
	public void updatePlaygroundInView() {
		// Update only if there is still a view.
		if (view != null) {
			LetrisGameView gameView = (LetrisGameView) view;
			gameView.updatePlayground((LetrisGameState) openGame);
		}
	}
	
	/**
	 * Redraw the preview of the opponent's playground in the
	 * game view according to the given game state.
	 */
	public void updatePreviewInView() {
		LetrisGameView gameView = (LetrisGameView) view;
		// TODO Implement this by using one of the open game states?
//		gameView.updatePreview(gameState);
	}
	
	/**
	 * Update the points from the game state in the view.
	 * @param points
	 */
	public void updatePoints() {
		LetrisGameView gameView = (LetrisGameView) view;
		for (int i = 0; i < getGameState().getPlayers().size(); i++){
			gameView.updatePoints(i+1,getGameState().getPlayerPoints(i+1),getGameState().getPlayerName(i+1));
		}
	}

	@Override
	protected void updateGame(GameState gameState) {
		super.updateGame(gameState);
		LetrisGameState g = (LetrisGameState) gameState;
		LetrisGameView gameView =  (LetrisGameView) view;
		
		//we already have the latest state
		if (g==null) return;
		
		switch (g.getState()) {
			//started
		case 3:
			this.controller.setKeysEnabled(true);
			gameModel.startMoving();		
			gameView.showLetrisGame();
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
			gameView.updatePoints(i+1,g.getPlayerPoints(i+1),g.getPlayerName(i+1));
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
			gameView.updatePoints(i+1,g.getPlayerPoints(i+1),g.getPlayerName(i+1));
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
	 * @param t		the taskText to register
	 */
	public void registerAniTask(AnimationTimerTask t) {
		aniTimer.registerTask(t);
	}

	/*
	 * Animation tasks will later be registered in the animation timer.
	 * a taskText exists for every direction the player can move.
	 */
	
	/**
	 * Timer taskText for moving the current moving letter block to the left.
	 */
	private AnimationTimerTask moveLeftTask = new AnimationTimerTask() {

		@Override
		public void run() {
			gameModel.moveLetterBlock(MovementDirection.LEFT);
			GWT.log("Move left");
		}

	};
	
	/**
	 * Timer taskText for moving the current moving letter block to the right.
	 */
	private AnimationTimerTask moveRightTask = new AnimationTimerTask() {

		@Override
		public void run() {
			gameModel.moveLetterBlock(MovementDirection.RIGHT);
			GWT.log("Move right");
		}

	};

	/**
	 * Timer taskText for moving the current moving letter block downwards.
	 */
	private AnimationTimerTask moveDownTask = new AnimationTimerTask() {

		@Override
		public void run() {
			gameModel.moveLetterBlock(MovementDirection.DOWN);
			GWT.log("Move down");
		}

	};
	
	/**
	 * Timer taskText for rotating the current moving letter block anticlockwise.
	 */
	private AnimationTimerTask rotateTask = new AnimationTimerTask() {

		@Override
		public void run() {
			gameModel.rotateLetterBlock(RotationDirection.ANTICLOCKWISE);
			GWT.log("Rotate left");
		}

	};

	/**
	 * Timer taskText for dropping the current moving letter block at once.
	 */
	private AnimationTimerTask dropTask = new AnimationTimerTask() {

		@Override
		public void run() {
			gameModel.dropLetterBlock();
			GWT.log("Drop");
		}

	};

	// We only want a click to be registered ONCE.
	private boolean keyUpDown = false;
	private boolean keyDownDown = false;
	private boolean keyLeftDown = false;
	private boolean keyRightDown = false;
	private boolean keySpaceDown = false;
	private boolean keyWDown = false;
	private boolean keyPDown = false;

	/**
	 * Takes key actions from the controller and fires movement
	 * or rotation commands in the model. Additionally
	 * handles repetition of the current word and toggling of
	 * pause mode. 
	 * @param up true, if the key went up; false, if the key went down
	 * @param key number representing the key
	 */
	public void handleKeyStroke(boolean up, int key){
		// If key went down ...
		if (!up) {
			switch(key){
			// Down key
			case 1:
				if (!keyDownDown) {
					keyDownDown = true;
					if (!gamePaused) {
						registerAniTask(moveDownTask);
					}
				}
				break;
			// Right key
			case 3:
				if (!keyRightDown) {
					keyRightDown = true;
					if (!gamePaused) {
						registerAniTask(moveRightTask);
					}
				}
				break;
			// Up key
			case 2:
				if (!keyUpDown) {
					keyUpDown = true;
					if (!gamePaused) {
						registerAniTask(rotateTask);
					}
				}
				break;
			// Left Key
			case 4:
				if (!keyLeftDown) {
					keyLeftDown = true;
					if (!gamePaused) {
						registerAniTask(moveLeftTask);
					}
				}
				break;
			// Space
			case 5:
				if (!keySpaceDown) {
					keySpaceDown = true;
					if (!gamePaused) {
						registerAniTask(dropTask);
					}
				}
				break;
			// W
			case 6:
				if (!keyWDown) {
					keyWDown = true;
					if (!gamePaused) {
						LetrisGameView gameView = (LetrisGameView) view;
						gameView.repeatTargetWord();
					}
				}
				break;
			// P
			case 7:
				if (!keyPDown) {
					keyPDown = true;
					togglePauseMode();
				}
				break;
			}
		}
		// If key went up ...
		else {
			switch(key){
			// Key down
			case 1:
				if (keyDownDown) {
					keyDownDown = false;
					if (!gamePaused) {
						moveDownTask.markForDelete();
					}
				}
				break;
			// Key right
			case 3:
				if (keyRightDown) {
					keyRightDown = false;
					if (!gamePaused) {
						moveRightTask.markForDelete();
					}
				}
				break;
			// Key up
			case 2:
				if (keyUpDown) {
					keyUpDown = false;
					if (!gamePaused) {
						rotateTask.markForDelete();
					}
				}
				break;
			// Key left
			case 4:
				if (keyLeftDown) {
					keyLeftDown = false;
					if (!gamePaused) {
						moveLeftTask.markForDelete();
					}
				}
				break;
			// Space
			case 5:
				if (keySpaceDown) {
					keySpaceDown = false;
					if (!gamePaused) {
						dropTask.markForDelete();
					}
				}
				break;
			// W
			case 6:
				if (keyWDown) {
					keyWDown = false;
				}
				break;
			// P
			case 7:
				if (keyPDown) {
					keyPDown = false;
				}
				break;
			}
		}
	}
	
	protected void restartGame() {
		endGame();
		init();
	}

	/**
	 * Remove the timer tasks when the game should be ended.
	 */
	public void endGame() {		
		gameModel.stopMoving();
		
		moveLeftTask.markForDelete();
		moveRightTask.markForDelete();
		rotateTask.markForDelete();
		moveDownTask.markForDelete();
		dropTask.markForDelete();

		keyUpDown = false;
		keyDownDown = false;
		keyLeftDown = false;
		keyRightDown = false;
		keySpaceDown = false;
		keyWDown = false;
		keyPDown = false;
	}
	
	/**
	 * Stop the game or start it again and implement
	 * a pause mode this way.
	 */
	private void togglePauseMode() {
		if (gamePaused) {
			
			// Start the game.
			gamePaused = !gamePaused;
			
			LetrisGameView gameView = (LetrisGameView) view;
			gameView.hidePauseMessage();
			
			gameModel.startMoving();
			
		} else {
			
			// Stop the game.
			gamePaused = !gamePaused;
			
			GWT.log("Game paused.");
			
			LetrisGameView gameView = (LetrisGameView) view;
			gameView.showPauseMessage();
			
			gameModel.stopMoving();
			
			moveLeftTask.markForDelete();
			moveRightTask.markForDelete();
			rotateTask.markForDelete();
			moveDownTask.markForDelete();
			dropTask.markForDelete();

			keyUpDown = false;
			keyDownDown = false;
			keyLeftDown = false;
			keyRightDown = false;
			keySpaceDown = false;
			keyWDown = false;
			keyPDown = false;
		}
	}

	public void startButtonClicked(){
		if (!openGame.isPlayerReady(this.playerID)) {
			commServ.updateReadyness(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
		}
	}
	
	/**
	 * What is to be done when the list of target words has been retrieved from the server.
	 */
	AsyncCallback<ArrayList<VowelGameWord>> targetWordsCallback = new AsyncCallback<ArrayList<VowelGameWord>>() {
		
		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
		}
		
		@Override
		public void onSuccess(ArrayList<VowelGameWord> targetWords) {
			setTargetWords(targetWords);
			
			// Initialize the game model.
			gameModel = new LetrisGameModel(LetrisGameCoordinator.this,
					STARTING_ROTATED_LETTER_RATIO,
					STARTING_DISTRACTOR_LETTER_RATIO,
					STARTING_TIME_PER_BLOCK);
			
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
