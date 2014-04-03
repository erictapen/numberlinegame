package com.wicam.numberlineweb.client.MultiplicationInverse;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameCommunicationService;
import com.wicam.numberlineweb.client.GameState;

@RemoteServiceRelativePath("multiplicationInverseGameCommunication")

public interface MultiplicationInverseGameCommunicationService extends GameCommunicationService {
	
	public GameState clickedAt(String clicked);
	
}

