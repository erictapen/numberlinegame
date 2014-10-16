package com.wicam.numberlineweb.client.EventServiceTest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameTypeSelector;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;

/**
 * Coordinator for the EventService Test.
 * @author timfissler
 *
 */

public class EventServiceTestCoordinator {

	private static final Domain TEST_DOMAIN = DomainFactory.getDomain("testdomain");
	private Panel rootPanel;
	private GameTypeSelector gts;
	private int clientID;
	private EventServiceTestCommunicationServiceAsync commServ;
	private EventServiceTestView view;
	private EventServiceTestListener listener;
	private RemoteEventService eventService;
	
	/**
	 * Constructs a new math assessment coordinator.
	 * @param commServ
	 * @param root
	 * @param gts
	 */
	public EventServiceTestCoordinator(EventServiceTestCommunicationServiceAsync commServ, 
			Panel root, GameTypeSelector gts) {
		this.commServ = commServ;
		this.rootPanel = root;
		this.gts = gts;
		this.clientID = 0;
		this.view = null;
	}
	
	/**
	 * Initializes the coordinator.
	 */
	public void init() {

		RemoteEventServiceFactory theEventServiceFactory = RemoteEventServiceFactory.getInstance();
		eventService = theEventServiceFactory.getRemoteEventService();
        
		listener = new EventServiceTestListener(this);
		eventService.addListener(TEST_DOMAIN, listener);
		
		view = new EventServiceTestView(this);
		rootPanel.clear();
		rootPanel.add(view);
	}
	
	/**
	 * Update the counter in the view.
	 * @param counter
	 */
	public void updateCounter(int counter) {
		view.setCounter(Integer.toString(counter));
	}
	
	/**
	 * User pressed the start button, counter on server is started.
	 */
	public void startCounter() {
		commServ.startCounter(startCallback);
	}
	
	/**
	 * User pressed the stop button, counter is stopped and app is shut. 
	 */
	public void stopCounter() {
		commServ.stopCounter(clientID, stopCallback);
	}
	
	AsyncCallback<Integer> startCallback = new AsyncCallback<Integer>() {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log("Wasn't able to start the counter.");
			GWT.log(caught.getMessage());
			
		}

		@Override
		public void onSuccess(Integer result) {
			clientID = result;
		}
		
	};
	
	AsyncCallback<Void> stopCallback = new AsyncCallback<Void>() {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log("Wasn't able to stop the counter.");
			GWT.log(caught.getMessage());
			
		}

		@Override
		public void onSuccess(Void result) {
			
			// Cleanup.
			eventService.removeListener(TEST_DOMAIN, listener);
			rootPanel.clear();
			gts.init(rootPanel);
		}
		
	};
	
}
