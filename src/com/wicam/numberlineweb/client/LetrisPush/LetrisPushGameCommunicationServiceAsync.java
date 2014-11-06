package com.wicam.numberlineweb.client.LetrisPush;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameJoinException;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Letris.LetrisGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;

/**
 * ServiceAsync interface for the LeTris game
 * 
 * @author timfissler
 *
 */

public interface LetrisPushGameCommunicationServiceAsync extends GameCommunicationServiceAsync {
	
	public void joinGame(String ids, String connectionID, AsyncCallback<String> callback);
	
	public void sendTargetUpdate(LetrisPushGamePlaygroundState playgroundState, AsyncCallback<Void> callback);
	
	public void getTargetWords(AsyncCallback<ArrayList<VowelGameWord>> callback);
}
