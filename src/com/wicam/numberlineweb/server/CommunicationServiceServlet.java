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

	Timer timeOutTimer = new Timer();




	int currentId=0;
	int gamePending;


	public CommunicationServiceServlet() {

		timeOutTimer.scheduleAtFixedRate(new TimeOutCheckerTask(timeOutStates, this), 0, 1000);



	}

	public ArrayList<NumberLineGameState> getOpenGames() {
		return openGames;		
	}


	private void startGame(int id) {

		NumberLineGameState game = getGameById(id);

		newNumbers(game);

		Timer t = new Timer();

		//wait 6 seconds for users to be ready
		t.schedule(new SetGameStateTask(id, 3, this), 6000);


	}

	/**
	 * Calculate new number & new exercise
	 * @param game
	 */
	private void newNumbers(NumberLineGameState game) {

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

		//only join if free...
		if (game.isFree()) {

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

			timeOutStates.add(new TimeOutState(playerid, game.getId(),6));

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

			System.out.println("++ " + current.getGameId() + " ++ " +  current.getPlayerId() );

			if (current.getGameId() == game && current.getPlayerId() == player) {

				System.out.println("Player #" + player + " in game #" + game + " resetted.");
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

			if (current.getGameId() == gameid) updateStates.remove(current);


		}

		while (timeOutListLocked()) {
			
			
		}
		
		timeOutListLock();
		
		Iterator<TimeOutState> it = timeOutStates.iterator();


		while (it.hasNext()) {

			TimeOutState current = it.next();

			if (current.getGameId() == gameid) timeOutStates.remove(current);


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

	
		timeOutListLock();

		setGameState(getGameById(gameid),100-playerid);
		getGameById(gameid).removePlayer(playerid);

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
	public boolean clickedAt(String clicked) {

		int playerid = Integer.parseInt(clicked.split(":")[1]);
		int gameid = Integer.parseInt(clicked.split(":")[0]);
		int clickedAt = Integer.parseInt(clicked.split(":")[2]);
		NumberLineGameState g = getGameById(gameid);

		if (playerid == 1){
			//save position for player 1
			g.setPlayerActPos(1,clickedAt);
			g.setPlayerAclicked(true);
			// change state if waiting for other player
			if (!g.isPlayerBclicked()){
				setGameState(getGameById(gameid),4);
				this.setChanged(gameid);
			}
		}

		if (playerid == 2){
			//save position for player 1
			g.setPlayerActPos(2,clickedAt);
			g.setPlayerBclicked(true);
			// change state if waiting for other player
			if (!g.isPlayerAclicked()){
				setGameState(getGameById(gameid),4);
				this.setChanged(gameid);
			}
		}

		System.out.println("KLICK: " + Integer.toString(clickedAt) +" / " +  Integer.toString(rawPosToReal(clickedAt,g)));
		if (getGameById(gameid).isPlayerAclicked() && getGameById(gameid).isPlayerBclicked()){
			setGameState(getGameById(gameid),5);

			// reset clicked state
			g.setPlayerAclicked(false);
			g.setPlayerBclicked(false);

			// reset ready state
			g.setPlayerAready(false);
			g.setPlayerBready(false);

			//who has won?

			if ((Math.abs(rawPosToReal(getGameById(gameid).getPlayerActPos(1),g)- getGameById(gameid).getExerciseNumber())
					== Math.abs(rawPosToReal(getGameById(gameid).getPlayerActPos(2),g)- getGameById(gameid).getExerciseNumber()))){

				//draw
				getGameById(gameid).setWinnerOfLastRound(0);
				System.out.println("Unentschieden :)");
				getGameById(gameid).setPlayerPoints(1,getGameById(gameid).getPlayerPoints(1) +1);
				getGameById(gameid).setPlayerPoints(2,getGameById(gameid).getPlayerPoints(2) +1);

			}else if (Math.abs(rawPosToReal(getGameById(gameid).getPlayerActPos(1),g)- getGameById(gameid).getExerciseNumber())
					< Math.abs(rawPosToReal(getGameById(gameid).getPlayerActPos(2),g)- getGameById(gameid).getExerciseNumber())) {

				//PLAYER 1

				getGameById(gameid).setWinnerOfLastRound(1);
				getGameById(gameid).setPlayerPoints(1,getGameById(gameid).getPlayerPoints(1) +1);
				System.out.println("Spieler A hat gewonnen");


			}else if (Math.abs(rawPosToReal(getGameById(gameid).getPlayerActPos(1),g)- getGameById(gameid).getExerciseNumber())
					> Math.abs(rawPosToReal(getGameById(gameid).getPlayerActPos(2),g)- getGameById(gameid).getExerciseNumber())){

				//PLAYER 2

				getGameById(gameid).setWinnerOfLastRound(2);	
				getGameById(gameid).setPlayerPoints(2,getGameById(gameid).getPlayerPoints(2) +1);
				System.out.println("Spieler B hat gewonnen");

			}

			//restart 			
			startGame(gameid);

		}

		return true;
	}

	/**
	 * TODO: there must be a better solution...
	 * <gameid>:<playerid>
	 */
	public boolean updateReadyness(String ids) {
		int playerid = Integer.parseInt(ids.split(":")[1]);
		int gameid = Integer.parseInt(ids.split(":")[0]);
		NumberLineGameState g = getGameById(gameid);
		if (playerid == 1)
			g.setPlayerAready(true);
		if (playerid == 2)
			g.setPlayerBready(true);
		this.setChanged(gameid);
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


	/**
	 * CHAT FUNCTIONALITY
	 * ==================
	 * 
	 * TODO: use extra classes for this.
	 */

	public boolean sendChatMsg(ChatMsg msg) {


		chatMsgs.add(msg);
		System.out.println("MSG: " + msg.toString());

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
