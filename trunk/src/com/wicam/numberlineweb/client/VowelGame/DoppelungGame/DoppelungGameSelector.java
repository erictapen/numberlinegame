package com.wicam.numberlineweb.client.VowelGame.DoppelungGame;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameSelector;

public class DoppelungGameSelector extends GameSelector {
	
	protected final DoppelungGameCreatePopupBox gamePopUp = new DoppelungGameCreatePopupBox("Neues Spiel erstellen", "Mein Spiel");
	
	public DoppelungGameSelector(GameCoordinator coordinator) {
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
						
						DoppelungGameState gameState = new DoppelungGameState();
						gameState.setGameName(gamePopUp.getTextValue());
						gameState.setNumberOfPlayers(gamePopUp.getPlayerCount());
						gameState.setNumberOfMaxNPCs(0);
						gameState.setNumberOfWordsToPlay(gamePopUp.getRoundCount());
						coordinator.openGame(gameState);
						gamePopUp.hide();
					}

				});

				gamePopUp.show();

			}
		});		
	}

}
