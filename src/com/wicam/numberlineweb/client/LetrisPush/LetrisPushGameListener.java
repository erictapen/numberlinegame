package com.wicam.numberlineweb.client.LetrisPush;

/**
 * Handle events that are send by the server.
 * @author timfissler
 *
 */

public class LetrisPushGameListener extends LetrisPushGameListenerAdapter {
	
	private LetrisPushGameCoordinator coord;
	
	public LetrisPushGameListener(LetrisPushGameCoordinator coord) {
		this.coord = coord;
	}

	@Override
	public void onTargetUpdate(LetrisPushGameTargetUpdateEvent targetUpdateEvent) {
		coord.updateFromOpponent(targetUpdateEvent.getPlaygroundState());
	}

}
