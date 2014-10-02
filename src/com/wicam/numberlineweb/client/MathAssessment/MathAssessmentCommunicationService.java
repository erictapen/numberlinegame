package com.wicam.numberlineweb.client.MathAssessment;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameCommunicationService;
import com.wicam.numberlineweb.client.GameState;

@RemoteServiceRelativePath("multiplicationGameCommunication")

public interface MathAssessmentCommunicationService extends GameCommunicationService {
	
	public GameState clickedAt(String clicked);	
}
