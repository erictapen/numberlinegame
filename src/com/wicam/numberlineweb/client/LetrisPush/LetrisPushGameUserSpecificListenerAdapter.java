package com.wicam.numberlineweb.client.LetrisPush;

import de.novanic.eventservice.client.event.Event;

public abstract class LetrisPushGameUserSpecificListenerAdapter implements LetrisPushGameUserSpecificListenerInterface {       
        
	public void apply(Event anEvent) {
		if (anEvent instanceof LetrisPushGameTargetUpdateEvent) {
			onTargetUpdate((LetrisPushGameTargetUpdateEvent)anEvent);
		}
		else if (anEvent instanceof LetrisPushGamePauseEvent) {
			onPause((LetrisPushGamePauseEvent)anEvent);
		}
		else if (anEvent instanceof LetrisPushGameUnpauseEvent) {
			onUnpause((LetrisPushGameUnpauseEvent)anEvent);
		}
	}
	
	public abstract void onTargetUpdate(LetrisPushGameTargetUpdateEvent targetUpdateEvent);
	public abstract void onPause(LetrisPushGamePauseEvent pauseEvent);
	public abstract void onUnpause(LetrisPushGameUnpauseEvent unpauseEvent);

}