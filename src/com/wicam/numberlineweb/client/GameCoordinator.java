package com.wicam.numberlineweb.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;
import com.wicam.numberlineweb.client.chat.ChatController;
import com.wicam.numberlineweb.client.chat.ChatCoordinator;
import com.wicam.numberlineweb.client.chat.ChatView;

public abstract class GameCoordinator {

	protected GameCommunicationServiceAsync commServ;
	protected ChatCommunicationServiceAsync chatCommServ;
	protected Panel rootPanel;
	protected GameSelector gameSelector;
	protected Timer t;
	protected GameView view;
	protected ChatCoordinator chatC;
	protected GameState openGame = null;
	protected GameState openedGame;
	protected int numberOfPlayers;
	protected int numberOfNPCs;
	protected int playerID;
	private int refreshIntervall;
	
	/**
	 * Constructs the coordinator with comm-service and a rootPanel
	 * @param commServ
	 * @param root
	 */

	public GameCoordinator(GameCommunicationServiceAsync commServ, ChatCommunicationServiceAsync chatCommServ, Panel root) {

		this.commServ = commServ;
		this.chatCommServ = chatCommServ;
		this.rootPanel = root;

	}
	
	/**
	 * Initializes the coordinator
	 */

	abstract public void init();
	
	/**
	 * Open a game name 'name'. Call back will get state of opened game
	 * @param name
	 */

	abstract public void openGame(GameState gameState);
	
	/**
	 * Close a game
	 * @param name
	 */

	public void closeGame(GameState g){
		System.out.println(g.getPlayerName(this.playerID) + " closed Game");
		commServ.leaveGame(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
		if (this.numberOfPlayers > 1){
			RootPanel.get("chatwrap").getElement().getStyle().setVisibility(Visibility.HIDDEN);
			RootPanel.get("chat").clear();
		}
		rootPanel.clear();
		init();
	}
	
	public void addChatView(){
		/*
		 * Optional: Add chat window from package "Chat"
		 */
		// chat only if more than 1 player

		ChatView chatView = new ChatView();
		chatC = new ChatCoordinator(openGame.getId(),chatView,chatCommServ);

		ChatController chatContr = new ChatController(chatC);
		chatView.addController(chatContr);
		chatView.init();
		
		RootPanel.get("chat").add(chatView);
		RootPanel.get("chatwrap").getElement().getStyle().setVisibility(Visibility.VISIBLE);
	}
	
	/**
	 * Called after our pĺayer joined the game.
	 * @param playerID
	 * @param gameID
	 */
	abstract protected void joinedGame(int playerID, int gameID);
	
	/**
	 * Called after game state was received.
	 * @param g
	 */
	abstract protected void updateGame(GameState gameState);
	
	/**
	 * sets the refresh rate of the main loop timer
	 */
	
	protected void setRefreshRate(int rate) {
		
		GWT.log("r-rate to " + rate);
		
		if (this.refreshIntervall != rate) {
			
			this.refreshIntervall = rate;
			t.scheduleRepeating(rate);
			
			
		}
			
	}
	
	/**
	 * Make update-call to server
	 */

	protected void update() {

		if (this.view != null) {
			commServ.update(Integer.toString(this.openGame.getId()) + ":" + Integer.toString(this.playerID), updateCallback);
		}
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
	 * join game #id with username 'name'
	 * for games without NPC
	 * @param id
	 * @param name
	 */

	public void joinGame(int id,String name, int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
		this.numberOfNPCs = 0;
		
		//we dont want anonymous players
		if (name.equals("")) name="Spieler";

		name = escapeString(name);

		commServ.joinGame(Integer.toString(id) + ":" + name, gameJoinedCallback);

	}
	
	
	public String escapeString(String str) {

		return str.replace(":", " ");

	}
	
	/**
	 * CALLBACKS.
	 * Callbacks are called after server-information was received
	 */

	protected AsyncCallback<GameState> gameOpenedCallBack = new AsyncCallback<GameState>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(GameState result) {

			openedGame = result;
			commServ.getOpenGames(openGamesAndJoinCallback);	

		}

	};

	protected AsyncCallback<GameState> updateCallback = new AsyncCallback<GameState>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(GameState result) {

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

	protected AsyncCallback<Boolean> dummyCallback = new AsyncCallback<Boolean>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(Boolean result) {


		}

	};


	AsyncCallback<ArrayList<GameState>> openGamesCallback = new AsyncCallback<ArrayList<GameState>>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(ArrayList<GameState> result) {


			gameSelector.setGameList(result);
		}

	};

	AsyncCallback<ArrayList<GameState>> openGamesAndJoinCallback = new AsyncCallback<ArrayList<GameState>>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(ArrayList<GameState> result) {


			if (result != null) {

				gameSelector.setSelected(openedGame);
				gameSelector.joinGame();
			}
		}

	};
	
}
