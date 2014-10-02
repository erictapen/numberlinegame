// Answer with properties
package com.wicam.numberlineweb.client.MathAssessment;

import com.google.gwt.user.client.rpc.IsSerializable;


public class MathAssessmentAnswer implements IsSerializable {
	
	protected String answer;
	protected boolean isCorrect;
	protected boolean isTaken;
	protected String color;
	
	
	// only for IsSerializable (requires empty constructor)
	public MathAssessmentAnswer() {
	}
	
	
	/**
	 * @param answer Answer to be displayed
	 * @param isCorrect Marks, if answer is correct
	 */
	public MathAssessmentAnswer(String answer, boolean isCorrect) {
		this.answer = answer;
		this.isCorrect = isCorrect;
		this.isTaken = false;
		this.color = "white";
	}

	
	
	/**
	 * Compares this to an other answer and checks if equal
	 * @param other MathAssessmentAnswer to compare this with
	 * @return Returns true, if other is equal to this
	 */
	public boolean equals(MathAssessmentAnswer other) {
		return this.answer.equals(other.getAnswer());
	}
	
	
	/**
	 * @return Returns the answer
	 */
	@Override
	public String toString() {
		return answer;
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


	/**
	 * Set color
	 * @param color Set color to this value
	 */
	public void setColor(String color) {
		this.color = color;
	}


	/**
	 * @return Returns color
	 */
	public String getColor() {
		return color;
	}

}
