package com.wicam.numberlineweb.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameCommunicationService;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameCoordinator;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsCommonicationService;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsCommonicationServiceAsync;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsCoordinator;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCommunicationService;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCoordinator;
import com.wicam.numberlineweb.client.chat.ChatCommunicationService;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;


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


		if (MobileDeviceChecker.checkMobile()) {
			
			RootPanel.getBodyElement().addClassName("mobile");
			RootPanel.getBodyElement().getStyle().setBackgroundColor("#ffffff");
			
		}
		
		RootPanel.getBodyElement().removeClassName("hidden");
		RootPanel.getBodyElement().addClassName("visible");
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
		gts.addGame("NumberLineGame", "nlg_pre.png", "Schätze die Position der Zahl!", new GameItemStarter() {

			@Override
			public void run() {
				
				GWT.log("gurr");

				commService = (NumberLineGameCommunicationServiceAsync) GWT.create(NumberLineGameCommunicationService.class);
				coordinator = new NumberLineGameCoordinator((NumberLineGameCommunicationServiceAsync) commService,chatCommService,RootPanel.get("game"),gts);

				gts.hide(RootPanel.get("game"));
				coordinator.init();
			}
		});

		//adds the doppelung game
		gts.addGame("Doppelungspiel", "pre_doppelung.png", "Hier könnte Ihre Beschreibung stehen.", new GameItemStarter() {

			@Override
			public void run() {

				commService = (DoppelungGameCommunicationServiceAsync) GWT.create(DoppelungGameCommunicationService.class);
				coordinator = new DoppelungGameCoordinator(commService,chatCommService,RootPanel.get("game"),gts);

				gts.hide(RootPanel.get("game"));
				coordinator.init();
			}
		});
		
		
		//adds the mathe game
		gts.addGame("Matheaufgaben", "Fragezeichen.gif", "Hier könnte Ihre Beschreibung stehen.", new GameItemStarter() {

			@Override
			public void run() {

				commService = (MathDiagnosticsCommonicationServiceAsync) GWT.create(MathDiagnosticsCommonicationService.class);
				coordinator = new MathDiagnosticsCoordinator(commService,chatCommService,RootPanel.get("game"),gts);

				gts.hide(RootPanel.get("game"));
				coordinator.init();
			}
		});
		
		
		//init the GTS on the root panel.
		gts.init(RootPanel.get("game"));
		

	}

}
