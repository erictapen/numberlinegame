package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Letris.LetrisGameLetterBlock.Orientation;
import com.wicam.numberlineweb.client.VowelGame.ConsonantPoint2D;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;

/**
 * Class that carries the game state of the LeTris game with its letter blocks.
 * @author timfissler
 *
 */

public class LetrisGameState extends GameState implements IsSerializable {

	// TODO Add multiplayer support.
	// TODO Add increasing game speed and/or more rotated letter blocks
	// depending on how much words have been displayed.
	// TODO Add logging outputs to see if the game works correctly.

	/**
	 * The word that is currently being displayed letter block by letter block. 
	 */
	private String currentWord;
	/**
	 * The letter blocks that have already been set on the bottom of the playground. 
	 */
	private ArrayList<LetrisGameLetterBlock> staticLetterBlocks = new ArrayList<LetrisGameLetterBlock>();
	/**
	 * The one letter block that currently moves from top to bottom, e.g. is being dropped.
	 */
	private LetrisGameLetterBlock movingLetterBlock;
	/**
	 * The letter blocks that are waiting to be displayed.
	 */
	private ArrayList<LetrisGameLetterBlock> letterBlocksToBeDisplayed;
	/**
	 * The target letter block creator is used to build a list of letter blocks from the current target word. 
	 */
	private LetrisGameTargetLetterBlockCreator letterBlockCreator;
	
	private LetrisGameCoordinator coordinator;
	/**
	 * The list of target words from that the current word is drawn randomly.
	 */
	private ArrayList<String> targetWords = new ArrayList<String>();
	/**
	 * The words that were build correctly by the player. 
	 */
	private ArrayList<String> correctWords = new ArrayList<String>();
	/**
	 * The words to be recognized. E.g. the words that are being or have been displayed
	 * but weren't build correctly by the player yet.
	 */
	private ArrayList<String> outstandingWords = new ArrayList<String>();
	/**
	 * Hold the position of all static letter blocks in a matrix.
	 */
	private LetrisGameLetterBlock[][] playground = new LetrisGameLetterBlock[20][10];
	
	
	public LetrisGameState(LetrisGameCoordinator coordinator, ArrayList<String> targetWords, double foreignLetterRatio,
			double rotatedLetterRatio, int timePerBlock) {
		super();
		this.coordinator = coordinator;
		this.targetWords = targetWords;
		this.letterBlockCreator = new LetrisGameTargetLetterBlockCreator(foreignLetterRatio, rotatedLetterRatio, timePerBlock, this);
		// Set up all the lists and the moving block.
		currentWord = getRandomWord();
		outstandingWords.add(currentWord);
		letterBlockCreator.createTargetLetterBlocks(currentWord);
		letterBlocksToBeDisplayed = letterBlockCreator.getTargetLetterBlocks();
		movingLetterBlock = letterBlocksToBeDisplayed.get(0);
		letterBlocksToBeDisplayed.remove(0);
	}
	
	public ArrayList<String> getTargetWords() {
		return targetWords;
	}

	public LetrisGameLetterBlock getMovingLetterBlock() {
		return movingLetterBlock;
	}

	public double getForeignLetterRatio() {
		return letterBlockCreator.getForeignLetterRatio();
	}

	public void setForeignLetterRatio(double foreignLetterRatio) {
		letterBlockCreator.setForeignLetterRatio(foreignLetterRatio);
	}

	public double getRotatedLetterRatio() {
		return letterBlockCreator.getRotatedLetterRatio();
	}

	public void setRotatedLetterRatio(double rotatedLetterRatio) {
		letterBlockCreator.setRotatedLetterRatio(rotatedLetterRatio);
	}

	public ArrayList<LetrisGameLetterBlock> getStaticLetterBlocks() {
		return staticLetterBlocks;
	}

	public ArrayList<LetrisGameLetterBlock> getLetterBlocksToBeDisplayed() {
		return letterBlocksToBeDisplayed;
	}
	
	public void setTimePerBlock(int timePerBlock) {
		letterBlockCreator.setTimePerBlock(timePerBlock);
	}
	
	public int getTimePerBlock() {
		return letterBlockCreator.getTimePerBlock();
	}
	
	@Override
	public int addPlayer(String newName, int uid) {
		newName = checkDuplicateName(newName);
		LetrisGamePlayer newPlayer = new LetrisGamePlayer();
		newPlayer.setName(newName);
		newPlayer.setUid(uid);
		players.add(newPlayer);
		return players.size();
	}
	
	public void setStartButtonClicked(int playerid, boolean startButtonClicked) {
		((LetrisGamePlayer)players.get(playerid-1)).setStartButtonClicked(startButtonClicked);
	}

	public boolean hasStartButtonClicked(int playerid) {
		if (playerid-1 < players.size())
			return ((LetrisGamePlayer)players.get(playerid-1)).isStartButtonClicked();
		return false;
	}

	public String getCurrentWord() {
		return currentWord;
	}
	
	/**
	 * Draw a random word from the word list.
	 * @return random word
	 */
	private String getRandomWord() {
		ListShuffler.shuffleList(targetWords);
		return targetWords.get(0);
	}
	
	public ArrayList<String> getCorrectWords() {
		return this.correctWords;
	}
	
	public ArrayList<String> getOutStandingWords() {
		return this.outstandingWords;
	}
	
	/**
	 * Check if the given letter block would collide with on of the static
	 * letter blocks.
	 * @param letterBlock to check
	 * @return true, if the given block would collide
	 */
	public boolean isCollidingWithStaticLetterBlocks(LetrisGameLetterBlock letterBlock) {
		boolean collides = false;
		for (int i = 0; i < staticLetterBlocks.size(); i++) {
			if (letterBlock.isColliding(staticLetterBlocks.get(i))) {
				collides = true;
				break;
			}
		}
		return collides;
	}
	
	public enum MovementDirection implements IsSerializable {
		RIGHT, LEFT, DOWN
	}
	
	public enum RotationDirection implements IsSerializable {
		CLOCKWISE, ANTICLOCKWISE
	}
	
	/**
	 * Handle movement of the currently moving letter block caused
	 * by the player.
	 * @param movementDirection
	 */
	public void moveLetterBlock(MovementDirection movementDirection) {
		movingLetterBlock.moveTo(movementDirection);
	}
	
	/**
	 * Drop the currently moving letter block instantly.
	 */
	public void dropLetterBlock() {
		movingLetterBlock.drop();
	}
	
	/**
	 * Handle rotation of the currently moving letter block caused
	 * by the player.
	 */
	public void rotateLetterBlock(RotationDirection rotationDirection) {
		switch (rotationDirection) {
		case CLOCKWISE:
			switch (movingLetterBlock.getOrientation()) {
			case SOUTH:
				movingLetterBlock.setOrientation(Orientation.WEST);
				break;
			case WEST:
				movingLetterBlock.setOrientation(Orientation.NORTH);
				break;
			case NORTH:
				movingLetterBlock.setOrientation(Orientation.EAST);
				break;
			case EAST:
				movingLetterBlock.setOrientation(Orientation.SOUTH);
				break;
			}
			break;
		case ANTICLOCKWISE:
			switch (movingLetterBlock.getOrientation()) {
			case SOUTH:
				movingLetterBlock.setOrientation(Orientation.EAST);
				break;
			case WEST:
				movingLetterBlock.setOrientation(Orientation.SOUTH);
				break;
			case NORTH:
				movingLetterBlock.setOrientation(Orientation.WEST);
				break;
			case EAST:
				movingLetterBlock.setOrientation(Orientation.NORTH);
				break;
			}
			break;
		}
	}
	
	/**
	 * Pass the given letter block for the position update to the coordinator.
	 * @param letterBlock
	 */
	public void updateMovingLetterBlock(LetrisGameLetterBlock letterBlock) {
		// Update coordinator.
		coordinator.updateMovingLetterBlock(letterBlock);
	}
	
	/**
	 * Check the playground matrix for correct words. If there is one
	 * add it to the correct words list and remove it from the outstanding
	 * words list. Also increase the correct amount of letters.
	 * Delete the letter blocks that build the correct word from the
	 * list of static letter blocks.
	 * @return true, if a correct word was found
	 */
	private void checkForCorrectWord() {
		ArrayList<LetrisGameLetterBlock> foundLetterBlocks;
		// Iterate over all rows.
		for (int i = 0; i < playground.length; i++) {
			foundLetterBlocks = findCorrectWord(playground[i]);
			if (foundLetterBlocks.size() > 0) {
				// Delete the letter blocks from the static ones.
				deleteCorrectLetterBlocks(foundLetterBlocks);
				// Break if one row contains a word.
				break;
			}
		}
	}
	
	/**
	 * Check a row of the playground matrix if it holds a correct word.
	 * Add it to the correct words remove it from the outstanding ones
	 * and also tell the coordinator about the found word.
	 * @param letterBlockRow
	 * @return a list of the found letter blocks that build up the word.
	 */
	private ArrayList<LetrisGameLetterBlock> findCorrectWord(LetrisGameLetterBlock[] letterBlockRow) {
		// Build up string from given array.
		String rowStr = "";
		int startIdx = -1;
		int endIdx = -1; 
		for (int i = 0; i < letterBlockRow.length; i++) {
			rowStr += letterBlockRow[i].getLetter();
		}
		String foundWord = "";
		// Loop over outstanding words and compare them with the string.
		for (String outstandingWord : outstandingWords) {
			startIdx = rowStr.indexOf(outstandingWord);
			if (startIdx >= 0) {
				// Break if a substring is found and save the indices of the substring.
				foundWord = outstandingWord;
				endIdx = startIdx + outstandingWord.length() - 1;
				break;
			}
		}
		outstandingWords.remove(foundWord);
		correctWords.add(foundWord);
		coordinator.foundCorrectWord(foundWord);
		// Build up an array of letter blocks with the given indices and return it.
		ArrayList<LetrisGameLetterBlock> foundLetterBlocks = new ArrayList<LetrisGameLetterBlock>();
		for (int i = startIdx; i <= endIdx; i++) {
			foundLetterBlocks.add(letterBlockRow[i]);
		}
		return foundLetterBlocks;
	}
	
	/**
	 * Delete the given list of blocks from the static list and force the
	 * reminding static list to update the position of letter blocks
	 * that might have been upon the deleted blocks. E.g. they should
	 * be dropped. Update the view and then check again for a correct word
	 * that might be build out of the dropped letter blocks.
	 * @param correctLetterBlocks
	 */
	private void deleteCorrectLetterBlocks(ArrayList<LetrisGameLetterBlock> correctLetterBlocks) {
		// TODO Implement this.
	}
	
	
	/**
	 * Put the current moving letter block into the static letter block list.
	 * Update the playground matrix with this letter block.
	 * Check the static letter blocks for correct words. And if there is one
	 * delete the member letter blocks and add the word to the recognized word list.
	 * Check if the list of letter blocks to be displayed holds at least one
	 * more. If yes, remove it and set it to be the new moving letter block.
	 * If not, get the next random word and fill up the display array and
	 * set the new moving letter block from it.  
	 */
	public void swapMovingLetterBlock() {
		// Add the old moving letter block to the beginning of the list so that it will be
		// sorted from top to bottom. That is useful for collision check, because we start
		// to look for possible collisions at the beginning of the static letter block
		// list.
		staticLetterBlocks.add(movingLetterBlock);
		playground[movingLetterBlock.getY()][movingLetterBlock.getX()] = movingLetterBlock;
		checkForCorrectWord();
		if (letterBlocksToBeDisplayed.size() == 0) {
			currentWord = getRandomWord();
			outstandingWords.add(currentWord);
			letterBlockCreator.createTargetLetterBlocks(currentWord);
			letterBlocksToBeDisplayed = letterBlockCreator.getTargetLetterBlocks();
		}		
		movingLetterBlock = letterBlocksToBeDisplayed.get(0);
		letterBlocksToBeDisplayed.remove(0);
	}
	
	/**
	 * Call the appropriate method of the coordinator.
	 */
	public void registerAnimationTask(AnimationTimerTask task) {
		coordinator.registerAniTask(task);
	}
}
