package com.wicam.numberlineweb.server;

import java.util.TimerTask;

/**
 * A simple TimerTask for setting the game-state after a specific amount of time.
 * @author patrick
 *
 */

public class SetGameStateTask extends TimerTask {

      
	int gameid;
	int state;
	CommunicationServiceServlet s;


	@Override
	public void run() {
		
		
		s.setGameState(s.getGameById(gameid), state);
		
		// reset clicked pos
		s.getGameById(gameid).setPlayerActPos(1, Integer.MIN_VALUE);
		s.getGameById(gameid).setPlayerActPos(2, Integer.MIN_VALUE);
		
		// numbers should be changed while winner info is displayed
		s.newNumbers(s.getGameById(gameid));
	}


	public SetGameStateTask(int gameid, int state, CommunicationServiceServlet s) {

		this.gameid = gameid;
		this.state = state;
		this.s =s;



	}

}
