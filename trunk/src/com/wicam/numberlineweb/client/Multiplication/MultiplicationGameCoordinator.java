package com.wicam.numberlineweb.client.Multiplication;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.GameTypeSelector;
import com.wicam.numberlineweb.client.NumberLineWeb;
import com.wicam.numberlineweb.client.Player;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

public class MultiplicationGameCoordinator extends GameCoordinator {
	
	private MultiplicationGameController controller;
	
	
	public MultiplicationGameCoordinator(MultiplicationGameCommunicationServiceAsync commServ,
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
	public void init() {

		gameSelector = new MultiplicationGameSelector((MultiplicationGameCoordinator) this);
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
	 * Open a game name 'name'. Call back will get state of opened game
	 * @param name
	 */
	public void openGame(GameState gameState) {
		
		GWT.log("opening! in MultGameCoord");

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
	protected void joinedGame(int playerID, int gameID) {

		super.joinedGame(playerID, gameID);
		this.playerID = playerID;

		//construct game
		controller = new MultiplicationGameController(this);
		
		this.view = new MultiplicationGameView(controller, numberOfPlayers, numberOfNPCs);
		
		MultiplicationGameView gameView = (MultiplicationGameView) view;

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

	
	
	/**
	 * Called after game state was received.
	 * @param gameState The GameState to update
	 */
	protected void updateGame(GameState gameState) {
		super.updateGame(gameState);

		MultiplicationGameState g = (MultiplicationGameState) gameState;
		MultiplicationGameView gameView = (MultiplicationGameView) view;
		//we already have the lates state
		if (g==null) return;
		
		switch (g.getState()) {
			//started
		case 3:
			
			gameView.setInfoText("Klicke auf die richtige Rechnung!");
			
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
	private void updateViewIngame(MultiplicationGameState g, MultiplicationGameView gameView) {
		gameView.drawAnwers(g.getAnswers());
		gameView.setResultText(g.getResult());
		
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
		MultiplicationGameView gameView = (MultiplicationGameView) view;
		setRefreshRate(1000);
		if (this.numberOfPlayers > 1)
			chatC.setUserName(g.getPlayerName(this.playerID));
		for (int i = 0; i < g.getPlayers().size(); i++)
			gameView.setPoints(i+1, 0, g.getPlayerName(i+1));
		int notReady = 0;
		for (Player p : g.getPlayers()) {
			notReady += (p.isReady()) ? 0 : 1;
		}
		gameView.setInfoText("Warte auf " + notReady + " Spieler...");
		//if (!g.isPlayerReady(playerID))
			//commServ.updateReadyness(Integer.toString(g.getId()) + ":" + Integer.toString(playerID), dummyCallback);
	}
	
	
	
	/**
	 * "Warte auf Spieler..." is displayed on the view
	 * and refresh rate is increased to 2000 ms
	 */
	@Override
	protected void handleWaitingForPlayersState(){
		setRefreshRate(2000);
		((MultiplicationGameView) view).setInfoText("Warte auf Spieler...");
	}

	
	
	/**
	 * Points are displayed and "Warte auf zweiten/andere Spieler..."
	 */
	@Override
	protected void handleWaitingForOtherPlayersState(GameState g){
		MultiplicationGameView gameView = (MultiplicationGameView) view;
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
	
	
	public void clickAt(String answer) {
		((MultiplicationGameCommunicationServiceAsync)commServ).clickedAt(
				Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID) + ":" + answer, updateCallback);
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
