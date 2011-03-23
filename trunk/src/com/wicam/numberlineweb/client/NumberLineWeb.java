package com.wicam.numberlineweb.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCoordinator;



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
		CommunicationServiceAsync commService = (CommunicationServiceAsync) GWT.create(CommunicationService.class);
		
		//our HTML file has a div with id = "game". this will be our mother panel, so
		// everything else can be styled with simple html / css.
		NumberLineGameCoordinator coordinator = new NumberLineGameCoordinator(commService,RootPanel.get("game"));
				
		coordinator.init();
		
	}
	
	
	


}
