package com.wicam.numberlineweb.client.EventServiceTest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameTypeSelector;

import de.novanic.eventservice.client.ClientHandler;
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

	private static final Domain GLOBAL_DOMAIN = DomainFactory.getDomain("globaldomain");
	private Panel rootPanel;
	private GameTypeSelector gts;
	private String clientID;
	private EventServiceTestCommunicationServiceAsync commServ;
	private EventServiceTestView view;
	private EventServiceTestCounterListener counterListener;
	private EventServiceTestFontColorListener fontColorListener;
	private RemoteEventService eventService;
	private ClientHandler clientHandler;
	
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
		this.clientID = "";
		this.view = null;
		this.counterListener = null;
		this.fontColorListener = null;
		this.eventService = null;
		this.clientHandler = null;
	}
	
	/**
	 * Initializes the coordinator.
	 */
	public void init() {

		RemoteEventServiceFactory theEventServiceFactory = RemoteEventServiceFactory.getInstance();
		eventService = theEventServiceFactory.getRemoteEventService();
        
		// Setup the listeners.
		counterListener = new EventServiceTestCounterListener(this);
		fontColorListener = new EventServiceTestFontColorListener(this);
		
		// Get the client handler with the connection id for user specific events.
		theEventServiceFactory.requestClientHandler(clientHandlerCallback);
	}
	
	/**
	 * Update the counter in the view.
	 * @param counter
	 */
	public void updateCounter(int counter) {
		view.setCounter(Integer.toString(counter));
	}
	
	/**
	 * Update the font color in the view.
	 * @param fontColor
	 */
	public void updateFontColor(String fontColor) {
		view.setFontColor(fontColor);
	}
	
	/**
	 * User pressed the start button, counter on server is started.
	 */
	public void startCounter() {
		commServ.startCounter(clientID, startCallback);
	}
	
	/**
	 * User pressed the stop button, counter is stopped and app is shut. 
	 */
	public void stopCounter() {
		commServ.stopCounter(clientID, stopCallback);
	}
	
	AsyncCallback<ClientHandler> clientHandlerCallback = new AsyncCallback<ClientHandler>() {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log("Failed to request client handler.");
			GWT.log(caught.getMessage());
		}

		@Override
		public void onSuccess(ClientHandler result) {
			clientHandler = result;
			clientID = clientHandler.getConnectionId();
			
			// Register the client with the retrieved client id from the client handler.
			commServ.registerClient(clientID, registerCallback);
		}
	};
	
	AsyncCallback<Void> registerCallback = new AsyncCallback<Void>() {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log("Wasn't able to register client.");
			GWT.log(caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			
			// Register event listener for user specific events.
			eventService.addListener(DomainFactory.USER_SPECIFIC_DOMAIN, counterListener);
			// Register domain specific event listener for global domain.
			eventService.addListener(GLOBAL_DOMAIN, fontColorListener);
			
			// Show view.
			view = new EventServiceTestView(EventServiceTestCoordinator.this);
			rootPanel.clear();
			rootPanel.add(view);
		}
		
	};
	
	AsyncCallback<Void> startCallback = new AsyncCallback<Void>() {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log("Wasn't able to start the counter.");
			GWT.log(caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {	
			// Do nothing.
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
			eventService.removeListener(DomainFactory.USER_SPECIFIC_DOMAIN, counterListener);
			eventService.removeListener(GLOBAL_DOMAIN, fontColorListener);
			rootPanel.clear();
			gts.init(rootPanel);
		}
		
	};
	
}
