package com.wicam.numberlineweb.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;
import com.wicam.numberlineweb.client.chatView.ChatMsg;

public interface CommunicationServiceAsync {

	public void getOpenGames(AsyncCallback<ArrayList<GameState>> callback);

	public void joinGame(String id,AsyncCallback<String> callback);
	
	public void leaveGame(String id,AsyncCallback<Boolean> callback);
	
	public void updateReadyness(String s,AsyncCallback<Boolean> callback);

	public void update(String ids, AsyncCallback<GameState> callback);
	
	// number line game
	
	public void openNumberLineGame(NumberLineGameState game,AsyncCallback<NumberLineGameState> callback);

	public void clickedAt(String s,AsyncCallback<GameState> callback);

	// chat
	
	public void sendChatMsg(ChatMsg msg, AsyncCallback<Boolean> callback);

	public void getNewChatMsgs(String ids, AsyncCallback<ChatMsg[]> callback);

}
