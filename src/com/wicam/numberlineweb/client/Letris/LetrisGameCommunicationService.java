package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameCommunicationService;
import com.wicam.numberlineweb.client.GameState;

/**
 * Service interface for the LeTris game
 * 
 * @author timfissler
 *
 */
@RemoteServiceRelativePath("letrisGameCommunication")

public interface LetrisGameCommunicationService extends
		GameCommunicationService {
	
//	public GameState buttonClicked(String ids);	
	
	public GameState updatePoints(String ids);
	
	public ArrayList<String> getTargetWords();
	
//	public GameState wordEntered(String ids);
	
//	public GameState enableWordInput(String ids);
	
//	public GameState updatePlayerPos(String ids);
}
