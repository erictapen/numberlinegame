package com.wicam.numberlineweb.client.Letris;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.PushButton;
import com.wicam.numberlineweb.client.Letris.LetrisGameModel.MovementDirection;
import com.wicam.numberlineweb.client.Letris.LetrisGameModel.Orientation;

/**
 * Class for the letter blocks in the LeTris game
 * that will be displayed on the playground.
 * @author timfissler
 *
 */

public class LetrisGameLetterBlock extends PushButton implements IsSerializable {

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
	
	// TODO Migrate these methods to the model.
//	public void startMoving(){
//		
//		gameModel.registerAnimationTask(move);
//
//	}
	
//	public void startMoving(int delay){
//		move.setDelay(delay);
//		gameModel.registerAnimationTask(move);
//	}
	
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
	
	public boolean isRemoved() {
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
	
	public void setLetter(String letter) {
		this.letter = letter;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getToY() {
		return this.toY;
	}
	
	public void setLastMovingDirection(MovementDirection direction) {
		this.lastMovingDirection = direction;
	}
	
	public int getRecoverX() {
		return this.recoverX;
	}

	public int getRecoverY() {
		return recoverY;
	}

	public void setRecoverY(int recoverY) {
		this.recoverY = recoverY;
	}

	public MovementDirection getLastMovingDirection() {
		return lastMovingDirection;
	}

	public void setToY(int toY) {
		this.toY = toY;
	}

	public void setRecoverX(int recoverX) {
		this.recoverX = recoverX;
	}
	
}