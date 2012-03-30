package com.wicam.numberlineweb.client.OverTen;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameCommunicationService;
import com.wicam.numberlineweb.client.GameState;

@RemoteServiceRelativePath("overTenGameCommunication")

public interface OverTenGameCommunicationService extends GameCommunicationService {
	
	public GameState clickedAt(String clicked);	
}
