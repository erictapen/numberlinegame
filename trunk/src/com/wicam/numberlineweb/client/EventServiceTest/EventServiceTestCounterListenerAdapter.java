package com.wicam.numberlineweb.client.EventServiceTest;

import de.novanic.eventservice.client.event.Event;

public abstract class EventServiceTestCounterListenerAdapter implements EventServiceTestCounterListenerInterface {       
        
	public void apply(Event anEvent) {
		if (anEvent instanceof EventServiceTestCounterEvent) {
			onNewCounter((EventServiceTestCounterEvent)anEvent);
		}
	}
	
	public abstract void onNewCounter(EventServiceTestCounterEvent aCounterEvent);

}