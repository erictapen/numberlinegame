package com.wicam.numberlineweb.server.EventServiceTest;

import java.util.TimerTask;

public class EventServiceTestCounterTask extends TimerTask {
	
	private String clientID;
	private EventServiceTestCommunicationServiceServlet servlet;

	public EventServiceTestCounterTask(String clientID, EventServiceTestCommunicationServiceServlet servlet) {
		this.clientID = clientID;
		this.servlet = servlet;
	}
	
	@Override
	public void run() {
		servlet.count(clientID);
	}

}
