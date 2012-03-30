// Answer with properties
package com.wicam.numberlineweb.client.OverTen;

import com.google.gwt.user.client.rpc.IsSerializable;


public class OverTenDigit implements IsSerializable {
	
	private int value;
	private boolean isTaken;
	private boolean chosen;
	
	
	
	// only for IsSerializable (requires empty constructor)
	public OverTenDigit() {
	}
	
	
	/**
	 * @param value Value to be displayed
	 */
	public OverTenDigit(int value) {
		this.value = value;
		this.isTaken = false;
		this.chosen = false;
	}

	
	
	/**
	 * Compares this to an other digit and checks if equal
	 * @param other Object to compare this with
	 * @return Returns true, if other is equal to this
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		OverTenDigit c = (OverTenDigit) obj;
		return (this.value == c.getValue());
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


	/**
	 * Set chosen
	 * @param chosen Set chosen to this value
	 */
	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}


	/**
	 * @return Returns chosen
	 */
	public boolean isChosen() {
		return chosen;
	}
	
	
	

}
