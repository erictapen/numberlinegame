package com.wicam.numberlineweb.client.LetrisPush;

import java.util.ArrayList;

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
import com.wicam.numberlineweb.client.LetrisPush.LetrisPushGameModel.MovementDirection;
import com.wicam.numberlineweb.client.LetrisPush.LetrisPushGameModel.RotationDirection;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

import de.novanic.eventservice.client.ClientHandler;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.domain.DomainFactory;

/**
 * Coordinator of the LeTris game.
 * 
 * @author timfissler
 * 
 */
// TODO Check pseudo random target words. Or directly switch to single item
// retrieval from server.
// TODO Add letter block 'ghost' to display the horizontal position of the
// current block
// on the bottom of the playground.
// TODO Add multiplayer support.
/*
 * TODO Problems to start two games in parallel. Why? Perhaps delete all polling
 * things and start with a blank version of the game communication?
 * TODO Locate the timed update via the client and delete it. TODO Implement
 * updates of one client if there is pushed some piece of information on the
 * server by another client. TODO Modify server update every time when
 * updateViewAndServer() is called. TODO Define events and listener (interfaces)
 * for multiplayer operations. TODO What happens in multiplayer games when one
 * player is game over before the other one? TODO Filler rows are not updated.
 * TODO Update server when points are updated but no filler row is deleted. TODO
 * Only send target updates if game consists of more than one player. TODO
 * Change pause functionality so that in case of two players the pause gets
 * activated for the other player too.
 */

/*
 * TODO Add AI for NPC player. 1. Estimate the target position of the current
 * block with error probability x (e.g. 0.02). 2. Move the current block to this
 * position with time delay per movement.
 */

public class LetrisPushGameCoordinator extends GameCoordinator {

	/**
	 * The controller of the LeTris game.
	 */
	protected LetrisPushGameController controller;
	/**
	 * The timer for movement animation.
	 */
	private LetrisPushGameAnimationTimer aniTimer = new LetrisPushGameAnimationTimer();
	/**
	 * The ratio of letters of a target word that don't belong to that word but
	 * are drawn from the alphabet.
	 */
	public static double STARTING_DISTRACTOR_LETTER_RATIO = 0.3;
	/**
	 * The ratio of all letter blocks of a target word that are not oriented
	 * correctly.
	 */
	public static double STARTING_ROTATED_LETTER_RATIO = 0;// 0.3;
	/**
	 * The time in milliseconds a block movement needs from one block to
	 * another.
	 */
	public static int STARTING_TIME_PER_BLOCK = 1000;
	/**
	 * The list of target words from that the current word is drawn randomly.
	 */
	private ArrayList<VowelGameWord> targetWords;
	/**
	 * The model of the LeTris game.
	 */
	private LetrisPushGameModel gameModel;
	/**
	 * Indicates whether the game is paused or not.
	 */
	private boolean gamePaused;
	/**
	 * The user-specific connection ID for user-specific GWTEventService events.
	 */
	private String connectionID;
	/**
	 * Event listener for retrieving user-specific events from the server
	 * (server push).
	 */
	private LetrisPushGameUserSpecificListener eventListener;
	/**
	 * Event service for retrieving user-specific events from the server (server
	 * push).
	 */
	private RemoteEventService remoteEventService;

	public LetrisPushGameCoordinator(GameCommunicationServiceAsync commServ,
			ChatCommunicationServiceAsync chatServ, Panel root,
			GameTypeSelector gts) {
		super(commServ, chatServ, root, gts);
	}

	/**
	 * returns the name of the game
	 */
	@Override
	public String getGameName() {
		return "LeTris";
	}

	public LetrisPushGameState getGameState() {
		return (LetrisPushGameState) this.openGame;
	}

	/**
	 * True, if the given letter block currently is visible on the playground.
	 * 
	 * @param letterBlock
	 * @return
	 */
	public boolean isVisibleOnPlayground(LetrisPushGameLetterBlock letterBlock) {
		LetrisPushGameView gameView = (LetrisPushGameView) view;
		return gameView.isVisibleOnPlayground(letterBlock);
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

		RemoteEventServiceFactory remoteEventServiceFactory = RemoteEventServiceFactory
				.getInstance();

		// Get the remote event service.
		remoteEventService = remoteEventServiceFactory.getRemoteEventService();

		// Setup the event listener.
		eventListener = new LetrisPushGameUserSpecificListener(this);

		// Retrieve the client handler with the connection id.
		remoteEventServiceFactory
				.requestClientHandler(new AsyncCallback<ClientHandler>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(ClientHandler result) {

						// Store connection id.
						connectionID = result.getConnectionId();

						// Get the target words from the server.
						LetrisPushGameCommunicationServiceAsync letrisCommServ = (LetrisPushGameCommunicationServiceAsync) commServ;
						letrisCommServ.getTargetWords(targetWordsCallback);
					}
				});
	}

	/**
	 * Set the target word that should be built by the player in the view.
	 */
	public void updateTargetWord() {
		// Update only if there is still a view.
		if (view != null) {
			LetrisPushGameView gameView = (LetrisPushGameView) view;
			gameView.updateTargetWord(getGameModel().getCurrentWord());
		}
	}

	/**
	 * Set the next letter block that will be dropped after the current one.
	 * 
	 * @param nextLetterBlock
	 *            the letter block to be shown
	 */
	public void updateNextBlock(LetrisPushGameLetterBlock nextLetterBlock) {
		// Update only if there is still a view.
		if (view != null) {
			LetrisPushGameView gameView = (LetrisPushGameView) view;
			gameView.updateNextBlock(nextLetterBlock);
		}
	}

	/**
	 * Update the given letter block in the view.
	 * 
	 * @param letterBlock
	 */
	public void updateLetterBlock(LetrisPushGameLetterBlock letterBlock) {
		LetrisPushGameView gameView = (LetrisPushGameView) view;
		gameView.updateLetterBlock(letterBlock);
	}

	/**
	 * Send the current playground state and the difference for the opponent
	 * player's filler rows level to the server.
	 * 
	 * @param opponentFillerRowsLevelDiff
	 */
	public void sendTargetUpdate(int opponentFillerRowsLevelDiff) {

		// Build playground state.
		LetrisPushGamePlaygroundState playgroundState = new LetrisPushGamePlaygroundState();
		playgroundState.setPlayerID(playerID);
		playgroundState.setOpponentPlayerID(getOpponentPlayerID());
		playgroundState.setPoints(getGameState().getPlayerPoints(playerID));
		playgroundState
				.setStaticLetterBlocks(gameModel.getStaticLetterBlocks());
		playgroundState.setFillerRowsLevelDiff(opponentFillerRowsLevelDiff);

		// Send it to the server.
		LetrisPushGameCommunicationServiceAsync letrisCommServ = (LetrisPushGameCommunicationServiceAsync) commServ;
		letrisCommServ.sendTargetUpdate(playgroundState, voidCallback);
	}

	/**
	 * Retrieve the player ID of the opponent player from the game state.
	 * 
	 * @return opponent player id
	 */
	private int getOpponentPlayerID() {
		/*
		 * The game consists of max 2 players so the one that hasn't the player
		 * id of this player is the opponent.
		 */
		int opponentPlayerID = 0;
		if (playerID == 1) {
			opponentPlayerID = 2;
		} else if (playerID == 2) {
			opponentPlayerID = 1;
		}
		return opponentPlayerID;
	}

	/**
	 * join game #id with username 'name'
	 * 
	 * @param id
	 * @param name
	 */

	public void joinGame(int id, String name, int numberOfPlayers,
			int numberOfNPCs) {
		this.numberOfPlayers = numberOfPlayers;
		this.numberOfNPCs = numberOfNPCs;

		// we dont want anonymous players
		if (name.equals(""))
			name = "Spieler";

		name = escapeString(name);

		// Register user-specific event listener.
		remoteEventService.addListener(DomainFactory.USER_SPECIFIC_DOMAIN,
				eventListener);

		((LetrisPushGameCommunicationServiceAsync) commServ).joinGame(
				Integer.toString(id) + ":" + name, connectionID,
				gameJoinedCallback);

	}

	/**
	 * join game #id with username 'name' for games without NPC
	 * 
	 * @param id
	 * @param name
	 */

	public void joinGame(int id, String name, int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
		this.numberOfNPCs = 0;

		// we dont want anonymous players
		if (name.equals(""))
			name = "Spieler";

		name = escapeString(name);

		((LetrisPushGameCommunicationServiceAsync) commServ).joinGame(
				Integer.toString(id) + ":" + name, connectionID,
				gameJoinedCallback);

	}

	@Override
	protected void joinedGame(int playerID, int gameID) {
		super.joinedGame(playerID, gameID);
		this.playerID = playerID;

		// construct game
		createControllerAndView();
		LetrisPushGameView gameView = (LetrisPushGameView) view;

		// construct an empty game-state with the given information
		LetrisPushGameState g = new LetrisPushGameState();
		g.setGameId(gameID);
		g.setState(-1);
		openGame = g;
		update();

		// clear the root panel and draw the game
		rootPanel.clear();
		rootPanel.add(gameView);

		if (numberOfPlayers > 1) {
			addChatView();
		}
	}

	public ArrayList<VowelGameWord> getTargetWords() {
		return targetWords;
	}

	public void setTargetWords(ArrayList<VowelGameWord> targetWords) {
		this.targetWords = targetWords;
	}

	protected void createControllerAndView() {
		controller = new LetrisPushGameController(this);
		this.view = new LetrisPushGameView(numberOfPlayers, controller,
				gameModel.getPlaygroundWidth(), gameModel.getPlaygroundHeight());
	}

	public LetrisPushGameModel getGameModel() {
		return gameModel;
	}

	/**
	 * Redraw the playground in the game view according to the current game
	 * state.
	 */
	public void updatePlaygroundInView() {
		// Update only if there is still a view.
		if (view != null) {
			LetrisPushGameView gameView = (LetrisPushGameView) view;
			gameView.updatePlayground(gameModel);
		}
	}

	/**
	 * Update the preview of the opponent playground and the opponent points.
	 * 
	 * @param opponentPlaygroundState
	 */
	public void updateFromOpponent(
			LetrisPushGamePlaygroundState opponentPlaygroundState) {

		// Update player points of opponent player.
		int playerID = opponentPlaygroundState.getPlayerID();
		int points = opponentPlaygroundState.getPoints();
		getGameState().setPlayerPoints(playerID, points);

		// Update filler rows.
		gameModel.updateFillerRows(opponentPlaygroundState
				.getFillerRowsLevelDiff());

		// Update points in view.
		updatePoints();

		// Update preview of opponent playground preview.
		updatePreviewInView(opponentPlaygroundState.getStaticLetterBlocks());
	}

	/**
	 * Redraw the preview of the opponent's playground in the game view
	 * according to the given game state.
	 */
	public void updatePreviewInView(
			ArrayList<LetrisPushGameLetterBlock> staticLetterBlocks) {
		LetrisPushGameView gameView = (LetrisPushGameView) view;
		gameView.updatePreview(staticLetterBlocks);
	}

	/**
	 * Update the points from the game state in the view.
	 * 
	 * @param points
	 */
	public void updatePoints() {
		LetrisPushGameView gameView = (LetrisPushGameView) view;
		for (int i = 0; i < getGameState().getPlayers().size(); i++) {
			gameView.updatePoints(i + 1, getGameState().getPlayerPoints(i + 1),
					getGameState().getPlayerName(i + 1));
		}
	}

	@Override
	protected void updateGame(GameState gameState) {
		super.updateGame(gameState);
		LetrisPushGameState g = (LetrisPushGameState) gameState;
		LetrisPushGameView gameView = (LetrisPushGameView) view;

		// we already have the latest state
		if (g == null)
			return;

		switch (g.getState()) {
		// started
		case 3:
			this.controller.setKeysEnabled(true);
			gameModel.startMoving();
			gameView.showLetrisGame();
			break;
		// for synchronization
		case 6:
			commServ.updateReadyness(Integer.toString(openGame.getId()) + ":"
					+ Integer.toString(playerID), dummyCallback);
			break;
		}
		openGame = g;
	}

	/**
	 * Sets user name in chat and sets points "Warte auf [other player name]" is
	 * displayed
	 * 
	 */
	@Override
	protected void handleAwaitingStartState(GameState g) {
		LetrisPushGameView gameView = (LetrisPushGameView) view;
		if (this.numberOfPlayers > 1)
			chatC.setUserName(g.getPlayerName(this.playerID));
		for (int i = 0; i < g.getPlayers().size(); i++) {
			gameView.updatePoints(i + 1, g.getPlayerPoints(i + 1),
					g.getPlayerName(i + 1));
		}
		if (g.isPlayerReady(this.playerID)) {
			// other player ready ?

			gameView.clearGamePanel();
			gameView.showWaitingForOtherPlayer("Warte auf "
					+ g.getPlayerName(playerID % 2 + 1) + "!");

		}
		setRefreshRate(1000);
	}

	/**
	 * the refresh set is increased since the description is displayed
	 */
	@Override
	protected void handleWaitingForPlayersState() {
		setRefreshRate(2000);
	}

	/**
	 * points are displayed and "Warte auf zweiten Spieler..." is shown
	 */
	@Override
	protected void handleWaitingForOtherPlayersState(GameState g) {
		LetrisPushGameView gameView = (LetrisPushGameView) view;
		setRefreshRate(2000);
		for (int i = 0; i < g.getPlayers().size(); i++) {
			gameView.updatePoints(i + 1, g.getPlayerPoints(i + 1),
					g.getPlayerName(i + 1));
		}
		if (g.isPlayerReady(this.playerID)) {
			// other player ready ?

			gameView.clearGamePanel();
			gameView.showWaitingForOtherPlayer("Warte auf zweiten Spieler...");

		}
	}

	/**
	 * Registers a new AnimationTimerTask
	 * 
	 * @param t
	 *            the taskText to register
	 */
	public void registerAniTask(LetrisPushGameAnimationTimerTask t) {
		aniTimer.registerTask(t);
	}

	/*
	 * Animation tasks will later be registered in the animation timer. a
	 * taskText exists for every direction the player can move.
	 */

	/**
	 * Timer taskText for moving the current moving letter block to the left.
	 */
	private LetrisPushGameAnimationTimerTask moveLeftTask = new LetrisPushGameAnimationTimerTask() {

		@Override
		public void run() {
			gameModel.moveLetterBlock(MovementDirection.LEFT);
			GWT.log("Move left");
		}

	};

	/**
	 * Timer taskText for moving the current moving letter block to the right.
	 */
	private LetrisPushGameAnimationTimerTask moveRightTask = new LetrisPushGameAnimationTimerTask() {

		@Override
		public void run() {
			gameModel.moveLetterBlock(MovementDirection.RIGHT);
			GWT.log("Move right");
		}

	};

	/**
	 * Timer taskText for moving the current moving letter block downwards.
	 */
	private LetrisPushGameAnimationTimerTask moveDownTask = new LetrisPushGameAnimationTimerTask() {

		@Override
		public void run() {
			gameModel.moveLetterBlock(MovementDirection.DOWN);
			GWT.log("Move down");
		}

	};

	/**
	 * Timer taskText for rotating the current moving letter block anti
	 * clockwise.
	 */
	private LetrisPushGameAnimationTimerTask rotateTask = new LetrisPushGameAnimationTimerTask() {

		@Override
		public void run() {
			gameModel.rotateLetterBlock(RotationDirection.ANTICLOCKWISE);
			GWT.log("Rotate left");
		}

	};

	/**
	 * Timer taskText for dropping the current moving letter block at once.
	 */
	private LetrisPushGameAnimationTimerTask dropTask = new LetrisPushGameAnimationTimerTask() {

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
	 * Takes key actions from the controller and fires movement or rotation
	 * commands in the model. Additionally handles repetition of the current
	 * word and toggling of pause mode.
	 * 
	 * @param up
	 *            true, if the key went up; false, if the key went down
	 * @param key
	 *            number representing the key
	 */
	public void handleKeyStroke(boolean up, int key) {
		// If key went down ...
		if (!up) {
			switch (key) {
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
						LetrisPushGameView gameView = (LetrisPushGameView) view;
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
			switch (key) {
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

		// Unregister event listener.
		remoteEventService.removeListener(DomainFactory.USER_SPECIFIC_DOMAIN,
				eventListener);
	}

	/**
	 * Stop the game or start it again and implement a pause mode this way. Send
	 * the pause toggle action to the server so that the other player - in case
	 * there is one - gets paused too.
	 */
	private void togglePauseMode() {
		if (gamePaused) {
			// Send the pause command to the server.
			LetrisPushGameCommunicationServiceAsync letrisCommServ = (LetrisPushGameCommunicationServiceAsync) commServ;
			letrisCommServ.pauseAllPlayers(getGameState(), voidCallback);
		} else {
			// Send the unpause command to the server.
			LetrisPushGameCommunicationServiceAsync letrisCommServ = (LetrisPushGameCommunicationServiceAsync) commServ;
			letrisCommServ.unpauseAllPlayers(getGameState(), voidCallback);
		}
	}

	/**
	 * Gets called in case of a pause event.
	 */
	public void pauseGame() {
		// Stop the game.
		gamePaused = !gamePaused;

		GWT.log("Game paused.");

		LetrisPushGameView gameView = (LetrisPushGameView) view;
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

	/**
	 * Gets called in case of an unpause event.
	 */
	public void unpauseGame() {
		// Start the game.
		gamePaused = !gamePaused;

		LetrisPushGameView gameView = (LetrisPushGameView) view;
		gameView.hidePauseMessage();

		gameModel.startMoving();
	}

	public void startButtonClicked() {
		if (!openGame.isPlayerReady(this.playerID)) {
			commServ.updateReadyness(Integer.toString(openGame.getId()) + ":"
					+ Integer.toString(playerID), dummyCallback);
		}
	}

	/**
	 * What is to be done when the list of target words has been retrieved from
	 * the server.
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
			gameModel = new LetrisPushGameModel(LetrisPushGameCoordinator.this,
					STARTING_ROTATED_LETTER_RATIO,
					STARTING_DISTRACTOR_LETTER_RATIO, STARTING_TIME_PER_BLOCK);

			// Set up the game selector.
			gameSelector = new LetrisPushGameSelector(
					LetrisPushGameCoordinator.this);
			rootPanel.add(gameSelector);

			t = new Timer() {
				@Override
				public void run() {
					update();
				}
			};

			// main loop-timer
			t.scheduleRepeating(500);
			refreshGameList();

			GWT.log("LeTris game coordinator loaded.");
		}

	};

	AsyncCallback<Void> voidCallback = new AsyncCallback<Void>() {

		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
		}

		@Override
		public void onSuccess(Void result) {
			// Do nothing.
		}
	};
}
