// Answer with properties
package com.wicam.numberlineweb.client.BuddyNumber;

import com.google.gwt.user.client.rpc.IsSerializable;


public class BuddyNumberDigit implements IsSerializable {
	
	private int value;
	private boolean isTaken;
	
	
	
	// only for IsSerializable (requires empty constructor)
	public BuddyNumberDigit() {
	}
	
	
	/**
	 * @param value Value to be displayed
	 */
	public BuddyNumberDigit(int value) {
		this.value = value;
		this.isTaken = false;
	}

	
	
	/**
	 * Compares this to an other digit and checks if equal
	 * @param other BuddyNumberDigit to compare this with
	 * @return Returns true, if other is equal to this
	 */
	public boolean equals(BuddyNumberDigit other) {
		return this.value == other.getValue();
	}


	/**
	 * @return Returns value
	 */
	public int getValue() {
		return value;
	}
	
	
	/**
	 * @return Returns value as String
	 */
	@Override
	public String toString() {
		return Integer.toString(value);
	}


	/**
	 * Set value
	 * @param value Set value to this value
	 */
	public void setValue(int value) {
		this.value = value;
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
