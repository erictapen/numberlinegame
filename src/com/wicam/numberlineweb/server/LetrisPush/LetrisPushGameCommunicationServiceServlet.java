package com.wicam.numberlineweb.server.LetrisPush;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wicam.numberlineweb.client.GameJoinException;
import com.wicam.numberlineweb.client.GameOpenException;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;
import com.wicam.numberlineweb.client.Letris.LetrisGameCommunicationService;
import com.wicam.numberlineweb.client.Letris.LetrisGameLetterBlock;
import com.wicam.numberlineweb.client.Letris.LetrisGameState;
import com.wicam.numberlineweb.client.LetrisPush.LetrisPushGameCommunicationService;
import com.wicam.numberlineweb.client.LetrisPush.LetrisPushGamePauseEvent;
import com.wicam.numberlineweb.client.LetrisPush.LetrisPushGamePlayer;
import com.wicam.numberlineweb.client.LetrisPush.LetrisPushGamePlaygroundState;
import com.wicam.numberlineweb.client.LetrisPush.LetrisPushGameState;
import com.wicam.numberlineweb.client.LetrisPush.LetrisPushGameTargetUpdateEvent;
import com.wicam.numberlineweb.client.LetrisPush.LetrisPushGameUnpauseEvent;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.TimeOutState;
import com.wicam.numberlineweb.server.UpdateState;
import com.wicam.numberlineweb.server.Letris.LetrisGameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.Letris.LetrisGameWordList;
import com.wicam.numberlineweb.server.database.drupal.DrupalCommunicator;
import com.wicam.numberlineweb.server.database.drupal.UserNotFoundException;
import com.wicam.numberlineweb.server.logging.LetrisGameHandicap;
import com.wicam.numberlineweb.server.logging.GameLogger.LogActionTrigger;
import com.wicam.numberlineweb.server.logging.GameLogger.LogActionType;

import de.novanic.eventservice.service.EventExecutorService;
import de.novanic.eventservice.service.EventExecutorServiceFactory;

/**
 * LeTris game communication service.
 * 
 * @author timfissler
 * 
 */

// TODO Add descriptions.
// TODO Add possibility to communicate other players achievements to add
// "deadlines" to the playground.
// TODO Remove event executor services and connection id when a player leaves
// the game.
// TODO Implement communication with opponent player.

public class LetrisPushGameCommunicationServiceServlet extends
		LetrisGameCommunicationServiceServlet implements
		LetrisPushGameCommunicationService {

	private static final long serialVersionUID = -7355416542911925385L;
	private LetrisPushGameWordList wordList = new LetrisPushGameWordList();
	/**
	 * Holds the connection id for a given player id.
	 */
	private HashMap<Integer, String> playerID2connectionID = new HashMap<Integer, String>();
	/**
	 * Holds the event executor service instances that will implement the server
	 * push to the specific clients.
	 */
	private HashMap<String, EventExecutorService> connectionID2EventExecutorService = new HashMap<String, EventExecutorService>();

	public LetrisPushGameCommunicationServiceServlet() {
		super("LeTris");
		this.handicapAdjustment = new LetrisGameHandicap();
	}

	public LetrisPushGameCommunicationServiceServlet(String internalName) {
		super(internalName);
		this.handicapAdjustment = new LetrisGameHandicap();
	}

	@Override
	public GameState openGame(GameState g) throws GameOpenException {
		GameState gameState = super.openGame(g);
		return gameState;
	}

	@Override
	public GameState updatePoints(String ids) {
		// TODO Change the behavior of this method.
		// int gameid = Integer.parseInt(ids.split(":")[0]);
		// int playerid = getPlayerId(gameid);
		// String letter = ids.split(":")[2];
		// int mcid = Integer.parseInt(ids.split(":")[3]);
		//
		// int points = 0;
		// LetrisGameState g = (LetrisGameState) getGameById(gameid);
		//
		// if (letter.equals(g.getCurWord().getConsonantPair()))
		// points = g.hasCorrectlyAnswered(playerid) ? 2 : 1;
		// else
		// points = -1;
		//
		// int newPoints = g.getPlayerPoints(playerid) + points;
		// if (newPoints < 0)
		// newPoints = 0;
		//
		// g.setPlayerPoints(playerid, newPoints);
		// g.getMovingConsonantsCoords().get(mcid).setRemoved(true);
		// g.setServerSendTime(System.currentTimeMillis());
		// return g;
		return null;
	}

	@Override
	public String getGameProperties(GameState g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<VowelGameWord> getTargetWords() {
		return LetrisGameWordList.createWordList();
	}

	/**
	 * Pauses the game for all players.
	 */
	public void pauseAllPlayers(LetrisPushGameState gameState) {
		// Pause the game of all players in the game state.
		for (Player player : gameState.getPlayers()) {
			// Retrieve player connection id.
			LetrisPushGamePlayer letrisPlayer = (LetrisPushGamePlayer) player;
			String connectionID = letrisPlayer.getConnectionID();
			// Send pause event.
			connectionID2EventExecutorService.get(connectionID)
					.addEventUserSpecific(new LetrisPushGamePauseEvent());
		}
	}

	/**
	 * Unpauses the game for all players.
	 */
	public void unpauseAllPlayers(LetrisPushGameState gameState) {
		// Unpause the game of all players in the game state.
		for (Player player : gameState.getPlayers()) {
			// Retrieve player connection id.
			LetrisPushGamePlayer letrisPlayer = (LetrisPushGamePlayer) player;
			String connectionID = letrisPlayer.getConnectionID();
			// Send unpause event.
			connectionID2EventExecutorService.get(connectionID)
					.addEventUserSpecific(new LetrisPushGameUnpauseEvent());
		}
	}

	/**
	 * Log the current target word.
	 * 
	 * @param g
	 */
	public void targetWordPresented(LetrisGameState g) {
		// TODO Implement this.
		System.out.println("Logging target word: " + g.getCurrentWord());
	}

	/**
	 * Log the last letter block that was set by the player.
	 * 
	 * @param letterBlock
	 * @param g
	 */
	public void letterBlockSet(LetrisGameLetterBlock letterBlock,
			LetrisGameState g) {
		// TODO Implement this.
		System.out.println("Logging set letter block: " + letterBlock);
	}

	/**
	 * Log the last word that was built correctly by the user and add a filler
	 * row to the playground of the other player.
	 * 
	 * @param word
	 * @param g
	 */
	public void correctWordBuilt(String word, LetrisGameState g) {
		// TODO Implement this.
		System.out.println("Logging correct word: " + word);
	}

	/**
	 * Log the last word that was built incorrectly by the user and remove a
	 * filler row to the playground of the other player (if possible).
	 * 
	 * @param word
	 * @param g
	 */
	public void incorrectWordBuilt(String word, LetrisGameState g) {
		// TODO Implement this.
		System.out.println("Logging incorrect word: " + word);
	}

	/**
	 * Join game with the given ids, given as a string: <gameid>:<username>
	 * 
	 * Returns gameid and username in the same format. Username may have been
	 * changed when already used.
	 * 
	 */
	// TODO Allow users to start multiple games for testing purpose.
	@Override
	@SuppressWarnings("unchecked")
	public synchronized String joinGame(String ids, String connectionID)
			throws GameJoinException {

		String player = ids.split(":")[1];
		int uid = -2;
		int id = Integer.parseInt(ids.split(":")[0]);

		if (player.split("/")[0].equals("___ID")) {

			uid = Integer.parseInt(player.split("/")[1]);
			if (uid == -2)
				player = "Gast";
			else {
//				Commented for debugging reasons
//				DrupalCommunicator dc = new DrupalCommunicator();
//				try {
//					player = dc.getUser(uid).getUname();
//				} catch (UserNotFoundException e) {
//					throw new GameJoinException("User with id =" + uid
//							+ " could not be found.");
//				}
			}

		}

		GameState game = getGameById(id);

		HttpServletRequest request = this.getThreadLocalRequest();
		HashMap<String, HashMap<Integer, Integer>> games;
		HashMap<Integer, Integer> pids;
		HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>> game2pid2uid;

		HttpSession session = request.getSession(true);

		// System.out.println(session.getAttributeNames());

		games = (HashMap<String, HashMap<Integer, Integer>>) session
				.getAttribute("pids");

		// Try to decide either upon the isNew()-property or upon a games is
		// already stored in the session.
		if (session.isNew() || games == null) {
			games = new HashMap<String, HashMap<Integer, Integer>>();
			pids = new HashMap<Integer, Integer>();
			game2pid2uid = new HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>();

			games.put(internalName, pids);

			game2pid2uid.put(internalName,
					new HashMap<Integer, HashMap<Integer, Integer>>());
			game2pid2uid.get(internalName).put(id,
					new HashMap<Integer, Integer>());

			session.setAttribute("pids", games);
			session.setAttribute("game2pid2uid", game2pid2uid);

		} else {

			pids = games.get(internalName);

			game2pid2uid = (HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>) session
					.getAttribute("game2pid2uid");

			if (pids == null) {

				pids = new HashMap<Integer, Integer>();
				games.put(internalName, pids);

			}

			if (game2pid2uid == null) {

				game2pid2uid = new HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>();
				game2pid2uid.put(internalName,
						new HashMap<Integer, HashMap<Integer, Integer>>());
				game2pid2uid.get(internalName).put(id,
						new HashMap<Integer, Integer>());

			}

			if (game2pid2uid.get(internalName) == null) {

				game2pid2uid.put(internalName,
						new HashMap<Integer, HashMap<Integer, Integer>>());

			}

			if (game2pid2uid.get(internalName).get(id) == null) {

				game2pid2uid.get(internalName).put(id,
						new HashMap<Integer, Integer>());
			}

		}

		// we dont want the same human player to play multiple players
		// TODO Switch that back.
//		if (pids.containsKey(id)) {
//			throw new GameJoinException("Du bist bereits in diesem Spiel!");
//		}

		System.out.println("Player '" + player + "' joined game #"
				+ Integer.toString(id));

		// only join if free and not yet started...
		if (game.isFree() && game.getState() < 2) {

			int playerid = ((LetrisPushGameState) game).addPlayer(player, uid,
					connectionID);

			// Store connection id for player id.
			playerID2connectionID.put(playerid, connectionID);

			// Create EventExecutorService for connection id.
			EventExecutorServiceFactory eventExecutorServiceFactory = EventExecutorServiceFactory
					.getInstance();
			connectionID2EventExecutorService.put(connectionID,
					eventExecutorServiceFactory
							.getEventExecutorService(connectionID));

			System.out.println("Registered client with connection ID: "
					+ connectionID);

			if (game.isFree()) {
				setGameState(getGameById(game.getId()), 1);

			} else {
				// add NPCs
				for (int i = 0; i < game.getNumberOfMaxNPCs(); i++)
					addNPC(game);
				setGameState(getGameById(game.getId()), 2);
			}

			pids.put(id, playerid);
			request.getSession(true).setAttribute("pids", games);

			game2pid2uid.get(internalName).get(id).put(playerid, uid);
			request.getSession(true).setAttribute("game2pid2uid", game2pid2uid);

			// add this user to the update-state list

			getUpdateStates().add(
					new UpdateState(playerid, game.getId(), false));

			// add this user to the timeout list

			getTimeOutStates().add(
					new TimeOutState(uid, playerid, game.getId(), 5));

			if (uid != -2) {
				this.gameId2Logger.get(id).log(game.getId(), uid,
						System.currentTimeMillis(), LogActionType.JOINED_GAME,
						"", this.getClass().getName(), LogActionTrigger.USER);

				// TODO Decide how game adjustment should be handled
				// Adjust game to user's ELO value
				// this.adjustToElo(uid, game);
			}

			return game.getId() + ":" + playerid;

		}

		throw new GameJoinException("Das Spiel ist voll.");

	}

	/**
	 * Takes the updates from one player and passes it to the other player via
	 * server push.
	 */
	@Override
	public void sendTargetUpdate(LetrisPushGamePlaygroundState playgroundState) {

		// Find opponent connection id.
		String opponentConnectionID;
		if (playerID2connectionID.containsKey(playgroundState
				.getOpponentPlayerID())) {
			opponentConnectionID = playerID2connectionID.get(playgroundState
					.getOpponentPlayerID());
		}
		// Break if opponent player is not registered.
		else {
			System.out.println("Player with id \""
					+ playgroundState.getOpponentPlayerID()
					+ "\" is not registered!");
			return;
		}

		// Build target update event.
		LetrisPushGameTargetUpdateEvent targetUpdateEvent = new LetrisPushGameTargetUpdateEvent();
		targetUpdateEvent.setPlaygroundState(playgroundState);

		// Send target update event.
		connectionID2EventExecutorService.get(opponentConnectionID)
				.addEventUserSpecific(targetUpdateEvent);
	}

}
