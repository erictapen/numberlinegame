package com.wicam.numberlineweb.client.VowelGame;

import com.google.gwt.user.client.rpc.IsSerializable;

public class VowelGameWord implements IsSerializable{
	
	private boolean shortVowel = false;
	private String word = "";
	private String consonantPair = "";

	public VowelGameWord(){}
	
	public VowelGameWord(String word, String consPair) {
		this.word = word;
		this.consonantPair = consPair;
		this.shortVowel = true;
	}
	
	public VowelGameWord(String word) {
		this.word = word;
		this.shortVowel = false;
	}
	
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
	
	public String toString() {
		return "(" + this.word + ", " + this.consonantPair + ")"; 
	}
}
