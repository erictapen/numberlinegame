package com.wicam.numberlineweb.client;

import com.allen_sauer.gwt.voices.client.Html5Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.wicam.numberlineweb.client.Resources.SoundResourcesImpl;

public abstract class GameView extends Composite {

	public final static String[] playerColors = {"red", "blue", "orange", "Magenta", "DarkKhaki"};
	protected int numberOfPlayers;
	protected GameController gameController;
	protected SoundResourcesImpl sr;
	protected SoundController soundController;
	
	protected GameView (int numberOfPlayers){
		this.numberOfPlayers = numberOfPlayers;
		
		this.initSound();
		
	}
	protected GameView (int numberOfPlayers, GameController gameController){
		this.numberOfPlayers = numberOfPlayers;
		this.gameController = gameController;
		
		this.initSound();
	}
	
	private void initSound() {
		
		this.soundController = new SoundController();
		this.soundController.setPreferredSoundType(Html5Sound.class);
		this.sr = GWT.create(SoundResourcesImpl.class);
		
	}
}
