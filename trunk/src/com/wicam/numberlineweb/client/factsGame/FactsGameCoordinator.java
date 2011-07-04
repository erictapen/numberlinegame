package com.wicam.numberlineweb.client.factsGame;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.GameTypeSelector;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

public class FactsGameCoordinator extends GameCoordinator {

	private FactsGameController controller;
	
	public FactsGameCoordinator(GameCommunicationServiceAsync commServ,
			ChatCommunicationServiceAsync chatCommServ, Panel root,
			GameTypeSelector gts) {
		super(commServ, chatCommServ, root, gts);
	}

	@Override
	public void init() {
		gameSelector = new FactsGameSelector((FactsGameCoordinator) this);
		rootPanel.add(gameSelector);

		t = new Timer() {
			public void run() {
				update();
			}
		};

		//main loop-timer
		t.scheduleRepeating(500);
		refreshGameList();

		GWT.log("facts game coordinator loaded.");
	}

	@Override
	public String getGameName() {

		return "Faktenspiel";

	}
	
	@Override
	protected void joinedGame(int playerID, int gameID) {
		this.playerID = playerID;

		//construct game
		controller = new FactsGameController(this);
		this.view = new FactsGameView(numberOfPlayers, controller);
		FactsGameView gameView =  (FactsGameView) view;

		//construct an empty game-state with the given information
		FactsGameState g = new FactsGameState();
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

	@Override
	protected void handleWaitingForPlayersState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleWaitingForOtherPlayersState(GameState g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleAwaitingStartState(GameState gameState) {
		// TODO Auto-generated method stub
		
	}

}
