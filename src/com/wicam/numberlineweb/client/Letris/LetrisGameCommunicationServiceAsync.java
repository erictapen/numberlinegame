package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;

/**
 * ServiceAsync interface for the LeTris game
 * 
 * @author timfissler
 *
 */

public interface LetrisGameCommunicationServiceAsync extends GameCommunicationServiceAsync {
	
	public void updatePoints(String ids, AsyncCallback<GameState> callback);
	
	public void getTargetWords(AsyncCallback<ArrayList<VowelGameWord>> callback);
}
