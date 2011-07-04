package com.wicam.numberlineweb.client.NumberLineGame;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.GameTypeSelector;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

public class NumberLineGameCoordinator extends GameCoordinator {
	
	private NumberLineController controller;
	private boolean sessionClicked = false;
	private boolean triedToClick = false; // indicates if players has already tried to click at a position which was not available

	public NumberLineGameCoordinator(NumberLineGameCommunicationServiceAsync commServ,
			ChatCommunicationServiceAsync chatCommServ, 
			Panel root, GameTypeSelector gts) {
		super(commServ, chatCommServ, root,gts);
	}
	
	@Override
	public String getGameName() {

		return "NumberLineGame";

	}
	
	/**
	 * Initializes the coordinator
	 */

	public void init() {

		gameSelector = new NumberLineGameSelector((NumberLineGameCoordinator) this);
		rootPanel.add(gameSelector);

		t = new Timer() {
			public void run() {
				update();
			}
		};

		//main loop-timer
		t.scheduleRepeating(500);
		refreshGameList();
		
		

		GWT.log("number line game coordinator loaded.");
	}
	
	/**
	 * Open a game name 'name'. Call back will get state of opened game
	 * @param name
	 */

	public void openGame(GameState gameState) {
		
		GWT.log("opening!");

		this.numberOfPlayers = gameState.getMaxNumberOfPlayers();
		this.numberOfNPCs = gameState.getNumberOfMaxNPCs();
		((NumberLineGameCommunicationServiceAsync)commServ).openGame(gameState, gameOpenedCallBack);

	}
	
	/**
	 * Called after our pĺayer joined the game.
	 * @param playerID
	 * @param gameID
	 */

	protected void joinedGame(int playerID, int gameID) {

		super.joinedGame(playerID, gameID);
		this.playerID = playerID;

		//construct game
		controller = new NumberLineController(this);
		this.view = new NumberLineGameView(controller, numberOfPlayers, numberOfNPCs);
		
		NumberLineGameView gameView = (NumberLineGameView) view;

		//construct an empty game-state with the given information
		NumberLineGameState g = new NumberLineGameState();
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

	/**
	 * Called after game state was received.
	 * @param g
	 */

	protected void updateGame(GameState gameState) {
		super.updateGame(gameState);

		NumberLineGameState g = (NumberLineGameState) gameState;
		NumberLineGameView gameView = (NumberLineGameView) view;
		//we already have the lates state
		if (g==null) return;

		//update pointer width if changed
		if (gameView.getPointerWidth() != g.getPointerWidth()) {

			gameView.setPointerWidth(g.getPointerWidth());

		}
		
		switch (g.getState()) {
			//started
		case 3:
			gameView.clear();
			sessionClicked=false;
			triedToClick=false;

			gameView.setLeftNumber(g.getLeftNumber());
			gameView.setRightNumber(g.getRightNumber());
			gameView.setExerciseNumber(g.getExerciseNumber());
			gameView.setInfoText("Mache deine Schätzung!");
			
			//kritischer moment, setze refreshrate nach oben
			setRefreshRate(200);
			
			
			break;

			//started, next player
		case 4:
			
			setRefreshRate(500);
			
			for (int i = 0; i < g.getPlayers().size(); i++){
				if (this.playerID != i+1)
					if(g.isPlayerClicked(i+1))
						gameView.setPointer(i+1, g.getPlayerActPos(i+1));
			}
			if (g.isPlayerClicked(this.playerID)){
				// if server has been asked, if the position is available, pointer has not been drawn
				if (g.getPlayers().size() > 2)
					gameView.setInfoText("Warte auf andere Spieler!");
				else
					gameView.setInfoText("Warte auf " + g.getPlayerName(playerID%2+1) + "!");
				gameView.setPointer(playerID, g.getPlayerActPos(this.playerID));
			}
			else {
				// if position was not available, player gets a second chance
				if (triedToClick || sessionClicked){
					gameView.setInfoText("Position ist bereits belegt! Wähle eine andere Position!");
					sessionClicked = false;
				}
				else{
					gameView.setInfoText("Mache deine Schätzung!");
				}
			}
			
			break;

			//evaluation, who has won?
		case 5:
			setRefreshRate(1000);
			
			for (int i = 0; i < g.getPlayers().size(); i++){
				gameView.setPointer(i+1, g.getPlayerActPos(i+1));
				gameView.setPoints(i+1, g.getPlayerPoints(i+1),g.getPlayerName(i+1));
			}

			gameView.showCorrectPositionPointer(g.getExerciseNumber());
			
			if (g.getWinnerOfLastRound() == 0) {
				gameView.setInfoText("Unentschieden!");

			}else if (g.getWinnerOfLastRound() == this.playerID) {
				gameView.setInfoText("Du hast gewonnen!");
			}else{
				for (int i = 0; i < g.getPlayers().size(); i++)
					if (g.getWinnerOfLastRound() == i+1)
						gameView.setInfoText(g.getPlayerName(i+1) + " hat gewonnen.");
			}

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
	 * info text: "Das Spiel beginnt in wenigen Sekunden"
	 */
	@Override
	protected void handleAwaitingStartState(GameState g){
		NumberLineGameView gameView = (NumberLineGameView) view;
		setRefreshRate(1000);
		if (this.numberOfPlayers > 1)
			chatC.setUserName(g.getPlayerName(this.playerID));
		for (int i = 0; i < g.getPlayers().size(); i++)
			gameView.setPoints(i+1, 0, g.getPlayerName(i+1));
		gameView.setInfoText("Das Spiel beginnt in wenigen Sekunden!");
		if (!g.isPlayerReady(playerID))
			commServ.updateReadyness(Integer.toString(g.getId()) + ":" + Integer.toString(playerID), dummyCallback);
	}
	
	
	/**
	 * "Warte auf Spieler..." is displayed on the view
	 * and refresh rate is increased to 2000 ms
	 */
	@Override
	protected void handleWaitingForPlayersState(){
		setRefreshRate(2000);
		((NumberLineGameView) view).setInfoText("Warte auf Spieler...");
	}

	/**
	 * Points are displayed and "Warte auf zweiten/andere Spieler..."
	 */
	@Override
	protected void handleWaitingForOtherPlayersState(GameState g){
		NumberLineGameView gameView = (NumberLineGameView) view;
		setRefreshRate(2000);
		for (int i = 0; i < g.getPlayers().size(); i++)
			gameView.setPoints(i+1, 0, g.getPlayerName(i+1));
		if (g.getMaxNumberOfPlayers() <= 2)
			gameView.setInfoText("Warte auf zweiten Spieler...");
		else
			gameView.setInfoText("Warte auf andere Spieler...");
	}
	
	/**
	 * Clicked at position x on the bar
	 * @param x
	 */

	public void clickAt(int x) {

		if ((openGame.getState() == 3 || openGame.getState() == 4) && 
				!sessionClicked){
			this.sessionClicked = true;
			this.triedToClick = true;

			// check if other player's position has already been displayed
			((NumberLineGameCommunicationServiceAsync)commServ).clickedAt(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID) + ":" + Integer.toString(x), updateCallback);
		}
	}

	public void mouseMovedTo(int x, int y) {



		if (((openGame.getState() == 3 ) ||
				(openGame.getState() == 4 && !sessionClicked))&&
				!sessionClicked) {
			NumberLineGameView gameView = (NumberLineGameView) view;
			//x = gameView.rawPosToReal(x);
			if (x>=100 && x<=500)	gameView.setPointer(playerID, x-100);
		}


	}


}
