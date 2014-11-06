package com.wicam.numberlineweb.client.LetrisPush;

import de.novanic.eventservice.client.event.listener.RemoteEventListener;

public interface LetrisPushGameListenerInterface extends RemoteEventListener {
	
	void onTargetUpdate(LetrisPushGameTargetUpdateEvent targetUpdateEvent);

}