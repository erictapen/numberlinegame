package com.wicam.numberlineweb.client.MultiplicationInverse;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.wicam.numberlineweb.client.GameJoinException;
import com.wicam.numberlineweb.client.GameSelector;
import com.wicam.numberlineweb.client.NumberLineWeb;
import com.wicam.numberlineweb.client.TextPopupBox;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameSelector;
import com.wicam.numberlineweb.server.database.drupal.DrupalCommunicator;
import com.wicam.numberlineweb.server.database.drupal.UserNotFoundException;

public class MultiplicationInverseGameSelector extends MultiplicationGameSelector {

	public MultiplicationInverseGameSelector(MultiplicationInverseGameCoordinator coordinator) {
		super(coordinator);
	}

	@Override
	protected void addGameCreationHandler() {

		// Do not show a popup.
		MultiplicationInverseGameState gameState = new MultiplicationInverseGameState();
		gameState.setGameName("Experiment");
		gameState.setNumberOfPlayers(1);
		gameState.setNumberOfMaxNPCs(1);
		gameState.setMaxRound(224);
		MultiplicationInverseGameSelector.this.coordinator.openGame(gameState);
	}
	
	@Override
	public void joinGame() {

		if (this.getSelectedGameId() < 0) return;

		if (NumberLineWeb.USERID == -1) {

			// Don't show the player's name popup.
			//TODO Fill in the name of the subject instead of the string "Player".
			// E.g. like that on the server side:
//			DrupalCommunicator dc = new DrupalCommunicator();
//			try {
//				player = dc.getUser(uid).getUname();
//			} catch (UserNotFoundException e) {
//				throw new GameJoinException("User with id =" + uid + " could not be found.");
//			}
			coordinator.joinGame(MultiplicationInverseGameSelector.this.getSelectedGameId(), "Player", getMaxNumberOfPlayers(),
					getMaxNumberOfNPCs());
			t.cancel();

		}else{

			coordinator.joinGame(MultiplicationInverseGameSelector.this.getSelectedGameId(), "___ID/" + NumberLineWeb.USERID,getMaxNumberOfPlayers(),
					getMaxNumberOfNPCs());
			t.cancel();

		}

//		gamePopUp.hide();

	
	}
	
}
