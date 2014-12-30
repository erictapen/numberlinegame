package com.wicam.numberlineweb.client.Letris;

/**
 * Transforms given model coordinates of letter blocks into view coordinates of letter block images.
 * @author timfissler
 *
 */

public class LetrisGameCoordinateTransform {

	/**
	 * Size of the model playground in letris blocks.
	 */
	private LetrisGameCoordinates modelSize;
	/**
	 * Size of the view in pixels.
	 */
	private LetrisGameCoordinates viewSize;
	/**
	 * Scale factor between model and view.
	 */
	private int scale;
	
	/**
	 * Create a new coordinate transform object.
	 * @param modelSize
	 * @param viewSize
	 */
	public LetrisGameCoordinateTransform(LetrisGameCoordinates modelSize,
			LetrisGameCoordinates viewSize) {
		this.modelSize = modelSize;
		this.viewSize = viewSize;
		this.scale = (viewSize.y - 1) / modelSize.y;
	}
	
	/**
	 * Transform the coordinates of the model to view coordinates.
	 * @param modelCoordinates
	 * @return
	 */
	public LetrisGameCoordinates transformModelToView(LetrisGameCoordinates modelCoordinates) {
		LetrisGameCoordinates viewCoordinates = new LetrisGameCoordinates(modelCoordinates);
		// Invert the y-axis.
		viewCoordinates.y = (modelSize.y - 1) - viewCoordinates.y;
		// Scale the coordinates.
		viewCoordinates.x *= scale;
		viewCoordinates.y *= scale;
		// Add the offset because of the grid's frame.
		viewCoordinates.x++;
		viewCoordinates.y++;
		return viewCoordinates;
	}
	
	/**
	 * Return a string representation of the given coordinates transform object.
	 */
	public String toString() {
		return "model size: " + modelSize + "\nview size: " + viewSize + "\nscale: " + scale;
	}

}
