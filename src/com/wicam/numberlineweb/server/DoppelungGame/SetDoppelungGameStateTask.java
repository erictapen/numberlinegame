package com.wicam.numberlineweb.server.DoppelungGame;

import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameState;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.SetGameStateTask;

public class SetDoppelungGameStateTask extends SetGameStateTask {

	@Override
	public void run(){
		super.run();
		
		// set next word
		DoppelungGameCommunicationServiceServlet doppelungServ = (DoppelungGameCommunicationServiceServlet)s;
		((DoppelungGameState)s.getGameById(gameid)).setNextWord(doppelungServ.getNextWord(gameid));
	}
	
	public SetDoppelungGameStateTask(int gameid, int state,
			GameCommunicationServiceServlet s) {
		super(gameid, state, s);
	}

}
