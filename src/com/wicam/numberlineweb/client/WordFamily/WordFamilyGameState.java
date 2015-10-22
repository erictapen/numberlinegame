package com.wicam.numberlineweb.client.WordFamily;

import java.io.Serializable;
import java.util.ArrayList;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;


/**
 * This is the state sent by the server. Holds all game information.
 * @author alex
 *
 */

public class WordFamilyGameState extends GameState implements Serializable{

	// we need a serial number for GWT to identify sent instances
	private static final long serialVersionUID = 846864907276321588L;

	// All stems
	private Word stem = new Word();

	// All words
	private ArrayList<Word> words = new ArrayList<Word>();
	private ArrayList<Word> correctWords = new ArrayList<Word>();
	
	// All taken stems as indexes
	private ArrayList<Integer> takenStems = new ArrayList<Integer>();
	
	// All correctly sent words
	private ArrayList<String> correctlyAnswered = new ArrayList<String>();
	
	// Use difficult words?
	private boolean hard = false;	
	
	// Round-counter
	private int round = 0;
	// Max number of rounds to play
	private int maxRound;

	
	
	public WordFamilyGameState() {
		
	}
	
	
	
	/**
	 * @return Returns communityDigits
	 */
	public Word getStem() {
		return stem;
	}



	/**
	 * Set communityDigits
	 * @param communityDigits Set communityDigits to this value
	 */
	public void setStem(Word stem) {
		this.stem = stem;
	}



	/**
	 * Set takenStems
	 * @param takenStems Set takenStems to this value
	 */
	public void setTakenStems(ArrayList<Integer> takenStems) {
		this.takenStems = takenStems;
	}



	/**
	 * @return Returns takenStems
	 */
	public ArrayList<Integer> getTakenStems() {
		return takenStems;
	}



	/**
	 * @return Returns handDigits
	 */
	public ArrayList<Word> getWords() {
		return words;
	}



	/**
	 * Set handDigits
	 * @param handDigits Set handDigits to this value
	 */
	public void setWords(ArrayList<Word> words) {
		this.words = words;
	}

	
	
	/**
	 * Disables/Enables all digits in a set of digits
	 * @param digits Set of digits to disable/enable
	 * @param taken Define, if all digits are taken or not
	 */
	public void setAllStemsTaken(boolean taken) {
		for (Word word : this.words) {
			word.setTaken(taken);
		}
	}
	
	

	/**
	 * @param r the new round
	 */
	public void setRound(int r) {
		this.round = r;
	}
	
	
	
	/**
	 * @return Returns the round
	 */
	public int getRound() {
		return this.round;
	}
	
	
	
	/**
	 * Set maximal number of rounds to play
	 * @param rounds Max rounds to play
	 */
	public void setMaxRound(int rounds) {
		this.maxRound = rounds;
	}
	
	
	
	/**
	 * Returns maximal number of rounds to play
	 * @return max rounds to play
	 */
	public int getMaxRound() {
		return this.maxRound;
	}


	
	/**
	 * Adds a new player to the game
	 * @param newName The new player's name
	 * @param uid The new player's ID
	 * @return Returns the new number of players
	 */
	@Override
	public int addPlayer(String newName, int uid) {
		newName = checkDuplicateName(newName);
		WordFamilyPlayer newPlayer = new WordFamilyPlayer();
		newPlayer.setName(newName);
		newPlayer.setColorId(players.size());
		newPlayer.setUid(uid);
		players.add(newPlayer);
		return players.size();
	}
	
	
	/**
	 * Returns a list of playernames
	 * @return Playernames as List
	 */
	public ArrayList<String> getPlayerNamesList() {
		ArrayList<String> playerNamesList = new ArrayList<String>();
		for (Player player: players)
			playerNamesList.add(player.getName());
		return playerNamesList;
	}


	/**
	 * Set correctlyAnswered
	 * @param correctlyAnswered Set correctlyAnswered to this value
	 */
	public void setCorrectlyAnswered(ArrayList<String> correctlyAnswered) {
		this.correctlyAnswered = correctlyAnswered;
	}
	
	
	/**
	 * Add Answer
	 * @param answer Answer to add
	 */
	public void addCorrectlyAnswered(String answer) {
		this.correctlyAnswered.add(answer);
	}

	
	public void clearCorrectlyAnswered() {
		this.correctlyAnswered.clear();
	}


	/**
	 * @return Returns correctlyAnswered
	 */
	public ArrayList<String> getCorrectlyAnswered() {
		return correctlyAnswered;
	}



	public String getCorrectAsHtmlList() {
		String res = "<ul>";
		for (Word word : this.correctWords) {
			res += "<li>"+word.getWord() + "</li>";
		}
		return res + "</ul>";
	}



	public ArrayList<Word> getCorrectWords() {
		return correctWords;
	}



	public void setCorrectWords(ArrayList<Word> correctWords) {
		this.correctWords = correctWords;
	}



	/**
	 * Set hard
	 * @param hard Set hard to this value
	 */
	public void setHard(boolean hard) {
		this.hard = hard;
	}



	/**
	 * @return Returns hard
	 */
	public boolean isHard() {
		return hard;
	}


	
}
