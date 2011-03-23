package com.wicam.numberlineweb.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;
import com.wicam.numberlineweb.client.chatView.ChatMsg;


@RemoteServiceRelativePath("communication")

public interface CommunicationService extends RemoteService {


	public ArrayList<NumberLineGameState> getOpenGames();

	public NumberLineGameState openGame(NumberLineGameState g);

	public String joinGame(String id);

	public boolean clickedAt(String clicked);
	
	public boolean updateReadyness(String s);

	public NumberLineGameState update(String ids);

	public boolean sendChatMsg(ChatMsg msg);

	public ChatMsg[] getNewChatMsgs(String ids);

}
