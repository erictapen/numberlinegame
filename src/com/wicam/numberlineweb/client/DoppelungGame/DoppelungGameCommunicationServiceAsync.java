package com.wicam.numberlineweb.client.DoppelungGame;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameState;

/**
 * ServiceAsync interface for the doppelung game
 * 
 * @author shuber
 *
 */

public interface DoppelungGameCommunicationServiceAsync extends GameCommunicationServiceAsync {

	public void buttonClicked(String ids,AsyncCallback<GameState> callback);
	
	public void updatePoints(String ids, AsyncCallback<GameState> callback);
	
	public void wordEntered(String ids, AsyncCallback<GameState> callback);
		
	public void enableWordInput(String ids, AsyncCallback<GameState> callback);
	
	public void updatePlayerPos(String ids, AsyncCallback<Boolean> callback);
}
