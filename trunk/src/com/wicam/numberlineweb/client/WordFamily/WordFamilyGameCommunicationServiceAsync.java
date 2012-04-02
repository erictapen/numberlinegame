package com.wicam.numberlineweb.client.WordFamily;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameState;

public interface WordFamilyGameCommunicationServiceAsync extends GameCommunicationServiceAsync {

	public void clickedAt(String s,AsyncCallback<GameState> callback);
}
