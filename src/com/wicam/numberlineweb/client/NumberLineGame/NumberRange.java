package com.wicam.numberlineweb.client.NumberLineGame;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NumberRange implements IsSerializable{

	private int minNumber = 100;
	private int maxNumber = 999;
	private boolean isRandom = true;

	public int getMinNumber() {
		return minNumber;
	}

	public void setMinNumber(int minNumber) {
		this.minNumber = minNumber;
	}

	public int getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}

	public boolean isRandom() {
		return isRandom;
	}

	public void setRandom(boolean isRandom) {
		this.isRandom = isRandom;
	}
	
	public void setRange(int minNumber, int maxNumber, boolean isRandom){
		this.setMinNumber(minNumber);
		this.setMaxNumber(maxNumber);
		this.setRandom(isRandom);
	}
}
