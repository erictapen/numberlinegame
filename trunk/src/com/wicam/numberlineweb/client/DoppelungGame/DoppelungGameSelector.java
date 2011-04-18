package com.wicam.numberlineweb.client.DoppelungGame;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameSelector;

public class DoppelungGameSelector extends GameSelector {
	
	public DoppelungGameSelector(GameCoordinator coordinator) {
		super(coordinator);
		
	}

	@Override
	protected void addGameCreationHandler() {
		createGameButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				gamePopUp.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						gamePopUp.setEnabled(false);
						DoppelungGameState gameState = new DoppelungGameState();
						gameState.setGameName(gamePopUp.getTextValue());
						gameState.setNumberOfPlayers(gamePopUp.getPlayerCount());
						gameState.setNumberOfMaxNPCs(0);
						coordinator.openGame(gameState);
						gamePopUp.hide();
					}

				});

				gamePopUp.show();

			}
		});		
	}

}
