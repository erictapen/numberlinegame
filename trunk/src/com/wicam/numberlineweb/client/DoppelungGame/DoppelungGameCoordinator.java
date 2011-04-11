package com.wicam.numberlineweb.client.DoppelungGame;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

public class DoppelungGameCoordinator extends GameCoordinator{

	private DoppelungGameController controller;
	
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
		commServ.openGame(g, gameOpenedCallBack);
	}

	@Override
	protected void joinedGame(int playerID, int gameID) {
		this.playerID = playerID;

		//construct game
		controller = new DoppelungGameController(this);
		this.view = new DoppelungGameView(numberOfPlayers, controller);
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
		DoppelungGameState g = (DoppelungGameState) gameState;
		DoppelungGameView gameView = (DoppelungGameView) view;
		
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
				// TODO: set Points
			}
			// TODO: wait for second player
			break;
			//awaiting start
		case 2:
			if (this.numberOfPlayers > 1)
				chatC.setUserName(g.getPlayerName(this.playerID));
			for (int i = 0; i < g.getPlayers().size(); i++){
				// TODO: set Points
			}
			setRefreshRate(1000);
			break;

		case 21:

			setRefreshRate(200);
			//start is pending. I am ready!
			if (!openGame.isPlayerReady(this.playerID)) {
				  commServ.updateReadyness(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
			}
			break;

			//started
		case 3:
			gameView.showVowelChoice();
			// TODO: play word
			System.out.println(g.getCurWord().getWord());
			break;
			
		case 4:
			
			break;

			//evaluation, who has won?
		case 5:


			break;
		case 6:
			break;
		case 7:
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
	
	public void startButtonClicked(){
		if (!openGame.isPlayerReady(this.playerID)) {
			commServ.updateReadyness(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
		}
	}

}
