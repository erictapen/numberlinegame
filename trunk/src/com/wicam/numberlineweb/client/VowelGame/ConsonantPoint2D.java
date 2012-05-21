package com.wicam.numberlineweb.client.VowelGame;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Class for the transmission of x/y-coordinates of the moving consonants
 * 
 * @author shuber
 *
 */

public class ConsonantPoint2D implements IsSerializable {

	private int x = 0;
	private int y = 0;
	private boolean removed = false;
	private boolean collected = false;
	private int collectorPlayerID;
	private String consonant = "";
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	
	
	/**
	 * Set the boolean variable removed
	 * 
	 * @param removed   variable indicating if the point has been removed
	 */
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public boolean isRemoved() {
		return removed;
	}
	
	public void setCollected(boolean collected, int playerid) {
		this.collected = collected;
		this.collectorPlayerID = playerid;
	}

	public boolean isCollected() {
		return this.collected;
	}
	
	public int getCollectorPlayerID() {
		return this.collectorPlayerID;
	}

	/**
	 * Set the consonants which the point represents
	 * 
	 * @param consonant	String containing the consonants
	 */
	public void setConsonant(String consonant) {
		this.consonant = consonant;
	}

	public String getConsonant() {
		return consonant;
	}
	
}
