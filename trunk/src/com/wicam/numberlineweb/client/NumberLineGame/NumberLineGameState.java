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


	private ArrayList<Player> players = new ArrayList<Player>();
	
	private int numberOfMaxPlayers;

	private int winnerOfLastRound;

	private int leftNumber;
	private int rightNumber;
	private int exerciseNumber;
	private int pointerWidth;

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
		for (Player player: players){
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
		for (Player player: players){
			// to ensure different names
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
		players.add(new Player());
		players.get(players.size()-1).setName(newName);
		return players.size();
	}

	public void removePlayer(int playerid) {
		if (playerid-1 < players.size())
			players.remove(playerid-1);
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
		for (Player player: players){
			player.setReady(false);
		}
	}
	
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfMaxPlayers = numberOfPlayers;
	}

	public int getNumberOfPlayers() {
		return numberOfMaxPlayers;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
}
