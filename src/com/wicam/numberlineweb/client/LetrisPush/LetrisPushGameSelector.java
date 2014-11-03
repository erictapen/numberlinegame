package com.wicam.numberlineweb.client.LetrisPush;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameSelector;

public class LetrisPushGameSelector extends GameSelector {
	
	protected final LetrisPushGameCreatePopupBox gamePopUp = new LetrisPushGameCreatePopupBox("Neues Spiel erstellen", "Mein Spiel");
	
	public LetrisPushGameSelector(GameCoordinator coordinator) {
		super(coordinator);
		
	}

	@Override
	protected void addGameCreationHandler() {
		createGameButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				if (!gamePopUp.hasClickHandler()) gamePopUp.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						
						LetrisPushGameCoordinator letrisCoordinator = (LetrisPushGameCoordinator) coordinator;
						LetrisPushGameState gameState = new LetrisPushGameState();
						// Set up the game state.
						gameState.setGameName(gamePopUp.getTextValue());
						gameState.setNumberOfPlayers(gamePopUp.getPlayerCount());
						gameState.setNumberOfMaxNPCs(gamePopUp.getNPCsCount());
						letrisCoordinator.getGameModel().setupGameState(gameState);
						coordinator.openGame(gameState);
						gamePopUp.hide();
					}

				});

				gamePopUp.show();

			}
		});		
	}

}
