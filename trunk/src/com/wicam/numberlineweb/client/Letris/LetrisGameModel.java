package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;

/**
 * Class that implements the game model of the LeTris game with its data and functions.
 * @author timfissler
 *
 */

public class LetrisGameModel {

	// TODO Add increasing game speed and/or more rotated letter blocks
	// depending on how much words have been displayed.
	// TODO Add logging outputs to see if the game works correctly.
	// TODO Add optional movement objects for the dropping blocks after deletion of a word.
	// TODO Tell the GameLogger that the user set a block and that a word was found.
	// TODO Add game over condition.
	// TODO Change outstanding to missing.
	// TODO Fire update method in coordinator after changes in GameState?
	
	/**
	 * The target letter block creator is used to build a list of letter blocks from the current target word. 
	 */
	private LetrisGameTargetLetterBlockCreator letterBlockCreator;
	/**
	 * The LeTris game coordinator.
	 */
	private LetrisGameCoordinator coordinator;
	/**
	 * The playerId of the client.
	 */
	private int playerId;
	/**
	 * Hold the position of all static letter blocks in a matrix.
	 */
	private LetrisGameLetterBlock[][] playground = new LetrisGameLetterBlock[20][10];
	/**
	 * Moves the current moving letter block.
	 */
	private LetrisGameMoveLetterBlockTask movingLetterBlockTask;
	
	
	public LetrisGameModel(LetrisGameCoordinator coordinator, double foreignLetterRatio,
			double rotatedLetterRatio, int timePerBlock, int playerId) {
		super();
		this.coordinator = coordinator;
		this.playerId = playerId;
		this.letterBlockCreator = new LetrisGameTargetLetterBlockCreator(foreignLetterRatio, rotatedLetterRatio, timePerBlock);
	}
	
	/**
	 * Construct a functional LeTris game state from a given empty one.
	 * @param gameState the empty game state
	 * @return gameState the functional game state
	 */
	public LetrisGameState setupGameState(LetrisGameState gameState) {
		gameState.setStaticLetterBlocks(playerId, new ArrayList<LetrisGameLetterBlock>());
		// Set up all the lists and the moving block.
		gameState.setCurrentWord(playerId, getNextRandomCurrentWord());
		// Add current word to outstanding words.
		gameState.addMissingWord(playerId, gameState.getCurrentWord(playerId));
		// Create new letter blocks and retrieve them.
		letterBlockCreator.createTargetLetterBlocks(gameState.getCurrentWord(playerId), gameState.getMissingWords(playerId));
		gameState.setLetterBlocksToBeDisplayed(playerId, letterBlockCreator.getTargetLetterBlocks());
		// Set the first letter block to be displayed as the moving letter block and
		// remove it from the list.
		gameState.setMovingLetterBlock(playerId, gameState.getLetterBlocksToBeDisplayed(playerId).get(0));
		gameState.removeLetterBlockToBeDisplayed(playerId, gameState.getMovingLetterBlock(playerId));
		// Setup the movement task.
		this.movingLetterBlockTask = new LetrisGameMoveLetterBlockTask(gameState.getMovingLetterBlock(playerId), this);
		return gameState;
	}
	
	/**
	 * Initialize the moving task and
	 * start the movement of the current
	 * moving block (and all other block,
	 * that might be influenced). 
	 */
	public void startMoving() {
//		if (this.movingLetterBlockTask == null) {
//			if (this.coordinator.getGameState().getMovingLetterBlock(playerId) != null) {
//				this.movingLetterBlockTask = new LetrisGameMoveLetterBlockTask(this.coordinator.getGameState().getMovingLetterBlock(playerId), this);
//			} else {
//				GWT.log("Moving block hasn't been set yet!");
//			}
//		}
		coordinator.registerAniTask(movingLetterBlockTask);
	}
	
	/**
	 * Stop the movement of all blocks of the LeTris game.
	 */
	public void stopMoving() {
		movingLetterBlockTask.markForDelete();
	}

	public double getForeignLetterRatio() {
		return letterBlockCreator.getOutstandingLetterRatio();
	}

	public void setForeignLetterRatio(double foreignLetterRatio) {
		letterBlockCreator.setOutstandingLetterRatio(foreignLetterRatio);
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
	private String getNextRandomCurrentWord() {
		// TODO Does the shuffling work this way?
		ListShuffler.shuffleList(coordinator.getTargetWords());
		return coordinator.getTargetWords().get(0);
	}
	
	/**
	 * Check if the given letter block would collide with on of the static
	 * letter blocks.
	 * @param letterBlock to check
	 * @return true, if the given block would collide
	 */
	public boolean isCollidingWithStaticLetterBlocks(LetrisGameLetterBlock letterBlock) {
		boolean collides = false;
		ArrayList<LetrisGameLetterBlock> staticLetterBlocks = this.coordinator.getGameState().getStaticLetterBlocks(playerId);
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
		this.coordinator.getGameState().getMovingLetterBlock(playerId).moveTo(movementDirection);
	}
	
	/**
	 * Drop the currently moving letter block instantly.
	 */
	public void dropLetterBlock() {
		coordinator.getGameState().getMovingLetterBlock(playerId).drop();
	}
	
	/**
	 * Handle rotation of the currently moving letter block caused
	 * by the player.
	 */
	public void rotateLetterBlock(RotationDirection rotationDirection) {
		LetrisGameLetterBlock movingLetterBlock = this.coordinator.getGameState().getMovingLetterBlock(playerId);
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
		ArrayList<String> outstandingWords = this.coordinator.getGameState().getMissingWords(playerId);
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
		this.coordinator.getGameState().addCorrectWord(playerId, foundWord);
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
		LetrisGameLetterBlock movingLetterBlock = this.coordinator.getGameState().getMovingLetterBlock(playerId);
		this.coordinator.getGameState().addStaticLetterBlock(playerId, movingLetterBlock);
		playground[movingLetterBlock.getY()][movingLetterBlock.getX()] = movingLetterBlock;
		checkForCorrectWord();
		ArrayList<LetrisGameLetterBlock> letterBlocksToBeDisplayed = this.coordinator.getGameState().getLetterBlocksToBeDisplayed(playerId);
		if (letterBlocksToBeDisplayed.size() == 0) {
			coordinator.getGameState().setCurrentWord(playerId, getNextRandomCurrentWord());
			// Add current word to outstanding words.
			this.coordinator.getGameState().addMissingWord(playerId, this.coordinator.getGameState().getCurrentWord(playerId));
			// Create new letter blocks and retrieve them.
			letterBlockCreator.createTargetLetterBlocks(this.coordinator.getGameState().getCurrentWord(playerId), this.coordinator.getGameState().getMissingWords(playerId));
			this.coordinator.getGameState().setLetterBlocksToBeDisplayed(playerId, letterBlockCreator.getTargetLetterBlocks());
		}		
		movingLetterBlock = letterBlocksToBeDisplayed.get(0);
		letterBlocksToBeDisplayed.remove(0);
	}
	
	/**
	 * Check if the given letter block would collide with on of the static
	 * letter blocks.
	 * @param letterBlock to check
	 * @return true, if the given block would collide
	 */
	public boolean isCollidingWithStaticLetterBlocks(int playerId, LetrisGameLetterBlock letterBlock) {
		boolean collides = false;
		ArrayList<LetrisGameLetterBlock> staticLetterBlocks = coordinator.getGameState().getStaticLetterBlocks(playerId);
		for (int i = 0; i < staticLetterBlocks.size(); i++) {
			if (letterBlock.isColliding(staticLetterBlocks.get(i))) {
				collides = true;
				break;
			}
		}
		return collides;
	}
	
	/**
	 * Call the appropriate method of the coordinator.
	 */
	public void registerAnimationTask(AnimationTimerTask task) {
		coordinator.registerAniTask(task);
	}
}