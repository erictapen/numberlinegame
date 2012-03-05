// Answer with properties
package com.wicam.numberlineweb.client.Multiplication;

import com.google.gwt.user.client.rpc.IsSerializable;


public class MultiplicationAnswer implements IsSerializable {
	
	private String answer;
	private boolean isCorrect;
	private boolean isTaken;
	
	
	
	// only for IsSerializable (requires empty constructor)
	public MultiplicationAnswer() {
	}
	
	
	/**
	 * @param answer Answer to be displayed
	 * @param isCorrect Marks, if answer is correct
	 */
	public MultiplicationAnswer(String answer, boolean isCorrect) {
		this.answer = answer;
		this.isCorrect = isCorrect;
		this.isTaken = false;
	}

	
	
	/**
	 * Compares this to an other answer and checks if equal
	 * @param other MultiplicationAnswer to compare this with
	 * @return Returns true, if other is equal to this
	 */
	public boolean isEqualTo(MultiplicationAnswer other) {
		return (this.answer.compareTo(other.getAnswer()) == 0);
	}
	
	

	/**
	 * @return Returns answer
	 */
	public String getAnswer() {
		return answer;
	}


	/**
	 * Set answer
	 * @param answer Set answer to this value
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}


	/**
	 * @return Returns isCorrect
	 */
	public boolean isCorrect() {
		return isCorrect;
	}


	/**
	 * Set isCorrect
	 * @param isCorrect Set isCorrect to this value
	 */
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}


	/**
	 * @return Returns isTaken
	 */
	public boolean isTaken() {
		return isTaken;
	}


	/**
	 * Set isTaken to true
	 */
	public void setTaken() {
		this.isTaken = true;
	}

}
