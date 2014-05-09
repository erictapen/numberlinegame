package com.wicam.numberlineweb.client.Letris;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.Serializer;
import com.google.gwt.user.server.rpc.RPC;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameSelector;

public class LetrisGameSelector extends GameSelector {
	
	protected final LetrisGameCreatePopupBox gamePopUp = new LetrisGameCreatePopupBox("Neues Spiel erstellen", "Mein Spiel");
	
	public LetrisGameSelector(GameCoordinator coordinator) {
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
						
						LetrisGameCoordinator letrisCoordinator = (LetrisGameCoordinator) coordinator;
						LetrisGameState gameState = new LetrisGameState(letrisCoordinator,
								letrisCoordinator.getTargetWords(), LetrisGameCoordinator.STARTING_FOREIGN_LETTER_RATIO,
								LetrisGameCoordinator.STARTING_ROTATED_LETTER_RATIO,
								LetrisGameCoordinator.STARTING_TIME_PER_BLOCK);
						// TODO Delete that.
						GWT.log("Type name of class LetrisGameState: " + GWT.getTypeName(gameState));
						gameState.setGameName(gamePopUp.getTextValue());
						gameState.setNumberOfPlayers(gamePopUp.getPlayerCount());
						gameState.setNumberOfMaxNPCs(gamePopUp.getNPCsCount());
						coordinator.openGame(gameState);
						gamePopUp.hide();
					}

				});

				gamePopUp.show();

			}
		});		
	}

}
