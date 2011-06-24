package com.wicam.numberlineweb.client.MathDiagnostics;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameSelector;
import com.wicam.numberlineweb.client.GameState;

public class MathDiagnosticsSelector extends GameSelector {

	protected final CellList<GameState> cellList = new CellList<GameState>(new MathDiagnosticsGameCell(),keyProvider);
	protected final MathDiagnosticsCreatePopupBox gamePopUp = new MathDiagnosticsCreatePopupBox("Neue Spiel erstellen", "Mein Spiel");
	private int task;
	
	public MathDiagnosticsSelector(GameCoordinator coordinator) {
		super(coordinator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void addGameCreationHandler() {
		createGameButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				if (!gamePopUp.hasClickHandler()) gamePopUp.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						
						MathDiagnosticsGameState gameState = new MathDiagnosticsGameState();
						gameState.setGameName(gamePopUp.getTextValue());
						task = gamePopUp.getTask();
						gameState.setTask(task);
						gameState.setNumberOfPlayers(1);
						gameState.setNumberOfMaxNPCs(0);
						coordinator.openGame(gameState);
						gamePopUp.hide();
					}

				});

				gamePopUp.show();

			}
		});	

	}
	
	public int getTask() {
		return task;
	}

}
