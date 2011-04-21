package com.wicam.numberlineweb.server;

public class EmptyGameTimeOutState {


	int gameID;
	int secsLeft;

	int timeOut;


	public EmptyGameTimeOutState(int gameID, int timeOut) {

		this.gameID = gameID;
		this.secsLeft = timeOut;
		this.timeOut = timeOut;

	}

	public boolean countDown() {

		this.secsLeft--;

		return (this.secsLeft > 1);


	}


	public int getGameId() {

		return gameID;

	}

	public void reset() {


		this.secsLeft = timeOut;

	}


}
