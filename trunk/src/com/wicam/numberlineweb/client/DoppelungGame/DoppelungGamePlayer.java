package com.wicam.numberlineweb.client.DoppelungGame;

import com.wicam.numberlineweb.client.Player;

public class DoppelungGamePlayer extends Player {
	
	private boolean startButtonClicked = false;

	public void setStartButtonClicked(boolean startButtonClicked) {
		this.startButtonClicked = startButtonClicked;
	}

	public boolean isStartButtonClicked() {
		return startButtonClicked;
	}
	
}
