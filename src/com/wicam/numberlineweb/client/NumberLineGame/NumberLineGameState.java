package com.wicam.numberlineweb.client.NumberLineGame;

import java.io.Serializable;


/**
 * This is the state sent by the server. Holds all game information.
 * @author patrick
 *
 */

public class NumberLineGameState implements Serializable{

	// we need a serial number for GWT to identify sent instances
	private static final long serialVersionUID = 846864907276321588L;


	private String playerA;
	private String playerB;

	private int playerApoints=0;
	private int playerBpoints=0;

	private int playerAactPos = Integer.MIN_VALUE;
	private int playerBactPos = Integer.MIN_VALUE;

	private int winnerOfLastRound;

	private int leftNumber;
	private int rightNumber;
	private int exerciseNumber;
	private int pointerWidth;

	private String gameName;
	private int gameId;

	private int state = -1;

	private boolean playerAclicked;
	private boolean playerBclicked;

	// needed to synchronize states
	private boolean playerAready;
	private boolean playerBready;


	public int getPlayerPoints(int id) {
		switch (id) {
		case 1:
			return playerApoints;

		case 2:
			return playerBpoints;

		}
		return 0;

	}

	public void setPointerWidth(int width) {

		this.pointerWidth = width;

	}

	public int getPointerWidth() {

		return this.pointerWidth;

	}

	public String getPlayerName(int id) {

		switch (id) {
		case 1:
			return playerA;

		case 2:
			return playerB;
		}
		return null;

	}

	public void setPlayerPoints(int id,int to) {
		switch (id) {
		case 1:
			playerApoints = to;
			break;
		case 2:
			playerBpoints = to;
			break;

		}

	}


	public int getPlayerActPos(int id) {
		switch (id) {
		case 1:
			return playerAactPos;

		case 2:
			return playerBactPos;

		}

		return 0;
	}

	public void setPlayerActPos(int id, int to) {
		switch (id) {
		case 1:
			playerAactPos = to;
			break;
		case 2:
			playerBactPos = to;
			break;
		}
	}

	public int getPlayerCount() {

		if (playerA != null && playerB != null) return 2;
		if (playerA != null || playerB != null) return 1;
		return 0;

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

		return playerA==null || playerB==null;


	}

	public int addPlayer(String name) {

		if (playerA==null) {
			playerA=name;
			return 1;
		}else{
			if (playerA.equals(name)) {
				name += " 2";
				playerA += " 1";
			}
			playerB=name;
			return 2;

		}


	}

	public void removePlayer(int id) {

		if (id == 1) {

			playerA=null;

		}else{

			playerB=null;
		}



	}

	public void setPlayerAclicked(boolean playerAclicked) {
		this.playerAclicked = playerAclicked;
	}

	public boolean isPlayerAclicked() {
		return playerAclicked;
	}

	public void setPlayerBclicked(boolean playerBclicked) {
		this.playerBclicked = playerBclicked;
	}

	public boolean isPlayerBclicked() {
		return playerBclicked;
	}

	public void setPlayerReady(int id,boolean playerReady) {
		if (id == 1) this.playerAready = playerReady;
		if (id == 2) this.playerBready = playerReady;
	}

	public boolean isPlayerReady(int id) {
		if (id == 1) return playerBready;
		if (id == 2) return playerBready;
		return false;
	}


}
