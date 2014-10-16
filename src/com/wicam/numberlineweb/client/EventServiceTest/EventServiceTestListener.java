package com.wicam.numberlineweb.client.EventServiceTest;

import com.wicam.numberlineweb.client.EventServiceTest.EventServiceTestCounterEvent;

public class EventServiceTestListener extends EventServiceTestListenerAdapter {
	
	private EventServiceTestCoordinator coord;
	
	public EventServiceTestListener(EventServiceTestCoordinator coord) {
		this.coord = coord;
	}

	@Override
	public void onNewCounter(EventServiceTestCounterEvent aCounterEvent) {
		
		coord.updateCounter(aCounterEvent.getCounter());

	}

}
