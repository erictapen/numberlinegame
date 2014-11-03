package com.wicam.numberlineweb.client.LetrisPush;

/**
 * Class for 2D Coordinates.
 * @author timfissler
 *
 */

public class LetrisPushGameCoordinates {
	
	public int x;
	public int y;
	
	public LetrisPushGameCoordinates() {
		this.x = 0;
		this.y = 0;
	}
	
	public LetrisPushGameCoordinates(LetrisPushGameCoordinates coordinates) {
		this.x = coordinates.x;
		this.y = coordinates.y;
	}
	
	public LetrisPushGameCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Add the given coordinates to this coordinates.
	 * @param coordinates
	 */
	public void add(LetrisPushGameCoordinates coordinates) {
		this.x += coordinates.x;
		this.y += coordinates.y;
	}
	
	/**
	 * Subtract the given coordinates to this coordinates.
	 * @param coordinates
	 */
	public void subtract(LetrisPushGameCoordinates coordinates) {
		this.x -= coordinates.x;
		this.y -= coordinates.y;
	}
	
	public String toString() {
		return x + ", " + y;
	}

}
