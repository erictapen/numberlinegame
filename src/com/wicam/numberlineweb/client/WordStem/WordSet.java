package com.wicam.numberlineweb.client.WordStem;

import java.io.Serializable;
import java.util.ArrayList;

public class WordSet implements Serializable {

	private static final long serialVersionUID = -3724485004558663839L;
	
	private String stem;
	private ArrayList<Word> words = new ArrayList<Word>();
	private Word distractor;
	private boolean hasDistractor;
	
	public WordSet(){}
	
	public WordSet(String stem, ArrayList<String> words, String distractor) {
		this.stem = stem;
		for (String string : words) {
			this.words.add(new Word(string));
		}
		this.distractor = new Word(distractor);
		this.hasDistractor = (distractor != "");
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
	 * @return Returns stem
	 */
	public String getStem() {
		return stem;
	}

	/**
	 * Set stem
	 * @param stem Set stem to this value
	 */
	public void setStem(String stem) {
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

	/**
	 * @return Returns distractor
	 */
	public Word getDistractor() {
		return distractor;
	}

	/**
	 * Set distractor
	 * @param distractor Set distractor to this value
	 */
	public void setDistractor(Word distractor) {
		this.distractor = distractor;
	}

	/**
	 * @return Returns hasDistractor
	 */
	public boolean hasDistractor() {
		return hasDistractor;
	}
	
	
	
	
	
}
