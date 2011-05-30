package com.wicam.numberlineweb.client.factsGame;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameSelector;

public class FactsGameSelector extends GameSelector {

	public FactsGameSelector(GameCoordinator coordinator) {
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
						
						FactsGameState gameState = new FactsGameState();
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
