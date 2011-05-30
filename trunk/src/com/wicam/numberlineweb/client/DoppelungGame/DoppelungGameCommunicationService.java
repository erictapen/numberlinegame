package com.wicam.numberlineweb.client.DoppelungGame;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameCommunicationService;
import com.wicam.numberlineweb.client.GameState;

@RemoteServiceRelativePath("doppelungGameCommunication")

public interface DoppelungGameCommunicationService extends
		GameCommunicationService {
	
	public GameState buttonClicked(String ids);	
	
	public GameState updatePoints(String ids);
	
	public GameState wordEntered(String ids);
	
	public DoppelungGameState keyEvent(String ids);
	
	public GameState enableWordInput(String ids);
	
	public Boolean updatePlayerPos(String ids);
}
