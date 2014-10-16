package com.wicam.numberlineweb.client.EventServiceTest;

import de.novanic.eventservice.client.event.Event;

public class EventServiceTestCounterEvent implements Event {

	private static final long serialVersionUID = -4894854208221577762L;
    private int counter = 0;
    
	/**
	 * @return the counter
	 */
	public int getCounter() {
		return counter;
	}
	/**
	 * @param counter the counter to set
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}
    
}