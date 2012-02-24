package com.wicam.numberlineweb.client.Multiplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

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
	
	// Random generator
	private Random rand = new Random();
	
	// The string used as multiplication sign
	private String sign = " x ";
	
	// All possible divisors
	private int[] possibleDivisors = {2,3,4,5,6,7,8,9};
	
	// The result
	private int result;
	
	// All wrong and right answers in the game
	private ArrayList<MultiplicationAnswer> answers;
	
	// Round-counter
	private int round = 0;
	// Max number of rounds to play
	private int maxRound;
	
	
	public MultiplicationGameState() {
		
	}
	
	
	
	/**
	 * @return Returns a random divisor
	 */
	private int getRandomDivisor() {
		return this.possibleDivisors[this.rand.nextInt(this.possibleDivisors.length)];
	}
	
	
	
	/**
	 * Generates a new result with a set of wrong and right answers
	 * @return Returns the new result
	 */
	public int setNewResult() {
		
		int first = getRandomDivisor();
		int second = getRandomDivisor();
		this.result = first * second;
		
		int noOfAnswers = 0;
		int randomNumber = rand.nextInt(11);
		
		// generate new answers as long there are less than 12
		while (noOfAnswers < 12) {
			int a = getRandomDivisor();
			int b = getRandomDivisor();
			
			// insert at least one right answer by chance
			if (noOfAnswers == randomNumber) {
				this.answers.add(new MultiplicationAnswer(first+this.sign+b, true));
				noOfAnswers++;
			}
			
			// genarate new answer
			MultiplicationAnswer newAnswer = new MultiplicationAnswer(a+this.sign+b, (a*b == this.result));
			
			// check, if the answer exists. if not, add to list
			if (!this.answers.contains(newAnswer)) {
				this.answers.add(newAnswer);
				noOfAnswers++;
			}
			
		}
		
		// increment the round-counter
		this.round++;
		
		return this.result;
	}
	
	
	/**
	 * @return Returns the result
	 */
	public int getResult() {
		return this.result;
	}
	
	
	
	/**
	 * @return Returns the result
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
	 * @return Returns all answers scrambled
	 */
	public ArrayList<MultiplicationAnswer> getScrambled() {
		return this.answers;
	}
	
	
	
	/**
	 * Deletes a given answer
	 * @param toDelete The answer to delete
	 * @return Returns, if the answer was deleted
	 */
	public boolean deleteAnswer(String toDelete) {
		boolean ret = false;
		for (MultiplicationAnswer answer : this.answers) {
			if (answer.getAnswer() == toDelete) {
				answer.setTaken(true);
				ret = true;
			}
		}
		return ret;
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
