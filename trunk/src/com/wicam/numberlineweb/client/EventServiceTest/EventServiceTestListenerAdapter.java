package com.wicam.numberlineweb.client.EventServiceTest;

import com.wicam.numberlineweb.client.EventServiceTest.EventServiceTestCounterEvent;

import de.novanic.eventservice.client.event.Event;

public abstract class EventServiceTestListenerAdapter implements EventServiceTestListenerInterface {       
        
	public void apply(Event anEvent) {
		if (anEvent instanceof EventServiceTestCounterEvent) {
			onNewCounter((EventServiceTestCounterEvent)anEvent);
		}
	}

	public abstract void onNewCounter(EventServiceTestCounterEvent aUserLeaveEvent);

}