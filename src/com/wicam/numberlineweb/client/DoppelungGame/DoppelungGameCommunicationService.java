package com.wicam.numberlineweb.client.DoppelungGame;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameCommunicationService;
import com.wicam.numberlineweb.client.GameState;

@RemoteServiceRelativePath("doppelungGameCommunication")

public interface DoppelungGameCommunicationService extends
		GameCommunicationService {
	
	public GameState bottonClicked(String ids);	
	
	public GameState shortVowelGameEnded(String ids);	
}
