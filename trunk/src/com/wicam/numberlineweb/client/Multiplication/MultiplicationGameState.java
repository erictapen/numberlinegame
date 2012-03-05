package com.wicam.numberlineweb.client.Multiplication;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;


/**
 * This is the state sent by the server. Holds all game information.
 * @author alex
 *
 */

public class MultiplicationGameState extends GameState implements Serializable{

	// we need a serial number for GWT to identify sent instances
	private static final long serialVersionUID = 846864907276321588L;
	
	// The result
	private int result;
	
	// All wrong and right answers in the game
	private ArrayList<MultiplicationAnswer> answers = new ArrayList<MultiplicationAnswer>();
	
	// Round-counter
	private int round = 0;
	// Max number of rounds to play
	private int maxRound;
	
	
	public MultiplicationGameState() {
		
	}
	
	
	
	/**
	 * @param res The new result
	 */
	public void setResult(int res) {
		this.result = res;
	}
	
	
	
	/**
	 * @return Returns the result
	 */
	public int getResult() {
		return this.result;
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
	 * @return Returns answers
	 */
	public ArrayList<MultiplicationAnswer> getAnswers() {
		return answers;
	}



	/**
	 * Set answers
	 * @param answers Set answers to this value
	 */
	public void setAnswers(ArrayList<MultiplicationAnswer> answers) {
		this.answers = answers;
	}



	/**
	 * @return Returns the number of not taken answers in game
	 */
	public int getNoOfAnswers() {
		int counter = 0;
		for (MultiplicationAnswer answer : this.answers) {
			if (!answer.isTaken())
				counter++;
		}
		return counter;
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
		MultiplicationPlayer newPlayer = new MultiplicationPlayer();
		newPlayer.setName(newName);
		((MultiplicationPlayer) newPlayer).setColorId(players.size());
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
	
}
