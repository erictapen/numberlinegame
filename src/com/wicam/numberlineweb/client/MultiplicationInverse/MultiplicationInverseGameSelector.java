package com.wicam.numberlineweb.client.MultiplicationInverse;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameCoordinator;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameSelector;

public class MultiplicationInverseGameSelector extends MultiplicationGameSelector {

	public MultiplicationInverseGameSelector(MultiplicationGameCoordinator coordinator) {
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

						GWT.log("click");

						MultiplicationInverseGameState gameState = new MultiplicationInverseGameState();
						gameState.setGameName(gamePopUp.getTextValue());
						gameState.setNumberOfPlayers(gamePopUp.getPlayerCount());
						gameState.setNumberOfMaxNPCs(gamePopUp.getNPCsCount());
						gameState.setMaxRound(gamePopUp.getRoundCount());
						MultiplicationInverseGameSelector.this.coordinator.openGame(gameState);
						gamePopUp.hide();
						

					}

				});

				gamePopUp.show();

			}
		});
	}
	
}
