package com.wicam.numberlineweb.client;

import com.google.gwt.user.client.ui.Composite;

public abstract class GameView extends Composite {

	protected int numberOfPlayers;
	
	protected GameView (int numberOfPlayers){
		this.numberOfPlayers = numberOfPlayers;
	}
}
