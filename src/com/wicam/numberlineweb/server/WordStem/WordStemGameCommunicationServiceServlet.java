package com.wicam.numberlineweb.server.WordStem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.core.shared.GWT;
import com.wicam.numberlineweb.client.GameOpenException;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.WordStem.WordSetCollection;
import com.wicam.numberlineweb.client.WordStem.WordStemGameCommunicationService;
import com.wicam.numberlineweb.client.WordStem.WordStemGameState;
import com.wicam.numberlineweb.client.WordStem.WordStemPlayer;
import com.wicam.numberlineweb.client.WordStem.Word;
import com.wicam.numberlineweb.client.WordStem.WordSet;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;

public class WordStemGameCommunicationServiceServlet extends
		GameCommunicationServiceServlet implements
		WordStemGameCommunicationService {

	// Used for GWT
	private static final long serialVersionUID = 7200332323767902482L;

	// Random generator
	private Random rand = new Random();

	// All WordSets
	private WordSetCollection words = new WordSetCollection();

	private ArrayList<Integer> npcIds = new ArrayList<Integer>();

	public final String[] playerColors = {"blue", "Magenta", "orange", "DarkGreen", "DarkCyan"};

	public WordStemGameCommunicationServiceServlet() {

		super("wordStem");

	}

	@Override
	protected void addNPC(GameState game) {
		int playerid = game.addPlayer("NPC", -2);
		npcIds.add(playerid);
		npcs.add(new WordStemNPC(this, game.getId(), playerid));
	}

	@Override
	protected boolean isNPC(int playerId) {
		return npcIds.contains(playerId);
	}

	/**
	 * @param state
	 *            WordStemGameState to alter
	 * @return The new WordStemGameState
	 */
	public WordStemGameState newWords(WordStemGameState state) {

		ArrayList<WordSet> sets;
		ArrayList<Word> words = new ArrayList<Word>();
		ArrayList<Word> stems = new ArrayList<Word>();

		try {
			sets = this.words
					.getRandomSets(4, state.getTakenStems(), this.rand);
		} catch (NullPointerException e) {
			System.out.println("All stems used, reset.");
			state.setTakenStems(new ArrayList<Word>());
			sets = this.words
					.getRandomSets(4, state.getTakenStems(), this.rand);
		}

		for (WordSet wordSet : sets) {
			words.addAll(wordSet.getWords());
			if (state.usesDistractor() && wordSet.hasDistractor()) {
				words.add(wordSet.getDistractor());
			}
			stems.add(wordSet.getStem());
		}

		if (state.usesDistractor()) {
			stems.add(this.words.getRandomStem(stems, this.rand));
		}

		Collections.shuffle(stems, this.rand);
		Collections.shuffle(words, this.rand);

		state.setStems(stems);
		state.getTakenStems().addAll(stems);
		state.setWords(words);

		// increment the round-counter
		state.setRound(state.getRound() + 1);

		return state;
	}

	private boolean isCorrect(Word stem, Word word) {

		try {
			WordSet set = this.words.getWordSet(stem.getWord());
			return set.getWords().contains(word);
		} catch (NullPointerException e) {
			System.out.println(stem.getWord()
					+ ": No such stem found in WordSetCollection.");
			return false;
		}

	}

	/**
	 * @param communityDigits
	 *            All community-digits
	 * @param handDigits
	 *            All hand-digits
	 * @return Checks, if the current GameState has a Solution left
	 */
	private boolean hasSolution(ArrayList<Word> stems, ArrayList<Word> words) {

		for (Word stem : stems) {
			for (Word word : words) {
				if (!word.isTaken() && isCorrect(stem, word)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param correct
	 *            True, if all answers have to be true
	 * @return Gives you "correct" words
	 */
	public ArrayList<Word> getSpecificAnswers(WordStemGameState g,
			boolean correct) {

		ArrayList<Word> res = new ArrayList<Word>();

		for (Word word : g.getWords()) {

			for (Word stem : g.getStems()) {

				WordSet s = this.words.getWordSet(stem.getWord());

				if (!word.isTaken() && !word.isSelected()
						&& !s.getDistractor().equals(word) == correct) {
					res.add(word);
				}

			}

		}

		return res;
	}

	/**
	 * @param state
	 *            id of the player
	 * @return Checks, if the player has already chosen a hand-digit
	 */
	private boolean playerHasHand(WordStemGameState g, int playerID) {
		return !((WordStemPlayer) g.getPlayers().get(playerID - 1))
				.getClickedOn().equals("");
	}

	/**
	 * @param clicked
	 *            gameid:playerid:cickedValue:clickedID:word/stem
	 * @return New WordStemGameState
	 */
	@Override
	synchronized public WordStemGameState clickedAt(String clicked) {

		int gameid = Integer.parseInt(clicked.split(":")[0]);
		return clickedAt(clicked, getPlayerId(gameid));

	}

	/**
	 * User has clicked.
	 * 
	 * @param clicked
	 *            gameid:playerid:cickedValue:clickedID:word/stem
	 * @param playerid
	 *            Playerid
	 * @return New WordStemGameState
	 */
	synchronized public WordStemGameState clickedAt(String clicked, int playerid) {

		int gameid = Integer.parseInt(clicked.split(":")[0]);

		String value = clicked.split(":")[2];
		int digitID = Integer.parseInt(clicked.split(":")[3]);
		String from = clicked.split(":")[4];

		WordStemGameState g = (WordStemGameState) this.getGameById(gameid);

		HttpServletRequest request = this.getThreadLocalRequest();
		int uid = -2;
		if (request != null) {
			HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>> map = (HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>) request
					.getSession().getAttribute("game2pid2uid");
			uid = map.get(internalName).get(gameid).get(playerid);
		}

		if (from.equals("word") && !g.getWords().get(digitID).isTaken()) { // first
																			// click

			g.setWordSelected(g.getWords(), g.getPlayerClickedOn(playerid),
					false);
			g.setPlayerClickedOn(playerid, value);
			g.setWordSelected(g.getWords(), value, true);
			this.setGameState(g, 3);

		} else if (from.equals("stem") && playerHasHand(g, playerid)) { // user
																		// has
																		// chosen
																		// a
																		// hand-digit

			if (isCorrect(new Word(value),
					new Word(g.getPlayerClickedOn(playerid)))) {

				g.setWordSelected(g.getWords(), g.getPlayerClickedOn(playerid),
						false);
				g.setWordTaken(g.getWords(), g.getPlayerClickedOn(playerid),
						true, playerColors[g.getPlayerColorID(playerid)]);
				this.getGameById(gameid).setPlayerPoints(playerid,
						this.getGameById(gameid).getPlayerPoints(playerid) + 1);

			} else {

				g.setWordSelected(g.getWords(), g.getPlayerClickedOn(playerid),
						false);
				this.getGameById(gameid).setPlayerPoints(playerid,
						this.getGameById(gameid).getPlayerPoints(playerid) - 1);

			}

			if (hasSolution(g.getStems(), g.getWords())) {

				this.setGameState(this.getGameById(gameid), 3);
				this.setChanged(gameid);

			} else {

				g.setAllStemsTaken(true);

				this.setGameState(this.getGameById(gameid), 5);

				if (g.getRound() >= g.getMaxRound()) {
					this.endGame(gameid);
				} else {
					this.showNextItem(gameid);
				}

			}

			// enable all hand-digits again
			((WordStemPlayer) g.getPlayers().get(playerid - 1))
					.setClickedOn("");

		} else {
			// TODO message: choose hand-digit first
		}

		g.setServerSendTime(System.currentTimeMillis());
		return g;
	}

	/**
	 * @param clicked
	 *            gameID:state:word
	 * @param npcWasRight
	 *            True, if the answer was correct
	 * @return Could NPC click? If not, the answer was taken
	 */
	public boolean npcClicked(String clicked, Boolean npcWasRight) {
		int gameid = Integer.parseInt(clicked.split(":")[0]);
		int playerid = Integer.parseInt(clicked.split(":")[1]);
		String word = clicked.split(":")[2];
		WordStemGameState g = (WordStemGameState) this.getGameById(gameid);

		// System.out.println("NPC clicked " + npcWasRight +" on "+word);

		try {

			if (!g.getWordByValue(word).isTaken()
					&& !g.getWordByValue(word).isSelected()) { // Nobody was
																// faster

				if (npcWasRight) {
					g.setWordTaken(g.getWords(), word, true,
							playerColors[g.getPlayerColorID(playerid)]);
					this.getGameById(gameid)
							.setPlayerPoints(
									playerid,
									this.getGameById(gameid).getPlayerPoints(
											playerid) + 1);
				} else {
					this.getGameById(gameid)
							.setPlayerPoints(
									playerid,
									this.getGameById(gameid).getPlayerPoints(
											playerid) - 1);
				}

				if (hasSolution(g.getStems(), g.getWords())) {

					this.setGameState(this.getGameById(gameid), 3);
					this.setChanged(gameid);

				} else {

					g.setAllStemsTaken(true);

					this.setGameState(this.getGameById(gameid), 5);

					if (g.getRound() >= g.getMaxRound()) {
						this.endGame(gameid);
					} else {
						this.showNextItem(gameid);
					}

				}

				return true;
			}
			return false;

		} catch (NoSuchElementException e) {
			return false;
		}

	}

	@Override
	public GameState openGame(GameState g) throws GameOpenException {

		g.setServerSendTime(System.currentTimeMillis());
		GWT.log("before opening game");
		GameState retGameState = super.openGame(g);
		GWT.log("after opening game");
		// return super.openGame(g);

		newWords((WordStemGameState) g);

		return retGameState;

	}

	public void showNextItem(int id) {

		Timer t = new Timer(true);
		t.schedule(new WordStemGameStateTask(id, 6, this), 6000);
	}

	@Override
	public String getGameProperties(GameState gameState) {

		WordStemGameState numberlineGameState = (WordStemGameState) gameState;

		String gamePropertiesStr = "{";

		gamePropertiesStr += "num_players : "
				+ numberlineGameState.getPlayerCount() + ", ";

		// TODO gameproperties

		gamePropertiesStr += "}";

		return gamePropertiesStr;

	}

}
