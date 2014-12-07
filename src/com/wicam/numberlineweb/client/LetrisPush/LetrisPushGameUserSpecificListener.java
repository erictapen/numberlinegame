package com.wicam.numberlineweb.client.LetrisPush;

/**
 * Handle events that are send by the server.
 * @author timfissler
 *
 */

public class LetrisPushGameUserSpecificListener extends LetrisPushGameUserSpecificListenerAdapter {
	
	private LetrisPushGameCoordinator coord;
	
	public LetrisPushGameUserSpecificListener(LetrisPushGameCoordinator coord) {
		this.coord = coord;
	}

	@Override
	public void onTargetUpdate(LetrisPushGameTargetUpdateEvent targetUpdateEvent) {
		coord.updateFromOpponent(targetUpdateEvent.getPlaygroundState());
	}
	
	@Override
	public void onPause(LetrisPushGamePauseEvent pauseEvent) {
		coord.pauseGame();
	}
	
	@Override
	public void onUnpause(LetrisPushGameUnpauseEvent unpauseEvent) {
		coord.unpauseGame();
	}

}
