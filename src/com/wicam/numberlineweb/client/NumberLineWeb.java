package com.wicam.numberlineweb.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.wicam.numberlineweb.client.EventServiceTest.EventServiceTestCommunicationService;
import com.wicam.numberlineweb.client.EventServiceTest.EventServiceTestCommunicationServiceAsync;
import com.wicam.numberlineweb.client.EventServiceTest.EventServiceTestCoordinator;
import com.wicam.numberlineweb.client.GameTypeSelector.GameType;
import com.wicam.numberlineweb.client.Resources.ImageResources;
import com.wicam.numberlineweb.client.SpellingAssessment.SpellingAssessmentCommunicationService;
import com.wicam.numberlineweb.client.SpellingAssessment.SpellingAssessmentCommunicationServiceAsync;
import com.wicam.numberlineweb.client.SpellingAssessment.SpellingAssessmentCoordinator;
import com.wicam.numberlineweb.client.VowelGame.DehnungGame.DehnungGameCommunicationService;
import com.wicam.numberlineweb.client.VowelGame.DehnungGame.DehnungGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.VowelGame.DehnungGame.DehnungGameCoordinator;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameCommunicationService;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameCoordinator;
import com.wicam.numberlineweb.client.WordFamily.WordFamilyGameCommunicationService;
import com.wicam.numberlineweb.client.WordFamily.WordFamilyGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.WordFamily.WordFamilyGameCoordinator;
import com.wicam.numberlineweb.client.WordStem.WordStemGameCommunicationService;
import com.wicam.numberlineweb.client.WordStem.WordStemGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.WordStem.WordStemGameCoordinator;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCommunicationService;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCoordinator;
import com.wicam.numberlineweb.client.OverTen.OverTenGameCommunicationService;
import com.wicam.numberlineweb.client.OverTen.OverTenGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.OverTen.OverTenGameCoordinator;
import com.wicam.numberlineweb.client.BuddyNumber.BuddyNumberGameCommunicationService;
import com.wicam.numberlineweb.client.BuddyNumber.BuddyNumberGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.BuddyNumber.BuddyNumberGameCoordinator;
import com.wicam.numberlineweb.client.Letris.LetrisGameCommunicationService;
import com.wicam.numberlineweb.client.Letris.LetrisGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.Letris.LetrisGameCoordinator;
import com.wicam.numberlineweb.client.LetrisPush.LetrisPushGameCommunicationService;
import com.wicam.numberlineweb.client.LetrisPush.LetrisPushGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.LetrisPush.LetrisPushGameCoordinator;
import com.wicam.numberlineweb.client.MathAssessment.MathAssessmentCommunicationService;
import com.wicam.numberlineweb.client.MathAssessment.MathAssessmentCommunicationServiceAsync;
import com.wicam.numberlineweb.client.MathAssessment.MathAssessmentCoordinator;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsCommonicationService;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsCommonicationServiceAsync;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsCoordinator;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameCommunicationService;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameCoordinator;
import com.wicam.numberlineweb.client.MultiplicationInverse.MultiplicationInverseGameCommunicationService;
import com.wicam.numberlineweb.client.MultiplicationInverse.MultiplicationInverseGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.MultiplicationInverse.MultiplicationInverseGameCoordinator;
import com.wicam.numberlineweb.client.chat.ChatCommunicationService;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;


/**
 * This class is an entry point, which means onModuleLoad is called after
 * the javascript source has been loaded. The entry point is specified in the *.gwt.xml-file
 * @author patrick
 *
 */
@SuppressWarnings("unused")
public class NumberLineWeb implements EntryPoint {


	protected GameCommunicationServiceAsync commService;
	protected ChatCommunicationServiceAsync chatCommService = (ChatCommunicationServiceAsync) GWT.create(ChatCommunicationService.class);
	protected GameCoordinator coordinator;
	protected GameTypeSelector gts = new GameTypeSelector();
	
	protected MathAssessmentCommunicationServiceAsync mathAssessmentCommService;
	protected MathAssessmentCoordinator mathAssessmentCoordinator;
	

	protected SpellingAssessmentCommunicationServiceAsync spellingAssessmentCommService;
	protected SpellingAssessmentCoordinator spellingAssessmentCoordinator;
	
	protected EventServiceTestCommunicationServiceAsync eventServiceCommServ;
	protected EventServiceTestCoordinator eventServiceCoord;
	

	// TODO Switch that back to -1 before going live in server.
	// Just for locale testing set the USERID to 2.
//	public static int USERID = -1;
	public static int USERID = 2;
	
	/**
	 * Everything starts here...
	 */
	@Override
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
		
		History.fireCurrentHistoryState();

	}

	
	/**
	 * Shows and initializes the game type selector. A specific click-handler
	 * must be given to every item the selector should hold.
	 * TODO: There should be an extra pre-coordinator to do this
	 */

	public void showGameTypeSelector() {
		
		// add math games container
		gts.addGame(GameType.CAT, "Mathespiele", ImageResources.INSTANCE.pre_multiplication().getSafeUri().asString(), 
				"Alle Spiele mit Zahlen", new GameItemStarter() {
			
			@Override
			public void run() {
				gts.showMath();
			}
		});
		
		// add grammar games container
//		gts.addGame(GameType.CAT, "Wortspiele", ImageResources.INSTANCE.pre_doppelung().getSafeUri().asString(), 
//				"Alle Spiele mit Wörtern", new GameItemStarter() {
//			
//			@Override
//			public void run() {
//				gts.showGrammar();
//			}
//		});
		
		// Adds the EventServiceTest app.
//		gts.addGame(GameType.CAT, "Server Push Test", "pre_mathDiagnostics.png", 
//				"", new GameItemStarter() {
//
//			@Override
//			public void run() {
//
//				eventServiceCommServ = (EventServiceTestCommunicationServiceAsync) GWT.create(EventServiceTestCommunicationService.class);
//				eventServiceCoord = new EventServiceTestCoordinator(eventServiceCommServ, RootPanel.get("game"), gts);
//
//				gts.hide(RootPanel.get("game"));
//				eventServiceCoord.init();
//			}
//		});
		
		//adds the numberlinegame
//		gts.addGame(GameType.MATH, "NumberLineGame", "nlg_pre.png", "Schätze die Position der Zahl!", new GameItemStarter() {
//
//			@Override
//			public void run() {
//
//				commService = (NumberLineGameCommunicationServiceAsync) GWT.create(NumberLineGameCommunicationService.class);
//				coordinator = new NumberLineGameCoordinator((NumberLineGameCommunicationServiceAsync) commService,chatCommService,RootPanel.get("game"),gts);
//				
//				gts.hide(RootPanel.get("game"));
//				coordinator.init();
//			}
//		});

		//adds the doppelung game
//		gts.addGame(GameType.GRAMMAR, "Doppelungspiel", ImageResources.INSTANCE.pre_doppelung().getSafeUri().asString(), 
//				"Hier könnte Ihre Beschreibung stehen.", new GameItemStarter() {
//
//			@Override
//			public void run() {
//
//				commService = (DoppelungGameCommunicationServiceAsync) GWT.create(DoppelungGameCommunicationService.class);
//				coordinator = new DoppelungGameCoordinator(commService,chatCommService,RootPanel.get("game"),gts);
//				
//				gts.hide(RootPanel.get("game"));
//				coordinator.init();
//			}
//		});
		
		//adds the Letris game
//		gts.addGame(GameType.GRAMMAR, "LeTris", ImageResources.INSTANCE.pre_letris().getSafeUri().asString(), 
//				"Hier könnte Ihre Beschreibung stehen.", new GameItemStarter() {
//
//			@Override
//			public void run() {
//
//				commService = (LetrisPushGameCommunicationServiceAsync) GWT.create(LetrisPushGameCommunicationService.class);
//				coordinator = new LetrisPushGameCoordinator(commService,chatCommService,RootPanel.get("game"),gts);
//
//				gts.hide(RootPanel.get("game"));
//				coordinator.init();
//			}
//		});
		
		
		//adds the mathe game
//		gts.addGame(GameType.MATH, "Matheaufgaben", "pre_mathDiagnostics.png", "Hier könnte Ihre Beschreibung stehen.", new GameItemStarter() {
//
//			@Override
//			public void run() {
//
//				commService = (MathDiagnosticsCommonicationServiceAsync) GWT.create(MathDiagnosticsCommonicationService.class);
//				coordinator = new MathDiagnosticsCoordinator(commService,chatCommService,RootPanel.get("game"),gts);
//				
//				gts.hide(RootPanel.get("game"));
//				coordinator.init();
//			}
//		});
		
		/*
		 * Adds the spelling assessment.
		 */
//		gts.addGame(GameType.GRAMMAR, "SpellingAssessment", ImageResources.INSTANCE.pre_letris().getSafeUri().asString(), 
//				"Hier könnte Ihre Beschreibung stehen.", new GameItemStarter() {
//
//			@Override
//			public void run() {
//				
//				// TODO Logo adjustment. 
//				spellingAssessmentCommService = (SpellingAssessmentCommunicationServiceAsync) GWT.create(SpellingAssessmentCommunicationService.class);
//				spellingAssessmentCoordinator = new SpellingAssessmentCoordinator(spellingAssessmentCommService,RootPanel.get("game"),gts);
//
//				gts.hide(RootPanel.get("game"));
//				spellingAssessmentCoordinator.init();
//			}
//		});
		
		
		//adds the dehnung game
		// TODO This game seems not to be working correctly.
//		gts.addGame(GameType.GRAMMAR, "Dehnungspiel", "pre_dehnung.png", "Hier könnte Ihre Beschreibung stehen.", new GameItemStarter() {
//
//			@Override
//			public void run() {
//
//				commService = (DehnungGameCommunicationServiceAsync) GWT.create(DehnungGameCommunicationService.class);
//				coordinator = new DehnungGameCoordinator(commService,chatCommService,RootPanel.get("game"),gts);
//				
//				gts.hide(RootPanel.get("game"));
//				coordinator.init();
//			}
//		});
		
//		//adds the Multiplication game
//		gts.addGame(GameType.MATH, "Multiplikation", ImageResources.INSTANCE.pre_multiplication().getSafeUri().asString(), 
//				"Rechne schnell!", new GameItemStarter() {
//
//			@Override
//			public void run() {
//				
//				GWT.log("gurr");
//
//				commService = (MultiplicationGameCommunicationServiceAsync) GWT.create(MultiplicationGameCommunicationService.class);
//				coordinator = new MultiplicationGameCoordinator((MultiplicationGameCommunicationServiceAsync) commService,chatCommService,RootPanel.get("game"),gts);
//
//				gts.hide(RootPanel.get("game"));
//				coordinator.init();
//			}
//		});
		
		// Adds the MathAssessment.
//		gts.addGame(GameType.MATH, "Mathe-Test", "pre_mathDiagnostics.png", 
//				"Rechne schnell!", new GameItemStarter() {
//
//			@Override
//			public void run() {
//				
//				mathAssessmentCommService = (MathAssessmentCommunicationServiceAsync) GWT.create(MathAssessmentCommunicationService.class);
//				mathAssessmentCoordinator = new MathAssessmentCoordinator(mathAssessmentCommService, RootPanel.get("game"), gts);
//
//
//				gts.hide(RootPanel.get("game"));
//				mathAssessmentCoordinator.init();
//			}
//		});
		
		//adds the inverse Multiplication game
		gts.addGame(GameType.MATH, "MultiplikationInverse", ImageResources.INSTANCE.pre_multiplication().getSafeUri().asString(), 
				"Rechne schnell!", new GameItemStarter() {

			@Override
			public void run() {
				
				GWT.log("gurr");

				commService = (MultiplicationInverseGameCommunicationServiceAsync) GWT.create(MultiplicationInverseGameCommunicationService.class);
				coordinator = new MultiplicationInverseGameCoordinator(commService,chatCommService,RootPanel.get("game"),gts);

				gts.hide(RootPanel.get("game"));
				coordinator.init();
			}
		});		
		
		
		//adds the BuddyNumber game
//		gts.addGame(GameType.MATH, "Partnerzahl", ImageResources.INSTANCE.pre_buddyNumber().getSafeUri().asString(), 
//				"Kombiniere gut!", new GameItemStarter() {
//
//			@Override
//			public void run() {
//				
//				GWT.log("gurr");
//
//				commService = (BuddyNumberGameCommunicationServiceAsync) GWT.create(BuddyNumberGameCommunicationService.class);
//				coordinator = new BuddyNumberGameCoordinator((BuddyNumberGameCommunicationServiceAsync) commService,chatCommService,RootPanel.get("game"),gts);
//				
//				gts.hide(RootPanel.get("game"));
//				coordinator.init();
//			}
//		});
		
		
		
		//adds the WordStem game
//		gts.addGame(GameType.GRAMMAR, "Wortbausteine", ImageResources.INSTANCE.pre_wordstem().getSafeUri().asString(), 
//				"Ordne zu!", new GameItemStarter() {
//
//			@Override
//			public void run() {
//				
//				GWT.log("gurr");
//
//				commService = (WordStemGameCommunicationServiceAsync) GWT.create(WordStemGameCommunicationService.class);
//				coordinator = new WordStemGameCoordinator((WordStemGameCommunicationServiceAsync) commService,chatCommService,RootPanel.get("game"),gts);
//				
//				gts.hide(RootPanel.get("game"));
//				coordinator.init();
//			}
//		});
		
		
		
		//adds the OverTen game
//		gts.addGame(GameType.MATH, "Über 10", ImageResources.INSTANCE.pre_overten().getSafeUri().asString(), 
//				"Summiere auf!", new GameItemStarter() {
//
//			@Override
//			public void run() {
//				
//				GWT.log("gurr");
//
//				commService = (OverTenGameCommunicationServiceAsync) GWT.create(OverTenGameCommunicationService.class);
//				coordinator = new OverTenGameCoordinator((OverTenGameCommunicationServiceAsync) commService,chatCommService,RootPanel.get("game"),gts);
//				
//				gts.hide(RootPanel.get("game"));
//				coordinator.init();
//			}
//		});
		
		
		//adds the WordFamily game
//		gts.addGame(GameType.GRAMMAR, "Wortfamilien", ImageResources.INSTANCE.pre_wordfamily().getSafeUri().asString(), 
//				"Merke dir viel!", new GameItemStarter() {
//
//			@Override
//			public void run() {
//				
//				GWT.log("gurr");
//
//				commService = (WordFamilyGameCommunicationServiceAsync) GWT.create(WordFamilyGameCommunicationService.class);
//				coordinator = new WordFamilyGameCoordinator((WordFamilyGameCommunicationServiceAsync) commService,chatCommService,RootPanel.get("game"),gts);
//				
//				gts.hide(RootPanel.get("game"));
//				coordinator.init();
//			}
//		});
		
		//init the GTS on the root panel.
		gts.init(RootPanel.get("game"));

	}

}
