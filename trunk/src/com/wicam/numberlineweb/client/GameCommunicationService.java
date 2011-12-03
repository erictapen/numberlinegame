package com.wicam.numberlineweb.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gameCommunication")

public interface GameCommunicationService extends  RemoteService {

	public ArrayList<GameState> getOpenGames();

	public GameState openGame(GameState g);
	
	public String joinGame(String id) throws GameJoinException;
	
	public boolean leaveGame(String id);
	
	public boolean updateReadyness(String s);

	public GameState update(String ids);

	public boolean loggingOn(boolean b);
	
}
