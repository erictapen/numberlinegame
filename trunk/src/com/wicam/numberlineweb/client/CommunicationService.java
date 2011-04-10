package com.wicam.numberlineweb.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;
import com.wicam.numberlineweb.client.chatView.ChatMsg;


@RemoteServiceRelativePath("communication")

public interface CommunicationService extends RemoteService {


	public ArrayList<GameState> getOpenGames();

	public String joinGame(String id);
	
	public boolean leaveGame(String id);
	
	public boolean updateReadyness(String s);

	public GameState update(String ids);
	
	// number line game
	
	public NumberLineGameState openNumberLineGame(NumberLineGameState g);
	
	public GameState clickedAt(String clicked);	

	// chat
	
	public boolean sendChatMsg(ChatMsg msg);

	public ChatMsg[] getNewChatMsgs(String ids);

}
