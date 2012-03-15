package com.wicam.numberlineweb.server.WordStem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.core.client.GWT;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;
import com.wicam.numberlineweb.client.WordStem.Word;
import com.wicam.numberlineweb.client.WordStem.WordSet;
import com.wicam.numberlineweb.client.WordStem.WordSetCollection;
import com.wicam.numberlineweb.client.WordStem.WordStemGameCommunicationService;
import com.wicam.numberlineweb.client.WordStem.WordStemGameState;
import com.wicam.numberlineweb.client.WordStem.WordStemPlayer;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
//import com.wicam.numberlineweb.server.logging.Logger.LogActionTrigger;
//import com.wicam.numberlineweb.server.logging.Logger.LogActionType;
//import com.wicam.numberlineweb.server.logging.NumberLineGameHandicap;

public class WordStemGameCommunicationServiceServlet extends
GameCommunicationServiceServlet implements WordStemGameCommunicationService {

	// Used for GWT
	private static final long serialVersionUID = 7200332323767902482L;

	// Random generator
	private Random rand = new Random();
	
	// All words with stems
	private WordSetCollection words = new WordSetCollection();
	

	private ArrayList<Integer> npcIds = new ArrayList<Integer>();

	public WordStemGameCommunicationServiceServlet() {

		super("wordStem");
		//this.handicapAdjustment = new NumberLineGameHandicap();

	}

	@Override
	protected void addNPC(GameState game){
		int playerid = game.addPlayer("NPC", -2);
		npcIds.add(playerid);
		new WordStemNPC(this, game.getId(), playerid);
	}

	protected boolean isNPC(int playerId){
		return npcIds.contains(playerId);
	}

	

	/**
	 * @param state WordStemGameState to alter
	 * @return The new WordStemGameState
	 */
	public WordStemGameState newWords(WordStemGameState state) {

		ArrayList<WordSet> sets;
		ArrayList<Word> words = new ArrayList<Word>();
		ArrayList<String> stems = new ArrayList<String>();
		
		try {
			sets = this.words.getRandomSets(state.getNoOfPots(), state.getTaken(), this.rand);			
		} catch( NullPointerException e ) {
			System.out.println("All stems used, reset.");
			state.setTaken(new ArrayList<String>());
			sets = this.words.getRandomSets(state.getNoOfPots(), state.getTaken(), this.rand);
		}
		
		
		for (WordSet wordSet : sets) {
			words.addAll(wordSet.getWords());
			if (state.usesDistractor()) {
				words.add(wordSet.getDistractor());				
			}
			stems.add(wordSet.getStem());
		}
		
		state.setStems(stems);
		state.getTaken().addAll(stems);
		state.setWords(words);
		
		// increment the round-counter
		state.setRound(state.getRound()+1);
		
		//state.setState(7);

		return state;
	}


	/**
	 * @param stems all available stems
	 * @param words all available words
	 * @return Checks, if the current GameState has a solution
	 */
	private boolean hasSolution(ArrayList<String> stems,
			ArrayList<Word> words) {
		
		ArrayList<WordSet> sets = new ArrayList<WordSet>();
		
		try {
			for (String stem : stems) {
				sets.add(this.words.getWordSet(stem));			
			}			
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		for (Word word : words) {
			for (WordSet wordSet : sets) {
				if (wordSet.contains(word) && !word.isTaken()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	

	/**
	 * @param clicked gameid:playerid:stem:word
	 * @return New BuddyNumberGameState
	 */
	synchronized public WordStemGameState clickedAt(String clicked) {

		int gameid = Integer.parseInt(clicked.split(":")[0]);
		return clickedAt(clicked,getPlayerId(gameid));

	}



	/**
	 * User has clicked.
	 * @param clicked gameid:playerid:stem:word
	 * @param playerid Playerid
	 * @return New WordStemGameState
	 */
	synchronized public WordStemGameState clickedAt(String clicked, int playerid) {

		int gameid = Integer.parseInt(clicked.split(":")[0]);

		String stem = clicked.split(":")[2];
		String word = clicked.split(":")[3];
		boolean droppedCorrect = false;
		
		try {			
			droppedCorrect = words.getWordSet(stem).contains(new Word(word));
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}
		
		//TODO remove
		System.out.println("User " + playerid + " dropped " + word + " on " + stem + " which was " + droppedCorrect);
		

		WordStemGameState g = (WordStemGameState) this.getGameById(gameid);

		HttpServletRequest request = this.getThreadLocalRequest();
		int uid = -2;
		if(request != null){
			HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>> map = (HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>) 
			request.getSession().getAttribute("game2pid2uid");
			uid = map.get(internalName).get(gameid).get(playerid);
		}

		if (!g.getWord(word).isTaken()) {
			
			if (droppedCorrect) {
				this.getGameById(gameid).setPlayerPoints(playerid,this.getGameById(gameid).getPlayerPoints(playerid) + 1);
				g.getWord(word).setTaken(true);
			} else {
				this.getGameById(gameid).setPlayerPoints(playerid,this.getGameById(gameid).getPlayerPoints(playerid) - 1);
			}
			
			
			if (hasSolution(g.getStems(), g.getWords())) {

				this.setGameState(this.getGameById(gameid),3);
				this.setChanged(gameid);

			} else {

				g.setAllWordsTaken();

				this.setGameState(this.getGameById(gameid),5);

				if (g.getRound() >= g.getMaxRound()){
					this.endGame(gameid);
					this.handicapAction(gameid);
				}
				else {
					this.showNextItem(gameid);			
				}

			}
			
		} else {
			//TODO message: someone was faster (impossible...)
		}


		g.setServerSendTime(System.currentTimeMillis());
		return g;
	}

	
	
	/**
	 * @param playerid NPC-ID
	 * @param clicked gameid:playerid:digit:digitID:from(com/hand)
	 * @return Could NPC click? If not, the answer was taken
	 */
	public boolean npcClicked(String clicked, Boolean npcWasRight) {
		
		
		return false;
	}	
	
	

	private void handicapAction(int gameid) {

		//TODO The current formula is just a proof of concept. 
		//At the moment, it will produce bad handicaps for
		//simpler games (e.g., when there are only few players and rounds) and good handicaps
		//for more complex games. 

		/*
		 * A user score is calculated by taking into account the following factors:
		 * 
		 * 1) Number of players (2 - 5)
		 *    Weight: 5
		 * 
		 * 2) Number of rounds (5 - 30)
		 *    Weight: 2
		 * 
		 * 3) Number of points (0 - 30)
		 *    Weight: 10
		 * 
		 * 4) The user gets 30 points if he won the game.
		 * 
		 * 
		 * ==> This results in a minimal score of 20 and a maximal score of 415 points. Normalizing this value yields
		 * the handicap where 1.0 is the best and 0.0 the worst possible value.
		 */

		WordStemGameState numberlineGameState = (WordStemGameState) this.getGameById(gameid);
		ArrayList<? extends Player> players = numberlineGameState.getPlayers();
		Collections.sort(players);

		int winnerUid = players.get(0).getUid();

		//TODO Find a reasonable use for number range.
		//int numRange = (numberlineGameState.getNumberRange().getMaxNumber() - numberlineGameState.getNumberRange().getMinNumber());

		int numPlayers = numberlineGameState.getPlayerCount();
		int numRounds = numberlineGameState.getMaxRound(); //Number of items equals number of rounds

		//General game properties that are not influenced by user performance
		//but contribute to the score
		double gamePropertyScore = 5*numPlayers + 2*numRounds;

		for(Player player : numberlineGameState.getPlayers()){
			if(player.getUid() != -2){


				double hasWon = (player.getUid() == winnerUid) ? 30 : 0;
				double points = player.getPoints();

				double minimalScore = 20;
				double maximalScore = 415;

				double userScore = hasWon + 10*points + gamePropertyScore;

				/*
				 * Normalize score to get handicap.
				 */
				double userHandicapNormalized = (userScore - minimalScore) / (maximalScore - minimalScore);

				//this.logger.log(gameid, player.getUid(), System.currentTimeMillis(), LogActionType.NUMBERLINE_HANDICAP,
				//"{\"handicap\" :" + userHandicapNormalized + "}", this.getClass().getName(), LogActionTrigger.USER);
			}
		}

	}


	@Override
	public GameState openGame(GameState g) {

		g.setServerSendTime(System.currentTimeMillis());
		GWT.log("before opening game");
		GameState retGameState = super.openGame(g);
		GWT.log("after opening game");
		//return super.openGame(g);

		newWords((WordStemGameState) g);

		return retGameState;

	}

	public void showNextItem(int id) {

		Timer t = new Timer();
		t.schedule(new WordStemGameStateTask(id, 6, this), 6000);
	}

	public String getGameProperties(GameState gameState) {

		WordStemGameState numberlineGameState = (WordStemGameState) gameState;

		String gamePropertiesStr = "{";

		gamePropertiesStr += "num_players : " + numberlineGameState.getPlayerCount() + ", ";

		//TODO gameproperties

		gamePropertiesStr += "}";

		return gamePropertiesStr;

	}


}
