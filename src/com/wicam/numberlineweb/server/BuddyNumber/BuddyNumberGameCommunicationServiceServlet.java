package com.wicam.numberlineweb.server.BuddyNumber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.core.client.GWT;
import com.wicam.numberlineweb.client.GameOpenException;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.BuddyNumber.BuddyNumberDigit;
import com.wicam.numberlineweb.client.BuddyNumber.BuddyNumberGameCommunicationService;
import com.wicam.numberlineweb.client.BuddyNumber.BuddyNumberGameState;
import com.wicam.numberlineweb.client.BuddyNumber.BuddyNumberPlayer;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
//import com.wicam.numberlineweb.server.logging.Logger.LogActionTrigger;
//import com.wicam.numberlineweb.server.logging.Logger.LogActionType;
//import com.wicam.numberlineweb.server.logging.NumberLineGameHandicap;
import com.wicam.numberlineweb.server.logging.GameLogger.LogActionTrigger;
import com.wicam.numberlineweb.server.logging.GameLogger.LogActionType;

public class BuddyNumberGameCommunicationServiceServlet extends
		GameCommunicationServiceServlet implements
		BuddyNumberGameCommunicationService {

	// Used for GWT
	private static final long serialVersionUID = 7200332323767902482L;

	// Random generator
	private Random rand = new Random();

	// All possible divisors
	private int[] possibleDigits = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	private ArrayList<Integer> npcIds = new ArrayList<Integer>();

	public BuddyNumberGameCommunicationServiceServlet() {

		super("buddyNumber");

	}

	@Override
	protected void addNPC(GameState game) {
		int playerid = game.addPlayer("NPC", -2);
		npcIds.add(playerid);
		npcs.add(new BuddyNumberNPC(this, game.getId(), playerid));
	}

	protected boolean isNPC(int playerId) {
		return npcIds.contains(playerId);
	}

	/**
	 * @param state
	 *            BuddyNumberGameState to alter
	 * @return The new BuddyNumberGameState
	 */
	public BuddyNumberGameState newDigits(BuddyNumberGameState state) {

		// Reset players
		for (int i = 0; i < state.getPlayers().size(); i++) {
			state.setPlayerClickedOn(i + 1, 0);
		}

		// Generate digits until at least one solution is possible
		while (!hasSolution(state.getCommunityDigits(), state.getHandDigits())) {
			// Reset digits
			state.getCommunityDigits().clear();
			state.getHandDigits().clear();

			while (state.getCommunityDigits().size() < 8) {
				state.getCommunityDigits().add(
						new BuddyNumberDigit(getRandomDigit()));
			}

			// unique hand-digits
			while (state.getHandDigits().size() < 4) {
				BuddyNumberDigit temp = new BuddyNumberDigit(getRandomDigit());
				if (!listContains(state.getHandDigits(), temp)) {
					state.getHandDigits().add(temp);
				}
			}
		}

		// increment the round-counter
		state.setRound(state.getRound() + 1);

		return state;
	}

	/**
	 * @param communityDigits
	 *            All community-digits
	 * @param handDigits
	 *            All hand-digits
	 * @return Checks, if the current GameState has a Solution left
	 */
	private boolean hasSolution(ArrayList<BuddyNumberDigit> communityDigits,
			ArrayList<BuddyNumberDigit> handDigits) {

		for (BuddyNumberDigit hand : handDigits) {
			for (BuddyNumberDigit community : communityDigits) {
				if (!community.isTaken()) {
					if (hand.getValue() + community.getValue() == 10) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * @return Returns a random divisor
	 */
	private int getRandomDigit() {
		return this.possibleDigits[this.rand
				.nextInt(this.possibleDigits.length)];
	}

	private boolean listContains(ArrayList<BuddyNumberDigit> digits,
			BuddyNumberDigit digit) {
		for (BuddyNumberDigit element : digits) {
			if (element.equals(digit)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param playerID
	 *            id of the player
	 * @return Checks, if the player has already chosen a hand-digit
	 */
	private boolean playerHasHand(BuddyNumberGameState g, int playerID) {
		return ((BuddyNumberPlayer) g.getPlayers().get(playerID - 1))
				.getClickedOn() != 0;
	}

	/**
	 * @param clicked
	 *            gameid:playerid:clickedAnswer
	 * @return New BuddyNumberGameState
	 */
	synchronized public BuddyNumberGameState clickedAt(String clicked) {

		int gameid = Integer.parseInt(clicked.split(":")[0]);
		return clickedAt(clicked, getPlayerId(gameid));

	}

	/**
	 * User has clicked.
	 * 
	 * @param clicked
	 *            gameid:playerid:clickedDigit:digitIDinSet:com/hand
	 * @param playerid
	 *            Playerid
	 * @return New BuddyNumberGameState
	 */
	synchronized public BuddyNumberGameState clickedAt(String clicked,
			int playerid) {

		int gameid = Integer.parseInt(clicked.split(":")[0]);

		int digit = Integer.parseInt(clicked.split(":")[2]);
		int digitID = Integer.parseInt(clicked.split(":")[3]);
		String from = clicked.split(":")[4];

		BuddyNumberGameState g = (BuddyNumberGameState) this
				.getGameById(gameid);

		HttpServletRequest request = this.getThreadLocalRequest();
		int uid = -2;
		if (request != null) {
			HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>> map = (HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>) request
					.getSession().getAttribute("game2pid2uid");
			uid = map.get(internalName).get(gameid).get(playerid);
		}

		if (from.equals("hand")) { // first click

			g.setPlayerClickedOn(playerid, digit);
			this.setGameState(g, 3);

		} else if (from.equals("com") && playerHasHand(g, playerid)) { // user
																		// has
																		// chosen
																		// a
																		// hand-digit

			if (!g.getCommunityDigits().get(digitID).isTaken()) { // Nobody was
																	// faster

				boolean wasRight = false;

				g.setDigitTaken(g.getCommunityDigits(), digitID, playerid);

				if (g.getPlayerClickedOn(playerid) + digit == 10) {

					this.getGameById(gameid)
							.setPlayerPoints(
									playerid,
									this.getGameById(gameid).getPlayerPoints(
											playerid) + 1);

					wasRight = true;

				} else {

					this.getGameById(gameid)
							.setPlayerPoints(
									playerid,
									this.getGameById(gameid).getPlayerPoints(
											playerid) - 1);

				}

				String actionParams = "{\"first_click_digit\" : "
						+ g.getPlayerClickedOn(playerid)
						+ ", \"second_click_digit\" : " + digit
						+ ", \"was_right\" : " + wasRight + "}";

				this.gameId2Logger.get(gameid).log(gameid, uid,
						System.currentTimeMillis(),
						LogActionType.BUDDYNUMBER_PICKED_NUMBER_PAIR,
						actionParams, this.getClass().getName(),
						LogActionTrigger.USER);

				if (hasSolution(g.getCommunityDigits(), g.getHandDigits())) {

					this.setGameState(this.getGameById(gameid), 3);
					this.setChanged(gameid);

				} else {

					g.setAllDigitsTakenIn(g.getCommunityDigits(), true);
					g.setAllDigitsTakenIn(g.getHandDigits(), true);

					this.setGameState(this.getGameById(gameid), 5);

					if (g.getRound() >= g.getMaxRound()) {
						this.endGame(gameid);
					} else {
						this.showNextItem(gameid);
					}

				}

			} else {
				// TODO message: someone was faster...

			}

			// enable all hand-digits again
			((BuddyNumberPlayer) g.getPlayers().get(playerid - 1))
					.setClickedOn(0);

		} else {
			// TODO message: choose hand-digit first
		}

		g.setServerSendTime(System.currentTimeMillis());
		return g;
	}

	/**
	 * @param playerid
	 *            NPC-ID
	 * @param clicked
	 *            gameid:playerid:digit:digitID:from(com/hand)
	 * @return Could NPC click? If not, the answer was taken
	 */
	public boolean npcClicked(String clicked, Boolean npcWasRight) {
		int gameid = Integer.parseInt(clicked.split(":")[0]);
		int playerid = Integer.parseInt(clicked.split(":")[1]);
		int digit = Integer.parseInt(clicked.split(":")[2]);
		int digitID = Integer.parseInt(clicked.split(":")[3]);
		BuddyNumberGameState g = (BuddyNumberGameState) this
				.getGameById(gameid);

		if (!g.getCommunityDigits().get(digitID).isTaken()) { // Nobody was
																// faster

			g.setDigitTaken(g.getCommunityDigits(), digitID, playerid);

			boolean wasRight = false;

			if (npcWasRight) {
				this.getGameById(gameid).setPlayerPoints(playerid,
						this.getGameById(gameid).getPlayerPoints(playerid) + 1);
				wasRight = true;
			} else {
				this.getGameById(gameid).setPlayerPoints(playerid,
						this.getGameById(gameid).getPlayerPoints(playerid) - 1);
			}

			String actionParams = "{\"digit\" : " + digit
					+ ", \"was_right\" : " + wasRight + "}";

			this.gameId2Logger.get(gameid).log(gameid,
					System.currentTimeMillis(),
					LogActionType.BUDDYNUMBER_NPC_PICKED_NUMBER, actionParams,
					this.getClass().getName(), LogActionTrigger.NPC);

			if (hasSolution(g.getCommunityDigits(), g.getHandDigits())) {
				this.setGameState(this.getGameById(gameid), 3);
				this.setChanged(gameid);
			} else {
				g.setAllDigitsTakenIn(g.getCommunityDigits(), true);
				g.setAllDigitsTakenIn(g.getHandDigits(), true);
				this.setGameState(this.getGameById(gameid), 5);
				if (g.getRound() >= g.getMaxRound()) {
					this.endGame(gameid);
				} else {
					this.showNextItem(gameid);
				}
			}

			return true;
		} else {

		}

		return false;
	}

	@Override
	public GameState openGame(GameState g) throws GameOpenException {

		g.setServerSendTime(System.currentTimeMillis());
		GWT.log("before opening game");
		GameState retGameState = super.openGame(g);
		GWT.log("after opening game");
		// return super.openGame(g);

		newDigits((BuddyNumberGameState) g);

		return retGameState;

	}

	public void showNextItem(int id) {

		Timer t = new Timer(true);
		t.schedule(new BuddyNumberGameStateTask(id, 6, this), 6000);
	}

	public String getGameProperties(GameState gameState) {

		BuddyNumberGameState buddyGameState = (BuddyNumberGameState) gameState;

		String gamePropertiesStr = "{";

		gamePropertiesStr += "\"num_players\" : "
				+ buddyGameState.getPlayerCount() + ", ";

		gamePropertiesStr += "\"max_rounds\" : " + buddyGameState.getMaxRound()
				+ ", ";

		gamePropertiesStr += "\"hand_digits\" : "
				+ buddyGameState.getHandDigits() + ", ";

		gamePropertiesStr += "\"community_digits\" : "
				+ buddyGameState.getCommunityDigits();

		gamePropertiesStr += "}";

		return gamePropertiesStr;

	}

}
