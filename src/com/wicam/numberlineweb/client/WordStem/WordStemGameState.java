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
	
	// Round-counter
	private int round = 0;
	
	// Max number of rounds to play
	private int maxRound;
	
	// All word stems to drop on
	private ArrayList<String> stems = new ArrayList<String>();
	
	// All words to drag
	private ArrayList<Word> words = new ArrayList<Word>();
	
	// All already taken stems
	private ArrayList<String> taken = new ArrayList<String>();
	
	// number of pots
	private int noOfPots = 4;
	
	// do we use distractors?
	private boolean usesDistractor;
	
	
	
	public WordStemGameState() {
		
	}
	
	

	/**
	 * @return Returns stems
	 */
	public ArrayList<String> getStems() {
		return stems;
	}



	/**
	 * Set stems
	 * @param stems Set stems to this value
	 */
	public void setStems(ArrayList<String> stems) {
		this.stems = stems;
	}



	/**
	 * @return Returns words
	 */
	public ArrayList<Word> getWords() {
		return words;
	}

	

	/**
	 * Set words
	 * @param words Set words to this value
	 */
	public void setWords(ArrayList<Word> words) {
		this.words = words;
	}

	
	
	/**
	 * @param text Word to get
	 * @return Returns Word with the value text
	 */
	public Word getWord(String text) throws NoSuchElementException {
		for (Word word : words) {
			if (word.getWord().equals(text)) {
				return word;
			}
		}
		throw new NoSuchElementException("No word \""+ text +"\" found.");
	}



	/**
	 * Set taken
	 * @param taken Set taken to this value
	 */
	public void setTaken(ArrayList<String> taken) {
		this.taken = taken;
	}



	/**
	 * @return Returns taken
	 */
	public ArrayList<String> getTaken() {
		return taken;
	}



	/**
	 * Set noOfPots
	 * @param noOfPots Set noOfPots to this value
	 */
	public void setNoOfPots(int noOfPots) {
		this.noOfPots = noOfPots;
	}



	/**
	 * @return Returns noOfPots
	 */
	public int getNoOfPots() {
		return noOfPots;
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
		((WordStemPlayer) newPlayer).setColorId(players.size());
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



	public void setAllWordsTaken() {
		for (Word word : this.words) {
			word.setTaken(true);
		}		
	}


	
}
