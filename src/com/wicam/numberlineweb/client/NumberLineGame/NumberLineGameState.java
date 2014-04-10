package com.wicam.numberlineweb.client.NumberLineGame;

import java.io.Serializable;
import java.util.ArrayList;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;


/**
 * This is the state sent by the server. Holds all game information.
 * @author patrick
 *
 */

public class NumberLineGameState extends GameState implements Serializable{

	// we need a serial number for GWT to identify sent instances
	private static final long serialVersionUID = 846864907276321588L;
	
	private int winnerOfLastRound;

	private NumberRange numberRange;
	
	private int leftNumber;
	private int rightNumber;
	private int exerciseNumber;
	private int pointerWidth;
	private int maxItems = 20; // maximal exercise numbers
	private int itemCount = 0; // number of exercise numbers that have been set
	
	public void setPointerWidth(int width) {

		this.pointerWidth = width;

	}
	
	public int getPointerWidth() {

		return this.pointerWidth;

	}

	public int getPlayerActPos(int playerid) {
		if(playerid-1 < players.size())
			return ((NumberLineGamePlayer) players.get(playerid-1)).getActPos();

		return 0;
	}

	public void setPlayerActPos(int playerid, int to) {
		if(playerid-1 < players.size())
			((NumberLineGamePlayer) players.get(playerid-1)).setActPos(to);
	}

	public void resetAllPlayerActPos(){
		for (Player player: players){
			((NumberLineGamePlayer) player).setActPos(Integer.MIN_VALUE);
		}
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

	@Override
	public int addPlayer(String newName, int uid) {
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
		NumberLineGamePlayer newPlayer = new NumberLineGamePlayer();
		newPlayer.setName(newName);
		newPlayer.setColorId(players.size());
		newPlayer.setUid(uid);
		players.add(newPlayer);
		return players.size();
	}


	public void setPlayerClicked(int playerid){
		if (playerid-1 < players.size())
			((NumberLineGamePlayer) players.get(playerid-1)).setClicked(true);
	}
	
	public boolean isPlayerClicked(int playerid){
		if (playerid-1 < players.size())
			return ((NumberLineGamePlayer) players.get(playerid-1)).isClicked();
		return false;
	}

	public void resetPlayersClicked(){
		for (int i = 0; i < players.size(); i++){
			((NumberLineGamePlayer) players.get(i)).setClicked(false);
		}
	}
	
	public ArrayList<String> getPlayerNamesList() {
		ArrayList<String> playerNamesList = new ArrayList<String>();
		for (Player player: players)
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

	public NumberRange getNumberRange() {
		return numberRange;
	}

	public void setNumberRange(NumberRange numberRange) {
		this.numberRange = numberRange;
	}
}
