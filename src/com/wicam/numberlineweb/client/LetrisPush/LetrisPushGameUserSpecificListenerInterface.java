package com.wicam.numberlineweb.client.LetrisPush;

import de.novanic.eventservice.client.event.listener.RemoteEventListener;

public interface LetrisPushGameUserSpecificListenerInterface extends RemoteEventListener {
	
	void onTargetUpdate(LetrisPushGameTargetUpdateEvent targetUpdateEvent);
	void onPause(LetrisPushGamePauseEvent pauseEvent);
	void onUnpause(LetrisPushGameUnpauseEvent unpauseEvent);

}