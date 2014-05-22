package com.wicam.numberlineweb.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class GameState implements IsSerializable{

	private String gameName;
	private int gameId;
	private int state = -1;
	
	private long serverSendTime = 0;
	
	private long pingId = -1;

	private int gameOpenedUserId;

	protected ArrayList<Player> players = new ArrayList<Player>();
	private int numberOfMaxPlayers;
	private int numberOfMaxNPCs;
	
	private int NPCResponseTime; // in milliseconds
	
	public int getNPCResponseTime() {
		return NPCResponseTime;
	}

	public void setNPCResponseTime(int nPCResponseTime) {
		NPCResponseTime = nPCResponseTime;
	}

	public int getState() {

		return state;
	}

	public void setState(int state) {

		this.state=state;
	}

	public void setGameId(int id) {

		this.gameId=id;

	}
	
	
	public long getPingId() {
		return pingId;
	}

	public void setPingId(long pingId) {
		this.pingId = pingId;
	}

	public void setGameName(String name) {

		this.gameName=name;

	}

	public boolean hasPlayerLeft(int pid) {
		
		return players.get(pid-1).hasLeftGame();
		
	}
	
	public String getName() {

		return gameName;

	}

	public int getId() {

		return gameId;

	}
	
	public boolean isFree() {
		return players.size() < numberOfMaxPlayers;
	}
	
	public int getPlayerCount() {
		return players.size();
	}
	
	public int getPlayerPoints(int playerid) {
		if (playerid-1 < players.size())
			return players.get(playerid-1).getPoints();
		return 0;
	}
	
	public String getPlayerName(int playerid) {

		if (playerid-1 < players.size())
			return players.get(playerid-1).getName();
		return null;
	}

	public long getServerSendTime() {
		return serverSendTime;
	}

	public void setServerSendTime(long serverSendTime) {
		this.serverSendTime = serverSendTime;
	}

	public void setPlayerPoints(int playerid,int to) {
		if(playerid-1 < players.size())
			players.get(playerid-1).setPoints(to);

	}

	public abstract int addPlayer(String newName, int uid);
	
	protected String checkDuplicateName(String newName){
		int countSameName = 1;
		for (Player player: players){
			// to ensure different namesremovePlayer
			if (newName.equals(player.getName())){
				countSameName++;
				// TODO: only a solution if number of players with same name < 10
				if (countSameName > 2)
					newName = newName.substring(0, newName.length()-2) + " " + countSameName;
				else
					newName = newName + " " + countSameName;
			}
		}
		return newName;
	}
	
	public void setPlayerReady(int playerid, boolean playerReady) {
		players.get(playerid-1).setReady(playerReady);
	}

	public boolean isPlayerReady(int playerid) {
		if (playerid-1 < players.size())
			return players.get(playerid-1).isReady();
		return false;
	}

	public void resetReadyness(){
		for (Player player: players){
			player.setReady(false);
		}
	}
	
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfMaxPlayers = numberOfPlayers;
	}

	public int getMaxNumberOfPlayers() {
		return numberOfMaxPlayers;
	}

	public void setNumberOfMaxNPCs(int numberOfMaxNPCs) {
		this.numberOfMaxNPCs = numberOfMaxNPCs;
	}

	public int getNumberOfMaxNPCs() {
		return numberOfMaxNPCs;
	}
	
	public ArrayList<? extends Player> getPlayers() {
		return players;
	}
	
	public void setHasLeftGame(int playerid, boolean leftGame) {
		this.players.get(playerid-1).setLeftGame(leftGame);
	}
	
	public boolean getHasLeftGame(int playerid) {
		return this.players.get(playerid-1).hasLeftGame();
	}

	public int getGameOpenedUserId() {
		return gameOpenedUserId;
	}

	public void setGameOpenedUserId(int gameOpenedUserId) {
		this.gameOpenedUserId = gameOpenedUserId;
	}
	
	public String toString() {
		String s = "game name: " + getName();
		s += "\ngame ID: " + getId();
		s += "\nstate: " + getState();
		s += "\nserver send time: " + getServerSendTime();
		s += "\nping ID: " + getPingId();
		s += "\ngame opened user ID: " + getGameOpenedUserId();
		s += "\nplayers: " + getPlayers();
		s += "\nnumber of max players: " + getPlayerCount();
		s += "\nnumber of max NPCs: " + getNumberOfMaxNPCs();
		s += "\nNPC response time: " + getNPCResponseTime();
		return s;
	}
}
