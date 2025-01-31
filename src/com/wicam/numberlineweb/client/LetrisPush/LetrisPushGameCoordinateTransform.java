package com.wicam.numberlineweb.client.LetrisPush;

/**
 * Transforms given model coordinates of letter blocks into view coordinates of letter block images.
 * @author timfissler
 *
 */

// TODO Add descriptions.
public class LetrisPushGameCoordinateTransform {
	
	private LetrisPushGameCoordinates modelSize;
	private LetrisPushGameCoordinates viewSize;
	private int scale;
	
	public LetrisPushGameCoordinateTransform(LetrisPushGameCoordinates modelSize,
			LetrisPushGameCoordinates viewSize) {
		this.modelSize = modelSize;
		this.viewSize = viewSize;
//		this.scale = (viewSize.y - 1) / modelSize.y;
		this.scale = viewSize.y / modelSize.y;
	}
	
	// TODO Transformation is still buggy?
	public LetrisPushGameCoordinates transformModelToView(LetrisPushGameCoordinates modelCoordinates) {
		LetrisPushGameCoordinates viewCoordinates = new LetrisPushGameCoordinates(modelCoordinates);
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
	
	public String toString() {
		return "model size: " + modelSize + "\nview size: " + viewSize + "\nscale: " + scale;
	}

}
