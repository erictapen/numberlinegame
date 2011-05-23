package com.wicam.numberlineweb.client.DoppelungGame;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ConsonantPoint2D implements IsSerializable {

	private int x = 0;
	private int y = 0;
	private boolean removed = false;
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

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setConsonant(String consonant) {
		this.consonant = consonant;
	}

	public String getConsonant() {
		return consonant;
	}
	
}
