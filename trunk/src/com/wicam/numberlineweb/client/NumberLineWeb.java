package com.wicam.numberlineweb.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameCommunicationService;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameCoordinator;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCommunicationService;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCoordinator;
import com.wicam.numberlineweb.client.chat.ChatCommunicationService;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;
import com.wicam.numberlineweb.client.factsGame.FactsGameCommunicationService;
import com.wicam.numberlineweb.client.factsGame.FactsGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.factsGame.FactsGameCoordinator;



/**
 * This class is an entry point, which means onModuleLoad is called after
 * the javascript source has been loaded. The entry point is specified in the *.gwt.xml-file
 * @author patrick
 *
 */
public class NumberLineWeb implements EntryPoint {


	protected GameCommunicationServiceAsync commService;
	protected ChatCommunicationServiceAsync chatCommService = (ChatCommunicationServiceAsync) GWT.create(ChatCommunicationService.class);
	protected GameCoordinator coordinator;
	protected GameTypeSelector gts = new GameTypeSelector();

	/**
	 * Everything starts here...
	 */
	public void onModuleLoad() {


		showGameTypeSelector();
		
		

	}

	
	/**
	 * Shows and initializes the game type selector. A specific click-handler
	 * must be given to every item the selector should hold.
	 * TODO: There should be an extra pre-coordinator to do this
	 */

	public void showGameTypeSelector() {
		//boolean numberLineGame = false;

		//adds the numberlinegame
		gts.addGame("NumberLineGame", "nlg_pre.png", "Schätze die Position der Zahl!", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				commService = (NumberLineGameCommunicationServiceAsync) GWT.create(NumberLineGameCommunicationService.class);
				coordinator = new NumberLineGameCoordinator((NumberLineGameCommunicationServiceAsync) commService,chatCommService,RootPanel.get("game"),gts);

				gts.hide(RootPanel.get("game"));
				coordinator.init();
			}
		});

		//adds the doppelung game
		gts.addGame("Doppelungspiel", "pre_doppelung.png", "Hier könnte Ihre Beschreibung stehen.", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				commService = (DoppelungGameCommunicationServiceAsync) GWT.create(DoppelungGameCommunicationService.class);
				coordinator = new DoppelungGameCoordinator(commService,chatCommService,RootPanel.get("game"),gts);

				gts.hide(RootPanel.get("game"));
				coordinator.init();
			}
		});
		
		//init the GTS on the root panel.
		gts.init(RootPanel.get("game"));

	}

}
