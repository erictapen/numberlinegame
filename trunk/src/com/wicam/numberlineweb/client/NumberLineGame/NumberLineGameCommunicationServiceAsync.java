package com.wicam.numberlineweb.client.NumberLineGame;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameState;

public interface NumberLineGameCommunicationServiceAsync extends GameCommunicationServiceAsync {

	public void clickedAt(String s,AsyncCallback<GameState> callback);

	void startBenchmarkGame(GameState gameState,
			AsyncCallback<Boolean> callback);
}
