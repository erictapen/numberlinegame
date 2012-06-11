package com.wicam.numberlineweb.client.VowelGame.DoppelungGame;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameCommunicationService;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;

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
	
	public GameState sendKeepAlive(String ids);

	public GameState setPlayerPoints(String string);
	
	public ArrayList<VowelGameWord> getSimpleWordList(String id);
}
