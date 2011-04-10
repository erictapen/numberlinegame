package com.wicam.numberlineweb.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class GameState implements IsSerializable{

	private String gameName;
	private int gameId;
	private int state = -1;
	
	protected ArrayList<Player> players = new ArrayList<Player>();
	private int numberOfMaxPlayers;
	private int numberOfMaxNPCs;
	
	public int getState() {

		return state;
	}

	public void setState(int state) {

		this.state=state;
	}

	public void setGameId(int id) {

		this.gameId=id;

	}

	public void setGameName(String name) {

		this.gameName=name;

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

	public void setPlayerPoints(int playerid,int to) {
		if(playerid-1 < players.size())
			players.get(playerid-1).setPoints(to);

	}

	public abstract int addPlayer(String newName);
	
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
}
