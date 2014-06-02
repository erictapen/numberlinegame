package com.wicam.numberlineweb.client.Letris;

/**
 * Transforms given model coordinates of letter blocks into view coordinates of letter block images.
 * @author timfissler
 *
 */

// TODO Add descriptions.
public class LetrisGameCoordinateTransform {
	
	private LetrisGameCoordinates modelSize;
	private LetrisGameCoordinates viewSize;
	private int scale;
	
	public LetrisGameCoordinateTransform(LetrisGameCoordinates modelSize,
			LetrisGameCoordinates viewSize) {
		this.modelSize = modelSize;
		this.viewSize = viewSize;
		this.scale = (viewSize.y - 1) / modelSize.y;
	}
	
	// TODO Transformation is still buggy?
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
		// Add the height of a letter block. TODO Why?
//		viewCoordinates.y += 20;
		return viewCoordinates;
	}
	
	public String toString() {
		return "model size: " + modelSize + "\nview size: " + viewSize + "\nscale: " + scale;
	}

}
