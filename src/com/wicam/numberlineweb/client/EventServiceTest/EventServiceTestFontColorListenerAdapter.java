package com.wicam.numberlineweb.client.EventServiceTest;

import de.novanic.eventservice.client.event.Event;

public abstract class EventServiceTestFontColorListenerAdapter implements EventServiceTestFontColorListenerInterface {       
        
	public void apply(Event anEvent) {
		if (anEvent instanceof EventServiceTestFontColorEvent) {
			onFontColorSwitch((EventServiceTestFontColorEvent)anEvent);
		}
	}
	
	public abstract void onFontColorSwitch(EventServiceTestFontColorEvent aFontColorEvent);

}