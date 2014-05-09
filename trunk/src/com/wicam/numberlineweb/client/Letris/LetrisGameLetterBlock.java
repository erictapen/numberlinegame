package com.wicam.numberlineweb.client.Letris;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.PushButton;
import com.wicam.numberlineweb.client.Letris.AnimationTimerTask;
import com.wicam.numberlineweb.client.Letris.LetrisGameState.MovementDirection;

/**
 * Class for the letter blocks in the LeTris game
 * that will be displayed on the playground.
 * @author timfissler
 *
 */

public class LetrisGameLetterBlock extends PushButton implements IsSerializable {
	
	/**
	 * The timer task for moving the block.
	 */
	private Move move;
	/**
	 * The model which this block is part of.
	 */
	private LetrisGameState gameState;
	/**
	 * The letter the letter block is representing.
	 */
	private String letter;
	/**
	 * The x position of the letter block on the playground.
	 */
	private int x;
	/**
	 * The y position of the letter block on the playground.
	 */
	private int y;
	/**
	 * The y position the block should move next. This cannot
	 * be set directly because it might interfere with the automatic
	 * downward movement of the block.
	 */
	private int toY;
	/**
	 * The recovered x position. It is used for backup of the last position.
	 */
	private int recoverX;
	/**
	 * The recovered y position. It is used for backup of the last position.
	 */
	private int recoverY;
	/**
	 * Indicates whether the block has already been removed from the playground.
	 */
	private boolean removed = false;
	/**
	 * The id of the letter block.
	 */
	private long id;
	/**
	 * Indicates whether the letter block started its dropping from
	 * top to bottom of the playground.
	 */
	private boolean startedMoving = false;
	/**
	 * The orientation of the letter block.
	 */
	private Orientation orientation;
	/**
	 * Static current version if the letter block id that is used for generating the
	 * new ids.
	 */
	private static long currentID = 0;
	/**
	 * Milliseconds to wait between the movement from
	 * one block position to the next in the dropping
	 * of the letter block.
	 */
	private int timePerBlock;
	/**
	 * The direction of the last movement of the letter block. Used to check
	 * whether the block just hit another one on the side or if it really was
	 * dropped onto the static blocks.
	 */
	private MovementDirection lastMovingDirection;


	/**
	 * Enum for possible orientations of a letter block.
	 * The orientation gives the direction from which
	 * one could read the letter properly. 
	 * @author timfissler
	 *
	 */
	public enum Orientation implements IsSerializable {
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
	
	public LetrisGameLetterBlock(String letter, int x, int y, Orientation orientation, int timePerBlock, LetrisGameState gameState){				
		// TODO Configure the button style and disable it.
		super(letter.toUpperCase());
		this.setEnabled(false);
		this.setSize("80px", "80px");
		
		this.letter = letter;
		this.gameState = gameState;
		this.orientation = orientation;
		this.x = x;
		this.y = y;
		this.recoverX = x;
		this.recoverY = y;
		this.timePerBlock = timePerBlock;
		this.id = currentID;
		currentID++;
		move = new Move(this);
	}
	
	public void setTimePerBlock(int timePerBlock){
		this.timePerBlock = timePerBlock;
	}
	
	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	
	
	public void startMoving(){
		
		gameState.registerAnimationTask(move);

	}
	
	public void startMoving(int delay){
		move.setDelay(delay);
		gameState.registerAnimationTask(move);
	}
	
	public void moveTo(MovementDirection direction){
		lastMovingDirection = direction;
		switch (direction) {
		case RIGHT:
			this.x++;
			break;
		case LEFT:
			this.x--;
			break;
		case DOWN:
			this.toY = this.y - 1;
			break;
		}
	}
	
	/**
	 * Drop the letter block instantly onto the other static blocks.
	 */
	public void drop() {
		// TODO Implement this.
	}
	
	public boolean removed() {
		return removed;
	}

	public void setRemoved(boolean stopped) {
		this.removed = stopped;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public String getLetter() {
		return letter;
	}


	public void setId(int id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setStartedMoving(boolean startedMoving) {
		this.startedMoving = startedMoving;
	}

	public boolean isStartedMoving() {
		return startedMoving;
	}

	public String toString() {
		return "[" + this.letter + ", id: " + id + ", " + orientation + "]";
	}

	/**
	 * Check if this block collides with anther given block.
	 * @param otherBlock
	 * @return true, if they collide
	 */
	public boolean isColliding(LetrisGameLetterBlock otherBlock) {
		return ((this.x == otherBlock.x) && (this.y == otherBlock.y));
	}
	
	// TODO Implement a second task for the blocks being dropped by player
	// or by the deletion of other blocks? Could also be solved by checking if the
	// Move is active and then setting the y position to the lowest available one
	// or reactivating the move before, if it isn't active. But then the swap
	// handling must be done differently.
	
	public class Move extends AnimationTimerTask{
		
		private LetrisGameLetterBlock letterBlock;

		private int droppingCounter;
		private int timerSpeed;
		
		Move(LetrisGameLetterBlock letterBlock){
			this.letterBlock = letterBlock;
			droppingCounter = 0;
			timerSpeed = 40;
		}
		
		public void setTimerSpeed(int timerSpeed) {
			this.timerSpeed = timerSpeed;
		}
		
		@Override
		public void run() {
			if (!removed){
				// Only move the letter block automatically if the time per block has been over.
				if ((droppingCounter >= (timePerBlock / timerSpeed)) && (y - 1 > toY)) {
					y = y - 1;
					lastMovingDirection = MovementDirection.DOWN;
					droppingCounter = 0;
				}
				else {
					y = toY;
					droppingCounter++;
				}
				// Check for collision or reaching the end of the playground.
				if (!gameState.isCollidingWithStaticLetterBlocks(letterBlock) &&
						!(x < 0) && !(x > 9) && !(y < 0)) {
					recoverX = x;
					recoverY = y;
					gameState.updateMovingLetterBlock(letterBlock);
				} else {
					// Don't update the position further if there has been a collision, but
					// restore the old position.
					x = recoverX;
					y = recoverY;
					// Here is an extra case needed for being dropped vs. just hitting another
					// block from left or right. Therefore the last movement direction is being used.
					if (lastMovingDirection == MovementDirection.DOWN) {
						gameState.swapMovingLetterBlock();
						this.markForDelete();
					}
				}
			}
		}
	}
}
