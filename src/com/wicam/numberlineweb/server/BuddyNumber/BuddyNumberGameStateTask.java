package com.wicam.numberlineweb.server.BuddyNumber;

import com.wicam.numberlineweb.client.BuddyNumber.BuddyNumberGameState;
import com.wicam.numberlineweb.server.SetGameStateTask;

public class BuddyNumberGameStateTask extends SetGameStateTask{


	@Override
	public void run(){
		super.run();

		if (((BuddyNumberGameState)s.getGameById(gameid)) != null) {

			// numbers should be changed while winner info is displayed
			((BuddyNumberGameCommunicationServiceServlet) s).newDigits((BuddyNumberGameState) s.getGameById(gameid));
		}
	}

	public BuddyNumberGameStateTask(int gameid, int state, BuddyNumberGameCommunicationServiceServlet s) {
		super(gameid, state, s);
	}

}
