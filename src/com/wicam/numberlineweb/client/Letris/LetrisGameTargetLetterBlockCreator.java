package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;

import com.wicam.numberlineweb.client.Letris.LetrisGameModel.Orientation;

/**
 * Take the given target word, divide it into separate letters, put them into a list
 * and return it shuffled so that the single letter blocks can be displayed in the Letris game field.
 * Also include foreign letters that are not members of the given word and rotate some of the letter blocks.
 * @author timfissler
 *
 */

public class LetrisGameTargetLetterBlockCreator {
	
	/**
	 * Current version of the letter block id that is used for generating the
	 * new ids.
	 */
	private static long currentID = 0;
	/**
	 * Ratio of the letters per word (depending on the word size)
	 * that are NOT members of the word itself.
	 */
	private double outstandingLetterRatio;
	/**
	 * Ratio of all letters (including the foreign ones)
	 * that are being returned in a rotated fashion.
	 */
	private double rotatedLetterRatio;
	/**
	 * List containing the single letters of the outstanding words.
	 */
	private ArrayList<String> outstandingLetters;
	/**
	 * List containing all the target letter blocks to be returned.
	 */
	private ArrayList<LetrisGameLetterBlock> targetLetterBlocks = new ArrayList<LetrisGameLetterBlock>();
	/**
	 * The words that are not correctly build yet.
	 */
	private ArrayList<String> outstandingWords;
	/**
	 * The current word being processed.
	 */
	private String targetWord;
	/**
	 * The x position of a letter block at the start of its falling.
	 */
	// TODO This could also be randomized to increase difficulty.
	private int startX;
	/**
	 * The y position of a letter block at the start of its falling.
	 */
	private int startY;

	/**
	 * Holds all wrong orientations of the letter blocks.
	 */
	private ArrayList<Orientation> wrongOrientations = new ArrayList<Orientation>();
	/**
	 * The time between the automatic movement of the currently moving letter block.
	 */
	private int timePerBlock;
	
	public LetrisGameTargetLetterBlockCreator(double foreignLetterRatio, double rotatedLetterRatio,
			int timePerBlock) {
		this.outstandingLetterRatio = foreignLetterRatio;
		this.rotatedLetterRatio = rotatedLetterRatio;
		this.timePerBlock = timePerBlock;
		// The playground should be 10 blocks in width, so set the starting x position
		// to the middle of the playground and choose 5.
		this.startX = 5;
		// The playground should be 20 blocks in height starting from
		// 0 at the bottom. Set the starting position to the top.
		this.startY = 19;
//		fillAlphabet();
		// Fill the wrong orientations.
		wrongOrientations.add(Orientation.EAST);
		wrongOrientations.add(Orientation.NORTH);
		wrongOrientations.add(Orientation.WEST);
	}
	
//	/**
//	 * Fill up the outstandingLetters list with all the letters of the outstandingLetters.
//	 */
//	private void fillAlphabet() {
//		for(char letter = 'A'; letter <= 'Z';letter++){
//			 outstandingLetters.add(String.valueOf(letter));
//		}
//		outstandingLetters.add("Ä");
//		outstandingLetters.add("Ö");
//		outstandingLetters.add("Ü");
//		outstandingLetters.add("ß");
//	}
	
	public void setTimePerBlock(int timePerBlock) {
		this.timePerBlock = timePerBlock;
	}
	
	public int getTimePerBlock() {
		return this.timePerBlock;
	}
	
	public double getOutstandingLetterRatio() {
		return outstandingLetterRatio;
	}

	public void setOutstandingLetterRatio(double foreignLetterRatio) {
		this.outstandingLetterRatio = foreignLetterRatio;
	}

	public double getRotatedLetterRatio() {
		return rotatedLetterRatio;
	}

	public void setRotatedLetterRatio(double rotatedLetterRatio) {
		this.rotatedLetterRatio = rotatedLetterRatio;
	}
	
	/**
	 * Make a new instance of a letter block with a unique version id.
	 * Always use capital letters for the letter blocks. 
	 * @param letter
	 * @param x
	 * @param y
	 * @param orientation
	 * @param timePerBlock
	 * @return the new letter block
	 */
	private LetrisGameLetterBlock makeNewLetterBlock(String letter, int x, int y,
			Orientation orientation, int timePerBlock) {
		LetrisGameLetterBlock letterBlock = new LetrisGameLetterBlock();
		letterBlock.setId(currentID);
		letterBlock.setLetter(letter.toUpperCase());
		letterBlock.setX(x);
		letterBlock.setY(y);
		letterBlock.setOrientation(orientation);
		letterBlock.setTimePerBlock(timePerBlock);
		return letterBlock;
	}
	
	/**
	 * Add those letter blocks to the list that are members of the target word.
	 */
	private void addMemberLetterBlocks() {
		String[] letterArray= targetWord.split("");
		for (String s : letterArray) {
			if (!s.equals("")) {
				LetrisGameLetterBlock letterBlock = makeNewLetterBlock(s, startX, startY, Orientation.SOUTH, timePerBlock);
				targetLetterBlocks.add(letterBlock);
			}
		}
	}
	
	/**
	 * Add those letter blocks that are not members of the target word.
	 * Draw the foreign letter blocks from the letters of the outstanding words and
	 * from the current word. This assures the possibility to build outstanding words more easily.
	 */
	private void addOutstandingLetterBlocks() {
		// Estimate number of foreign letters.
		int targetWordSize = targetWord.length();
		int foreignLetters = (int) Math.floor(targetWordSize * outstandingLetterRatio);
		updateOutstandingLetters();
		// Loop over number of foreign letters.
		for (int i = 0; i < foreignLetters; i++) {
			// Estimate randomly the foreign letter to add.
			String foreignLetter = getRandomOutstandingLetter();
			LetrisGameLetterBlock letterBlock = makeNewLetterBlock(foreignLetter, startX, startY, Orientation.SOUTH, timePerBlock);
			targetLetterBlocks.add(letterBlock);
		}
	}
	
	/**
	 * Randomly rotate some of the letters in the target letter list.
	 * Attention: After a call to this method, the rotated letter blocks
	 * are always at the beginning of the list. So another shuffling
	 * might be suitable.  
	 */
	private void randomRotateLetterBlocks() {
		// Estimate the number of letters to be rotated.
		int numberOfLetters = targetLetterBlocks.size();
		int rotateLetters = (int) Math.floor(numberOfLetters * rotatedLetterRatio);
		// Shuffle the list to rotate some random letter blocks.
		ListShuffler.shuffleList(targetLetterBlocks);
		// Rotate the first n letter blocks.
		for (int i = 0; i < rotateLetters; i++) {
			// Estimate the new orientation of the block randomly.
			Orientation o = getRandomWrongOrientation();
			// Set the new orientation.
			targetLetterBlocks.get(i).setOrientation(o);
		}
		
	}
	
	/**
	 * Return a random wrong orientation.
	 * @return random wrong orientation
	 */
	private Orientation getRandomWrongOrientation() {
		ListShuffler.shuffleList(this.wrongOrientations);
		return this.wrongOrientations.get(0);
	}
	
	/**
	 * Update the list of outstanding letters from the list of outstanding
	 * words from the game state.
	 */
	private void updateOutstandingLetters() {
		// Add the target word to the outstanding words.
		outstandingWords.add(targetWord);
		outstandingLetters = new ArrayList<String>();
		for (String word : outstandingWords) {
			String[] letters = word.split("");
			for (String letter : letters) {
				if (!letter.equals("")) {
					outstandingLetters.add(letter);
				}
			}
		}
	}
	
	/**
	 * Return a random letter of the outstandingLetters. 
	 * @return random letter
	 */
	private String getRandomOutstandingLetter() {
		ListShuffler.shuffleList(outstandingLetters);
		return outstandingLetters.get(0);
	}

	/**
	 * Set a new target word and create the letter blocks
	 * for being displayed later according to this word
	 * and the outstanding words. The target letter blocks
	 * that were created can be returned with getTargetLetterBlocks().
	 * @param targetWord
	 * @param outstandingWords
	 */
	public void createTargetLetterBlocks(String targetWord, ArrayList<String> outstandingWords) {
		this.targetWord = targetWord;
		// Copy the original list to a new instance so that internal changes won't
		// affect the original list.
		this.outstandingWords = new ArrayList<String>(outstandingWords);
		addMemberLetterBlocks();
		addOutstandingLetterBlocks();
		randomRotateLetterBlocks();
		ListShuffler.shuffleList(targetLetterBlocks);
	}
	
	/**
	 * Get the created target letter blocks. 
	 * @return
	 */
	// TODO Should I clone the list before passing it further?
	public ArrayList<LetrisGameLetterBlock> getTargetLetterBlocks() {
		return targetLetterBlocks;
	}
	
	public String getTargetWord() {
		return targetWord;
	}
	
	/*
	// Test the class.
	public static void main(String[] args) {
		LetrisGameTargetLetterBlockListCreater c = new LetrisGameTargetLetterBlockListCreater(0.2, 0.5, null);
		c.createTargetLetterBlocks("Strand");
		System.out.println(c.getTargetLetterBlocks());
		c.createTargetLetterBlocks("Hallöchen");
		System.out.println(c.getTargetLetterBlocks());
	}
	//*/
}