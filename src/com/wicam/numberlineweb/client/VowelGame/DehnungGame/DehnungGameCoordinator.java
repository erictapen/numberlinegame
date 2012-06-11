package com.wicam.numberlineweb.client.VowelGame.DehnungGame;

import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameTypeSelector;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameController;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameCoordinator;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameState;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;

public class DehnungGameCoordinator extends DoppelungGameCoordinator {

	public DehnungGameCoordinator(GameCommunicationServiceAsync commServ,
			ChatCommunicationServiceAsync chatServ, Panel root,
			GameTypeSelector gs) {
		super(commServ, chatServ, root, gs);
	}
	
	/**
	 * returns the name of the game
	 */
	@Override
	public String getGameName() {

		return "Dehnungspiel";

	}

	@Override
	protected void createControllerAndView(){
		controller = new DoppelungGameController(this);
		this.view = new DehnungGameView(numberOfPlayers, controller,
				(DoppelungGameState) openGame);
	}
}
