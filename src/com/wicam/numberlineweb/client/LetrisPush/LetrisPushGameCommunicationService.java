package com.wicam.numberlineweb.client.LetrisPush;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameCommunicationService;
import com.wicam.numberlineweb.client.GameJoinException;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Letris.LetrisGameCommunicationService;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;

/**
 * Service interface for the LeTris game
 * 
 * @author timfissler
 *
 */
@RemoteServiceRelativePath("letrisPushGameCommunication")

public interface LetrisPushGameCommunicationService extends
		GameCommunicationService {
	
	public String joinGame(String ids, String connectionID) throws GameJoinException;
	
	public void sendTargetUpdate(LetrisPushGamePlaygroundState playgroundState);
	
	public ArrayList<VowelGameWord> getTargetWords();
}
