package com.wicam.numberlineweb.client.SpellingAssessment;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SpellingAssessmentState implements IsSerializable {

	/**
	 * The name of the player as retrieved from the drupal database.
	 */
	protected String playerName = "";
	/**
	 * The identification number of the assessment client as it is
	 * declared by the server.
	 */
	protected int assessmentID = 0;
	/**
	 * The user ID as given from drupal.
	 */
	protected int userID = 0;
	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}
	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	/**
	 * @return the assessmentID
	 */
	public int getAssessmentID() {
		return assessmentID;
	}
	/**
	 * @param assessmentID the assessmentID to set
	 */
	public void setAssessmentID(int assessmentID) {
		this.assessmentID = assessmentID;
	}
	
}
