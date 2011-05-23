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
		
		// reset everything
		((DoppelungGameState)s.getGameById(gameid)).setShowSoundFeedback(1, false);
		((DoppelungGameState)s.getGameById(gameid)).setShowSoundFeedback(2, false);
		((DoppelungGameState)s.getGameById(gameid)).setShowWordFeedback(1, false);
		((DoppelungGameState)s.getGameById(gameid)).setShowWordFeedback(2, false);
		((DoppelungGameState)s.getGameById(gameid)).setEndedShortVowelGame(1, false);
		((DoppelungGameState)s.getGameById(gameid)).setEndedShortVowelGame(2, false);
		((DoppelungGameState)s.getGameById(gameid)).setAnswer(1, false);
		((DoppelungGameState)s.getGameById(gameid)).setAnswer(2, false);
		((DoppelungGameState)s.getGameById(gameid)).resetSoundTries(1);
		((DoppelungGameState)s.getGameById(gameid)).resetWordTries(1);
		((DoppelungGameState)s.getGameById(gameid)).resetSoundTries(2);
		((DoppelungGameState)s.getGameById(gameid)).resetWordTries(2);
	}
	
	public SetDoppelungGameStateTask(int gameid, int state,
			GameCommunicationServiceServlet s) {
		super(gameid, state, s);
	}

}
