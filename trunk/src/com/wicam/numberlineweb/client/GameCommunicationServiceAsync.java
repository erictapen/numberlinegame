package com.wicam.numberlineweb.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GameCommunicationServiceAsync {

	public void getOpenGames(AsyncCallback<ArrayList<GameState>> callback);

	public void openGame(GameState game,AsyncCallback<GameState> callback);
	
	public void joinGame(String id,AsyncCallback<String> callback);
	
	public void leaveGame(String id,AsyncCallback<Boolean> callback);
	
	public void updateReadyness(String s,AsyncCallback<Boolean> callback);

	public void update(String ids, AsyncCallback<GameState> callback);

	public void logRoundStarted(AsyncCallback<Boolean> callback);
	
	public void logRoundEnded(AsyncCallback<Boolean> callback);
	
	public void logUserMadeMove(AsyncCallback<Boolean> callback);

	public void loggingOn(boolean b, AsyncCallback<Boolean> callback);

	
}
