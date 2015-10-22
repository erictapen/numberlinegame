package com.wicam.numberlineweb.client.Multiplication;

import java.io.Serializable;
import java.util.ArrayList;

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
	protected int result;
	
	// All wrong and right answers in the game
	protected ArrayList<MultiplicationAnswer> answers = new ArrayList<MultiplicationAnswer>();
	
	// Round-counter
	protected int round = 0;
	// Max number of rounds to play
	protected int maxRound;
	
	
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
	 * @param correct Specifies, if the answers have to be correct
	 * @param taken Specifies, if the answers have to be taken
	 * @return Returns all answers, that are "correct" (given parameter)
	 */
	public ArrayList<MultiplicationAnswer> getSpecificAnswers(boolean correct, boolean taken) {
		ArrayList<MultiplicationAnswer> res = new ArrayList<MultiplicationAnswer>();
		for (MultiplicationAnswer answer : this.answers) {
			if (answer.isCorrect() == correct && answer.isTaken() == taken) {
				res.add(answer);
			}
		}
		return res;
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
		MultiplicationPlayer newPlayer = new MultiplicationPlayer();
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
	
}