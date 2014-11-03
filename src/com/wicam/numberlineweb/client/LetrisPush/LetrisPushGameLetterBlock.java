package com.wicam.numberlineweb.client.LetrisPush;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.wicam.numberlineweb.client.LetrisPush.LetrisPushGameModel.MovementDirection;
import com.wicam.numberlineweb.client.LetrisPush.LetrisPushGameModel.Orientation;

/**
 * Class for the letter blocks in the LeTris game
 * that will be displayed on the playground.
 * @author timfissler
 *
 */
// TODO Include LetrisGameCoordinates.
public class LetrisPushGameLetterBlock implements IsSerializable {

	/**
	 * The letter the letter block is representing.
	 */
	private String letter;
	/**
	 * The x position of the letter block on the playground.
	 * Increases from left to right.
	 */
	private int x;
	/**
	 * The y position of the letter block on the playground.
	 * Increases from bottom to top.
	 */
	private int y;
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
	 * Recover the last moving direction.
	 */
	private MovementDirection recoverLastMovingDirection;
	
	public void setTimePerBlock(int timePerBlock){
		this.timePerBlock = timePerBlock;
	}
	
	public int getTimePerBlock() {
		return this.timePerBlock;
	}
	
	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	
	/**
	 * Undo the last movement that was made using the method move().
	 */
	public void undoLastMovement() {
		if (this.lastMovingDirection == MovementDirection.DOWN) {
			this.y = this.recoverY;
		} else {
			this.x = this.recoverX;
		}
		this.lastMovingDirection = this.recoverLastMovingDirection;
	}
	
	/**
	 * Move the block into the given direction. This can be made undone
	 * by using the undoLastMovement() method.
	 * @param direction
	 */
	public void move(MovementDirection direction) {
		this.recoverLastMovingDirection = this.lastMovingDirection;
		lastMovingDirection = direction;
		switch (direction) {
		case RIGHT:
			this.recoverX = this.x;
			this.x++;
			break;
		case LEFT:
			this.recoverX = this.x;
			this.x--;
			break;
		case DOWN:
			this.recoverY = this.y;
			this.y--;
			break;
		}
	}
	
	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean stopped) {
		this.removed = stopped;
	}
	
	public void setX(int x) {
		this.x = x;
		this.recoverX = x;
	}
	
	public void setY(int y) {
		this.y = y;
		this.recoverY = y;
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

	public void setId(long id) {
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
		return "[" + this.letter + ", id: " + id + ", " + orientation + ", position: " + x + ", " + y + "]";
	}
	
	/**
	 * Compare this with another given object and return true if both objects
	 * are letter blocks and have the same id.
	 */
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj.getClass() == this.getClass()) {
			LetrisPushGameLetterBlock otherLetterBlock = (LetrisPushGameLetterBlock) obj;
			if (otherLetterBlock.id == this.id) {
				result = true;
			}
		}
		return result; 
	}

	/**
	 * Check if this block collides with anther given block.
	 * @param otherBlock
	 * @return true, if they collide
	 */
	public boolean isColliding(LetrisPushGameLetterBlock otherBlock) {
		return ((this.x == otherBlock.x) && (this.y == otherBlock.y));
	}
	
	public void setLetter(String letter) {
		this.letter = letter;
	}
	
	public void setLastMovingDirection(MovementDirection direction) {
		this.lastMovingDirection = direction;
	}

	public MovementDirection getLastMovingDirection() {
		return lastMovingDirection;
	}
	
}