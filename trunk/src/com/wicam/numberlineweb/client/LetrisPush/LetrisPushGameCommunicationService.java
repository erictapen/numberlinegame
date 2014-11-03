package com.wicam.numberlineweb.client.LetrisPush;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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
		LetrisGameCommunicationService {
	
//	public GameState updatePoints(String ids);
//	
//	public ArrayList<VowelGameWord> getTargetWords();
}
