package com.wicam.numberlineweb.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wicam.numberlineweb.client.GameCommunicationService;
import com.wicam.numberlineweb.client.GameJoinException;
import com.wicam.numberlineweb.client.GameOpenException;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.server.database.drupal.DrupalCommunicator;
import com.wicam.numberlineweb.server.database.drupal.UserNotFoundException;
import com.wicam.numberlineweb.server.logging.GameLogger;
import com.wicam.numberlineweb.server.logging.GameLogger.LogActionTrigger;
import com.wicam.numberlineweb.server.logging.GameLogger.LogActionType;
import com.wicam.numberlineweb.server.logging.GameLogger.LoggingActive;
import com.wicam.numberlineweb.server.logging.IHandicap;

public abstract class GameCommunicationServiceServlet extends CustomRemoteServiceServlet implements GameCommunicationService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5789421149680201217L;

	protected ArrayList<GameState> openGames = new ArrayList<GameState>();
	private ArrayList<UpdateState> updateStates = new ArrayList<UpdateState>();
	private ArrayList<TimeOutState> timeOutStates = new ArrayList<TimeOutState>();
	private ArrayList<EmptyGameTimeOutState> emptyGameTimeOutStates = new ArrayList<EmptyGameTimeOutState>();
	protected IHandicap handicapAdjustment;
	GameCommunicationServiceServlet comm;

	protected Map<Integer, GameLogger> gameId2Logger = new HashMap<Integer, GameLogger>();
	
	boolean timeOutListLock = false;
	private Timer timeOutTimer = new Timer("TimerTimeOut", true);
	protected int currentId=0;
	int gamePending;
	protected String internalName;

	protected List<NPC> npcs = new ArrayList<NPC>();;
	
	public GameCommunicationServiceServlet (String internalName){
		timeOutTimer.scheduleAtFixedRate(new TimeOutCheckerTask(getTimeOutStates(),getEmptyGameTimeOutStates(), this), 0, 4000);
		this.internalName=internalName;
	}

	@Override
	public ArrayList<GameState> getOpenGames() {
		return openGames;		
	}

	@Override
	public GameState openGame(GameState g) throws GameOpenException {
		// Allow only one game at a time being played on the server.
//		System.out.println("Es ist gerade " + this.getOpenGames().size() + " Spiel offen.");
		if (this.getOpenGames().size() > 0) {
			throw new GameOpenException("Es kann gerade kein Spiel geöffnet werden, da die Platform bereits von einem anderen Spieler genutzt wird! Bitte versuche es später noch einmal.");
		}
		
		currentId++;

		g.setGameId(currentId);

		openGames.add(g);
		//add game to empty game time out list
		System.out.println("Added game #" + currentId + " to empty game timeouts.");
		emptyGameTimeOutStates.add(new EmptyGameTimeOutState(currentId,20));
		
		System.out.println("Opened Game " + Integer.toString(currentId));
		
		System.out.println("User id: " + g.getGameOpenedUserId());
		
		this.gameId2Logger.put(currentId, new GameLogger());
		
		if (this.gameId2Logger.containsKey(currentId))
			this.gameId2Logger.get(currentId).log(currentId, System.currentTimeMillis(), LogActionType.GAME_STARTED, "", 
					this.getClass().getName(), LogActionTrigger.APPLICATION);
		
		return g;
	}

	public void endGame(int id) {

		Timer t = new Timer("TimerEndGame", true);
		
		String gamePropertiesStr = this.getGameProperties(this.getGameById(id));
		
		this.gameId2Logger.get(id).updateGameProperties(id, this.getClass().getName(), gamePropertiesStr);
		
		this.gameId2Logger.get(id).log(id, System.currentTimeMillis(), LogActionType.GAME_ENDED, "", 
				this.getClass().getName(), LogActionTrigger.APPLICATION);

		this.writeLogToDatabase(id);
		
		this.terminateNPCTimers();
		
		// winner screen
		t.schedule(new SetGameStateTask(id, 97, this), 6000);
		
	}

	/**
	 * Join game with the given ids, given as a string:
	 * <gameid>:<username>
	 * 
	 * Returns gameid and username in the same format. Username
	 * may have been changed when already used.
	 * 
	 */

	@Override
	@SuppressWarnings("unchecked")
	public String joinGame(String ids) throws GameJoinException {

		String player = ids.split(":")[1];
		int uid = -2;
		int id = Integer.parseInt(ids.split(":")[0]);

		if (player.split("/")[0].equals("___ID")) {

			uid = Integer.parseInt(player.split("/")[1]);
			if (uid == -2) player = "Gast"; else{
				DrupalCommunicator dc = new DrupalCommunicator();
				try {
					player = dc.getUser(uid).getUname();
				} catch (UserNotFoundException e) {
					throw new GameJoinException("User with id =" + uid + " could not be found.");
				}
			}

		}
		
		GameState game = getGameById(id);

		HttpServletRequest request = this.getThreadLocalRequest();
		HashMap<String,HashMap<Integer,Integer>> games;
		HashMap<Integer,Integer> pids;
		HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>> game2pid2uid;

		HttpSession session = request.getSession(true);

		if (session.isNew()) {
			games = new HashMap<String,HashMap<Integer,Integer>>();
			pids = new HashMap<Integer,Integer>();
			game2pid2uid = new HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>();
			
			games.put(internalName, pids);
			
			game2pid2uid.put(internalName, new HashMap<Integer, HashMap<Integer, Integer>>());
			game2pid2uid.get(internalName).put(id, new HashMap<Integer, Integer>());
			
			session.setAttribute("pids",games);
			session.setAttribute("game2pid2uid", game2pid2uid);

		}else{

			games = (HashMap<String,HashMap<Integer,Integer>>) session.getAttribute("pids");
			pids = games.get(internalName);
			
			game2pid2uid = (HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>) session.getAttribute("game2pid2uid");

			if (pids == null) {

				pids = new HashMap<Integer,Integer>();
				games.put(internalName,pids);

			}
			
			if (game2pid2uid == null){
				
				game2pid2uid = new HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>();
				game2pid2uid.put(internalName, new HashMap<Integer, HashMap<Integer, Integer>>());
				game2pid2uid.get(internalName).put(id, new HashMap<Integer, Integer>());
				
			}
			
			if (game2pid2uid.get(internalName) == null){
				
				game2pid2uid.put(internalName, new HashMap<Integer, HashMap<Integer, Integer>>());
				
			}
			
			if (game2pid2uid.get(internalName).get(id) == null){
				
				game2pid2uid.get(internalName).put(id, new HashMap<Integer, Integer>());
			}

		}

		//we dont want the same human player to play multiple players
		if (pids.containsKey(id)) {
			throw new GameJoinException("Du bist bereits in diesem Spiel!");
		}

		System.out.println("Player '" + player + "' joined game #" + Integer.toString(id));

		
		
		//only join if free and not yet started...
		if (game.isFree() && game.getState() < 2) {

			int playerid = game.addPlayer(player, uid);
			

			if (game.isFree()) {
				setGameState(getGameById(game.getId()),1);

			}else{
				// add NPCs
				for (int i = 0; i < game.getNumberOfMaxNPCs(); i++)
					addNPC(game);
				setGameState(getGameById(game.getId()),2);
			}

			pids.put(id, playerid);
			request.getSession(true).setAttribute("pids", games);

			game2pid2uid.get(internalName).get(id).put(playerid, uid);
			request.getSession(true).setAttribute("game2pid2uid", game2pid2uid);
			
			//add this user to the update-state list

			getUpdateStates().add(new UpdateState(playerid,game.getId(),false));

			//add this user to the timeout list

			getTimeOutStates().add(new TimeOutState(uid, playerid, game.getId(),5));
			
			if(uid != -2){
				this.gameId2Logger.get(id).log(game.getId(), uid, System.currentTimeMillis(), LogActionType.JOINED_GAME, 
						"", this.getClass().getName(), LogActionTrigger.USER);
				
				//TODO Decide how game adjustment should be handled
				//Adjust game to user's ELO value
//				this.adjustToElo(uid, game);
			}

			return game.getId() + ":" + playerid;

		}

		throw new GameJoinException("Das Spiel ist voll.");

	}

	protected void addNPC(GameState game){}

	protected boolean isNPC(int playerId){
		return false;
	}

	/**
	 * get a gamestate by its game-ID
	 * @param i
	 * @return
	 */

	@Override
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

	protected void resetUpdateTimer(int player, int game) {



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


	protected void removeGame(int gameid) {


		openGames.remove(getGameById(gameid));

		Iterator<UpdateState> i = getUpdateStates().iterator();

		removePlayerId(gameid);

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
		
		this.terminateNPCTimers();
		
		//Rollback changes and close database connection
		GameLogger logger = this.gameId2Logger.get(gameid);
		
		if (!logger.connectionClosed()) {
		
			logger.rollbackChanges();
			logger.closeConnection();
		
		}

		System.out.println("Open games: " + this.openGames.size());
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

	void leavePlayer(int uid, int playerid, int gameid) {

		if(uid != -2)
			this.gameId2Logger.get(gameid).log(gameid, uid, System.currentTimeMillis(), LogActionType.LEFT_GAME, 
					"", this.getClass().getName(), LogActionTrigger.USER);

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
	@Override
	public GameState update(String ids) {


		final int id = Integer.parseInt(ids.split(":")[0]);
		final int pingid = Integer.parseInt(ids.split(":")[2]);
		final int player = getPlayerId(id);

		resetUpdateTimer(player, id);

		//we only want to create network traffic if something has changed
		if (!this.isUpToDate(id,player)) {
			setUpToDate(id,player);

			GameState g = getGameById(id);
			
			if (g == null)
				return null;

			//tell the client when this state was sent to prevent asynchronous
			//processing
			g.setServerSendTime(System.currentTimeMillis());

			g.setPingId(pingid);
			return g;
		}else{
			//otherwise, just send null
			return null;

		}
	}

	@Override
	synchronized public boolean updateReadyness(String ids) {

		int gameid = Integer.parseInt(ids.split(":")[0]);
		return updateReadyness(ids,getPlayerId(gameid));

	}

	/**
	 * <gameid>:<playerid>
	 */
	synchronized public boolean updateReadyness(String ids, int playerid) {

		int gameid = Integer.parseInt(ids.split(":")[0]);

		System.out.println(gameid + "\t" + playerid);
		GameState g = getGameById(gameid);

		g.setPlayerReady(playerid,true);

		boolean allReady = true;
		for (int i = 0; i < g.getPlayers().size(); i++){
			if (!g.isPlayerReady(i+1))
				allReady = false;
		}

		//we cant be ready if game isnt full yet
		if (g.getMaxNumberOfPlayers() > g.getPlayers().size()) allReady = false;

		if (allReady) setGameState(g, 3);


		setChanged(g.getId());

		//we dont want a player to get the update sooner than the other
		
		return true;
	}
	
	@Override
	synchronized public boolean loggingOn(boolean b){
		if(b)
			GameLogger.loggingActive = LoggingActive.ON;
		else
			GameLogger.loggingActive = LoggingActive.OFF;
		return true;
	}

	/**
	 * Sets the game state and mark game as updated.
	 * @param g
	 * @param state
	 */

	public void setGameState(GameState g, int state) {

		if (g != null) {
			
			g.setState(state);
			setChanged(g.getId());
		}

	}

	@Override
	public boolean leaveGame(String ids){

		int gameid = Integer.parseInt(ids.split(":")[0]);
		int playerid = getPlayerId(gameid);
		return leaveGame(ids,playerid);

	}


	public boolean leaveGame(String ids, int playerid){

		int gameid = Integer.parseInt(ids.split(":")[0]);

		GameState g = getGameById(gameid);

		g.setHasLeftGame(playerid, true);

		setGameState(getGameById(gameid),99);
		setChanged(g.getId());
		if (!isNPC(playerid)) removePlayerId(gameid);

		boolean allLeft = true;
		for (int i = 0; i < g.getPlayers().size()-g.getNumberOfMaxNPCs(); i++){
			if (!g.getHasLeftGame(i+1))
				allLeft = false;
		}

//		System.out.println("all left: " + allLeft);
		
		// remove game if all players left
		// TODO This might be buggy if not all the players leave the game by ending it but by hitting
		// the back-button of their browser?
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

	public ArrayList<EmptyGameTimeOutState> getEmptyGameTimeOutStates() {
		return emptyGameTimeOutStates;
	}

	protected int getPlayerId(int gameid) throws NullPointerException {

		// Check the idMap object not to be null and try to get it as often as
		// necessary so that it won't be null.
		HashMap<String,HashMap<Integer,Integer>> idMap = null;
		while (idMap == null) {
			HttpServletRequest request = this.getThreadLocalRequest();
	
			idMap = (HashMap<String,HashMap<Integer,Integer>>) request.getSession().getAttribute("pids");
		}
		
		// Test if there is a table for the given internal name, e.g. the game name.
		// assert (idMap.get(internalName) != null) : "internal name: " + internalName + ", gameid: " + gameid + "idMap:\n" + idMap;
		return idMap.get(internalName).get(gameid);

	}

	protected void removePlayerId(int gameid) {

		HttpServletRequest request = this.getThreadLocalRequest();
		
		// TODO Does this method do its job in a deterministic fashion? What about the two null-checks? What if they don't become true?
		if(request != null){

			HashMap<String,HashMap<Integer,Integer>> idMap = (HashMap<String,HashMap<Integer,Integer>>) request.getSession().getAttribute("pids");
			if (idMap.get(internalName) != null) idMap.get(internalName).remove(gameid);
			request.setAttribute("pids", idMap);
			
		}

	}
	
//	private void adjustToElo(int uid, GameState game){
//		int eloValue;
//		eloValue = this.gameId2Logger.get(currentId).getEloRating(uid);
//		this.handicapAdjustment.adjustGameSetting(eloValue, game);
//
//	}
	
	protected void writeLogToDatabase(int gameid) {
		
		GameState g = this.getGameById(gameid);
		GameLogger logger = this.gameId2Logger.get(gameid);
		
		if (g.getGameOpenedUserId() > 0)
			logger.commitChanges();
		else
			logger.rollbackChanges();
		
		logger.closeConnection();
		
	}
	
	protected void terminateNPCTimers() {
		
		for (NPC npc : this.npcs)
			npc.terminateTimer();
			
	}
	
	//This method is overridden to prevent security exceptions
	//caused by Firefox not adding the X-GWT-PERMUTATION header 
	//(see http://code.google.com/p/gwteventservice/issues/detail?id=30).
	@Override
	protected void checkPermutationStrongName() throws SecurityException {
	    return;
	}

}
