package com.wicam.numberlineweb.client.MultiplicationInverse;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.wicam.numberlineweb.client.AbstractGameSelector;
import com.wicam.numberlineweb.client.GameCell;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.GameCreatePopupBox;
import com.wicam.numberlineweb.client.GameJoinException;
import com.wicam.numberlineweb.client.GameSelector;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.HistoryChangeHandler;
import com.wicam.numberlineweb.client.NumberLineWeb;
import com.wicam.numberlineweb.client.TextPopupBox;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameSelector;
import com.wicam.numberlineweb.server.database.drupal.DrupalCommunicator;
import com.wicam.numberlineweb.server.database.drupal.UserNotFoundException;

public class MultiplicationInverseGameSelector extends AbstractGameSelector {
	
	public MultiplicationInverseGameSelector(MultiplicationInverseGameCoordinator coordinator) {

		this.coordinator = coordinator;

		init();
		sinkEvents(Event.MOUSEEVENTS);
		this.initWidget(motherPanel);

	}


	@SuppressWarnings("deprecation")
	protected void init() {

		RootPanel.get().add(motherPanel);
		
		HistoryChangeHandler.setHistoryListener(new HistoryListener() {

			@Override
			public void onHistoryChanged(String historyToken) {

				if (historyToken.equals("")) {
					// TODO Delete that.
					System.out.println("get GTS");
					coordinator.getRootPanel().clear();
					coordinator.getGTS().init(coordinator.getRootPanel());

				}
			}
		});

		// Don't step back to the game selector but to the overview instead.
//		History.newItem("gameSelector-" + coordinator.getGameName(),false);
		
		// Don't build the page.
		/*

		joinGameButton = new Button("Mitspielen");


		joinGameButton.setEnabled(false);

		PushButton homeButton = new PushButton("");
		homeButton.addStyleName("homebutton");

		createGameButton = new Button("Neues Spiel");

		cellList.setSelectionModel(selectionModel);
		cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler(){

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				// TODO Auto-generated method stub
				if (selectionModel.getSelectedObject() != null) {

					joinGameButton.setEnabled(true);

				}else{
					joinGameButton.setEnabled(false);
				}
			}
		});

		ScrollPanel s = new ScrollPanel(cellList);

		s.setWidth("400px");
		s.setHeight("300px");

		joinGameButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				joinGame();

			}
		});

		homeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				History.back();
			}
		});
		
		*/
		
		addGameCreationHandler();

		/*
		motherPanel.setWidth("600px");
		motherPanel.setHeight("400px");
		motherPanel.add(s);

		HTML title = new HTML();

		title.setHTML("<div class='selectorTitle'>" + coordinator.getGameName() + " - Offene Spiele");

		motherPanel.setWidgetPosition(s, 25, 70);

		motherPanel.add(title);
		motherPanel.setWidgetPosition(title, 15, 20);


		createGameButton.setHeight("30px");
		createGameButton.setWidth("220px");


		joinGameButton.setHeight("30px");
		joinGameButton.setWidth("220px");


		motherPanel.add(createGameButton);

		motherPanel.add(joinGameButton);
		motherPanel.add(homeButton);


		motherPanel.setWidgetPosition(joinGameButton, boxWidth-250, 30);
		//motherPanel.setWidgetPosition(refreshButton, 15, 340);

		motherPanel.setWidgetPosition(createGameButton, boxWidth-250, 60);
		*/

		clearGameList();

		t = new Timer() {

			public void run() {

				coordinator.refreshGameList();
			}
		};

		//main loop-timer
		t.scheduleRepeating(2000);

	}
	
	/**
	 * Set the properties of this game.
	 */
	protected void addGameCreationHandler() {
		// Do not show a popup.
		MultiplicationInverseGameState gameState = new MultiplicationInverseGameState();
		gameState.setGameName("Experiment");
		gameState.setNumberOfPlayers(1);
		gameState.setNumberOfMaxNPCs(1);
		// Set the number of rounds per subject.
		gameState.setMaxRound(2);
		MultiplicationInverseGameSelector.this.coordinator.openGame(gameState);
	}
	
	public void joinGame() {

		if (this.getSelectedGameId() < 0) return;

		if (NumberLineWeb.USERID == -1) {

			// Don't show the player's name popup.
			//TODO Fill in the name of the subject instead of the string "Player".
			// E.g. like that on the server side:
//			DrupalCommunicator dc = new DrupalCommunicator();
//			try {
//				player = dc.getUser(uid).getUname();
//			} catch (UserNotFoundException e) {
//				throw new GameJoinException("User with id =" + uid + " could not be found.");
//			}
			coordinator.joinGame(MultiplicationInverseGameSelector.this.getSelectedGameId(), "Player", getMaxNumberOfPlayers(),
					getMaxNumberOfNPCs());
			t.cancel();
			
		} else {
			coordinator.joinGame(MultiplicationInverseGameSelector.this.getSelectedGameId(), "___ID/" + NumberLineWeb.USERID,getMaxNumberOfPlayers(),
					getMaxNumberOfNPCs());
			t.cancel();
		}
//		gamePopUp.hide();	
	}
		
}
