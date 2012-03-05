package com.wicam.numberlineweb.server.Multiplication;

import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameState;
import com.wicam.numberlineweb.server.SetGameStateTask;

public class MultiplicationGameStateTask extends SetGameStateTask{


	@Override
	public void run(){
		super.run();

		if (((MultiplicationGameState)s.getGameById(gameid)) != null) {
			// reset clicked pos
			//((MultiplicationGameState)s.getGameById(gameid)).resetAllPlayerActPos();

			// numbers should be changed while winner info is displayed
			((MultiplicationGameCommunicationServiceServlet) s).newResults((MultiplicationGameState) s.getGameById(gameid));
		}
	}

	public MultiplicationGameStateTask(int gameid, int state, MultiplicationGameCommunicationServiceServlet s) {
		super(gameid, state, s);
	}

}
