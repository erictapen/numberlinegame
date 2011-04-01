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


	private NumberLineGameState openGame = null;
	private Panel rootPanel;
	private NumberLineController controller;
	private boolean sessionClicked = false;
	private ChatCoordinator chatC;
	private NumberLineGameState openedGame;
	private Timer t;
	private int playerID;
	private NumberLineView gameView;
	private NumberLineGameSelector gameSelector;
	private CommunicationServiceAsync commServ;
	
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
		t.scheduleRepeating(2000);
		refreshGameList();
		
		GWT.log("game coordinator loaded.");
	}
	
	/**
	 * Open a game name 'name'. Call back will get state of opened game
	 * @param name
	 */

	public void openGame(String name) {

		NumberLineGameState g = new NumberLineGameState();
		g.setGameName(name);

		commServ.openGame(g, gameOpenedCallBack);

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

	public void joinGame(int id,String name) {

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
		this.gameView = new NumberLineView();
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

		switch (g.getState()) {

		//game closed
		case -1:
			//TODO: close game
			break;
		//awaiting players
		case 0:
			gameView.setInfoText("Warte auf Spieler...");
			break;
		//awaiting 2nd player
		case 1:
			gameView.setInfoText("Warte auf zweiten Spieler...");
			break;
		//awaiting start
		case 2:

			chatC.setUserName(g.getPlayerName(this.playerID));
			gameView.setPoints(0, g.getPlayerName(this.playerID));
			gameView.setEnemyPoints(0, g.getPlayerName((this.playerID % 2) +1));
			gameView.setInfoText("Das Spiel beginnt in wenigen Sekunden!");
			break;

		case 21:
			
			//start is pending. I am ready!
			if (!openGame.isPlayerReady(this.playerID)) {
				GWT.log("pending... sending ready!");
				commServ.updateReadyness(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), updateCallback);
			}
			break;
			
		//started
		case 3:
			gameView.clear();
			sessionClicked=false;

			gameView.setLeftNumber(g.getLeftNumber());
			gameView.setRightNumber(g.getRightNumber());
			gameView.setExerciseNumber(g.getExerciseNumber());
			// TODO Umlaute
			gameView.setInfoText("Mache deine Schaetzung!");
			break;

		//started, next player
		case 4:
			if (this.playerID == 1){
				if(g.isPlayerBclicked()) {
					gameView.setEnemyPointer(g.getPlayerActPos(2));
				}
				if(g.isPlayerAclicked()){
					// if server has been asked, if the position is available, pointer has not been drawn
					gameView.setInfoText("Warte auf " + g.getPlayerName(2) + "!");
					gameView.setOwnPointer(g.getPlayerActPos(1));
				}
				else {
					// if position was not available, player gets a second chance
					if (sessionClicked){
						gameView.setInfoText("Position ist bereits belegt! Waehle eine andere Position!");
						sessionClicked = false;
					}
					else{
						gameView.setInfoText("Mache deine Schaetzung!");
					}
				}
			}
			
			if (this.playerID == 2){
				if(g.isPlayerAclicked()) {
					gameView.setEnemyPointer(g.getPlayerActPos(1));
				}
				if(g.isPlayerBclicked()){
					// if server has been asked, if the position is available, pointer has not been drawn
					gameView.setInfoText("Warte auf " + g.getPlayerName(1) + "!");
					gameView.setOwnPointer(g.getPlayerActPos(2));
				}
				else {
					if (sessionClicked){
						// if position was not available, player gets a second chance
						gameView.setInfoText("Position ist bereits belegt! Waehle eine andere Position!");
						sessionClicked = false;
					}
					else{
						gameView.setInfoText("Mache deine Schaetzung!");
					}
				}
			}
			
			break;

		//evaluation, who has won?
		case 5:
			System.out.println("both clicked:\t" + g.getPlayerActPos(1) + "\t" + g.getPlayerActPos(2));
			System.out.println("exercise number:\t" + openGame.getExerciseNumber());
			if (this.playerID == 2) {
				gameView.setOwnPointer(g.getPlayerActPos(2));
				gameView.setEnemyPointer(g.getPlayerActPos(1));
				gameView.showPointerText(g.getPlayerActPos(this.playerID),openGame.getExerciseNumber());
				gameView.showEnemyPointerText(g.getPlayerActPos(1),openGame.getExerciseNumber());
				gameView.setEnemyPoints(g.getPlayerPoints(1),g.getPlayerName(1));
				gameView.setPoints(g.getPlayerPoints(this.playerID),g.getPlayerName(2));
			}else{
				gameView.setOwnPointer(g.getPlayerActPos(1));
				gameView.setEnemyPointer(g.getPlayerActPos(2));
				gameView.showPointerText(g.getPlayerActPos(this.playerID),openGame.getExerciseNumber());
				gameView.showEnemyPointerText(g.getPlayerActPos(2),openGame.getExerciseNumber());
				gameView.setEnemyPoints(g.getPlayerPoints(2),g.getPlayerName(2));
				gameView.setPoints(g.getPlayerPoints(this.playerID),g.getPlayerName(1));
			}

			if (g.getWinnerOfLastRound() == 0) {
			
				gameView.setInfoText("Unentschieden!");
				
			}else if (g.getWinnerOfLastRound() == this.playerID ) {
				gameView.setInfoText("Du hast gewonnen!");
			}else{
				gameView.setInfoText(g.getPlayerName((this.playerID % 2) +1) + " hat gewonnen.");
			}

			break;
		}	
		
		if (g.getState() == 100 - ((this.playerID % 2) +1)) {
			
			gameView.setInfoText("Der andere Spieler hat das Spiel verlassen!");
			
			
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
			
			// check if other player's position has already been displayed
			int posOtherPlayer = openGame.getPlayerActPos(playerID%2+1);
			
			// ask server if it is available
			if (posOtherPlayer == Integer.MIN_VALUE){
				commServ.clickedAt(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID) + ":" + Integer.toString(x), updateCallback);
			}
			else {
				// it has been already displayed. Thus, we don't have to communicate with the server
				if (Math.abs(x - posOtherPlayer) < 12){
					this.sessionClicked = false;
					gameView.setInfoText("Position ist bereits belegt! Waehle eine andere Position!");
				}
				else {
					gameView.setOwnPointer(x);
					commServ.clickedAt(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID) + ":" + Integer.toString(x), updateCallback);
				}
			}
		}
	}

	public String escapeString(String str) {
		
		return str.replace(":", " ");
		
	}
	


	public void mouseMovedTo(int x, int y) {


		/**
		if (((openGame.getState() == 3 && this.playerID ==1) ||
				(openGame.getState() == 4 && this.playerID ==2))&&
				!sessionClicked) {

			x = gameView.rawPosToReal(x);
			gameView.setOwnPointer(x);
		}
		 **/

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


			if (result != null) {
				
				gameSelector.clearGameList();
				Iterator<NumberLineGameState> i = result.iterator();
				while(i.hasNext()) {

					gameSelector.addGame(i.next());
				}
				
			}
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
				gameSelector.clearGameList();
				Iterator<NumberLineGameState> i = result.iterator();
				while(i.hasNext()) {

					NumberLineGameState g = i.next();
					gameSelector.addGame(g);

				}

				gameSelector.setSelected(openedGame);
				gameSelector.joinGame();
			}
		}

	};





}
