package com.wicam.numberlineweb.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;
import com.wicam.numberlineweb.client.chat.ChatController;
import com.wicam.numberlineweb.client.chat.ChatCoordinator;
import com.wicam.numberlineweb.client.chat.ChatView;

public abstract class GameCoordinator implements ValueChangeHandler<String> {

	protected GameCommunicationServiceAsync commServ;
	protected ChatCommunicationServiceAsync chatCommServ;
	protected Panel rootPanel;
	protected AbstractGameSelector gameSelector;
	protected Timer t;
	protected GameView view;
	protected ChatCoordinator chatC;
	protected GameState openGame = null;
	protected GameState openedGame;
	protected int numberOfPlayers;
	protected int numberOfNPCs;
	protected int playerID;
	protected GameTypeSelector gts;
	protected long lastServerSendTime=-1;

	private int refreshIntervall;
	protected long latency = 0;
	protected long averageLatency=0;
	protected Queue<Long> lastTenLatencies = new LinkedList<Long>();
	protected long timeStamp = 0;
	protected HashMap<Long,Long> pingTimes= new HashMap<Long,Long>();
	
	protected HandlerRegistration handlerReg;


	/**
	 * Constructs the coordinator with comm-service and a rootPanel
	 * @param commServ
	 * @param root
	 */

	public GameCoordinator(GameCommunicationServiceAsync commServ, ChatCommunicationServiceAsync chatCommServ, Panel root, GameTypeSelector gts) {

		this.commServ = commServ;
		this.chatCommServ = chatCommServ;
		this.rootPanel = root;
		this.gts=gts;


	}
	
	/**
	 * Get the root panel of the coordinator.
	 * @return root panel
	 */
	public Panel getRootPanel() {
		return this.rootPanel;
	}

	/**
	 * Returns the instance of our current game type selector.
	 */
	public GameTypeSelector getGTS() {
		return gts;
	}

	/**
	 * Initializes the coordinator
	 */

	abstract public void init();

	/**
	 * Open a game name 'name'. Call back will get state of opened game
	 * @param name
	 */

	public void openGame(GameState gameState){
		GWT.log("opening!");
		
		this.numberOfPlayers = gameState.getMaxNumberOfPlayers();
		this.numberOfNPCs = gameState.getNumberOfMaxNPCs();
		
		gameState.setGameOpenedUserId(NumberLineWeb.USERID);
		
		commServ.openGame(gameState, gameOpenedCallBack);
	}

	/**
	 * Returns the game's name. Must be overwritten.
	 * @param name
	 */


	abstract public String getGameName();



	/**
	 * Close a game
	 * @param name
	 */

	public void handleCloseGameState(){
		commServ.leaveGame(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
		if (this.numberOfPlayers > 1){
			RootPanel.get("chatwrap").getElement().getStyle().setVisibility(Visibility.HIDDEN);
			RootPanel.get("chat").clear();
		}
		rootPanel.clear();
		this.view = null;
		lastServerSendTime = -1;
		restartGame();
	}
	
	protected void restartGame()
	{
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
	protected void joinedGame(int playerID, int gameID) {

		History.newItem("game-" + getGameName(),false);
		
		this.handlerReg = History.addValueChangeHandler(this);
		
	}
	
	/**
	 * Action in case of history back event.
	 * @param event
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if (event.getValue().matches("gameSelector.*")) {
			this.handlerReg.removeHandler();
			new GameCloseQuestion(this);
		}	
	}

	/**
	 * Called after game state was received.
	 * @param g
	 */
	protected void updateGame(GameState gameState){
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
	}

	protected void handleAwaitingReadyConfirm() {
		setRefreshRate(200);
		commServ.updateReadyness(Integer.toString(openGame.getId()) + ":" + Integer.toString(playerID), dummyCallback);
	}

	/**
	 * 
	 * @param gameState
	 */
	abstract protected void handleAwaitingStartState(GameState gameState);

	/**
	 * game state "waiting for players" has to be handled in this method
	 */
	abstract protected void handleWaitingForPlayersState();


	/**
	 * game state "waiting for other players" has to be handled in this method
	 */
	abstract protected void handleWaitingForOtherPlayersState(GameState g);


	/**
	 * basic implementation for game closed state
	 * 
	 * @param gameState		current game state
	 */
	protected void handleGameClosedState(GameState gameState) {
		setRefreshRate(2000);
		//TODO: close game
	}


	/**
	 * basic implementation for showing the highscore
	 * 
	 * @param gameState		current game state
	 */
	protected void handlePerformanceState(GameState gameState){
		HighScoreView h = new HighScoreView(openGame.getPlayers(),GameView.playerColors);
		rootPanel.clear();
		h.init(rootPanel);
	}


	/**
	 * basic implementation for player left case
	 * 
	 * @param g		current game state
	 */
	protected void handlePlayerLeftState(GameState g){
		// player has left the game

		Iterator<? extends Player> i = g.getPlayers().iterator();

		while (i.hasNext()) {

			Player current = i.next();

			int pid = g.getPlayers().indexOf(current)+1;
			GWT.log(Integer.toString(pid));
			GWT.log(Integer.toString(openGame.getPlayers().size()));

			if (current.hasLeftGame() && (openGame.getPlayers().size() >= pid &&!(openGame.getPlayers().get(pid-1).hasLeftGame()))) {
				showPlayerLeft(current.getName());
			}

		}
	}

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

		long id = (long) Math.random() * 500000;

		pingTimes.put(id, System.currentTimeMillis());

		timeStamp = System.currentTimeMillis();
		if (this.view != null) {
			commServ.update(Integer.toString(this.openGame.getId()) + ":" + Integer.toString(this.playerID) + ":" + id, updateCallback);
		}
	}

	/**
	 * refreshes the game lis Callback will redraw list
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

	public void calcAverageLatency() {


		Iterator<Long> it = lastTenLatencies.iterator();

		long temp= 0;

		while(it.hasNext()) {

			temp+=it.next();

		}

		averageLatency = temp / (lastTenLatencies.size());

		RootPanel.get("ping_view").getElement().setInnerHTML("avg. latency: " + averageLatency + " ms");

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
			Window.alert(caught.getMessage());
		}

		@Override
		public void onSuccess(GameState result) {
			openedGame = result;
			if (result.getServerSendTime() > lastServerSendTime) {

				lastServerSendTime = result.getServerSendTime();
				commServ.getOpenGames(openGamesAndJoinCallback);

			}else{

				GWT.log("Received an outlived game state, ignoring...");
				return;

			}


		}

	};

	protected AsyncCallback<GameState> updateCallback = new AsyncCallback<GameState>() {

		@Override
		public void onFailure(Throwable caught) {


		}

		@Override
		public void onSuccess(GameState result) {

			if (result != null) {

				if (pingTimes.containsKey(result.getPingId())) {
					latency = System.currentTimeMillis() -  pingTimes.get(result.getPingId());

					GWT.log("ping: " + Long.toString(latency) + "ms (average: " + averageLatency + "ms)");
				}

				if (result.getServerSendTime() >= lastServerSendTime) {

					lastServerSendTime = result.getServerSendTime();
					updateGame(result);


				}else{

					GWT.log("Received an outlived game state, ignoring...");

				}



				lastTenLatencies.add(latency);
				if (lastTenLatencies.size()>10) lastTenLatencies.poll();
				calcAverageLatency();
			}

		}

	};

	AsyncCallback<String> gameJoinedCallback = new AsyncCallback<String>() {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
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
			// Do nothing.
		}

		@Override
		public void onSuccess(Boolean result) {


		}

	};


	AsyncCallback<ArrayList<GameState>> openGamesCallback = new AsyncCallback<ArrayList<GameState>>() {

		@Override
		public void onFailure(Throwable caught) {
			// Do nothing.
		}

		@Override
		public void onSuccess(ArrayList<GameState> result) {

			gameSelector.setGameList(result);
		}

	};

	AsyncCallback<ArrayList<GameState>> openGamesAndJoinCallback = new AsyncCallback<ArrayList<GameState>>() {

		@Override
		public void onFailure(Throwable caught) {
			// Do nothing.
		}

		@Override
		public void onSuccess(ArrayList<GameState> result) {


			if (result != null) {

				gameSelector.setSelected(openedGame);
				gameSelector.joinGame();
			}
		}

	};

	protected class GameCloseQuestion extends DialogBox {
		
		GameCoordinator coord;

		public GameCloseQuestion(GameCoordinator coord) {
			// Set the dialog box's caption.
			setText("Möchtest du das Spiel wirklich verlassen?");

			this.setAnimationEnabled(true);
			this.coord = coord;

			// DialogBox is a SimplePanel, so you have to set its widget property to
			// whatever you want its contents to be.
			Button ok = new Button("Ja");
			Button no = new Button("Nein");
			ok.setWidth("60px");
			no.setWidth("60px");
			ok.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					GameCloseQuestion.this.hide();
					handleCloseGameState();
				}
			});
			no.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					History.newItem("game-" + getGameName(),false);
					GWT.log(this.getClass() + " pushed new HistoryToken: " + History.getToken());
					// The prior value change handler was removed when the user gave the "back" command,
					// so add it again now.
					History.addValueChangeHandler(GameCloseQuestion.this.coord);
					GameCloseQuestion.this.hide();
				}
			});

			HorizontalPanel p = new HorizontalPanel();

			p.setSize("230px", "40px");

			p.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			p.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

			p.add(ok);
			p.add(no);

			this.setWidget(p);
			this.center();
			this.show();
		}
	}

	public void showPlayerLeft(String playerName) {

		new PlayerLeftInfo(playerName);

	}

	private class PlayerLeftInfo extends DialogBox {

		public PlayerLeftInfo(String player) {

			setText(player + " hat das Spiel verlassen.");


			this.setAnimationEnabled(true);

			Button ok = new Button("OK");
			ok.setWidth("60px");

			ok.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					PlayerLeftInfo.this.hide();
				}
			});
			HorizontalPanel p = new HorizontalPanel();

			p.setSize("230px", "40px");
			p.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			p.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			p.add(ok);

			this.setWidget(p);
			this.center();
			this.show();
		}
	}




}
