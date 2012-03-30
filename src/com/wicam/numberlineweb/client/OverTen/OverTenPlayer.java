package com.wicam.numberlineweb.client.OverTen;

import java.io.Serializable;

import com.wicam.numberlineweb.client.Player;
import com.wicam.numberlineweb.client.OverTen.OverTenCalculation.Sign;

public class OverTenPlayer extends Player implements Serializable{

	// Necessary for Serializable
	private static final long serialVersionUID = 2437389490131688801L;
	
	/**
	 * The calculation the user clicked on.
	 * null, if none clicked, else contains OverTenCalculation clicked
	 */
	private OverTenCalculation calculation = new OverTenCalculation();

	/**
	 * The first community-digit the user clicked on.
	 * -1, if none clicked, else contains index clicked
	 */
	private int firstDigit = -1;

	
	/**
	 * Reset the player's calculation to "0 + 0"
	 */
	public void reset() {
		this.setCalculation(new OverTenCalculation());
		this.setFirstDigit(-1);
	}
	
	
	/**
	 * @return Returns calculation
	 */
	public OverTenCalculation getCalculation() {
		return calculation;
	}

	/**
	 * Set calculation
	 * @param calculation Set calculation to this value
	 */
	public void setCalculation(OverTenCalculation calculation) {
		this.calculation = calculation;
	}

	/**
	 * @return Returns firstDigit
	 */
	public int getFirstDigit() {
		return firstDigit;
	}

	/**
	 * Set firstDigit
	 * @param firstDigit Set firstDigit to this value
	 */
	public void setFirstDigit(int firstDigit) {
		this.firstDigit = firstDigit;
	}

	
}