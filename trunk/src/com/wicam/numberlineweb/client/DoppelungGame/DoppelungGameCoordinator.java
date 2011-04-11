package com.wicam.numberlineweb.client.DoppelungGame;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

public class DoppelungGameCoordinator extends GameCoordinator{

	public DoppelungGameCoordinator(GameCommunicationServiceAsync commServ, ChatCommunicationServiceAsync chatServ,
			Panel root) {
		super(commServ, chatServ, root);
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
		// TODO: for DoppelungGame commServ.openNumberLineGame(g, gameOpenedCallBack);
	}

	@Override
	protected void joinedGame(int playerID, int gameID) {
		this.playerID = playerID;

		//construct game
		this.view = new DoppelungGameView(numberOfPlayers, numberOfNPCs);
		DoppelungGameView gameView = (DoppelungGameView) view;
		
		
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
		// TODO Auto-generated method stub
		
	}

}
