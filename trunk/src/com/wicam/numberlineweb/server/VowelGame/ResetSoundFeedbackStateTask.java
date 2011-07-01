package com.wicam.numberlineweb.server.VowelGame;

import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameState;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.SetGameStateTask;

/**
 * A simple TimerTask for setting the game-state after a specific amount of time.
 * @author patrick
 *
 */

public class ResetSoundFeedbackStateTask extends SetGameStateTask {


	private int playerid;

	@Override
	public void run() {
		((DoppelungGameState)s.getGameById(gameid)).setShowSoundFeedback(playerid, false);
		s.setChanged(gameid);
	}

	public ResetSoundFeedbackStateTask(int gameid, int playerid, int state, GameCommunicationServiceServlet s) {
		super(gameid, state, s);
		this.playerid = playerid;
	}

}
