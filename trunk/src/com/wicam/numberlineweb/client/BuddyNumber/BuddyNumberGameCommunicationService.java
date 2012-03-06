package com.wicam.numberlineweb.client.BuddyNumber;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameCommunicationService;
import com.wicam.numberlineweb.client.GameState;

@RemoteServiceRelativePath("buddyNumberGameCommunication")

public interface BuddyNumberGameCommunicationService extends GameCommunicationService {
	
	public GameState clickedAt(String clicked);	
}
