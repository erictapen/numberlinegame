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
import com.wicam.numberlineweb.client.WordStem.WordStemGameCommunicationService;
import com.wicam.numberlineweb.client.WordStem.WordStemGameState;
import com.wicam.numberlineweb.client.WordStem.WordStemPlayer;
import com.wicam.numberlineweb.client.WordStem.WordSetCollection;
import com.wicam.numberlineweb.client.WordStem.Word;
import com.wicam.numberlineweb.client.WordStem.WordSet;
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

	// All WordSets
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
		ArrayList<Word> stems = new ArrayList<Word>();

		try {
			sets = this.words.getRandomSets(4, state.getTakenStems(), this.rand);			
		} catch( NullPointerException e ) {
			System.out.println("All stems used, reset.");
			state.setTakenStems(new ArrayList<Word>());
			sets = this.words.getRandomSets(4, state.getTakenStems(), this.rand);
		}


		for (WordSet wordSet : sets) {
			words.addAll(wordSet.getWords());
			if (state.usesDistractor() && wordSet.hasDistractor()) {
				words.add(wordSet.getDistractor());
			}
			stems.add(wordSet.getStem());
		}

		Collections.shuffle(stems, this.rand);
		Collections.shuffle(words, this.rand);

		state.setStems(stems);
		state.getTakenStems().addAll(stems);
		state.setWords(words);

		// increment the round-counter
		state.setRound(state.getRound()+1);

		return state;
	}



	private boolean isCorrect(Word stem, Word word) {

		try {			
			WordSet set = this.words.getWordSet(stem.getWord());
			return set.getWords().contains(word);
		} catch (NullPointerException e) {
			System.out.println(stem.getWord() + ": No such stem found in WordSetCollection.");
			return false;
		}

	}


	/**
	 * @param communityDigits All community-digits
	 * @param handDigits All hand-digits
	 * @return Checks, if the current GameState has a Solution left
	 */
	private boolean hasSolution(ArrayList<Word> stems,
			ArrayList<Word> words) {

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
	 * @param correct True, if all answers have to be true
	 * @return Gives you "correct" words
	 */
	public ArrayList<Word> getSpecificAnswers(WordStemGameState g, boolean correct) {
		
		ArrayList<Word> res = new ArrayList<Word>();
		
		for (Word word : g.getWords()) {
			
			for (Word stem : g.getStems()) {
				
				WordSet s = this.words.getWordSet(stem.getWord());
				
				if (!word.isTaken() && !word.isSelected() && !s.getDistractor().equals(word) == correct) {
					res.add(word);
				}
				
			}
			
		}
		
		return res;
	}
	


	/**
	 * @param playerID id of the player
	 * @return Checks, if the player has already chosen a hand-digit
	 */
	private boolean playerHasHand(WordStemGameState g, int playerID) {
		return !((WordStemPlayer)g.getPlayers().get(playerID)).getClickedOn().equals("");
	}



	/**
	 * @param clicked gameid:playerid:cickedValue:clickedID:word/stem
	 * @return New WordStemGameState
	 */
	synchronized public WordStemGameState clickedAt(String clicked) {

		int gameid = Integer.parseInt(clicked.split(":")[0]);
		return clickedAt(clicked,getPlayerId(gameid));

	}



	/**
	 * User has clicked.
	 * @param clicked gameid:playerid:cickedValue:clickedID:word/stem
	 * @param playerid Playerid
	 * @return New BuddyNumberGameState
	 */
	synchronized public WordStemGameState clickedAt(String clicked, int playerid) {

		int gameid = Integer.parseInt(clicked.split(":")[0]);

		String value = clicked.split(":")[2];
		int digitID = Integer.parseInt(clicked.split(":")[3]);
		String from = clicked.split(":")[4];



		WordStemGameState g = (WordStemGameState) this.getGameById(gameid);

		HttpServletRequest request = this.getThreadLocalRequest();
		int uid = -2;
		if(request != null){
			HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>> map = (HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>) 
			request.getSession().getAttribute("game2pid2uid");
			uid = map.get(internalName).get(gameid).get(playerid);
		}

		if (from.equals("word") && !g.getWords().get(digitID).isTaken()) { // first click

			g.setWordSelected(g.getWords(), g.getPlayerClickedOn(playerid), false);
			g.setPlayerClickedOn(playerid, value);
			g.setWordSelected(g.getWords(), value, true);
			this.setGameState(g, 3);

		} else if (from.equals("stem") && playerHasHand(g, playerid)) { // user has chosen a hand-digit


			if (isCorrect(new Word(value), new Word(g.getPlayerClickedOn(playerid)))) {

				g.setWordSelected(g.getWords(), g.getPlayerClickedOn(playerid), false);
				g.setWordTaken(g.getWords(), g.getPlayerClickedOn(playerid), true);
				this.getGameById(gameid).setPlayerPoints(playerid,this.getGameById(gameid).getPlayerPoints(playerid) + 1);

			} else {
				
				g.setWordSelected(g.getWords(), g.getPlayerClickedOn(playerid), false);
				this.getGameById(gameid).setPlayerPoints(playerid,this.getGameById(gameid).getPlayerPoints(playerid) - 1);

			}

			if (hasSolution(g.getStems(), g.getWords())) {

				this.setGameState(this.getGameById(gameid),3);
				this.setChanged(gameid);

			} else {

				g.setAllStemsTaken(true);

				this.setGameState(this.getGameById(gameid),5);

				if (g.getRound() >= g.getMaxRound()){
					this.endGame(gameid);
					this.handicapAction(gameid);
				}
				else {
					this.showNextItem(gameid);			
				}

			}

			// enable all hand-digits again
			((WordStemPlayer)g.getPlayers().get(playerid)).setClickedOn("");


		} else {
			//TODO message: choose hand-digit first
		}


		g.setServerSendTime(System.currentTimeMillis());
		return g;
	}



	/**
	 * @param clicked gameID:playerID:word
	 * @param npcWasRight True, if the answer was correct
	 * @return Could NPC click? If not, the answer was taken
	 */
	public boolean npcClicked(String clicked, Boolean npcWasRight) {
		int gameid = Integer.parseInt(clicked.split(":")[0]);
		int playerid = Integer.parseInt(clicked.split(":")[1]);
		String word = clicked.split(":")[2];
		WordStemGameState g = (WordStemGameState) this.getGameById(gameid); 

		System.out.println("NPC clicked " + npcWasRight +" on "+word);

		try {
			
			if (!g.getWordByValue(word).isTaken() && !g.getWordByValue(word).isSelected()) { // Nobody was faster
				
				
				if (npcWasRight) {
					g.setWordTaken(g.getWords(), word, true);
					this.getGameById(gameid).setPlayerPoints(playerid,this.getGameById(gameid).getPlayerPoints(playerid) + 1);
				} else {
					this.getGameById(gameid).setPlayerPoints(playerid,this.getGameById(gameid).getPlayerPoints(playerid) - 1);
				}
				
				if (hasSolution(g.getStems(), g.getWords())) {
					
					this.setGameState(this.getGameById(gameid),3);
					this.setChanged(gameid);
					
				} else {
					
					g.setAllStemsTaken(true);
					
					this.setGameState(this.getGameById(gameid),5);
					
					if (g.getRound() >= g.getMaxRound()){
						this.endGame(gameid);
						this.handicapAction(gameid);
					}
					else {
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
