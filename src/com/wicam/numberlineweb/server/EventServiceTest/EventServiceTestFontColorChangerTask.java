package com.wicam.numberlineweb.server.EventServiceTest;

import java.util.TimerTask;

public class EventServiceTestFontColorChangerTask extends TimerTask {
	
	private EventServiceTestCommunicationServiceServlet servlet;
	
	public EventServiceTestFontColorChangerTask(EventServiceTestCommunicationServiceServlet servlet) {
		this.servlet = servlet;
	}
	
	@Override
	public void run() {
		servlet.switchFontColor(); 
	}

}
