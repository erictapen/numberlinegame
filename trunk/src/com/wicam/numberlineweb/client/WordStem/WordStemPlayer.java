package com.wicam.numberlineweb.client.WordStem;

import java.io.Serializable;

import com.wicam.numberlineweb.client.Player;

public class WordStemPlayer extends Player implements Serializable{

	// Necessary for Serializable
	private static final long serialVersionUID = 2437389490131688801L;
	
	
	/**
	 * The hand-digit the user clicked on.
	 * 0, if none clicked, else contains value clicked
	 */
	private String clickedOn = "";


	/**
	 * Set clickedOn
	 * @param clickedOn Set clickedOn to this value
	 */
	public void setClickedOn(String clickedOn) {
		this.clickedOn = clickedOn;
	}


	/**
	 * @return Returns clickedOn
	 */
	public String getClickedOn() {
		return clickedOn;
	}
	
}