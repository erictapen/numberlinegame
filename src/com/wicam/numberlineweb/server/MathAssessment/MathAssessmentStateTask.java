package com.wicam.numberlineweb.server.MathAssessment;

import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameState;
import com.wicam.numberlineweb.server.SetGameStateTask;

public class MathAssessmentStateTask extends SetGameStateTask{


	@Override
	public void run(){
		super.run();

		if (((MultiplicationGameState)s.getGameById(gameid)) != null) {
			// reset clicked pos
			//((MathAssessmentState)s.getGameById(gameid)).resetAllPlayerActPos();

			// numbers should be changed while winner info is displayed
			((MathAssessmentCommunicationServiceServlet) s).newResults((MultiplicationGameState) s.getGameById(gameid));
		}
	}

	public MathAssessmentStateTask(int gameid, int state, MathAssessmentCommunicationServiceServlet s) {
		super(gameid, state, s);
	}

}