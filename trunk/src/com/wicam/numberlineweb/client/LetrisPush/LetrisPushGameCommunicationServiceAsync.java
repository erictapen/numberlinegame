package com.wicam.numberlineweb.client.LetrisPush;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Letris.LetrisGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;

/**
 * ServiceAsync interface for the LeTris game
 * 
 * @author timfissler
 *
 */

public interface LetrisPushGameCommunicationServiceAsync extends LetrisGameCommunicationServiceAsync {
	
//	public void updatePoints(String ids, AsyncCallback<GameState> callback);
//	
//	public void getTargetWords(AsyncCallback<ArrayList<VowelGameWord>> callback);
}
