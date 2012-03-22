package com.wicam.numberlineweb.client.NumberLineGame;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameCommunicationService;
import com.wicam.numberlineweb.client.GameState;

@RemoteServiceRelativePath("numberLineGameCommunication")

public interface NumberLineGameCommunicationService extends GameCommunicationService {
	
	public GameState clickedAt(String clicked);	
	
	public boolean startBenchmarkGame(GameState gameState);
}
