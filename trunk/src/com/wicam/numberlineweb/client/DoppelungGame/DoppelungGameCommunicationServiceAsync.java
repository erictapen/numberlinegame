package com.wicam.numberlineweb.client.DoppelungGame;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameState;

public interface DoppelungGameCommunicationServiceAsync extends GameCommunicationServiceAsync {

	public void bottonClicked(String ids,AsyncCallback<GameState> callback);
	
	public void updatePoints(String ids, AsyncCallback<GameState> callback);
	
	public void wordEntered(String ids, AsyncCallback<GameState> callback);
}
