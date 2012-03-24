package com.wicam.numberlineweb.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GameCommunicationServiceAsync {

	public void getOpenGames(AsyncCallback<ArrayList<GameState>> callback);

	public void openGame(GameState game,AsyncCallback<GameState> callback);
	
	public void joinGame(String id,AsyncCallback<String> callback);
	
	public void joinGameWithoutTimeout(String id,AsyncCallback<String> callback);
	
	public void leaveGame(String id,AsyncCallback<Boolean> callback);
	
	public void updateReadyness(String s,AsyncCallback<Boolean> callback);

	public void update(String ids, AsyncCallback<GameState> callback);

	public void loggingOn(boolean b, AsyncCallback<Boolean> callback);

	void getGameProperties(GameState g, AsyncCallback<String> callback);

	void getGameById(int id, AsyncCallback<GameState> callback);

	
}
