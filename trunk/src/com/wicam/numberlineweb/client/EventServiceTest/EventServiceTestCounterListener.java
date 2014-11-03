package com.wicam.numberlineweb.client.EventServiceTest;

import com.wicam.numberlineweb.client.EventServiceTest.EventServiceTestCounterEvent;

public class EventServiceTestCounterListener extends EventServiceTestCounterListenerAdapter {
	
	private EventServiceTestCoordinator coord;
	
	public EventServiceTestCounterListener(EventServiceTestCoordinator coord) {
		this.coord = coord;
	}

	@Override
	public void onNewCounter(EventServiceTestCounterEvent aCounterEvent) {
		
		coord.updateCounter(aCounterEvent.getCounter());

	}

}
