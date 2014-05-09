package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.wicam.numberlineweb.client.Letris.LetrisGameLetterBlock.Orientation;

/**
 * Take the given target word, divide it into separate letters, put them into a list
 * and return it shuffled so that the single letter blocks can be displayed in the Letris game field.
 * Also include foreign letters that are not members of the given word and rotate some of the letter blocks.
 * @author timfissler
 *
 */

public class LetrisGameTargetLetterBlockCreator implements IsSerializable {
	
	/**
	 * Ratio of the letters per word (depending on the word size)
	 * that are NOT members of the word itself.
	 */
	private double foreignLetterRatio;
	/**
	 * Ratio of all letters (including the foreign ones)
	 * that are being returned in a rotated fashion.
	 */
	private double rotatedLetterRatio;
	/**
	 * List containing the whole alphabet.
	 */
	private ArrayList<String> alphabet = new ArrayList<String>();
	/**
	 * List containing all the target letter blocks to be returned.
	 */
	private ArrayList<LetrisGameLetterBlock> targetLetterBlocks = new ArrayList<LetrisGameLetterBlock>();
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

	private LetrisGameState gameState;
	/**
	 * Holds all wrong orientations of the letter blocks.
	 */
	private ArrayList<Orientation> wrongOrientations = new ArrayList<Orientation>();
	/**
	 * The time between the automatic movement of the currently moving letter block.
	 */
	private int timePerBlock;
	
	public LetrisGameTargetLetterBlockCreator(double foreignLetterRatio, double rotatedLetterRatio,
			int timePerBlock, LetrisGameState gameState) {
		this.foreignLetterRatio = foreignLetterRatio;
		this.rotatedLetterRatio = rotatedLetterRatio;
		this.timePerBlock = timePerBlock;
		this.gameState = gameState;
		// The playground should be 10 blocks in width, so set the starting x position
		// to the middle of the playground and choose 5.
		this.startX = 5;
		// The playground should be 20 blocks in height starting from
		// 0 at the bottom. Set the starting position to the top.
		this.startY = 19;
		fillAlphabet();
		// Fill the wrong orientations.
		wrongOrientations.add(Orientation.EAST);
		wrongOrientations.add(Orientation.NORTH);
		wrongOrientations.add(Orientation.WEST);
	}
	
	/**
	 * Fill up the alphabet list with all the letters of the alphabet.
	 */
	private void fillAlphabet() {
		for(char letter = 'A'; letter <= 'Z';letter++){
			 alphabet.add(String.valueOf(letter));
		}
		alphabet.add("Ä");
		alphabet.add("Ö");
		alphabet.add("Ü");
		alphabet.add("ß");
	}
	
	public void setTimePerBlock(int timePerBlock) {
		this.timePerBlock = timePerBlock;
	}
	
	public int getTimePerBlock() {
		return this.timePerBlock;
	}
	
	public double getForeignLetterRatio() {
		return foreignLetterRatio;
	}

	public void setForeignLetterRatio(double foreignLetterRatio) {
		this.foreignLetterRatio = foreignLetterRatio;
	}

	public double getRotatedLetterRatio() {
		return rotatedLetterRatio;
	}

	public void setRotatedLetterRatio(double rotatedLetterRatio) {
		this.rotatedLetterRatio = rotatedLetterRatio;
	}
	
	/**
	 * Add those letter blocks to the list that are members of the target word.
	 */
	private void addMemberLetterBlocks() {
		String[] letterArray= targetWord.split("");
		for (String s : letterArray) {
			if (!s.equals("")) {
				LetrisGameLetterBlock letterBlock = new LetrisGameLetterBlock(s, startX, startY,
															Orientation.SOUTH, timePerBlock, gameState);
				targetLetterBlocks.add(letterBlock);
			}
		}
	}
	
	/**
	 * Add those letter blocks that are not members of the target word.
	 * Estimate them randomly from the alphabet.
	 */
	private void addForeignLetterBlocks() {
		// Estimate number of foreign letters.
		int targetWordSize = targetWord.length();
		int foreignLetters = (int) Math.floor(targetWordSize * foreignLetterRatio); 
		// Loop over number of foreign letters.
		for (int i = 0; i < foreignLetters; i++) {
			// Estimate randomly the foreign letter to add.
			String foreignLetter = getRandomLetter();
			LetrisGameLetterBlock letterBlock = new LetrisGameLetterBlock(foreignLetter, startX, startY,
														Orientation.SOUTH, timePerBlock, gameState);
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
	 * Return a random letter of the alphabet. 
	 * @return random letter
	 */
	private String getRandomLetter() {
		ListShuffler.shuffleList(alphabet);
		return alphabet.get(0);
	}

	public void createTargetLetterBlocks(String targetWord) {
		this.targetWord = targetWord;
		addMemberLetterBlocks();
		addForeignLetterBlocks();
		randomRotateLetterBlocks();
		ListShuffler.shuffleList(targetLetterBlocks);
	}
	
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