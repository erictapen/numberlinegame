package com.wicam.numberlineweb.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.wicam.numberlineweb.client.VowelGame.DehnungGame.DehnungGameCommunicationService;
import com.wicam.numberlineweb.client.VowelGame.DehnungGame.DehnungGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.VowelGame.DehnungGame.DehnungGameCoordinator;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameCoordinator;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameCommunicationService;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.WordStem.WordStemGameCommunicationService;
import com.wicam.numberlineweb.client.WordStem.WordStemGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.WordStem.WordStemGameCoordinator;
import com.wicam.numberlineweb.client.BuddyNumber.BuddyNumberGameCommunicationService;
import com.wicam.numberlineweb.client.BuddyNumber.BuddyNumberGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.BuddyNumber.BuddyNumberGameCoordinator;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsCommonicationService;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsCommonicationServiceAsync;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsCoordinator;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCommunicationService;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCoordinator;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameCommunicationService;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameCoordinator;
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
	
	public static int USERID = -1;

	/**
	 * Everything starts here...
	 */
	public void onModuleLoad() {
		
		if (Window.Location.getParameter("embedded") != null) {
			
			RootPanel.get("game").getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			RootPanel.get("unilogo").getElement().getStyle().setDisplay(Display.NONE);
			RootPanel.getBodyElement().addClassName("embedded");
			
			NumberLineWeb.USERID = Integer.parseInt(Window.Location.getParameter("uid"));
			
		}

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

		final AsyncCallback<Boolean> dummyCallback = new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(Boolean result) {


			}

		};
		
		final String logging = Window.Location.getParameter("logging");
		
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
		gts.addGame("Matheaufgaben", "pre_mathDiagnostics.png", "Hier könnte Ihre Beschreibung stehen.", new GameItemStarter() {

			@Override
			public void run() {

				commService = (MathDiagnosticsCommonicationServiceAsync) GWT.create(MathDiagnosticsCommonicationService.class);
				coordinator = new MathDiagnosticsCoordinator(commService,chatCommService,RootPanel.get("game"),gts);
				
				gts.hide(RootPanel.get("game"));
				coordinator.init();
			}
		});
		
		
		//adds the doppelung game
		gts.addGame("Dehnungspiel", "pre_dehnung.png", "Hier könnte Ihre Beschreibung stehen.", new GameItemStarter() {

			@Override
			public void run() {

				commService = (DehnungGameCommunicationServiceAsync) GWT.create(DehnungGameCommunicationService.class);
				coordinator = new DehnungGameCoordinator(commService,chatCommService,RootPanel.get("game"),gts);
				
				gts.hide(RootPanel.get("game"));
				coordinator.init();
			}
		});
		
		//adds the Multiplication game
		gts.addGame("Multiplikation", "pre_multiplication.png", "Rechne schnell!", new GameItemStarter() {

			@Override
			public void run() {
				
				GWT.log("gurr");

				commService = (MultiplicationGameCommunicationServiceAsync) GWT.create(MultiplicationGameCommunicationService.class);
				coordinator = new MultiplicationGameCoordinator((MultiplicationGameCommunicationServiceAsync) commService,chatCommService,RootPanel.get("game"),gts);

				gts.hide(RootPanel.get("game"));
				coordinator.init();
			}
		});
		
		
		
		//adds the BuddyNumber game
		gts.addGame("Partnerzahl", "pre_buddyNumber.png", "Kombiniere gut!", new GameItemStarter() {

			@Override
			public void run() {
				
				GWT.log("gurr");

				commService = (BuddyNumberGameCommunicationServiceAsync) GWT.create(BuddyNumberGameCommunicationService.class);
				coordinator = new BuddyNumberGameCoordinator((BuddyNumberGameCommunicationServiceAsync) commService,chatCommService,RootPanel.get("game"),gts);
				
				gts.hide(RootPanel.get("game"));
				coordinator.init();
			}
		});
		
		
		
		//adds the WordStem game
		gts.addGame("Wortstamm", "pre_buddyNumber.png", "Ordne zu!", new GameItemStarter() {

			@Override
			public void run() {
				
				GWT.log("gurr");

				commService = (WordStemGameCommunicationServiceAsync) GWT.create(WordStemGameCommunicationService.class);
				coordinator = new WordStemGameCoordinator((WordStemGameCommunicationServiceAsync) commService,chatCommService,RootPanel.get("game"),gts);
				
				gts.hide(RootPanel.get("game"));
				coordinator.init();
			}
		});

		
		
		
		//init the GTS on the root panel.
		gts.init(RootPanel.get("game"));

	}
	
	
	

}
