package com.wicam.numberlineweb.client.WordFamily;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class WordFamily implements IsSerializable {
	
	private Word stem;
	private ArrayList<Word> words = new ArrayList<Word>();
	
	public WordFamily(){}
	
	public WordFamily(String stem, ArrayList<String> words) {
		this.stem = new Word(stem);
		for (String string : words) {
			this.words.add(new Word(string));
		}
		
	}
	
	// clone-constructor
	public WordFamily(WordFamily other) {
		this.stem = other.getStem();
		for (Word word : other.getWords()) {
			this.words.add(new Word(word));
		}
	}

	
	/**
	 * Determines, if word is in this WordSet
	 * @param word Word to check
	 * @return True, if word is in this WordSet
	 */
	public boolean contains(Word word) {
		return this.words.contains(word);
	}
	
	/**
	 * Determines, if word is in this WordSet
	 * @param word Word to check
	 * @return True, if word is in this WordSet
	 */
	public boolean contains(String word) {
		return this.contains(new Word(word));
	}
	
	
	/**
	 * @return Returns stem
	 */
	public Word getStem() {
		return stem;
	}

	/**
	 * Set stem
	 * @param stem Set stem to this value
	 */
	public void setStem(Word stem) {
		this.stem = stem;
	}

	/**
	 * @return Returns words
	 */
	public ArrayList<Word> getWords() {
		return words;
	}

	/**
	 * Set words
	 * @param words Set words to this value
	 */
	public void setWords(ArrayList<Word> words) {
		this.words = words;
	}

	
}
