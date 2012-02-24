// Answer with properties
package com.wicam.numberlineweb.client.Multiplication;


public class MultiplicationAnswer {
	
	private String answer;
	private boolean isCorrect;
	private boolean isTaken;
	
	
	public MultiplicationAnswer(String answer, boolean isCorrect) {
		this.answer = answer;
		this.isCorrect = isCorrect;
		this.isTaken = false;
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
	 * Set isTaken
	 * @param isTaken Set isTaken to this value
	 */
	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}
	
	
	
	
	
	

}
