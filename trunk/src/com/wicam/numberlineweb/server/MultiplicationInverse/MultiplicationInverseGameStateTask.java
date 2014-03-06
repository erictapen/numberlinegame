package com.wicam.numberlineweb.server.MultiplicationInverse;

import com.wicam.numberlineweb.client.MultiplicationInverse.MultiplicationInverseGameState;
import com.wicam.numberlineweb.server.Multiplication.MultiplicationGameStateTask;

public class MultiplicationInverseGameStateTask extends MultiplicationGameStateTask {

	@Override
	public void run(){
		super.run();

		if (((MultiplicationInverseGameState)s.getGameById(gameid)) != null) {
			// reset clicked pos
			//((MultiplicationGameState)s.getGameById(gameid)).resetAllPlayerActPos();

			// numbers should be changed while winner info is displayed
			((MultiplicationInverseGameCommunicationServiceServlet) s).newResults((MultiplicationInverseGameState) s.getGameById(gameid));
		}
	}

	public MultiplicationInverseGameStateTask(int gameid, int state, MultiplicationInverseGameCommunicationServiceServlet s) {
		super(gameid, state, s);
	}
	
}
