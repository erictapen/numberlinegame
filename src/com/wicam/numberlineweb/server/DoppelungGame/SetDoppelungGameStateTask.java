package com.wicam.numberlineweb.server.DoppelungGame;

import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameState;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameWord;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.SetGameStateTask;

public class SetDoppelungGameStateTask extends SetGameStateTask {

	@Override
	public void run(){
		super.run();
		
		// set next word
		DoppelungGameCommunicationServiceServlet doppelungServ = (DoppelungGameCommunicationServiceServlet)s;
		DoppelungGameWord word = doppelungServ.getNextWord(gameid);
		((DoppelungGameState)s.getGameById(gameid)).setNextWord(word);
		
		// reset correct answered
		((DoppelungGameState)s.getGameById(gameid)).setCorrectAnswered(false);
	}
	
	public SetDoppelungGameStateTask(int gameid, int state,
			GameCommunicationServiceServlet s) {
		super(gameid, state, s);
	}

}
