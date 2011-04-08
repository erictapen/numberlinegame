package com.wicam.numberlineweb.client.NumberLineGame;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * This is the state sent by the server. Holds all game information.
 * @author patrick
 *
 */

public class NumberLineGameState implements Serializable{

	// we need a serial number for GWT to identify sent instances
	private static final long serialVersionUID = 846864907276321588L;


	private ArrayList<NumberLineGamePlayer> players = new ArrayList<NumberLineGamePlayer>();
	
	private int numberOfMaxPlayers;
	private int numberOfMaxNPCs;
	
	private int winnerOfLastRound;

	private int leftNumber;
	private int rightNumber;
	private int exerciseNumber;
	private int pointerWidth;
	private int maxItems = 20; // maximal exercise numbers
	private int itemCount = 0; // number of exercise numbers that have been set

	private String gameName;
	private int gameId;

	private int state = -1;


	public int getPlayerPoints(int playerid) {
		if (playerid-1 < players.size())
			return players.get(playerid-1).getPoints();
		return 0;
	}

	public void setPointerWidth(int width) {

		this.pointerWidth = width;

	}
	
	public int getPointerWidth() {

		return this.pointerWidth;

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


	public int getPlayerActPos(int playerid) {
		if(playerid-1 < players.size())
			return players.get(playerid-1).getActPos();

		return 0;
	}

	public void setPlayerActPos(int playerid, int to) {
		if(playerid-1 < players.size())
			players.get(playerid-1).setActPos(to);
	}

	public void resetAllPlayerActPos(){
		for (NumberLineGamePlayer player: players){
			player.setActPos(Integer.MIN_VALUE);
		}
	}
	
	public int getPlayerCount() {
		return players.size();
	}

	public int getLeftNumber() {
		return leftNumber;
	}

	public void setLeftNumber(int leftNumber) {
		this.leftNumber = leftNumber;
	}

	public int getExerciseNumber() {
		return exerciseNumber;
	}

	public void setExerciseNumber(int exerciseNumber) {
		itemCount++;
		this.exerciseNumber = exerciseNumber;
	}

	public int getRightNumber() {
		return rightNumber;
	}

	public void setRightNumber(int rightNumber) {
		this.rightNumber = rightNumber;
	}


	public int getWinnerOfLastRound() {
		return winnerOfLastRound;
	}

	public void setWinnerOfLastRound(int winnerOfLastRound) {
		this.winnerOfLastRound = winnerOfLastRound;
	}


	public NumberLineGameState() {



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

	public int addPlayer(String newName) {
		int countSameName = 1;
		for (NumberLineGamePlayer player: players){
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
		System.out.println(newName);
		players.add(new NumberLineGamePlayer());
		players.get(players.size()-1).setName(newName);
		return players.size();
	}


	public void setPlayerClicked(int playerid){
		if (playerid-1 < players.size())
			players.get(playerid-1).setClicked(true);
	}
	
	public boolean isPlayerClicked(int playerid){
		if (playerid-1 < players.size())
			return players.get(playerid-1).isClicked();
		return false;
	}

	public void resetPlayersClicked(){
		for (int i = 0; i < players.size(); i++){
			players.get(i).setClicked(false);
		}
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
		for (NumberLineGamePlayer player: players){
			player.setReady(false);
		}
	}
	
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfMaxPlayers = numberOfPlayers;
	}

	public int getMaxNumberOfPlayers() {
		return numberOfMaxPlayers;
	}

	public ArrayList<NumberLineGamePlayer> getPlayers() {
		return players;
	}
	
	public ArrayList<String> getPlayerNamesList() {
		ArrayList<String> playerNamesList = new ArrayList<String>();
		for (NumberLineGamePlayer player: players)
			playerNamesList.add(player.getName());
		return playerNamesList;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}

	public int getMaxItems() {
		return maxItems;
	}
	
	public void setHasLeftGame(int playerid, boolean leftGame) {
		this.players.get(playerid-1).setLeftGame(leftGame);
	}
	
	public boolean getHasLeftGame(int playerid) {
		return this.players.get(playerid-1).hasLeftGame();
	}

	public void setNumberOfMaxNPCs(int numberOfMaxNPCs) {
		this.numberOfMaxNPCs = numberOfMaxNPCs;
	}

	public int getNumberOfMaxNPCs() {
		return numberOfMaxNPCs;
	}
	
}
