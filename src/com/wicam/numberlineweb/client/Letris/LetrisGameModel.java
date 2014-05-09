package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;

/**
 * Class that implements the game model of the LeTris game with its data and functions.
 * @author timfissler
 *
 */

public class LetrisGameModel {

	// TODO Add increasing game speed and/or more rotated letter blocks
	// depending on how much words have been displayed.
	// TODO Add logging outputs to see if the game works correctly.
	// TODO Add a movement object for the moving block.
	// TODO Add optional movement objects for the dropping blocks after deletion of a word.
	
	/**
	 * The target letter block creator is used to build a list of letter blocks from the current target word. 
	 */
	private LetrisGameTargetLetterBlockCreator letterBlockCreator;
	/**
	 * The LeTris game coordinator.
	 */
	private LetrisGameCoordinator coordinator;
	/**
	 * The current state of the game;
	 */
	private LetrisGameState gameState;
	/**
	 * The playerId of the client.
	 */
	private int playerId;
	/**
	 * Hold the position of all static letter blocks in a matrix.
	 */
	private LetrisGameLetterBlock[][] playground = new LetrisGameLetterBlock[20][10];
	
	
	public LetrisGameModel(LetrisGameCoordinator coordinator, double foreignLetterRatio,
			double rotatedLetterRatio, int timePerBlock, LetrisGameState gameState, int playerId) {
		super();
		this.coordinator = coordinator;
		this.gameState = gameState;
		this.playerId = playerId;
		this.letterBlockCreator = new LetrisGameTargetLetterBlockCreator(foreignLetterRatio, rotatedLetterRatio, timePerBlock);
		// Set up all the lists and the moving block.
		setNextRandomCurrentWord();
		// Add current word to outstanding words.
		gameState.getPlayerId2OutstandingWords().get(this.playerId).add(gameState.getPlayerId2CurrentWord().get(this.playerId));
		// Create new letter blocks and retrieve them.
		letterBlockCreator.createTargetLetterBlocks(gameState.getPlayerId2CurrentWord().get(this.playerId));
		gameState.getPlayerId2LetterBlocksToBeDisplayed().put(this.playerId, letterBlockCreator.getTargetLetterBlocks());
		// Set the first letter block to be displayed as the moving letter block and
		// remove it from the list.
		gameState.getPlayerId2MovingLetterBlock().put(this.playerId, gameState.getPlayerId2LetterBlocksToBeDisplayed().get(this.playerId).get(0));
		gameState.getPlayerId2LetterBlocksToBeDisplayed().get(this.playerId).remove(0);
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
	
	public void setTimePerBlock(int timePerBlock) {
		letterBlockCreator.setTimePerBlock(timePerBlock);
	}
	
	public int getTimePerBlock() {
		return letterBlockCreator.getTimePerBlock();
	}
	
	/**
	 * Draw a random word from the word list. And set it
	 * as next current word.
	 */
	private void setNextRandomCurrentWord() {
		ListShuffler.shuffleList(gameState.getTargetWords());
		gameState.getPlayerId2CurrentWord().put(this.playerId, gameState.getTargetWords().get(0));
	}
	
	/**
	 * Check if the given letter block would collide with on of the static
	 * letter blocks.
	 * @param letterBlock to check
	 * @return true, if the given block would collide
	 */
	public boolean isCollidingWithStaticLetterBlocks(LetrisGameLetterBlock letterBlock) {
		boolean collides = false;
		ArrayList<LetrisGameLetterBlock> staticLetterBlocks = gameState.getPlayerId2StaticLetterBlocks().get(this.playerId);
		for (int i = 0; i < staticLetterBlocks.size(); i++) {
			if (letterBlock.isColliding(staticLetterBlocks.get(i))) {
				collides = true;
				break;
			}
		}
		return collides;
	}
	
	public enum MovementDirection {
		RIGHT, LEFT, DOWN
	}
	
	public enum RotationDirection {
		CLOCKWISE, ANTICLOCKWISE
	}
	
	/**
	 * Enum for possible orientations of a letter block.
	 * The orientation gives the direction from which
	 * one could read the letter properly. 
	 * @author timfissler
	 *
	 */
	public enum Orientation {
		NORTH, SOUTH, EAST, WEST;
		
		public String toString() {
			String s = "";
			switch (this) {
			case NORTH:
				s = "NORTH";
				break;
			case SOUTH:
				s = "SOUTH";
				break;
			case EAST:
				s = "EAST";
				break;
			case WEST:
				s = "WEST";
				break;
			default:
				break;
			}
			return s;
		}
		
	}
	
	/**
	 * Handle movement of the currently moving letter block caused
	 * by the player.
	 * @param movementDirection
	 */
	public void moveLetterBlock(MovementDirection movementDirection) {
		gameState.getPlayerId2MovingLetterBlock().get(this.playerId).moveTo(movementDirection);
	}
	
	/**
	 * Drop the currently moving letter block instantly.
	 */
	public void dropLetterBlock() {
		gameState.getPlayerId2MovingLetterBlock().get(this.playerId).drop();
	}
	
	/**
	 * Handle rotation of the currently moving letter block caused
	 * by the player.
	 */
	public void rotateLetterBlock(RotationDirection rotationDirection) {
		LetrisGameLetterBlock movingLetterBlock = gameState.getPlayerId2MovingLetterBlock().get(this.playerId);
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
		ArrayList<String> outstandingWords = gameState.getPlayerId2OutstandingWords().get(this.playerId);
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
		gameState.getPlayerId2CorrectWords().get(this.playerId).add(foundWord);
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
		LetrisGameLetterBlock movingLetterBlock = gameState.getPlayerId2MovingLetterBlock().get(this.playerId);
		gameState.getPlayerId2StaticLetterBlocks().get(this.playerId).add(movingLetterBlock);
		playground[movingLetterBlock.getY()][movingLetterBlock.getX()] = movingLetterBlock;
		checkForCorrectWord();
		ArrayList<LetrisGameLetterBlock> letterBlocksToBeDisplayed = gameState.getPlayerId2LetterBlocksToBeDisplayed().get(this.playerId);
		if (letterBlocksToBeDisplayed.size() == 0) {
			setNextRandomCurrentWord();
			// Add current word to outstanding words.
			gameState.getPlayerId2OutstandingWords().get(this.playerId).add(gameState.getPlayerId2CurrentWord().get(this.playerId));
			// Create new letter blocks and retrieve them.
			letterBlockCreator.createTargetLetterBlocks(gameState.getPlayerId2CurrentWord().get(this.playerId));
			gameState.getPlayerId2LetterBlocksToBeDisplayed().put(this.playerId, letterBlockCreator.getTargetLetterBlocks());
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
