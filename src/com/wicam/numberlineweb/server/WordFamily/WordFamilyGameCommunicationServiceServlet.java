package com.wicam.numberlineweb.server.WordFamily;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.core.shared.GWT;
import com.wicam.numberlineweb.client.GameOpenException;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.WordFamily.WordFamilyCollection;
import com.wicam.numberlineweb.client.WordFamily.WordFamilyGameCommunicationService;
import com.wicam.numberlineweb.client.WordFamily.WordFamilyGameState;
import com.wicam.numberlineweb.client.WordFamily.Word;
import com.wicam.numberlineweb.client.WordFamily.WordFamily;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;

public class WordFamilyGameCommunicationServiceServlet extends
		GameCommunicationServiceServlet implements
		WordFamilyGameCommunicationService {

	// Used for GWT
	private static final long serialVersionUID = 7200332323767902482L;

	// Random generator
	private Random rand = new Random();

	// All WordSets
	private WordFamilyCollection wordsNormal = new WordFamilyCollection(false);
	private WordFamilyCollection wordsHard = new WordFamilyCollection(true);

	private ArrayList<Integer> npcIds = new ArrayList<Integer>();

	public WordFamilyGameCommunicationServiceServlet() {

		super("wordFamily");

	}

	@Override
	protected boolean isNPC(int playerId) {
		return npcIds.contains(playerId);
	}

	/**
	 * @param state
	 *            WordFamilyGameState to alter
	 * @return The new WordFamilyGameState
	 */
	public WordFamilyGameState newWords(WordFamilyGameState state) {

		WordFamilyCollection words = (state.isHard()) ? this.wordsHard
				: this.wordsNormal;

		ArrayList<WordFamily> sets;
		ArrayList<Word> newWords = new ArrayList<Word>();

		sets = words.getRandomFamilies(1, state.getTakenStems(), this.rand);
		newWords.addAll(sets.get(0).getWords());
		state.clearCorrectlyAnswered();

		ArrayList<Word> correctWords = new ArrayList<Word>();
		for (Word word : sets.get(0).getWords()) {
			correctWords.add(new Word(word));
		}
		state.setCorrectWords(correctWords);

		newWords.addAll(words.getRandomWords(5, newWords, this.rand));

		Collections.shuffle(newWords, this.rand);

		state.setStem(sets.get(0).getStem());
		state.setWords(newWords);

		// increment the round-counter
		state.setRound(state.getRound() + 1);

		return state;
	}

	private boolean isCorrect(WordFamilyGameState g, Word stem, Word word) {
		WordFamilyCollection words = (g.isHard()) ? this.wordsHard
				: this.wordsNormal;
		try {
			WordFamily set = words.getWordSet(stem.getWord());
			return set.getWords().contains(word);
		} catch (NullPointerException e) {
			System.out.println(stem.getWord()
					+ ": No such stem found in WordFamilyCollection.");
			return false;
		}

	}

	/**
	 * @param clicked
	 *            gameid:playerid:submittedValue
	 * @return New WordFamilyGameState
	 */
	@Override
	synchronized public WordFamilyGameState clickedAt(String clicked) {

		if (clicked.split(":")[1].equals("cleared")) {
			this.setGameState(
					this.getGameById(Integer.parseInt(clicked.split(":")[0])),
					3);
			((WordFamilyGameState) this.getGameById(Integer.parseInt(clicked
					.split(":")[0]))).clearCorrectlyAnswered();
			return (WordFamilyGameState) this.getGameById(Integer
					.parseInt(clicked.split(":")[0]));
		} else {
			int gameid = Integer.parseInt(clicked.split(":")[0]);
			return clickedAt(clicked, getPlayerId(gameid));
		}

	}

	/**
	 * User has clicked.
	 * 
	 * @param clicked
	 *            gameid:playerid:submittedValue
	 * @param playerid
	 *            Playerid
	 * @return New WordFamilyGameState
	 */
	synchronized public WordFamilyGameState clickedAt(String clicked,
			int playerid) {

		int gameid = Integer.parseInt(clicked.split(":")[0]);
		String value;
		try {
			value = clicked.split(":")[2];
		} catch (IndexOutOfBoundsException e) {
			value = "";
		}

		WordFamilyGameState g = (WordFamilyGameState) this.getGameById(gameid);

		HttpServletRequest request = this.getThreadLocalRequest();
		@SuppressWarnings("unused")
		int uid = -2;
		if (request != null) {
			@SuppressWarnings("unchecked")
			HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>> map = (HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>) request
					.getSession().getAttribute("game2pid2uid");
			uid = map.get(internalName).get(gameid).get(playerid);
		}

		if (value.equals("done")) {
			this.setGameState(this.getGameById(gameid), 5);

			if (g.getRound() > g.getMaxRound()) {
				this.endGame(gameid);
			} else {
				this.showNextItem(gameid);
			}
		} else {
			this.setGameState(this.getGameById(gameid), 4);
			if (isCorrect(g, g.getStem(), new Word(value))
					&& !getWordByValue(g.getCorrectWords(), value).isTaken()) {
				g.addCorrectlyAnswered(value);
				getWordByValue(g.getCorrectWords(), value).setTaken(true);
				this.getGameById(gameid).setPlayerPoints(playerid,
						this.getGameById(gameid).getPlayerPoints(playerid) + 1);
			} else {
				this.getGameById(gameid).setPlayerPoints(playerid,
						this.getGameById(gameid).getPlayerPoints(playerid) - 1);
			}
		}

		g.setServerSendTime(System.currentTimeMillis());
		return g;
	}

	private Word getWordByValue(ArrayList<Word> words, String value) {
		for (Word word : words) {
			if (word.getWord().equals(value))
				return word;
		}
		return null;
	}

	@Override
	public GameState openGame(GameState g) throws GameOpenException {

		g.setServerSendTime(System.currentTimeMillis());
		GWT.log("before opening game");
		GameState retGameState = super.openGame(g);
		GWT.log("after opening game");
		// return super.openGame(g);

		newWords((WordFamilyGameState) g);

		return retGameState;

	}

	public void showNextItem(int id) {

		Timer t = new Timer(true);
		t.schedule(new WordFamilyGameStateTask(id, 6, this), 6000);
		t.schedule(new WordFamilyGameStateTask(id, 42, this), 6500);
	}

	@Override
	public String getGameProperties(GameState gameState) {

		WordFamilyGameState numberlineGameState = (WordFamilyGameState) gameState;

		String gamePropertiesStr = "{";

		gamePropertiesStr += "num_players : "
				+ numberlineGameState.getPlayerCount() + ", ";

		// TODO gameproperties

		gamePropertiesStr += "}";

		return gamePropertiesStr;

	}

}
