package com.wicam.numberlineweb.server;

import java.util.TimerTask;

/**
 * A simple TimerTask for setting the game-state after a specific amount of time.
 * @author patrick
 *
 */

public class SetGameStateTask extends TimerTask {

      
	protected int gameid;
	int state;
	protected GameCommunication s;


	@Override
	public void run() {
		s.setGameState(s.getGameById(gameid), state);
	}


	public SetGameStateTask(int gameid, int state, GameCommunication s) {

		this.gameid = gameid;
		this.state = state;
		this.s =s;



	}

}
