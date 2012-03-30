package com.wicam.numberlineweb.client.OverTen;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.wicam.numberlineweb.client.GameSelector;
import com.wicam.numberlineweb.client.GameState;



/**
 * A game browser, which can later be used for other games as well.
 * 
 *
 */

public class OverTenGameSelector extends GameSelector {

	protected final CellList<GameState> cellList = new CellList<GameState>(new OverTenGameCell(),keyProvider);
	protected final OverTenGameCreatePopupBox gamePopUp = new OverTenGameCreatePopupBox("Neues Spiel erstellen", "Mein Spiel");

	public OverTenGameSelector(OverTenGameCoordinator coordinator) {
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

						OverTenGameState gameState = new OverTenGameState();
						gameState.setGameName(gamePopUp.getTextValue());
						gameState.setNumberOfPlayers(gamePopUp.getPlayerCount());
						gameState.setNumberOfMaxNPCs(gamePopUp.getNPCsCount());
						gameState.setMaxRound(gamePopUp.getRoundCount());
						OverTenGameSelector.this.coordinator.openGame(gameState);
						gamePopUp.hide();
						

					}

				});

				gamePopUp.show();

			}
		});
	}

}
