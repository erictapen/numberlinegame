package com.wicam.numberlineweb.client.NumberLineGame;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.wicam.numberlineweb.client.GameSelector;
import com.wicam.numberlineweb.client.GameState;



/**
 * A game browser, which can later be used for other games as well.
 * @author patrick
 *
 */

public class NumberLineGameSelector extends GameSelector {
	
	protected final CellList<GameState> cellList = new CellList<GameState>(new NumberLineGameCell(),keyProvider);
	protected final NumberLineGameCreatePopupBox gamePopUp = new NumberLineGameCreatePopupBox("Neues Spiel erstellen", "Mein Spiel");
	
	public NumberLineGameSelector(NumberLineGameCoordinator coordinator) {
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
						NumberLineGameState gameState = new NumberLineGameState();
						gameState.setGameName(gamePopUp.getTextValue());
						gameState.setNumberOfPlayers(gamePopUp.getPlayerCount());
						gameState.setNumberOfMaxNPCs(gamePopUp.getNPCsCount());
						gameState.setMaxItems(gamePopUp.getRoundCount());
						gameState.setNumberRange(gamePopUp.getNumberRange());
						coordinator.openGame(gameState);
						gamePopUp.hide();
					}

				});

				gamePopUp.show();

			}
		});
	}

}
