package com.wicam.numberlineweb.server.DoppelungGame;

import java.util.TimerTask;

import com.wicam.numberlineweb.client.DoppelungGame.ConsonantPoint2D;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameState;

public class UpdateMcCoordsTimerTask extends TimerTask {

	private int gameid;
	private DoppelungGameCommunicationServiceServlet s;
	private int spaceSpeed;
	
	public UpdateMcCoordsTimerTask(int gameid, int speed, DoppelungGameCommunicationServiceServlet s){
		this.gameid = gameid;
		this.spaceSpeed = speed;
		this.s = s;
	}
	
	@Override
	public void run() {
		DoppelungGameState g = (DoppelungGameState)s.getGameById(gameid);
		for(ConsonantPoint2D cp:g.getMovingConsonantsCoords()){
			cp.setY(cp.getY()+spaceSpeed);
		}
		s.setChanged(gameid);
	}

}
