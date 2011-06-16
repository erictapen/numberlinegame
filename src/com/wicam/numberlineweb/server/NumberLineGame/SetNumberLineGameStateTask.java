package com.wicam.numberlineweb.server.NumberLineGame;

import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;
import com.wicam.numberlineweb.server.SetGameStateTask;

public class SetNumberLineGameStateTask extends SetGameStateTask{


	@Override
	public void run(){
		super.run();

		if (((NumberLineGameState)s.getGameById(gameid)) != null) {
			// reset clicked pos
			((NumberLineGameState)s.getGameById(gameid)).resetAllPlayerActPos();

			// numbers should be changed while winner info is displayed
			((NumberLineGameCommunicationServiceServlet) s).newNumbers((NumberLineGameState) s.getGameById(gameid));
		}
	}

	public SetNumberLineGameStateTask(int gameid, int state, NumberLineGameCommunicationServiceServlet s) {
		super(gameid, state, s);
	}

}
