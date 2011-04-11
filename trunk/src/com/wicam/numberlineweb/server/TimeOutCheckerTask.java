package com.wicam.numberlineweb.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;

public class TimeOutCheckerTask extends TimerTask {

	
	private ArrayList<TimeOutState> timeOutStates;
	private GameCommunicationServiceServlet gameComm;
	
	
	public TimeOutCheckerTask(ArrayList<TimeOutState> timeOutStates, GameCommunicationServiceServlet gameComm) {
		
		
		this.timeOutStates = timeOutStates;
		this.gameComm = gameComm;
		
		
	}
	
	
	@Override
	public void run() {
		
		
		
		Iterator<TimeOutState> i = timeOutStates.iterator();
		
		
		
		while (gameComm.timeOutListLocked()) {
			
			
		}
		
		gameComm.timeOutListLock();
		
		while (i.hasNext()) {
			
			TimeOutState current = i.next();
			
			if (!current.countDown()) {
			
				timeOutStates.remove(current);
				gameComm.timeOutListUnLock();
				System.out.println("player " + current.getPlayerId() + " timed out.");
				gameComm.leavePlayer(current.getPlayerId(), current.getGameId());
			
				break;
			}
			
		}
		
		
		gameComm.timeOutListUnLock();
		
		
	}
	
	
	

}
