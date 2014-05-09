package com.wicam.numberlineweb.client.Letris;

import com.wicam.numberlineweb.client.Letris.LetrisGameModel.MovementDirection;

/**
 * Thread to handle the movement of a letter block. 
 * @author timfissler
 *
 */

//TODO Implement a second task(?) for the blocks being dropped by player
// or by the deletion of other blocks? Could also be solved by checking if the
// Move is active and then setting the y position to the lowest available one
// or reactivating the move before, if it isn't active. But then the swap
// handling must be done differently.

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

	@Override
	public void run() {
		if (!letterBlock.isRemoved()){
			// Only move the letter block automatically if the time per block has been over.
			if ((droppingCounter >= (letterBlock.getTimePerBlock() / timerSpeed)) && (letterBlock.getY() - 1 > letterBlock.getToY())) {
				letterBlock.setY(letterBlock.getY() - 1);
				letterBlock.setLastMovingDirection(MovementDirection.DOWN);
				droppingCounter = 0;
			}
			else {
				letterBlock.setY(letterBlock.getToY());
				droppingCounter++;
			}
			// Check for collision or reaching the end of the playground.
			if (!gameModel.isCollidingWithStaticLetterBlocks(letterBlock) &&
					!(letterBlock.getX() < 0) && !(letterBlock.getX() > 9) && !(letterBlock.getY() < 0)) {
				letterBlock.setRecoverX(letterBlock.getX());
				letterBlock.setRecoverY(letterBlock.getY());
				gameModel.updateMovingLetterBlock(letterBlock);
			} else {
				// Don't update the position further if there has been a collision, but
				// restore the old position.
				letterBlock.setX(letterBlock.getRecoverX());
				letterBlock.setY(letterBlock.getRecoverY());
				// Here is an extra case needed for being dropped vs. just hitting another
				// block from left or right. Therefore the last movement direction is being used.
				if (letterBlock.getLastMovingDirection() == MovementDirection.DOWN) {
					gameModel.swapMovingLetterBlock();
					this.markForDelete();
				}
			}
		}
	}
}
