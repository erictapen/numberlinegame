package com.wicam.numberlineweb.client.NumberLineGame;

import java.io.Serializable;

import com.wicam.numberlineweb.client.Player;

public class NumberLineGamePlayer extends Player implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2437389490131688801L;
	private boolean clicked = false;
	private int actPos = Integer.MIN_VALUE;
	private int colorId = 0;

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	
	public boolean isClicked() {
		return clicked;
	}
	
	public void setActPos(int actPos) {
		this.actPos = actPos;
	}
	
	public int getActPos() {
		return actPos;
	}
	
	
}