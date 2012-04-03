package com.wicam.numberlineweb.client.WordFamily;

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

public class WordFamilyGameSelector extends GameSelector {

	protected final CellList<GameState> cellList = new CellList<GameState>(new WordFamilyGameCell(),keyProvider);
	protected final WordFamilyGameCreatePopupBox gamePopUp = new WordFamilyGameCreatePopupBox("Neues Spiel erstellen", "Mein Spiel");

	public WordFamilyGameSelector(WordFamilyGameCoordinator coordinator) {
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

						WordFamilyGameState gameState = new WordFamilyGameState();
						gameState.setGameName(gamePopUp.getTextValue());
						gameState.setNumberOfPlayers(1);
						gameState.setNumberOfMaxNPCs(0);
						gameState.setHard(gamePopUp.getDiffucult());
						gameState.setMaxRound(gamePopUp.getRoundCount());
						WordFamilyGameSelector.this.coordinator.openGame(gameState);
						gamePopUp.hide();
						

					}

				});

				gamePopUp.show();

			}
		});
	}

}
