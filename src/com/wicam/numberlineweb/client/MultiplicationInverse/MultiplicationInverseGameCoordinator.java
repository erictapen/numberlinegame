package com.wicam.numberlineweb.client.MultiplicationInverse;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.GameTypeSelector;
import com.wicam.numberlineweb.client.HistoryChangeHandler;
import com.wicam.numberlineweb.client.NumberLineWeb;
import com.wicam.numberlineweb.client.Player;
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

public class MultiplicationInverseGameCoordinator extends GameCoordinator {

	protected MultiplicationInverseGameController controller;
	
	public MultiplicationInverseGameCoordinator(
			GameCommunicationServiceAsync commServ,
			ChatCommunicationServiceAsync chatCommServ, Panel root,
			GameTypeSelector gts) {
		super(commServ, chatCommServ, root, gts);
	}
	
	/**
	 * returns the name of the game
	 */
	public String getGameName() {

		return "MultplicationInverse";

	}
	
	/**
	 * Updates the game view once
	 * @param g state to get data from
	 * @param gameView View to update
	 * 
	 */
	protected void updateViewIngame(MultiplicationInverseGameState g, MultiplicationInverseGameView gameView) {
		gameView.drawAnwers(g.getAnswers());
		gameView.setTaskText(g.getTask());
		
		for (int i = 0; i < g.getPlayers().size(); i++){
			gameView.setPoints(i+1, g.getPlayerPoints(i+1),g.getPlayerName(i+1));
		}
	}

	/**
	 * Initializes the coordinator
	 */
	public void init() {
		
		// Don't show the game selector page, but precede to the game description instead.
		gameSelector = new MultiplicationInverseGameSelector((MultiplicationInverseGameCoordinator) this);
//		rootPanel.add(gameSelector);
		

		t = new Timer() {
			public void run() {
				update();
			}
		};

		//main loop-timer
		t.scheduleRepeating(500);
		refreshGameList();
		
		

		GWT.log("inverse multiplication game coordinator loaded.");
	}
	
	
	/**
	 * Called after our player joined the game.
	 * @param playerID
	 * @param gameID
	 */
	@Override
	protected void joinedGame(int playerID, int gameID) {

//		super.joinedGame(playerID, gameID);
		
		// Don't jump back to the game but to the overview instead.
//		History.newItem("game-" + getGameName(),false);
		HistoryChangeHandler.setHistoryListener(new HistoryListener() {


			@Override
			public void onHistoryChanged(String historyToken) {

				if (historyToken.matches("gameSelector.*")) {
					new GameCloseQuestion();
				}


			}
		});
		
		this.playerID = playerID;

		//construct game
		controller = new MultiplicationInverseGameController(this);
		
		this.view = new MultiplicationInverseGameView(controller, numberOfPlayers, numberOfNPCs);
		
		MultiplicationInverseGameView gameView = (MultiplicationInverseGameView) view;

		//construct an empty game-state with the given information
		MultiplicationInverseGameState g = new MultiplicationInverseGameState();
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
	 * Open a game name 'name'. Call back will get state of opened game
	 * @param name
	 */
	@Override
	public void openGame(GameState gameState) {
		
		GWT.log("opening! in MultInverseGameCoord");

		this.numberOfPlayers = gameState.getMaxNumberOfPlayers();
		this.numberOfNPCs = gameState.getNumberOfMaxNPCs();
		
		gameState.setGameOpenedUserId(NumberLineWeb.USERID);
		
		((MultiplicationInverseGameCommunicationServiceAsync)commServ).openGame(gameState, gameOpenedCallBack);

	}
	
	/**
	 * Called after game state was received.
	 * @param gameState The GameState to update
	 */
	protected void updateGame(GameState gameState) {
//		super.updateGame(gameState);
		
		// handle basic cases
		switch (gameState.getState()){
		// game closed
		case -1:
			handleGameClosedState(gameState);
			break;
			// awaiting players
		case 0:
			handleWaitingForPlayersState();
			break;
			// awaiting other players
		case 1:
			handleWaitingForOtherPlayersState(gameState);
			break;
			// awaiting start 
		case 2:
			handleAwaitingStartState(gameState);
			break;
			//awaiting start confirmation
		case 21:
			handleAwaitingReadyConfirm();
			break;
			// performance
		case 97:
			handlePerformanceState(gameState);
			break;
			// close game
		case 98:
			handleCloseGameState();
			break;
			// player left
		case 99:
			handlePlayerLeftState(gameState);
			break;
		}

		MultiplicationInverseGameState g = (MultiplicationInverseGameState) gameState;
		MultiplicationInverseGameView gameView = (MultiplicationInverseGameView) view;
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
	 * "Warte auf Spieler..." is displayed on the view
	 * and refresh rate is increased to 2000 ms
	 */
	@Override
	protected void handleWaitingForPlayersState(){
		setRefreshRate(2000);
		((MultiplicationInverseGameView) view).setInfoText("Warte auf Spieler...");
	}	
	
	/**
	 * Points are displayed and "Warte auf zweiten/andere Spieler..."
	 */
	@Override
	protected void handleWaitingForOtherPlayersState(GameState g){
		MultiplicationInverseGameView gameView = (MultiplicationInverseGameView) view;
		setRefreshRate(2000);
		for (int i = 0; i < g.getPlayers().size(); i++)
			gameView.setPoints(i+1, 0, g.getPlayerName(i+1));
		if (g.getMaxNumberOfPlayers() <= 2)
			gameView.setInfoText("Warte auf zweiten Spieler...");
		else
			gameView.setInfoText("Warte auf andere Spieler...");
	}
	
	/**
	 * Sets user name in chat and sets points
	 * info text: "Das Spiel beginnt in wenigen Sekunden"
	 */
	@Override
	protected void handleAwaitingStartState(GameState g){
		MultiplicationInverseGameView gameView = (MultiplicationInverseGameView) view;
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
	
	@Override
	protected void restartGame()
	{
		gts.init(getRootPanel());
		gts.showCats();
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
		((MultiplicationInverseGameCommunicationServiceAsync)commServ).clickedAt(
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
