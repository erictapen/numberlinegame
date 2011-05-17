package com.wicam.numberlineweb.client.DoppelungGame;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DoppelungGameWord implements IsSerializable{
	
	private boolean shortVowel = false;
	private String word = "";
	private String consonantPair = "";
	private int soundTries = 0;
	private int wordTries = 0;

	public void setShortVowel(boolean shortVowel) {
		this.shortVowel = shortVowel;
	}

	public boolean isShortVowel() {
		return shortVowel;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getWordString() {
		return word;
	}

	public void setConsonantPair(String consonantPair) {
		this.consonantPair = consonantPair;
	}

	public String getConsonantPair() {
		return consonantPair;
	}
	
	public int getSoundTries(){
		return soundTries;
	}
	
	public void incSoundTries(){
		soundTries++;
	}
	
	public int getWordTries(){
		return wordTries;
	}
	
	public void incWordTries(){
		wordTries++;
	}
}
