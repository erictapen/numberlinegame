package com.wicam.numberlineweb.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCommunicationService;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCoordinator;
import com.wicam.numberlineweb.client.chat.ChatCommunicationService;
import com.wicam.numberlineweb.client.chat.ChatCommunicationServiceAsync;



/**
 * This class is an entry point, which means onModuleLoad is called after
 * the javascript source has been loaded. Entry point is specified in the *.gwt.xml-file
 * @author patrick
 *
 */
public class NumberLineWeb implements EntryPoint {


	/**
	 * Everything starts here...
	 */
	public void onModuleLoad() {
		
		
		//first, lets initialize our communication systems
		NumberLineGameCommunicationServiceAsync commService = (NumberLineGameCommunicationServiceAsync) GWT.create(NumberLineGameCommunicationService.class);
		ChatCommunicationServiceAsync chatCommService = (ChatCommunicationServiceAsync) GWT.create(ChatCommunicationService.class);
		
		//our HTML file has a div with id = "game". this will be our mother panel, so
		// everything else can be styled with simple html / css.
		NumberLineGameCoordinator coordinator = new NumberLineGameCoordinator(commService,chatCommService,RootPanel.get("game"));
		
		coordinator.init();
		
	}
	
	
	


}
