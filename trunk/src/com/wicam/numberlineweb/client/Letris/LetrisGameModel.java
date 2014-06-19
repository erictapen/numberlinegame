package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Timer;

/**
 * Class that implements the game model of the LeTris game with its data and functions.
 * @author timfissler
 *
 */

public class LetrisGameModel {

	// TODO Add increasing game speed and/or more rotated letter blocks
	// depending on how much words have been displayed.
	// TODO Tell the GameLogger that the user set a block and that a word was found.
	// TODO Add game over condition. E.g. when the fillerRows of the game state exceed 16.
	// TODO Recognize one word at a time.
	// TODO Remove filler rows when there is more than one correct built word in a row.
	// TODO Implement points for dropping blocks depending on their height.
	
	/**
	 * The width of the playground in blocks. 
	 */
	private int playgroundWidth = 15;
	/**
	 * The height of the playground in blocks.
	 */
	private int playgroundHeight = 19;
	/**
	 * The target letter block creator is used to build a list of letter blocks from the current target word. 
	 */
	private LetrisGameTargetLetterBlockCreator letterBlockCreator;
	/**
	 * The LeTris game coordinator.
	 */
	private LetrisGameCoordinator coordinator;
	/**
	 * Hold the position of all static letter blocks in a matrix.
	 */
	private LetrisGameLetterBlock[][] playground = new LetrisGameLetterBlock[playgroundHeight][playgroundWidth];
	/**
	 * Moves the current moving letter block.
	 */
	private LetrisGameMoveLetterBlockTask movingLetterBlockTask;
	
	
	public LetrisGameModel(LetrisGameCoordinator coordinator, double distractorLetterRatio, 
			double rotatedLetterRatio, int timePerBlock) {
		super();
		this.coordinator = coordinator;
		this.letterBlockCreator = new LetrisGameTargetLetterBlockCreator(rotatedLetterRatio, distractorLetterRatio, timePerBlock);
	}
	
	/**
	 * Construct a functional LeTris game state from a given empty one.
	 * @param gameState the empty game state
	 * @return gameState the functional game state
	 */
	public void setupGameState(LetrisGameState gameState) {
		gameState.setStaticLetterBlocks(new ArrayList<LetrisGameLetterBlock>());
		gameState.setMissingWords(new ArrayList<String>());
		gameState.setCorrectWords(new ArrayList<String>());
		// Set up all the lists and the moving block.
		gameState.setCurrentWord(getNextRandomCurrentWord());
		// Add current word to missing words.
		gameState.addMissingWord(gameState.getCurrentWord());
		// Create new letter blocks and retrieve them.
		letterBlockCreator.createTargetLetterBlocks(gameState.getCurrentWord());
		gameState.setLetterBlocksToBeDisplayed(letterBlockCreator.getTargetLetterBlocks());
		// Copy the list so that the blocks to be deleted aren't influenced by the successive
		// Deletion of the blocks to be displayed.
		gameState.setLetterBlocksToBeDeleted(new ArrayList<LetrisGameLetterBlock>(letterBlockCreator.getTargetLetterBlocks()));
		// Set the first letter block to be displayed as the moving letter block and
		// remove it from the list.
		gameState.setMovingLetterBlock(gameState.getLetterBlocksToBeDisplayed().get(0));
		gameState.removeLetterBlockToBeDisplayed(gameState.getMovingLetterBlock());
	}
	
	/**
	 * Initialize the moving task and
	 * start the movement of the current
	 * moving block (and all other block,
	 * that might be influenced). 
	 */
	public void startMoving() {
		if (this.movingLetterBlockTask == null) {
			if (this.coordinator.getGameState().getMovingLetterBlock() != null) {
				this.movingLetterBlockTask = new LetrisGameMoveLetterBlockTask(this.coordinator.getGameState().getMovingLetterBlock(), this);
				// TODO Present current target word auditively.
				GWT.log("Current target word: " + coordinator.getGameState().getCurrentWord());
				coordinator.updateTargetWord();
				// Copy the letter block.
				LetrisGameLetterBlock nextLetterBlock = letterBlockCreator.copyLetterBlock(
						coordinator.getGameState().getLetterBlocksToBeDisplayed().get(0));
				coordinator.updateNextBlock(nextLetterBlock);
			} else {
				GWT.log("Moving block hasn't been set yet!");
			}
		}
		coordinator.registerAniTask(movingLetterBlockTask);
	}
	
	/**
	 * Stop the movement of all blocks of the LeTris game.
	 */
	public void stopMoving() {
		// Only stop if there is a moving task to stop.
		if (movingLetterBlockTask != null) {
			movingLetterBlockTask.markForDelete();
		}
	}

	public int getPlaygroundWidth() {
		return playgroundWidth;
	}

	public int getPlaygroundHeight() {
		return playgroundHeight;
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
		ArrayList<LetrisGameLetterBlock> staticLetterBlocks = this.coordinator.getGameState().getStaticLetterBlocks();
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
	 * Enumeration for possible orientations of a letter block.
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
	 * Update view and server with the current game state.
	 */
	public void updateViewAndServer() {
		coordinator.updatePlaygroundInView();
		coordinator.pushGameStateToServer();
	}
	
	/**
	 * Updates the game state with the latest version of the currently moving
	 * letter block.
	 * @param letterBlock
	 */
	public void updateMovingLetterBlock(LetrisGameLetterBlock letterBlock) {
		coordinator.getGameState().setMovingLetterBlock(letterBlock);
	}
	
	/**
	 * Handle movement of the currently moving letter block caused
	 * by the player.
	 * @param movementDirection
	 */
	public void moveLetterBlock(MovementDirection movementDirection) {
		LetrisGameLetterBlock movingLetterBlock = this.coordinator.getGameState().getMovingLetterBlock(); 
		movingLetterBlock.move(movementDirection);
		// Collision checking.
		if (!isCollidingWithStaticLetterBlocks(movingLetterBlock)
				&& !(movingLetterBlock.getY() < 0)
				&& !(movingLetterBlock.getX() < 0)
				&& !(movingLetterBlock.getX() > 14)) {
			// No collision.
			movingLetterBlockTask.updateMovingLetterBlock(movingLetterBlock);
			updateViewAndServer();
		} else {
			// Collision.
			// Store last moving direction to be checked later.
			MovementDirection lastDirection = movingLetterBlock.getLastMovingDirection();
			// Don't update the position further if there has been a collision, but
			// restore the old position.
			movingLetterBlock.undoLastMovement();
			// Here is an extra case needed for being dropped vs. just hitting another
			// block from left or right. Therefore the last movement direction is being used.
			if (lastDirection == MovementDirection.DOWN) {
				movingLetterBlockTask.markForDelete();
				swapMovingLetterBlock();
			}
		}
	}
	
	/**
	 * Drop the currently moving letter block instantly.
	 */
	public void dropLetterBlock() {
		LetrisGameLetterBlock movingLetterBlock = this.coordinator.getGameState().getMovingLetterBlock();
		int yPos = findLowestFreePosition(movingLetterBlock.getX());
		movingLetterBlock.setY(yPos);
		this.movingLetterBlockTask.updateMovingLetterBlock(movingLetterBlock);
		updateViewAndServer();
		this.movingLetterBlockTask.markForDelete();
		swapMovingLetterBlock();
	}
	
	/**
	 * Given a fixed x position find the lowest y
	 * position that is not occupied with
	 * another letter block.
	 * @param x
	 * @return
	 */
	private int findLowestFreePosition(int x) {
		int y = 18;
		while (y > -1 && playground[y][x] == null) {
			y--;
		}
		y++;
		return y;
	}
	
	/**
	 * Handle rotation of the currently moving letter block caused
	 * by the player.
	 */
	public void rotateLetterBlock(RotationDirection rotationDirection) {
		LetrisGameLetterBlock movingLetterBlock = this.coordinator.getGameState().getMovingLetterBlock();
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
		updateViewAndServer();
	}
	
	/**
	 * Check the playground matrix for correct words. If there is one
	 * add it to the correct words list and remove it from the missing
	 * words list. Also increase the correct amount of letters.
	 * Delete the letter blocks that build the correct word from the
	 * list of static letter blocks.
	 * @param handleOnlyCorrectWord
	 * @return true, if a correct word was found
	 */
	private void checkForCorrectWord(boolean handleOnlyCorrectWord) {
		ArrayList<LetrisGameLetterBlock> foundLetterBlocks;
		// Look for correct word one row above the fillerRow level.
		int fillerRows = coordinator.getGameState().getFillerLevel();
		int row = fillerRows + 1;
		foundLetterBlocks = findCorrectWord(playground[row]);
		boolean foundCorrectWord = (foundLetterBlocks.size() > 0);
		ArrayList<LetrisGameLetterBlock> blocksToBeDeleted = coordinator.getGameState().getLetterBlocksToBeDeleted();
		
		// Delay the removing of the blocks and the creation of filler blocks.  
		HandleWordFinishedTimer timer = new HandleWordFinishedTimer(foundCorrectWord, handleOnlyCorrectWord, blocksToBeDeleted);
		timer.schedule(1000);
	}
	
	/**
	 * When a word is either correctly finished or finished but wrong,
	 * this method deletes the letter blocks of the word from the playground
	 * and adds or removes filler rows.
	 * @param foundCorrectWord
	 * @param handleOnlyCorrectWord
	 * @param blocksToBeDeleted
	 */
	private void handleWordFinished(boolean foundCorrectWord, boolean handleOnlyCorrectWord, 
			ArrayList<LetrisGameLetterBlock> blocksToBeDeleted) {
		int fillerRows = coordinator.getGameState().getFillerLevel();
		int row = fillerRows + 1;
		if (foundCorrectWord) {
			// Delete the letter blocks from the static ones.
			deleteSetLetterBlocks(blocksToBeDeleted);
			/* 
			 * TODO If the word was correct two times in a row
			 * decrease the filler row level and delete the upper
			 * filler row.
			 */
		}
		if (!foundCorrectWord && !handleOnlyCorrectWord) {
			// Delete the letter blocks from the static ones.
			deleteSetLetterBlocks(blocksToBeDeleted);
			// Increase the filler row level.
			coordinator.getGameState().setFillerLevel(row);
			letterBlockCreator.createFillerRow(row);
			addFillerRow(letterBlockCreator.getFillerRow());
		}
		// TODO Continue with the next word AFTER this method. Move code appropriately.
	}
	
	/**
	 * Check a row of the playground matrix if it holds a correct word.
	 * Add it to the correct words remove it from the missing ones
	 * and also tell the coordinator about the found word.
	 * @param letterBlockRow
	 * @return a list of the found letter blocks that build up the word.
	 */
	// TODO Add checking of the letters are all oriented south.
	private ArrayList<LetrisGameLetterBlock> findCorrectWord(LetrisGameLetterBlock[] letterBlockRow) {
		// Build up string from given array.
		String rowStr = "";
		int startIdx = -1;
		int endIdx = -1;
		for (int i = 0; i < letterBlockRow.length; i++) {
			if (letterBlockRow[i] != null) {
				rowStr += letterBlockRow[i].getLetter();
			} else {
				rowStr += "*";
			}
		}
		String foundWord = "";
		// Loop over missing words and compare them with the string.
		ArrayList<String> missingWords = this.coordinator.getGameState().getMissingWords();
		for (String missingWord : missingWords) {
			startIdx = rowStr.indexOf(missingWord.toUpperCase());
			if (startIdx >= 0) {
				// Break if a substring is found and save the indices of the substring.
				foundWord = missingWord;
				handleFoundWord(foundWord);
				endIdx = startIdx + missingWord.length() - 1;
				break;
			}
		}
		ArrayList<LetrisGameLetterBlock> foundLetterBlocks = new ArrayList<LetrisGameLetterBlock>();
		// If there was a word found ...
		if (startIdx >= 0) {
			// Build up an array of letter blocks with the given indices and return it.
			for (int i = startIdx; i <= endIdx; i++) {
				foundLetterBlocks.add(letterBlockRow[i]);
			}
		}
		return foundLetterBlocks;
	}
	
	/**
	 * Handle found word. Add correct word to the game state,
	 * update points and show correct word message.
	 * @param foundWord
	 */
	private void handleFoundWord(String foundWord) {
		coordinator.getGameState().addCorrectWord(foundWord);
		coordinator.getGameState().removeMissingWord(foundWord);
		// For each correct letter in the correct words list the player gets 10 points.
		int points = coordinator.getGameState().getPlayerPoints(coordinator.getPlayerID());
		points += (foundWord.length() * 10);
		coordinator.getGameState().setPlayerPoints(coordinator.getPlayerID(), points);
		// TODO Implement correct word message dialog box.
		coordinator.updatePoints();
	}
	
	/**
	 * Delete the given list of blocks from the static list, 
	 * remove the blocks from the playground
	 * and update the view and the state.
	 * @param letterBlocks
	 */
	private void deleteSetLetterBlocks(ArrayList<LetrisGameLetterBlock> letterBlocks) {
		this.coordinator.getGameState().getStaticLetterBlocks().removeAll(letterBlocks);
		for (LetrisGameLetterBlock letterBlock : letterBlocks) {
			playground[letterBlock.getY()][letterBlock.getX()] = null;
		}
		updateViewAndServer();
	}
	
	/**
	 * Add the given list of filler letter blocks to the static list,
	 * add them to the playground and update the view and the state.
	 * @param fillerBlocks
	 */
	private void addFillerRow(ArrayList<LetrisGameLetterBlock> fillerBlocks) {
		for (LetrisGameLetterBlock fillerBlock : fillerBlocks) {
			this.coordinator.getGameState().addStaticLetterBlock(fillerBlock);
			playground[fillerBlock.getY()][fillerBlock.getX()] = fillerBlock;
		}
		updateViewAndServer();
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
		LetrisGameLetterBlock movingLetterBlock = this.coordinator.getGameState().getMovingLetterBlock();
		this.coordinator.getGameState().addStaticLetterBlock(movingLetterBlock);
		playground[movingLetterBlock.getY()][movingLetterBlock.getX()] = movingLetterBlock;
		coordinator.getGameState().setMovingLetterBlock(null);		
		ArrayList<LetrisGameLetterBlock> letterBlocksToBeDisplayed = this.coordinator.getGameState().getLetterBlocksToBeDisplayed();
		// Wait one second before removing the correct words.
		boolean letterBlockToBeDisplayedIsEmpty = (letterBlocksToBeDisplayed.size() == 0);
		checkForCorrectWord(!letterBlockToBeDisplayedIsEmpty);
		
		if (letterBlocksToBeDisplayed.size() == 0) {
			coordinator.getGameState().setCurrentWord(getNextRandomCurrentWord());
			// Add current word to missing words and present it.
			this.coordinator.getGameState().addMissingWord(this.coordinator.getGameState().getCurrentWord());
			// TODO Present current target word auditively
			GWT.log("Current target word: " + coordinator.getGameState().getCurrentWord());
			coordinator.updateTargetWord();
			// Create new letter blocks and retrieve them.
			letterBlockCreator.createTargetLetterBlocks(this.coordinator.getGameState().getCurrentWord());
			this.coordinator.getGameState().setLetterBlocksToBeDisplayed(letterBlockCreator.getTargetLetterBlocks());
			// Copy the list so that the blocks to be deleted aren't influenced by the successive
			// Deletion of the blocks to be displayed.
			this.coordinator.getGameState().setLetterBlocksToBeDeleted(new ArrayList<LetrisGameLetterBlock>(letterBlockCreator.getTargetLetterBlocks()));
			letterBlocksToBeDisplayed = coordinator.getGameState().getLetterBlocksToBeDisplayed();
		}
		
		movingLetterBlock = letterBlocksToBeDisplayed.get(0);
		coordinator.getGameState().removeLetterBlockToBeDisplayed(movingLetterBlock);
		coordinator.getGameState().setMovingLetterBlock(movingLetterBlock);
		/*
		 *  If the last letter of the current word is dropped, then there is no next
		 *  block to be shown here. So show nothing.
		 */
		if (coordinator.getGameState().getLetterBlocksToBeDisplayed().size() >= 1) {
			// Copy the letter block.
			LetrisGameLetterBlock nextLetterBlock = letterBlockCreator.copyLetterBlock(
					coordinator.getGameState().getLetterBlocksToBeDisplayed().get(0));
			coordinator.updateNextBlock(nextLetterBlock);
		} else {
			// Show no next block.
			coordinator.updateNextBlock(null);
		}
		// Create new movement task for new letter block.
		movingLetterBlockTask = new LetrisGameMoveLetterBlockTask(movingLetterBlock, this);
		coordinator.registerAniTask(movingLetterBlockTask);
	}
	
	/**
	 * Call the appropriate method of the coordinator.
	 */
//	public void registerAnimationTask(AnimationTimerTask task) {
//		coordinator.registerAniTask(task);
//	}

	/**
	 * Timer for handling the delay between the drop of the last
	 * letter block and the removing of the letters of the
	 * word. This delay is needed so that the player has
	 * a chance to see which word he has built.
	 * @author timfissler
	 *
	 */
	private class HandleWordFinishedTimer extends Timer {

		private boolean foundCorrectWord;
		private boolean handleOnlyCorrectWord;
		private ArrayList<LetrisGameLetterBlock> blocksToBeDeleted;
		
		public HandleWordFinishedTimer(boolean foundCorrectWord, boolean handleOnlyCorrectWord, 
				ArrayList<LetrisGameLetterBlock> blocksToBeDeleted) {
			this.foundCorrectWord = foundCorrectWord;
			this.handleOnlyCorrectWord = handleOnlyCorrectWord;
			this.blocksToBeDeleted = blocksToBeDeleted;
		}
		
		@Override
		public void run() {
			handleWordFinished(foundCorrectWord, handleOnlyCorrectWord, blocksToBeDeleted);
		}
		
	}
}