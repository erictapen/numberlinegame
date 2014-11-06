package com.wicam.numberlineweb.client.LetrisPush;

import de.novanic.eventservice.client.event.Event;

public abstract class LetrisPushGameListenerAdapter implements LetrisPushGameListenerInterface {       
        
	public void apply(Event anEvent) {
		if (anEvent instanceof LetrisPushGameTargetUpdateEvent) {
			onTargetUpdate((LetrisPushGameTargetUpdateEvent)anEvent);
		}
	}
	
	public abstract void onTargetUpdate(LetrisPushGameTargetUpdateEvent targetUpdateEvent);

}