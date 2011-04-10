package com.wicam.numberlineweb.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wicam.numberlineweb.client.CommunicationService;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;
import com.wicam.numberlineweb.client.chatView.ChatMsg;
import com.wicam.numberlineweb.server.NumberLineGame.NumberLineGameCommunication;

/**
 * Servlet for the number line game. Everthing server-side is handled here
 * @author patrick
 *
 */ 

public class CommunicationServiceServlet extends RemoteServiceServlet implements CommunicationService{

	private static final long serialVersionUID = -7771441559779705194L;
	private ChatCommunication chatComm = new ChatCommunication();
	private GameCommunication gameComm;
	private NumberLineGameCommunication numberLineGameComm;


	public CommunicationServiceServlet() {
		gameComm = new GameCommunication(this);
		numberLineGameComm = new NumberLineGameCommunication(gameComm);
	}
	
	public ChatCommunication getChatComm() {
		return chatComm;
	}

	public GameCommunication getGameComm() {
		return gameComm;
	}

	public NumberLineGameCommunication getNumberLineGameComm() {
		return numberLineGameComm;
	}

	/**
	 * GAME FUNCTIONALITY
	 * ==================
	 */

	@Override
	public ArrayList<GameState> getOpenGames() {
		return gameComm.getOpenGames();
	}

	@Override
	public String joinGame(String ids) {
		return gameComm.joinGame(ids);
	}

	@Override
	public boolean leaveGame(String ids) {
		return gameComm.leaveGame(ids);
	}

	@Override
	public boolean updateReadyness(String ids) {
		return gameComm.updateReadyness(ids);
	}

	@Override
	public GameState update(String ids) {
		return gameComm.update(ids);
	}

	@Override
	public NumberLineGameState openNumberLineGame(NumberLineGameState g) {
		return gameComm.openNumberLineGame(g);
	}

	/**
	 * CHAT FUNCTIONALITY
	 * ==================
	 */

	public boolean sendChatMsg(ChatMsg msg) {
		return (chatComm.sendChatMsg(msg));
	}

	public ChatMsg popNewMsgs(int gameid, String uname) {
		return chatComm.popNewMsgs(gameid, uname);
	}

	@Override
	public ChatMsg[] getNewChatMsgs(String ids) {
		return chatComm.getNewChatMsgs(ids);
	}

	/**
	 * NUMBER LINE GAME FUNCTIONALITY
	 * ==================
	 */
	
	@Override
	public GameState clickedAt(String clicked) {
		return numberLineGameComm.clickedAt(clicked);
	}

}
