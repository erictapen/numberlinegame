package com.wicam.numberlineweb.client.WordStem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;


/**
 * This is the state sent by the server. Holds all game information.
 * @author alex
 *
 */

public class WordStemGameState extends GameState implements Serializable{

	// we need a serial number for GWT to identify sent instances
	private static final long serialVersionUID = 846864907276321588L;

	// All stems
	private ArrayList<Word> stems = new ArrayList<Word>();

	// All words
	private ArrayList<Word> words = new ArrayList<Word>();
	
	// All taken stems
	private ArrayList<Word> takenStems = new ArrayList<Word>();
	
	// Game uses distractor
	private boolean usesDistractor = false;
	
	// Round-counter
	private int round = 0;
	// Max number of rounds to play
	private int maxRound;
	
	
	public WordStemGameState() {
		
	}
	
	
	
	/**
	 * @return Returns communityDigits
	 */
	public ArrayList<Word> getStems() {
		return stems;
	}



	/**
	 * Set communityDigits
	 * @param communityDigits Set communityDigits to this value
	 */
	public void setStems(ArrayList<Word> stems) {
		this.stems = stems;
	}



	/**
	 * Set takenStems
	 * @param takenStems Set takenStems to this value
	 */
	public void setTakenStems(ArrayList<Word> takenStems) {
		this.takenStems = takenStems;
	}



	/**
	 * @return Returns takenStems
	 */
	public ArrayList<Word> getTakenStems() {
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
	 * Set usesDistractor
	 * @param usesDistractor Set usesDistractor to this value
	 */
	public void setUsesDistractor(boolean usesDistractor) {
		this.usesDistractor = usesDistractor;
	}



	/**
	 * @return Returns usesDistractor
	 */
	public boolean usesDistractor() {
		return usesDistractor;
	}


	/**
	 * @param state
	 * @return Returns the player's color-id
	 */
	public int getPlayerColorID(int playerID) {
		return ((WordStemPlayer) players.get(playerID-1)).getColorId();
	}
	

	/**
	 * @param state ID of the player to be looked up
	 * @return Returns the hand-digit the user has clicked on
	 */
	public String getPlayerClickedOn(int playerID) {
		return ((WordStemPlayer) players.get(playerID-1)).getClickedOn();
	}
	
	
	/**
	 * Set the hand-digit a user clicked on
	 * @param state ID of the player to be updated
	 * @param clickedOn hand-digit the player clicked on
	 */
	public void setPlayerClickedOn(int playerID, String clickedOn) {
		((WordStemPlayer) players.get(playerID-1)).setClickedOn(clickedOn);
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
	 * Disables/Enables one word in a set of words by its ID
	 * @param words Set of words to disable/enable from
	 * @param wordID Define, what word should be disabled
	 * @param disable Defines, if word should be disabled or enabled
	 */
	public void setWordTaken(ArrayList<Word> words, int wordID, boolean disable) {
		words.get(wordID).setTaken(disable);
	}
	
	
	/**
	 * Disables/Enables one word in a set of words by its value
	 * @param words Set of words to disable/enable from
	 * @param value Define, what word should be disabled
	 * @param disable Defines, if word should be disabled or enabled
	 */
	public void setWordTaken(ArrayList<Word> words, String value, boolean disable, String color) {
		for (Word word : words) {
			if (word.getWord().equals(value)) {
				word.setTaken(disable);
				word.setColor(color);
				break;
			}
		}
	}
	
	
	/**
	 * Disables/Enables one word in a set of words by its value
	 * @param words Set of words to disable/enable from
	 * @param value Define, what word should be disabled
	 * @param disable Defines, if word should be disabled or enabled
	 */
	public void setWordSelected(ArrayList<Word> words, String value, boolean selected) {
		for (Word word : words) {
			if (word.getWord().equals(value)) {
				word.setSelected(selected);
				break;
			}
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
		int countSameName = 1;
		for (Player player: players){
			// to ensure different namesremovePlayer
			if (newName.equals(player.getName())){
				countSameName++;
				// TODO: only a solution if number of players with same name < 10
				if (countSameName > 2)
					newName = newName.substring(0, newName.length()-2) + " " + countSameName;
				else
					newName = newName + " " + countSameName;
			}
		}
		WordStemPlayer newPlayer = new WordStemPlayer();
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



	public Word getWordByValue(String value) throws NoSuchElementException {
		for (Word word : this.words) {
			if (word.getWord().equals(value)) {
				return word;
			}
		}
		throw new NoSuchElementException("No word with the value "+value);
	}


	
}
