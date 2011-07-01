package com.wicam.numberlineweb.server.VowelGame;

import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameState;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.SetGameStateTask;
import com.wicam.numberlineweb.server.VowelGame.DoppelungGame.DoppelungGameCommunicationServiceServlet;

public class SetDoppelungGameStateTask extends SetGameStateTask {

	@Override
	public void run(){
		super.run();
		
		// set next word
		DoppelungGameCommunicationServiceServlet doppelungServ = (DoppelungGameCommunicationServiceServlet)s;
		VowelGameWord word = doppelungServ.getNextWord(gameid);
		DoppelungGameState g = ((DoppelungGameState)s.getGameById(gameid));
		g.setNextWord(word);
		
		// reset everything
		g.setPlayerPosX(1, 270);
		g.setPlayerPosY(1, 330);
		g.setPlayerPosX(2, 270);
		g.setPlayerPosY(2, 330);
		g.setShowSoundFeedback(1, false);
		g.setShowSoundFeedback(2, false);
		g.setShowWordFeedback(1, false);
		g.setShowWordFeedback(2, false);
		g.setEndedShortVowelGame(1, false);
		g.setEndedShortVowelGame(2, false);
		g.setAnswer(1, false);
		g.setAnswer(2, false);
		g.resetSoundTries(1);
		g.resetWordTries(1);
		g.resetSoundTries(2);
		g.resetWordTries(2);
	}
	
	public SetDoppelungGameStateTask(int gameid, int state,
			GameCommunicationServiceServlet s) {
		super(gameid, state, s);
	}

}
