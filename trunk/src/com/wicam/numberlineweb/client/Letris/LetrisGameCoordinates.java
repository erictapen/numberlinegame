package com.wicam.numberlineweb.client.Letris;

/**
 * Class for 2D Coordinates.
 * @author timfissler
 *
 */

public class LetrisGameCoordinates {
	
	public int x;
	public int y;
	
	public LetrisGameCoordinates() {
		this.x = 0;
		this.y = 0;
	}
	
	public LetrisGameCoordinates(LetrisGameCoordinates coordinates) {
		this.x = coordinates.x;
		this.y = coordinates.y;
	}
	
	public LetrisGameCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Add the given coordinates to this coordinates.
	 * @param coordinates
	 */
	public void add(LetrisGameCoordinates coordinates) {
		this.x += coordinates.x;
		this.y += coordinates.y;
	}
	
	/**
	 * Subtract the given coordinates to this coordinates.
	 * @param coordinates
	 */
	public void subtract(LetrisGameCoordinates coordinates) {
		this.x -= coordinates.x;
		this.y -= coordinates.y;
	}
	
	public String toString() {
		return x + ", " + y;
	}

}
