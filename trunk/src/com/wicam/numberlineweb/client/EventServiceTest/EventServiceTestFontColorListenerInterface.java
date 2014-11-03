package com.wicam.numberlineweb.client.EventServiceTest;

import de.novanic.eventservice.client.event.listener.RemoteEventListener;

public interface EventServiceTestFontColorListenerInterface extends RemoteEventListener {
	
	void onFontColorSwitch(EventServiceTestFontColorEvent aFontColorEvent);

}