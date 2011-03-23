package com.wicam.numberlineweb.server;

/**
 * The update-state. Holds information about whether a user is
 * up-to-date.
 * @author patrick
 *
 */
public class UpdateState {
	
	
	private int playerID;
	private int gameId;
	private boolean actual;
	
	
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public boolean isActual() {
		return actual;
	}
	public void setActual(boolean actual) {
		this.actual = actual;
	}
	public UpdateState(int playerID, int gameId, boolean actual) {
		super();
		this.playerID = playerID;
		this.gameId = gameId;
		this.actual = actual;
	}
	

}
