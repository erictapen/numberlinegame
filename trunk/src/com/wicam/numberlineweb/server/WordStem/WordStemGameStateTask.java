package com.wicam.numberlineweb.server.WordStem;

import com.wicam.numberlineweb.client.WordStem.WordStemGameState;
import com.wicam.numberlineweb.server.SetGameStateTask;

public class WordStemGameStateTask extends SetGameStateTask{


	@Override
	public void run(){
		super.run();

		if (((WordStemGameState)s.getGameById(gameid)) != null) {

			// numbers should be changed while winner info is displayed
			((WordStemGameCommunicationServiceServlet) s).newWords((WordStemGameState) s.getGameById(gameid));
		}
	}

	public WordStemGameStateTask(int gameid, int state, WordStemGameCommunicationServiceServlet s) {
		super(gameid, state, s);
	}

}
