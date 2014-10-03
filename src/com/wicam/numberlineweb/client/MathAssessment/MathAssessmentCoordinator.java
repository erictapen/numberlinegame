package com.wicam.numberlineweb.client.MathAssessment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.GameTypeSelector;
import com.wicam.numberlineweb.client.NumberLineWeb;
import com.wicam.numberlineweb.client.Player;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

public class MathAssessmentCoordinator extends GameCoordinator {
	
	/* 
	 * TODO Add functionality for handling the trial rounds and the (time dependent) switching between
	 * the three different views.
	 * TODO Add method for retrieval of the item list.
	 */	
	
	protected MathAssessmentController controller;
	
	
	public MathAssessmentCoordinator(GameCommunicationServiceAsync commServ,
			ChatCommunicationServiceAsync chatCommServ, 
			Panel root, GameTypeSelector gts) {
		super(commServ, chatCommServ, root,gts);
	}
	
	@Override
	public String getGameName() {

		return "Multiplikation";

	}
	
	
	/**
	 * Initializes the coordinator
	 */
	@Override
	public void init() {

//		gameSelector = new MultiplicationGameSelector(this);
//		rootPanel.add(gameSelector);

		// TODO Change this so that a regular update is not needed anymore.
		t = new Timer() {
			@Override
			public void run() {
				update();
			}
		};

		//main loop-timer
		t.scheduleRepeating(500);
		refreshGameList();

		GWT.log("math assessment coordinator loaded.");
	}
	
	
	/**
	 * Is being called when the user has entered a result to the current task.
	 * @param answer
	 * @param timestamp
	 */
	public void userAnswered(double answer, long timestamp) {
		// TODO Implement this.
	}
	
	/**
	 * Open a game name 'name'. Call back will get state of opened game
	 * @param name
	 */
	@Override
	public void openGame(GameState gameState) {

		this.numberOfPlayers = gameState.getMaxNumberOfPlayers();
		this.numberOfNPCs = gameState.getNumberOfMaxNPCs();
		
		gameState.setGameOpenedUserId(NumberLineWeb.USERID);
		
		((MultiplicationGameCommunicationServiceAsync)commServ).openGame(gameState, gameOpenedCallBack);

	}
	
	
	
	/**
	 * Called after our player joined the game.
	 * @param playerID
	 * @param gameID
	 */
	@Override
	protected void joinedGame(int playerID, int gameID) {

		super.joinedGame(playerID, gameID);
		this.playerID = playerID;

		//construct game
		controller = new MathAssessmentController(this);
		
		this.view = new MathAssessmentView(controller);
		
		MathAssessmentView gameView = (MathAssessmentView) view;

		//construct an empty game-state with the given information
		MathAssessmentState g = new MathAssessmentState();
		g.setGameId(gameID);
		g.setState(-1);
		this.openGame = g;
		update();

		//clear the root panel and draw the game
		rootPanel.clear();
		rootPanel.add(gameView);
	}

	
	
	/**
	 * Called after game state was received.
	 * @param gameState The GameState to update
	 */
	@Override
	protected void updateGame(GameState gameState) {
		
		// TODO Review this and remove code that is not necessary anymore.
		super.updateGame(gameState);

		MathAssessmentState g = (MathAssessmentState) gameState;
		MathAssessmentView gameView = (MathAssessmentView) view;
		//we already have the latest state
		if (g==null) return;
		
		switch (g.getState()) {
			//started
		case 3:
			
			//kritischer moment, setze refreshrate nach oben
			setRefreshRate(200);
			
			break;

			//evaluation, who has won?
		case 5:
			
			setRefreshRate(1000);
				
			break;
			
			// for synchronization
		case 6:
			commServ.updateReadyness(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
			break;
		}

		openGame = g;
	}
	
	
	/**
	 * User clicked on "Start game"
	 */
	public void startButtonClicked() {
		if (!openGame.isPlayerReady(this.playerID)) {
			commServ.updateReadyness(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
		}		
	}

	/**
	 * We do not need this functionality here.
	 */
	@Override
	protected void handleAwaitingStartState(GameState gameState) {}

	/**
	 * We do not need this functionality here.
	 */
	@Override
	protected void handleWaitingForPlayersState() {}

	/**
	 * We do not need this functionality here.
	 */
	@Override
	protected void handleWaitingForOtherPlayersState(GameState g) {}

}
