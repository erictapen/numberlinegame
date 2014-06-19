package com.wicam.numberlineweb.client.Letris;

import com.wicam.numberlineweb.client.Letris.LetrisGameModel.MovementDirection;

/**
 * Thread to handle the movement of a letter block. 
 * @author timfissler
 *
 */
public class LetrisGameMoveLetterBlockTask extends AnimationTimerTask {

	private LetrisGameLetterBlock letterBlock;
	private int droppingCounter;
	private int timerSpeed;
	private LetrisGameModel gameModel;

	public LetrisGameMoveLetterBlockTask(LetrisGameLetterBlock letterBlock,
			LetrisGameModel gameModel) {
		this.letterBlock = letterBlock;
		this.gameModel = gameModel;
		droppingCounter = 0;
		timerSpeed = 40;
	}

	public void setTimerSpeed(int timerSpeed) {
		this.timerSpeed = timerSpeed;
	}
	
	/**
	 * Updates the letter block being moved with the given one.
	 * @param letterBlock
	 */
	public void updateMovingLetterBlock(LetrisGameLetterBlock letterBlock) {
		this.letterBlock = letterBlock;
	}

	@Override
	public synchronized void run() {
		// TODO Do we still need the isRemoved property?
		if (!letterBlock.isRemoved()){
			boolean needsUpdate = false;
			// Only move the letter block automatically if the time per block has been over.
			if (droppingCounter >= (letterBlock.getTimePerBlock() / timerSpeed)) {
				letterBlock.move(MovementDirection.DOWN);
				droppingCounter = 0;
				needsUpdate = true;
			}
			else {
				droppingCounter++;
			}
			// Check for collision or reaching the end of the playground.
			// Do only collision and vertical checking here.
			if (needsUpdate) {
				if (!gameModel.isCollidingWithStaticLetterBlocks(letterBlock)
						&& !(letterBlock.getY() < 0)) {
					gameModel.updateMovingLetterBlock(letterBlock);
					gameModel.updateViewAndServer();
				} else {
					// Store last moving direction to be checked later.
					MovementDirection lastDirection = letterBlock.getLastMovingDirection();
					// Don't update the position further if there has been a collision, but
					// restore the old position.
					letterBlock.undoLastMovement();
					// Here is an extra case needed for being dropped vs. just hitting another
					// block from left or right. Therefore the last movement direction is being used.
					if (lastDirection == MovementDirection.DOWN) {
						this.markForDelete();
						gameModel.swapMovingLetterBlock();
					}
				}
			}
		}
	}
}
