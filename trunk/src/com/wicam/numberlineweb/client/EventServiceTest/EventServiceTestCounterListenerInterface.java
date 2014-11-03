package com.wicam.numberlineweb.client.EventServiceTest;

import de.novanic.eventservice.client.event.listener.RemoteEventListener;

public interface EventServiceTestCounterListenerInterface extends RemoteEventListener {
	
	void onNewCounter(EventServiceTestCounterEvent aCounterEvent);

}