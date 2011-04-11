package com.wicam.numberlineweb.client.DoppelungGame;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DoppelungGameWord implements IsSerializable{
	
	private boolean shortVowel = false;
	private String word = "";

	public void setShortVowel(boolean shortVowel) {
		this.shortVowel = shortVowel;
	}

	public boolean isShortVowel() {
		return shortVowel;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}
}
