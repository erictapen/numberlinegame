package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameCommunicationService;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;

/**
 * Service interface for the LeTris game
 * 
 * @author timfissler
 *
 */
@RemoteServiceRelativePath("letrisGameCommunication")

public interface LetrisGameCommunicationService extends
		GameCommunicationService {	
	
	public GameState updatePoints(String ids);
	
	public ArrayList<VowelGameWord> getTargetWords();
}
