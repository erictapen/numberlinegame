package com.wicam.numberlineweb.server.WordFamily;

import com.wicam.numberlineweb.client.WordFamily.WordFamilyGameState;
import com.wicam.numberlineweb.server.SetGameStateTask;

public class WordFamilyGameStateTask extends SetGameStateTask{


	@Override
	public void run(){
		super.run();

		if (((WordFamilyGameState)s.getGameById(gameid)) != null) {

			// numbers should be changed while winner info is displayed
			((WordFamilyGameCommunicationServiceServlet) s).newWords((WordFamilyGameState) s.getGameById(gameid));
		}
	}

	public WordFamilyGameStateTask(int gameid, int state, WordFamilyGameCommunicationServiceServlet s) {
		super(gameid, state, s);
	}

}
