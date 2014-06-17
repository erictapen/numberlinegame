package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;

import com.wicam.numberlineweb.client.Letris.LetrisGameModel.Orientation;

/**
 * Take the given target word, divide it into separate letters, put them into a list
 * and return it shuffled so that the single letter blocks can be displayed in the Letris game field.
 * Also rotate some of the letter blocks.
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
	 * Ratio of all letters (including the foreign ones)
	 * that are being returned in a rotated fashion.
	 */
	private double rotatedLetterRatio;
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
	/**
	 * Holds all wrong orientations of the letter blocks.
	 */
	private ArrayList<Orientation> wrongOrientations = new ArrayList<Orientation>();
	/**
	 * The time between the automatic movement of the currently moving letter block.
	 */
	private int timePerBlock;
	
	public LetrisGameTargetLetterBlockCreator(double rotatedLetterRatio,
			int timePerBlock) {
		this.rotatedLetterRatio = rotatedLetterRatio;
		this.timePerBlock = timePerBlock;
		// The playground should be 15 blocks in width, so set the starting x position
		// to the middle of the playground and choose 7.
		this.startX = 7;
		// The playground should be 19 blocks in height starting from
		// 0 at the bottom. Set the starting position to the top.
		this.startY = 18;
		// Fill the wrong orientations.
		wrongOrientations.add(Orientation.EAST);
		wrongOrientations.add(Orientation.NORTH);
		wrongOrientations.add(Orientation.WEST);
	}
	
	public void setTimePerBlock(int timePerBlock) {
		this.timePerBlock = timePerBlock;
	}
	
	public int getTimePerBlock() {
		return this.timePerBlock;
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
		currentID++;
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
	private void addLetterBlocks() {
		String[] letterArray= targetWord.split("");
		for (String s : letterArray) {
			if (!s.equals("")) {
				LetrisGameLetterBlock letterBlock = makeNewLetterBlock(s, startX, startY, Orientation.SOUTH, timePerBlock);
				targetLetterBlocks.add(letterBlock);
			}
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
	 * Set a new target word and create the letter blocks
	 * for being displayed later according to this word.
	 * The target letter blocks that were created can be
	 * returned with getTargetLetterBlocks().
	 * @param targetWord
	 */
	public void createTargetLetterBlocks(String targetWord) {
		this.targetWord = targetWord;
		targetLetterBlocks.clear();
		addLetterBlocks();
		randomRotateLetterBlocks();
		ListShuffler.shuffleList(targetLetterBlocks);
	}
	
	/**
	 * Copy the given letter block.
	 * @param letterBlock
	 * @return
	 */
	public LetrisGameLetterBlock copyLetterBlock(LetrisGameLetterBlock letterBlock) {
		return makeNewLetterBlock(letterBlock.getLetter(),
				letterBlock.getX(),
				letterBlock.getY(),
				letterBlock.getOrientation(),
				letterBlock.getTimePerBlock());
	}
	
	/**
	 * Get the created target letter blocks. 
	 * @return
	 */
	public ArrayList<LetrisGameLetterBlock> getTargetLetterBlocks() {
		return targetLetterBlocks;
	}
	
	public String getTargetWord() {
		return targetWord;
	}
	
}