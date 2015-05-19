package com.wicam.numberlineweb.server.MultiplicationInverse;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class to store the features of an item of the inverse Multiplication Game.
 * @author timfissler
 *
 */

public class MultiplicationInverseItem {

	private int firstFactor;
	private int secondFactor;
	private int result;
	private ArrayList<Integer> possibleAnswers;
	private int numberOfPresentations;
	private boolean isSimple;
	
	public MultiplicationInverseItem(int firstFactor, int secondFactor, int result, boolean isSimple) {
		this.firstFactor = firstFactor;
		this.secondFactor = secondFactor;
		this.result = result;
		this.isSimple = isSimple;
		this.numberOfPresentations = 0;
		this.possibleAnswers = new ArrayList<Integer>();
	}
	
	/**
	 * Add a possible answer for this item to the set of answers.
	 * @param answer
	 */
	public void addPossibleAnswer(int answer) {
		this.possibleAnswers.add(answer);
	}
	
	/**
	 * Increase the number of item presentations (e.g. the trials with this item).
	 */
	public void increaseNumberOfPresentations() {
		this.numberOfPresentations++;
	}
	
	public int getNumberOfPresentations() {
		return this.numberOfPresentations;
	}

	public int getFirstFactor() {
		return firstFactor;
	}

	public int getSecondFactor() {
		return secondFactor;
	}

	public int getResult() {
		return result;
	}

	public ArrayList<Integer> getPossibleAnswers() {
		return possibleAnswers;
	}
	
	public boolean isSimple() {
		return this.isSimple;
	}
	
	/**
	 * Return a shuffled version of the possible answers.
	 * @return
	 */
	public ArrayList<Integer> getShuffledPossibleAnsers() {
		ArrayList<Integer> shuffledAnswers = new ArrayList<Integer>();
		// Clone the original list so that it remains sorted.
		for (int x : possibleAnswers) {
			shuffledAnswers.add(x);
		}
		Collections.shuffle(shuffledAnswers);
		return shuffledAnswers;
	}
	
	@Override
	public String toString() {
		String str = "Item: " + firstFactor + ", " + secondFactor + ", " + 
				result + ", ";
		for (int x : this.possibleAnswers) {
			str += x + ", ";
		}
		str += this.isSimple + ". ";
		str += "was presented " + this.numberOfPresentations + " times.";
		return str;
	}

}
