package com.wicam.numberlineweb.client.VowelGame.DoppelungGame;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameCommunicationService;
import com.wicam.numberlineweb.client.GameState;

/**
 * Service interface for the doppelung game
 * 
 * @author shuber
 *
 */
@RemoteServiceRelativePath("doppelungGameCommunication")

public interface DoppelungGameCommunicationService extends
		GameCommunicationService {
	
	public GameState buttonClicked(String ids);	
	
	public GameState updatePoints(String ids);
	
	public GameState wordEntered(String ids);
	
	public GameState enableWordInput(String ids);
	
	public GameState updatePlayerPos(String ids);
}
