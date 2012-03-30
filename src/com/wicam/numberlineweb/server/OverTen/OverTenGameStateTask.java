package com.wicam.numberlineweb.server.OverTen;

import com.wicam.numberlineweb.client.OverTen.OverTenGameState;
import com.wicam.numberlineweb.server.SetGameStateTask;

public class OverTenGameStateTask extends SetGameStateTask{


	@Override
	public void run(){
		super.run();

		if (((OverTenGameState)s.getGameById(gameid)) != null) {

			// numbers should be changed while winner info is displayed
			((OverTenGameCommunicationServiceServlet) s).newCalcs((OverTenGameState) s.getGameById(gameid));
		}
	}

	public OverTenGameStateTask(int gameid, int state, OverTenGameCommunicationServiceServlet s) {
		super(gameid, state, s);
	}

}
