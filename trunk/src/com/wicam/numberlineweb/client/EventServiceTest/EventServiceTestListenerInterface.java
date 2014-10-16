package com.wicam.numberlineweb.client.EventServiceTest;

import com.wicam.numberlineweb.client.EventServiceTest.EventServiceTestCounterEvent;

import de.novanic.eventservice.client.event.listener.RemoteEventListener;

public interface EventServiceTestListenerInterface extends RemoteEventListener {
        
	void onNewCounter(EventServiceTestCounterEvent aCounterEvent);

}