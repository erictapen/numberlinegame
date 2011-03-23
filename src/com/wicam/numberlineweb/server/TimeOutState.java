package com.wicam.numberlineweb.server;

public class TimeOutState {
	
	
	
	int playerID;
	int gameID;
	int secsLeft;
	
	int timeOut;
	
	
	public TimeOutState(int playerID, int gameID, int timeOut) {
		
		this.playerID = playerID;
		this.gameID = gameID;
		this.secsLeft = timeOut;
		this.timeOut = timeOut;
				
	}
	
	public boolean countDown() {
		
		this.secsLeft--;
		
		return (this.secsLeft > 1);
		
		
	}
	
	public int getPlayerId() {
		
		return playerID;
		
	}
	
	public int getGameId() {
		
		return gameID;
		
	}
	
	public void reset() {
		
	
		this.secsLeft = timeOut;
		
	}
	

}
