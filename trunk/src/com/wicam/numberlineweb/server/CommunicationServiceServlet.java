package com.wicam.numberlineweb.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;



import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wicam.numberlineweb.client.CommunicationService;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;
import com.wicam.numberlineweb.client.chatView.ChatMsg;

/**
 * Servlet for the number line game. Everthing server-side is handled here
 * @author patrick
 *
 */ 

public class CommunicationServiceServlet extends RemoteServiceServlet implements CommunicationService{

	private static final long serialVersionUID = -7771441559779705194L;
	ArrayList<NumberLineGameState> openGames = new ArrayList<NumberLineGameState>();
	ArrayList<UpdateState> updateStates = new ArrayList<UpdateState>();
	ArrayList<ChatMsg> chatMsgs = new ArrayList<ChatMsg>();
	ArrayList<TimeOutState> timeOutStates = new ArrayList<TimeOutState>();

	boolean timeOutListLock = false;
	

	private Timer timeOutTimer = new Timer();




	int currentId=0;
	int gamePending;


	public CommunicationServiceServlet() {

		timeOutTimer.scheduleAtFixedRate(new TimeOutCheckerTask(timeOutStates, this), 0, 4000);



	}

	public ArrayList<NumberLineGameState> getOpenGames() {
		return openGames;		
	}


	private void startGame(int id) {

		Timer t = new Timer();

		//wait 6 seconds for users to be ready
		t.schedule(new SetGameStateTask(id, 21, this), 6000);


	}

	private void endGame(int id) {

		Timer t = new Timer();

		//wait 6 seconds for users to be ready
		t.schedule(new SetGameStateTask(id, 6, this), 6000);


	}
	
	/**
	 * Calculate new number & new exercise
	 * @param game
	 */
	void newNumbers(NumberLineGameState game) {

		int leftNumber;
		int rightNumber;
		int exerciseNumber;

		leftNumber = (int) (Math.random() * 900);
		rightNumber = (int) (Math.random() * (1000-leftNumber)) + leftNumber;
		exerciseNumber = leftNumber + ((int) (Math.random() * (rightNumber - leftNumber))) ;

		game.setLeftNumber(leftNumber);
		game.setRightNumber(rightNumber);
		game.setExerciseNumber(exerciseNumber);

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

		NumberLineGameState game = getGameById(id);

		
		//only join if free and not yet started...
		if (game.isFree() && game.getState() < 2) {

			int playerid = game.addPlayer(player);

			if (game.isFree()) {
				setGameState(getGameById(game.getId()),1);

			}else{
				setGameState(getGameById(game.getId()),2);
				startGame(id);
			}

			//add this user to the update-state list
	
			
			updateStates.add(new UpdateState(playerid,game.getId(),false));
			
			//add this user to the timeout list

		
			timeOutStates.add(new TimeOutState(playerid, game.getId(),5));

			
			return game.getId() + ":" + playerid;

		}

		return "Game full.";

	}

	/**
	 * opens a game with the give state
	 */

	public NumberLineGameState openGame(NumberLineGameState game) {

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

	public NumberLineGameState getGameById(int i) {

		Iterator<NumberLineGameState> it = openGames.iterator();

		while (it.hasNext()) {
			NumberLineGameState g=it.next();

			if (g.getId() == i) return g;

		}

		return null;

	}

	/**
	 * Set the status for a given game-ID to changed. 
	 * @param gameID
	 */

	public void setChanged(int gameID) {

		

		Iterator<UpdateState> i = updateStates.iterator();

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

		
		Iterator<UpdateState> i = updateStates.iterator();

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

		

		Iterator<UpdateState> i = updateStates.iterator();

		while(i.hasNext()) {

			UpdateState s = i.next();

			if (s.getGameId() == id && s.getPlayerID() == player) {
				return s.isActual();
			}

		}
		
		return false;
	}


	private void resetUpdateTimer(int player, int game) {



		Iterator<TimeOutState> i = timeOutStates.iterator();

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

		Iterator<UpdateState> i = updateStates.iterator();


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

		Iterator<TimeOutState> it = timeOutStates.iterator();


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

		if (getGameById(gameid).getPlayerCount() == 0) {

			removeGame(gameid);
			

		}


		this.setChanged(gameid);

	}

	/**
	 * Send game states to users
	 */
	@Override
	public NumberLineGameState update(String ids) {

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
	 * User has clicked. Again, the format for clicked ist
	 * <gameid>:<playerid>:<clickPosition>
	 */
	@Override
	synchronized public NumberLineGameState clickedAt(String clicked) {

		int playerid = Integer.parseInt(clicked.split(":")[1]);
		int gameid = Integer.parseInt(clicked.split(":")[0]);
		int clickedAt = Integer.parseInt(clicked.split(":")[2]);
		NumberLineGameState g = getGameById(gameid);

		
		
		if (!g.isPlayerClicked(playerid)){
			boolean posIsFree = true;
			for (int i = 0; i < g.getPlayers().size(); i++){
				if (i+1 != playerid){
					int posOtherPlayer = g.getPlayerActPos(i+1);
				
					if (!(posOtherPlayer == Integer.MIN_VALUE) && Math.abs(clickedAt - posOtherPlayer) < g.getPointerWidth())
						posIsFree = false;
						
				}
			}
			if (posIsFree){
				//save position for playerid
				g.setPlayerActPos(playerid,clickedAt);
				g.setPlayerClicked(playerid);
				// change state if waiting for other player
				boolean otherPlayersClicked = true;
				for (int i = 0; i < g.getPlayers().size(); i++){
					if (i+1 != playerid)
						if (!g.isPlayerClicked(i+1))
							otherPlayersClicked = false;
				}
				if (!otherPlayersClicked){
					setGameState(getGameById(gameid),4);
					this.setChanged(gameid);
				}
			}
			else {
				setGameState(getGameById(gameid),4);
				this.setChanged(gameid);
			}
		}

		boolean allPlayersClicked = true;
		for (int i = 0; i < g.getPlayers().size(); i++)
			if (!g.isPlayerClicked(i+1))
				allPlayersClicked = false;
		
		if (allPlayersClicked){
			setGameState(getGameById(gameid),5);

			// reset clicked state
			g.resetPlayersClicked();

			// reset ready state
			g.resetReadyness();

			//who has won?

			ArrayList<Integer> playersWithMinDiff = new ArrayList<Integer>();
			double minDiff = Double.MAX_VALUE;
			for (int i = 0; i < g.getPlayers().size(); i++){
				double curDiff = Math.abs(rawPosToReal(getGameById(gameid).getPlayerActPos(i+1),g)- getGameById(gameid).getExerciseNumber());
				if (curDiff < minDiff){
					minDiff = curDiff;
					// reset list
					playersWithMinDiff = new ArrayList<Integer>();
					// add player
					playersWithMinDiff.add(i+1);
				}
				// add other player
				else if (curDiff == minDiff)
					playersWithMinDiff.add(i+1);
			}
			
			if (playersWithMinDiff.size() > 1){
				//draw
				getGameById(gameid).setWinnerOfLastRound(0);
				System.out.println("Unentschieden :)");
				for (Integer i: playersWithMinDiff){
					getGameById(gameid).setPlayerPoints(i,getGameById(gameid).getPlayerPoints(i) +1);
				}
			}else {
				// one player best
				
				getGameById(gameid).setWinnerOfLastRound(playersWithMinDiff.get(0));
				getGameById(gameid).setPlayerPoints(playersWithMinDiff.get(0),getGameById(gameid).getPlayerPoints(playersWithMinDiff.get(0)) +1);
				System.out.println(getGameById(gameid).getPlayerName(playersWithMinDiff.get(0))+ " hat gewonnen");
			}

			//restart 
			if (getGameById(gameid).getItemCount() == getGameById(gameid).getMaxItems()){
				endGame(gameid);
			}
			else
				startGame(gameid);
		}

		return getGameById(gameid);
	}


	/**
	 * TODO: there must be a better solution...
	 * <gameid>:<playerid>
	 */
	public boolean updateReadyness(String ids) {

		int playerid = Integer.parseInt(ids.split(":")[1]);
		int gameid = Integer.parseInt(ids.split(":")[0]);
		NumberLineGameState g = getGameById(gameid);

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
	 * converts a real cursor-position (the one that is displayed to the user) into
	 * a raw pixel-position
	 * @param pos
	 * @param g
	 * @return
	 */

	public int realPosToRaw(int pos, NumberLineGameState g) {

		return (int)((pos -g.getLeftNumber()) /  ((double)(g.getRightNumber() -g.getLeftNumber())/400));

	}

	/**
	 * converts the raw pixel-position to a user-readable value.
	 * @param pos
	 * @param g
	 * @return
	 */

	public int rawPosToReal(int pos,NumberLineGameState g) {

		return g.getLeftNumber() + (int) ((pos) *  ((double)(g.getRightNumber() - g.getLeftNumber())/400));


	}

	/**
	 * Sets the game state and mark game as updated.
	 * @param g
	 * @param state
	 */

	public void setGameState(NumberLineGameState g, int state) {

		g.setState(state);
		setChanged(g.getId());

	}
	
	// TODO: comment it
	public boolean leaveGame(String ids){
		int playerid = Integer.parseInt(ids.split(":")[1]);
		int gameid = Integer.parseInt(ids.split(":")[0]);
		NumberLineGameState g = getGameById(gameid);
		
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


	/**
	 * CHAT FUNCTIONALITY
	 * ==================
	 * 
	 * TODO: use extra classes for this.
	 */

	public boolean sendChatMsg(ChatMsg msg) {


		chatMsgs.add(msg);

		return false;
	}

	public ChatMsg popNewMsgs(int gameid, String uname) {

		Iterator<ChatMsg> i = chatMsgs.iterator();

		while(i.hasNext()) {

			ChatMsg act = i.next();

			if (act.getGameID() == gameid && !act.getFrom().equals(uname)) {

				chatMsgs.remove(act);
				return act;

			}

		}

		return null;
	}


	@Override
	public ChatMsg[] getNewChatMsgs(String ids) {

		String uname = ids.split(":")[1];
		int gameid = Integer.parseInt(ids.split(":")[0]);



		ArrayList<ChatMsg> temp = new ArrayList<ChatMsg>();

		ChatMsg a = popNewMsgs(gameid, uname);

		while (a != null) {

			temp.add(a);
			a = popNewMsgs(gameid, uname);

		}

		ChatMsg ret[] = new ChatMsg[temp.size()];
		ret = temp.toArray(ret);

		return ret;


	}





}
