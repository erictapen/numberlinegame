package com.wicam.numberlineweb.client.NumberLineGame;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.wicam.numberlineweb.client.CommunicationServiceAsync;
import com.wicam.numberlineweb.client.chatView.ChatController;
import com.wicam.numberlineweb.client.chatView.ChatCoordinator;
import com.wicam.numberlineweb.client.chatView.ChatView;

public class NumberLineGameCoordinator {

	private int numberOfPlayers;
	private int numberOfNPCs;
	private NumberLineGameState openGame = null;
	private Panel rootPanel;
	private NumberLineController controller;
	private boolean sessionClicked = false;
	private boolean triedToClick = false; // indicates if players has already tried to click at a position which was not available
	private ChatCoordinator chatC;
	private NumberLineGameState openedGame;
	private Timer t;
	private int playerID;
	private NumberLineView gameView;
	private NumberLineGameSelector gameSelector;
	private CommunicationServiceAsync commServ;
	private int refreshIntervall;

	/**
	 * Constructs the coordinator with comm-service and a rootPanel
	 * @param commServ
	 * @param root
	 */

	public NumberLineGameCoordinator(CommunicationServiceAsync commServ,Panel root) {

		this.commServ = commServ;
		this.rootPanel = root;

	}

	/**
	 * Initializes the coordinator
	 */

	public void init() {

		gameSelector = new NumberLineGameSelector(this);
		rootPanel.add(gameSelector);

		t = new Timer() {
			public void run() {
				update();
			}
		};

		//main loop-timer
		t.scheduleRepeating(500);
		refreshGameList();
		
		

		GWT.log("game coordinator loaded.");
	}

	
	/**
	 * sets the refresh rate of the main loop timer
	 */
	
	private void setRefreshRate(int rate) {
		
		GWT.log("r-rate to " + rate);
		
		if (this.refreshIntervall != rate) {
			
			this.refreshIntervall = rate;
			t.scheduleRepeating(rate);
			
			
		}
		
		
	}
	
	
	
	/**
	 * Open a game name 'name'. Call back will get state of opened game
	 * @param name
	 */

	public void openGame(String name, int numberOfPlayers, int numberOfNPCs, int numberOfRounds) {

		this.numberOfPlayers = numberOfPlayers;
		this.numberOfNPCs = numberOfNPCs;
		NumberLineGameState g = new NumberLineGameState();
		g.setGameName(name);
		g.setNumberOfPlayers(numberOfPlayers);
		g.setNumberOfMaxNPCs(numberOfNPCs);
		g.setMaxItems(numberOfRounds);

		commServ.openGame(g, gameOpenedCallBack);

	}
	
	/**
	 * Close a game
	 * @param name
	 */

	public void closeGame(NumberLineGameState g) {
		System.out.println(g.getPlayerName(this.playerID) + " closed Game");
		commServ.leaveGame(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
		RootPanel.get("chatwrap").getElement().getStyle().setVisibility(Visibility.HIDDEN);
		RootPanel.get("chat").clear();
		rootPanel.clear();
		init();
	}

	/**
	 * refreshes the game list. Callback will redraw list
	 */

	public void refreshGameList() {

		commServ.getOpenGames(openGamesCallback);		

	}

	/**
	 * join game #id with username 'name'
	 * @param id
	 * @param name
	 */

	public void joinGame(int id,String name, int numberOfPlayers, int numberOfNPCs) {
		this.numberOfPlayers = numberOfPlayers;
		this.numberOfNPCs = numberOfNPCs;
		
		//we dont want anonymous players
		if (name.equals("")) name="Spieler";

		name = escapeString(name);

		commServ.joinGame(Integer.toString(id) + ":" + name, gameJoinedCallback);

	}

	/**
	 * Called after our pĺayer joined the game.
	 * @param playerID
	 * @param gameID
	 */

	private void joinedGame(int playerID, int gameID) {

		this.playerID = playerID;

		//construct game
		this.gameView = new NumberLineView(numberOfPlayers, numberOfNPCs);
		controller = new NumberLineController(this);
		gameView.addMouseHandler(controller);

		//construct an empty game-state with the given information
		NumberLineGameState g = new NumberLineGameState();
		g.setGameId(gameID);
		g.setState(-1);
		this.openGame = g;
		update();

		//clear the root panel and draw the game
		rootPanel.clear();
		rootPanel.add(gameView);
		
		/*
		 * Optional: Add chat window from package "Chat"
		 */

		ChatView chatView = new ChatView();
		chatC = new ChatCoordinator(openGame.getId(),chatView,commServ);

		ChatController chatContr = new ChatController(chatC);
		chatView.addController(chatContr);
		chatView.init();

		RootPanel.get("chat").add(chatView);
		RootPanel.get("chatwrap").getElement().getStyle().setVisibility(Visibility.VISIBLE);

	}

	/**
	 * Called after game state was received.
	 * @param g
	 */

	private void updateGame(NumberLineGameState g) {

		//we already have the lates state
		if (g==null) return;

		//update pointer width if changed
		if (gameView.getPointerWidth() != g.getPointerWidth()) {

			gameView.setPointerWidth(g.getPointerWidth());

		}
		
		switch (g.getState()) {

		//game closed
		case -1:
			setRefreshRate(2000);
			//TODO: close game
			break;
			//awaiting players
		case 0:
			setRefreshRate(2000);
			gameView.setInfoText("Warte auf Spieler...");
			break;
			//awaiting 2nd player
		case 1:
			setRefreshRate(2000);
			for (int i = 0; i < g.getPlayers().size(); i++)
				gameView.setPoints(i+1, 0, g.getPlayerName(i+1));
			if (g.getMaxNumberOfPlayers() <= 2)
				gameView.setInfoText("Warte auf zweiten Spieler...");
			else
				gameView.setInfoText("Warte auf andere Spieler...");
			break;
			//awaiting start
		case 2:

			setRefreshRate(1000);
			chatC.setUserName(g.getPlayerName(this.playerID));
			for (int i = 0; i < g.getPlayers().size(); i++)
				gameView.setPoints(i+1, 0, g.getPlayerName(i+1));
			gameView.setInfoText("Das Spiel beginnt in wenigen Sekunden!");
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
					gameView.setInfoText("Position ist bereits belegt! Waehle eine andere Position!");
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
				gameView.showPointerText(i+1, g.getPlayerActPos(i+1), openGame.getExerciseNumber());
				gameView.setPoints(i+1, g.getPlayerPoints(i+1),g.getPlayerName(i+1));
			}

			gameView.showCorrectPositionPointer(openGame.getExerciseNumber());
			
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
		case 6:
			// TODO: winner screen
			closeGame(g);
			break;
			
		case 99:
			// player has left the game
			
			Iterator<NumberLineGamePlayer> i = g.getPlayers().iterator();
			
			while (i.hasNext()) {
				
				NumberLineGamePlayer current = i.next();
				
				if (current.hasLeftGame() && !openGame.getPlayers().get(g.getPlayers().indexOf(current)).hasLeftGame()) {
					
					gameView.setInfoText(current.getName() + " hat das Spiel verlassen");
					gameView.deletePlayerFromPointList(g.getPlayers().indexOf(current)+1);
				}
				
				
			}
			
			
			break;
			
		}	



		openGame = g;


	}

	/**
	 * Make update-call to server
	 */

	private void update() {

		if (this.gameView != null) {
			commServ.update(Integer.toString(this.openGame.getId()) + ":" + Integer.toString(this.playerID), updateCallback);
		}
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
			commServ.clickedAt(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID) + ":" + Integer.toString(x), updateCallback);
		}
	}

	public String escapeString(String str) {

		return str.replace(":", " ");

	}



	public void mouseMovedTo(int x, int y) {



		if (((openGame.getState() == 3 ) ||
				(openGame.getState() == 4 && !sessionClicked))&&
				!sessionClicked) {

			//x = gameView.rawPosToReal(x);
			if (x>=100 && x<=500)	gameView.setPointer(playerID, x-100);
		}


	}


	/**
	 * CALLBACKS.
	 * Callbacks are called after server-information was received
	 */

	AsyncCallback<NumberLineGameState> gameOpenedCallBack = new AsyncCallback<NumberLineGameState>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(NumberLineGameState result) {

			openedGame = result;
			commServ.getOpenGames(openGamesAndJoinCallback);	

		}

	};

	AsyncCallback<NumberLineGameState> updateCallback = new AsyncCallback<NumberLineGameState>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(NumberLineGameState result) {

			updateGame(result);

		}

	};

	AsyncCallback<String> gameJoinedCallback = new AsyncCallback<String>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(String result) {

			int playerid = Integer.parseInt(result.split(":")[1]);
			int gameid = Integer.parseInt(result.split(":")[0]);

			joinedGame(playerid,gameid);

		}

	};

	AsyncCallback<Boolean> dummyCallback = new AsyncCallback<Boolean>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(Boolean result) {


		}

	};


	AsyncCallback<ArrayList<NumberLineGameState>> openGamesCallback = new AsyncCallback<ArrayList<NumberLineGameState>>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(ArrayList<NumberLineGameState> result) {


			gameSelector.setGameList(result);
		}

	};

	AsyncCallback<ArrayList<NumberLineGameState>> openGamesAndJoinCallback = new AsyncCallback<ArrayList<NumberLineGameState>>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(ArrayList<NumberLineGameState> result) {


			if (result != null) {

				gameSelector.setSelected(openedGame);
				gameSelector.joinGame();
			}
		}

	};





}
