package com.wicam.numberlineweb.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;
import com.wicam.numberlineweb.client.chatView.ChatMsg;

public interface CommunicationServiceAsync {

	public void getOpenGames(AsyncCallback<ArrayList<NumberLineGameState>> callback);

	public void openGame(NumberLineGameState game,AsyncCallback<NumberLineGameState> callback);

	public void joinGame(String id,AsyncCallback<String> callback);

	public void clickedAt(String s,AsyncCallback<NumberLineGameState> callback);
	
	public void updateReadyness(String s,AsyncCallback<Boolean> callback);

	public void update(String ids, AsyncCallback<NumberLineGameState> callback);

	public void sendChatMsg(ChatMsg msg, AsyncCallback<Boolean> callback);

	public void getNewChatMsgs(String ids, AsyncCallback<ChatMsg[]> callback);

}
