package com.wicam.numberlineweb.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;

public class TimeOutCheckerTask extends TimerTask {

	
	private ArrayList<TimeOutState> timeOutStates;
	private CommunicationServiceServlet servlet;
	
	
	public TimeOutCheckerTask(ArrayList<TimeOutState> timeOutStates, CommunicationServiceServlet servlet) {
		
		
		this.timeOutStates = timeOutStates;
		this.servlet = servlet;
		
		
	}
	
	
	@Override
	public void run() {
		
		Iterator<TimeOutState> i = timeOutStates.iterator();
		
		while (servlet.timeOutListLocked()) {
			
			
		}
		
		servlet.timeOutListLock();
		
		while (i.hasNext()) {
			
			TimeOutState current = i.next();
			
			if (!current.countDown()) {
				System.out.println("Player " + current.getPlayerId() + " timed out :(");
				servlet.leavePlayer(current.getPlayerId(), current.getGameId());
			}
			
		}
		
		servlet.timeOutListUnLock();
		
		
	}
	
	
	

}
