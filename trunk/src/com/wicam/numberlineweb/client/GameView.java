package com.wicam.numberlineweb.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.Composite;

public abstract class GameView extends Composite {

	public final static String[] playerColors = {"blue", "red", "orange", "Magenta", "DarkKhaki"};
	protected int numberOfPlayers;
	protected GameController gameController;
	
	protected GameView (int numberOfPlayers){
		this.numberOfPlayers = numberOfPlayers;
		
		
	}
	protected GameView (int numberOfPlayers, GameController gameController){
		this.numberOfPlayers = numberOfPlayers;
		this.gameController = gameController;
		
	}
	
}
