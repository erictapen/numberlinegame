package com.wicam.numberlineweb.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wicam.numberlineweb.client.GameCommunicationService;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;

public abstract class GameCommunicationServiceServlet extends RemoteServiceServlet implements GameCommunicationService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5789421149680201217L;

	protected ArrayList<GameState> openGames = new ArrayList<GameState>();
	private ArrayList<UpdateState> updateStates = new ArrayList<UpdateState>();
	private ArrayList<TimeOutState> timeOutStates = new ArrayList<TimeOutState>();
	GameCommunicationServiceServlet comm;
	
	boolean timeOutListLock = false;
	private Timer timeOutTimer = new Timer();
	protected int currentId=0;
	int gamePending;
	
	public GameCommunicationServiceServlet (){
		timeOutTimer.scheduleAtFixedRate(new TimeOutCheckerTask(getTimeOutStates(), this), 0, 4000);
	}
	
	public ArrayList<GameState> getOpenGames() {
		return openGames;		
	}


	public void startGame(int id) {

		Timer t = new Timer();

		//wait 6 seconds for users to be ready
		t.schedule(new SetGameStateTask(id, 21, this), 6000);
	}
	
	@Override
	public GameState openGame(GameState g) {
		currentId++;

		g.setGameId(currentId);

		openGames.add(g);

		System.out.println("Opend Game " + Integer.toString(currentId));
		
		return g;
	}

	public void endGame(int id) {

		Timer t = new Timer();

		// winner screen
		t.schedule(new SetGameStateTask(id, 6, this), 6000);
		// close game
		t.schedule(new SetGameStateTask(id, 7, this), 6000+15000);


	}

	/**
	 * Join game with the given ids, given as a string:
	 * <gameid>:<username>
	 * 
	 * Returns gameid and username in the same format. Username
	 * may have been changed when already used.
	 * 
	 */

	public String joinGame(String ids) {

		String player = ids.split(":")[1];
		int id = Integer.parseInt(ids.split(":")[0]);

		System.out.println("Player '" + player + "' joined game #" + Integer.toString(id));

		GameState game = getGameById(id);

		
		//only join if free and not yet started...
		if (game.isFree() && game.getState() < 2) {

			int playerid = game.addPlayer(player);

			if (game.isFree()) {
				setGameState(getGameById(game.getId()),1);

			}else{
				// add NPCs
				for (int i = 0; i < game.getNumberOfMaxNPCs(); i++)
					addNPC(game);
				setGameState(getGameById(game.getId()),2);
				startGame(id);
			}

			//add this user to the update-state list
			
			getUpdateStates().add(new UpdateState(playerid,game.getId(),false));
			
			//add this user to the timeout list
		
			getTimeOutStates().add(new TimeOutState(playerid, game.getId(),5));
			
			return game.getId() + ":" + playerid;

		}

		return "Game full.";

	}

	abstract protected void addNPC(GameState game);
	
	/**
	 * opens a game with the give state
	 */

	public NumberLineGameState openNumberLineGame(NumberLineGameState game) {

		currentId++;

		game.setGameId(currentId);

		//this can later be made changeable
		game.setPointerWidth(12);

		openGames.add(game);

		System.out.println("Opend Game " + Integer.toString(currentId));
		
		return game;

	}

	/**
	 * get a gamestate by its game-ID
	 * @param i
	 * @return
	 */

	public GameState getGameById(int i) {

		Iterator<GameState> it = openGames.iterator();

		while (it.hasNext()) {
			GameState g=it.next();

			if (g.getId() == i) return g;

		}

		return null;

	}

	/**
	 * Set the status for a given game-ID to changed. 
	 * @param gameID
	 */

	public void setChanged(int gameID) {

		

		Iterator<UpdateState> i = getUpdateStates().iterator();

		while(i.hasNext()) {

			UpdateState s = i.next();

			if (s.getGameId() == gameID) {
				s.setActual(false);
			}

		}
		
		

	}

	/**
	 * Sets the update-state for a given user and game to
	 * "up to date". The user will not receive any new
	 * gamestate-packages, until the state changes to
	 * not up to date again.
	 * @param id
	 * @param player
	 */

	public void setUpToDate(int id, int player) {

		
		Iterator<UpdateState> i = getUpdateStates().iterator();

		while(i.hasNext()) {

			UpdateState s = i.next();

			if (s.getGameId() == id && s.getPlayerID() == player) {
				s.setActual(true);
			}
		}

	}

	/**
	 * Is the given user up to date?
	 * @param id
	 * @param player
	 * @return
	 */

	public boolean isUpToDate(int id, int player) {

		

		Iterator<UpdateState> i = getUpdateStates().iterator();

		while(i.hasNext()) {

			UpdateState s = i.next();

			if (s.getGameId() == id && s.getPlayerID() == player) {
				return s.isActual();
			}

		}
		
		return false;
	}


	private void resetUpdateTimer(int player, int game) {



		Iterator<TimeOutState> i = getTimeOutStates().iterator();

		TimeOutState current;

		while (i.hasNext()) {


			current = i.next();


			if (current.getGameId() == game && current.getPlayerId() == player) {

				current.reset();
				return;

			}


		}
		
	


	}


	private void removeGame(int gameid) {

		openGames.remove(getGameById(gameid));

		Iterator<UpdateState> i = getUpdateStates().iterator();


		while (i.hasNext()) {

			UpdateState current = i.next();

			if (current.getGameId() == gameid) {
				i.remove();
				System.out.println("Removed game #" + gameid);
				break;
			}


		}
		
		

		while (timeOutListLocked()) {


		}

		timeOutListLock();

		Iterator<TimeOutState> it = getTimeOutStates().iterator();


		while (it.hasNext()) {

			TimeOutState current = it.next();

			if (current.getGameId() == gameid) it.remove();


		}

		timeOutListUnLock();


	}

	boolean timeOutListLocked() {

		return timeOutListLock;

	}

	void timeOutListLock() {

		timeOutListLock = true;

	}

	void timeOutListUnLock() {

		timeOutListLock = false;

	}

	void leavePlayer(int playerid, int gameid) {



		setGameState(getGameById(gameid),99);
		getGameById(gameid).setHasLeftGame(playerid,true);

		if ((getGameById(gameid).getPlayerCount()-getGameById(gameid).getNumberOfMaxNPCs()) <= 0) {

			removeGame(gameid);
			

		}


		this.setChanged(gameid);

	}

	/**
	 * Send game states to users
	 */
	public GameState update(String ids) {

		final int player = Integer.parseInt(ids.split(":")[1]);
		final int id = Integer.parseInt(ids.split(":")[0]);

		resetUpdateTimer(player, id);

		//we only want to create network traffic is something has changes
		if (!this.isUpToDate(id,player)) {
			setUpToDate(id,player);
			return getGameById(id);
		}else{
			//otherwise, just send null
			return null;

		}
	}
	
	/**
	 * <gameid>:<playerid>
	 */
	synchronized public boolean updateReadyness(String ids) {
		int playerid = Integer.parseInt(ids.split(":")[1]);
		int gameid = Integer.parseInt(ids.split(":")[0]);
		GameState g = getGameById(gameid);
		
		g.setPlayerReady(playerid,true);

		boolean allReady = true;
		for (int i = 0; i < g.getPlayers().size(); i++){
			if (!g.isPlayerReady(i+1))
				allReady = false;
		}
		
		if (allReady) setGameState(g, 3);


		setChanged(g.getId());

		//we dont want a player to get the update sooner than the other
		return true;
	}
	
	
	/**
	 * Sets the game state and mark game as updated.
	 * @param g
	 * @param state
	 */

	public void setGameState(GameState g, int state) {

		g.setState(state);
		setChanged(g.getId());

	}
	
	// TODO: comment it
	public boolean leaveGame(String ids){
		int playerid = Integer.parseInt(ids.split(":")[1]);
		int gameid = Integer.parseInt(ids.split(":")[0]);
		GameState g = getGameById(gameid);
		
		g.setHasLeftGame(playerid, true);
		setChanged(g.getId());
		
		boolean allLeft = true;
		for (int i = 0; i < g.getPlayers().size(); i++){
			if (!g.getHasLeftGame(i+1))
				allLeft = false;
		}
		
		// remove game if all players left
		if(allLeft)
			this.removeGame(gameid);
		
		return true;
	}

	public ArrayList<UpdateState> getUpdateStates() {
		return updateStates;
	}

	public ArrayList<TimeOutState> getTimeOutStates() {
		return timeOutStates;
	}
}
