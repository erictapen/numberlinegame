package com.wicam.numberlineweb.client.OverTen;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.GameTypeSelector;
import com.wicam.numberlineweb.client.NumberLineWeb;
import com.wicam.numberlineweb.client.OverTen.OverTenGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

public class OverTenGameCoordinator extends GameCoordinator {
	
	private OverTenGameController controller;
	
	
	public OverTenGameCoordinator(OverTenGameCommunicationServiceAsync commServ,
			ChatCommunicationServiceAsync chatCommServ, 
			Panel root, GameTypeSelector gts) {
		super(commServ, chatCommServ, root,gts);
	}
	
	@Override
	public String getGameName() {

		return "Partnerzahlen";

	}
	
	
	
	/**
	 * Initializes the coordinator
	 */
	public void init() {

		gameSelector = new OverTenGameSelector((OverTenGameCoordinator) this);
		rootPanel.add(gameSelector);

		t = new Timer() {
			public void run() {
				update();
			}
		};

		//main loop-timer
		t.scheduleRepeating(500);
		refreshGameList();
		
		

		GWT.log("OverTen game coordinator loaded.");
	}
	
	
	
	/**
	 * Open a game name 'name'. Call back will get state of opened game
	 * @param name
	 */
	public void openGame(GameState gameState) {
		
		GWT.log("opening! in OverTenGameCoord");

		this.numberOfPlayers = gameState.getMaxNumberOfPlayers();
		this.numberOfNPCs = gameState.getNumberOfMaxNPCs();
		
		gameState.setGameOpenedUserId(NumberLineWeb.USERID);
		
		((OverTenGameCommunicationServiceAsync)commServ).openGame(gameState, gameOpenedCallBack);

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
		controller = new OverTenGameController(this);
		
		this.view = new OverTenGameView(controller, numberOfPlayers, numberOfNPCs);
		
		OverTenGameView gameView = (OverTenGameView) view;

		//construct an empty game-state with the given information
		OverTenGameState g = new OverTenGameState();
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
	 * @param gameState The GameState to update
	 */
	protected void updateGame(GameState gameState) {
		super.updateGame(gameState);

		OverTenGameState g = (OverTenGameState) gameState;
		OverTenGameView gameView = (OverTenGameView) view;
		//we already have the lates state
		if (g==null) return;
		
		switch (g.getState()) {
			//started 
		case 3:
			
			if (g.getPlayerCalculation(playerID).equals(new OverTenCalculation())) {				
				gameView.setFirstText();
			} else if (g.getPlayerDigit(playerID) == -1) {
				gameView.setSecondText(g.getPlayerCalculation(playerID).getFirst());
			} else {
				try {					
					gameView.setThirdText(g.getPlayerCalculation(playerID).calc());
				} catch (ArithmeticException e) {
					// should never happen...
					e.printStackTrace();
					gameView.setThirdText(0);
				}
			}
			
			updateViewIngame(g, gameView);
			
			//kritischer moment, setze refreshrate nach oben
			setRefreshRate(200);
			
			break;

			//evaluation, who has won?
		case 5:
			
			updateViewIngame(g, gameView);
			setRefreshRate(1000);
			gameView.setInfoText("Alle richtigen Antworten sind weg.");
				
			break;
			
			// for synchronization
		case 6:
			commServ.updateReadyness(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
			break;
		}
		



		openGame = g;


	}
	
	
	
	/**
	 * Updates the game view once
	 * @param g state to get data from
	 * @param gameView View to update
	 * 
	 */
	private void updateViewIngame(OverTenGameState g, OverTenGameView gameView) {
		gameView.drawCommunityDigits(g.getCommunityDigits());
		gameView.drawCalculations(g.getCalculations(), ((OverTenPlayer)g.getPlayers().get(playerID)).getCalculation().toString());
		
		for (int i = 0; i < g.getPlayers().size(); i++){
			gameView.setPoints(i+1, g.getPlayerPoints(i+1),g.getPlayerName(i+1));
		}
	}
	
	
	
	/**
	 * Sets user name in chat and sets points
	 * info text: "Das Spiel beginnt in wenigen Sekunden"
	 */
	@Override
	protected void handleAwaitingStartState(GameState g){
		OverTenGameView gameView = (OverTenGameView) view;
		setRefreshRate(1000);
		if (this.numberOfPlayers > 1)
			chatC.setUserName(g.getPlayerName(this.playerID));
		for (int i = 0; i < g.getPlayers().size(); i++)
			gameView.setPoints(i+1, 0, g.getPlayerName(i+1));
		gameView.setInfoText("Das Spiel beginnt in wenigen Sekunden!");
	}
	
	
	
	/**
	 * "Warte auf Spieler..." is displayed on the view
	 * and refresh rate is increased to 2000 ms
	 */
	@Override
	protected void handleWaitingForPlayersState(){
		setRefreshRate(2000);
		((OverTenGameView) view).setInfoText("Warte auf Spieler...");
	}

	
	
	/**
	 * Points are displayed and "Warte auf zweiten/andere Spieler..."
	 */
	@Override
	protected void handleWaitingForOtherPlayersState(GameState g){
		OverTenGameView gameView = (OverTenGameView) view;
		setRefreshRate(2000);
		for (int i = 0; i < g.getPlayers().size(); i++)
			gameView.setPoints(i+1, 0, g.getPlayerName(i+1));
		if (g.getMaxNumberOfPlayers() <= 2)
			gameView.setInfoText("Warte auf zweiten Spieler...");
		else
			gameView.setInfoText("Warte auf andere Spieler...");
	}
	
	/**
	 * Clicked at position (x,y)
	 * @param x x-position
	 * @param y y-position
	 * @param w Widget, that was clicked
	 */
	public void clickAt(Widget w, int x, int y) {
	}
	
	
	/**
	 * @param digit Digit clicked on, format: "value:idInSet:community/hand"
	 */
	public void clickAt(String digit) {
		((OverTenGameCommunicationServiceAsync)commServ).clickedAt(
				Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID) + ":" + digit, updateCallback);
	}
	
	

	/**
	 * Mouse was moved to (x,y)
	 * @param x x-position
	 * @param y y-position
	 * @param w Widget, that was hovered
	 */
	public void mouseMovedTo(Widget w, int x, int y) {
	}

	
	
	/**
	 * User clicked on "Start game"
	 */
	public void startButtonClicked() {
		if (!openGame.isPlayerReady(this.playerID)) {
			commServ.updateReadyness(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
		}		
	}


}
