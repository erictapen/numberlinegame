package com.wicam.numberlineweb.client.MultiplicationInverse;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameTypeSelector;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameController;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameCoordinator;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameSelector;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameState;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameView;
import com.wicam.numberlineweb.client.VowelGame.DehnungGame.DehnungGameView;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameController;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameState;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

public class MultiplicationInverseGameCoordinator extends MultiplicationGameCoordinator {

	public MultiplicationInverseGameCoordinator(
			GameCommunicationServiceAsync commServ,
			ChatCommunicationServiceAsync chatCommServ, Panel root,
			GameTypeSelector gts) {
		super(commServ, chatCommServ, root, gts);
	}
	
	/**
	 * returns the name of the game
	 */
	@Override
	public String getGameName() {

		return "MultplicationInverse";

	}

	/**
	 * Initializes the coordinator
	 */
	@Override
	public void init() {

		GWT.log("init");
		
		gameSelector = new MultiplicationGameSelector((MultiplicationInverseGameCoordinator) this);
		rootPanel.add(gameSelector);

		t = new Timer() {
			public void run() {
				update();
			}
		};

		//main loop-timer
		t.scheduleRepeating(500);
		refreshGameList();
		
		

		GWT.log("multiplication game coordinator loaded.");
	}
	
	/**
	 * Called after our player joined the game.
	 * @param playerID
	 * @param gameID
	 */
	protected void joinedGame(int playerID, int gameID) {

		super.joinedGame(playerID, gameID);
		this.playerID = playerID;

		//construct game
		controller = new MultiplicationGameController(this);
		
		this.view = new MultiplicationInverseGameView(controller, numberOfPlayers, numberOfNPCs);
		
		MultiplicationInverseGameView gameView = (MultiplicationInverseGameView) view;

		//construct an empty game-state with the given information
		MultiplicationGameState g = new MultiplicationGameState();
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

}
