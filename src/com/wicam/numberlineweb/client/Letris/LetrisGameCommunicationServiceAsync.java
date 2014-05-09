package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameState;

/**
 * ServiceAsync interface for the LeTris game
 * 
 * @author timfissler
 *
 */

public interface LetrisGameCommunicationServiceAsync extends GameCommunicationServiceAsync {

//	public void buttonClicked(String ids,AsyncCallback<GameState> callback);
	
	public void updatePoints(String ids, AsyncCallback<GameState> callback);
	
	public void getTargetWords(AsyncCallback<ArrayList<String>> callback);
	
//	public void wordEntered(String ids, AsyncCallback<GameState> callback);
		
//	public void enableWordInput(String ids, AsyncCallback<GameState> callback);
	
//	public void updatePlayerPos(String ids, AsyncCallback<GameState> callback);
}
