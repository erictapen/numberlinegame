package com.wicam.numberlineweb.client.WordFamily;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.GameTypeSelector;
import com.wicam.numberlineweb.client.NumberLineWeb;
import com.wicam.numberlineweb.client.WordFamily.WordFamilyGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

public class WordFamilyGameCoordinator extends GameCoordinator {
	
	private WordFamilyGameController controller;
	
	
	public WordFamilyGameCoordinator(WordFamilyGameCommunicationServiceAsync commServ,
			ChatCommunicationServiceAsync chatCommServ, 
			Panel root, GameTypeSelector gts) {
		super(commServ, chatCommServ, root,gts);
	}
	
	@Override
	public String getGameName() {

		return "Wortfamilien";

	}
	
	
	
	/**
	 * Initializes the coordinator
	 */
	@Override
	public void init() {

		gameSelector = new WordFamilyGameSelector(this);
		rootPanel.add(gameSelector);

		t = new Timer() {
			@Override
			public void run() {
				update();
			}
		};

		//main loop-timer
		t.scheduleRepeating(500);
		refreshGameList();
		
		

		GWT.log("WordFamily game coordinator loaded.");
	}
	
	
	
	/**
	 * Open a game name 'name'. Call back will get state of opened game
	 * @param name
	 */
	@Override
	public void openGame(GameState gameState) {
		
		GWT.log("opening! in WordFamilyGameCoord");

		this.numberOfPlayers = gameState.getMaxNumberOfPlayers();
		this.numberOfNPCs = gameState.getNumberOfMaxNPCs();
		
		gameState.setGameOpenedUserId(NumberLineWeb.USERID);
		
		((WordFamilyGameCommunicationServiceAsync)commServ).openGame(gameState, gameOpenedCallBack);

	}
	
	
	
	/**
	 * Called after our player joined the game.
	 * @param state
	 * @param gameID
	 */
	@Override
	protected void joinedGame(int playerID, int gameID) {

		super.joinedGame(playerID, gameID);
		this.playerID = playerID;

		//construct game
		controller = new WordFamilyGameController(this);
		
		this.view = new WordFamilyGameView(controller, numberOfPlayers, numberOfNPCs);
		
		WordFamilyGameView gameView = (WordFamilyGameView) view;

		//construct an empty game-state with the given information
		WordFamilyGameState g = new WordFamilyGameState();
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
	@Override
	protected void updateGame(GameState gameState) {
		super.updateGame(gameState);

		WordFamilyGameState g = (WordFamilyGameState) gameState;
		WordFamilyGameView gameView = (WordFamilyGameView) view;
		//we already have the lates state
		if (g==null) return;
		
		switch (g.getState()) {
			//started, words showing
		case 3:
			
			if (!gameView.isRunning()) gameView.run(g.getWords(), g.getStem());
//			updateViewIngame(g, gameView);
			
			// easy, just wait for the first response
			setRefreshRate(1000);
			
			break;

			// waiting for responses
		case 4:
			
			setRefreshRate(200);
			updateViewIngame(g, gameView);

			
			break;
			
			//evaluation, who has won?
		case 5:
			
			updateViewIngame(g, gameView);
			setRefreshRate(1000);
			int correct = g.getCorrectlyAnswered().size();
			gameView.setInfoText("Du hast dir "+correct+ ((correct == 1)?" Wort":" Wörter") +" aus dieser Familie richtig gemerkt.<br><br>" +
					"Alle richtigen Wörter waren: "+g.getCorrectAsHtmlList());
			gameView.disableInput();
				
			break;
			
			// new words
		case 42:
			
			gameView.reset();
			((WordFamilyGameCommunicationServiceAsync)commServ).clickedAt(""+openGame.getId()+":cleared", updateCallback);
			
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
	private void updateViewIngame(WordFamilyGameState g, WordFamilyGameView gameView) {
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
		WordFamilyGameView gameView = (WordFamilyGameView) view;
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
		((WordFamilyGameView) view).setInfoText("Warte auf Spieler...");
	}

	
	
	/**
	 * Points are displayed and "Warte auf zweiten/andere Spieler..."
	 */
	@Override
	protected void handleWaitingForOtherPlayersState(GameState g){
		WordFamilyGameView gameView = (WordFamilyGameView) view;
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
	 * @param digit Digit clicked on, format: "value:idInSet:stem/word"
	 */
	public void clickAt(String digit) {
		((WordFamilyGameCommunicationServiceAsync)commServ).clickedAt(
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
