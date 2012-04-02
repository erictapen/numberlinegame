package com.wicam.numberlineweb.client.WordFamily;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameCommunicationService;
import com.wicam.numberlineweb.client.GameState;

@RemoteServiceRelativePath("wordFamilyGameCommunication")

public interface WordFamilyGameCommunicationService extends GameCommunicationService {
	
	public GameState clickedAt(String clicked);	
}
