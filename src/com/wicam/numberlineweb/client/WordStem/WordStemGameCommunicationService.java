package com.wicam.numberlineweb.client.WordStem;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameCommunicationService;
import com.wicam.numberlineweb.client.GameState;

@RemoteServiceRelativePath("wordStemGameCommunication")

public interface WordStemGameCommunicationService extends GameCommunicationService {
	
	public GameState clickedAt(String clicked);	
}
