package com.wicam.numberlineweb.client.LetrisPush;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Carries all information that is needed to show the playground and
 * the points of the opponent player.
 * @author timfissler
 *
 */

public class LetrisPushGamePlaygroundState implements IsSerializable {
	
	private int opponentPlayerID;
	private int playerID;
	private int points;
	private ArrayList<LetrisPushGameLetterBlock> staticLetterBlocks;
	private int fillerRowsLevelDiff;
	
	/**
	 * @return the playerID
	 */
	public int getPlayerID() {
		return playerID;
	}
	/**
	 * @param playerID the playerID to set
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	/**
	 * @return the fillerRowsLevelDiff
	 */
	public int getFillerRowsLevelDiff() {
		return fillerRowsLevelDiff;
	}
	/**
	 * @param fillerRowsLevelDiff the fillerRowsLevelDiff to set
	 */
	public void setFillerRowsLevelDiff(int fillerRowsLevelDiff) {
		this.fillerRowsLevelDiff = fillerRowsLevelDiff;
	}
	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}
	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	/**
	 * @return the staticLetterBlocks
	 */
	public ArrayList<LetrisPushGameLetterBlock> getStaticLetterBlocks() {
		return staticLetterBlocks;
	}
	/**
	 * @param staticLetterBlocks the staticLetterBlocks to set
	 */
	public void setStaticLetterBlocks(
			ArrayList<LetrisPushGameLetterBlock> staticLetterBlocks) {
		this.staticLetterBlocks = staticLetterBlocks;
	}
	/**
	 * @return the oppoenentPlayerID
	 */
	public int getOpponentPlayerID() {
		return opponentPlayerID;
	}
	/**
	 * @param opponentPlayerID the playerID of the opponent to set
	 */
	public void setOpponentPlayerID(int playerID) {
		this.opponentPlayerID = playerID;
	}

}
