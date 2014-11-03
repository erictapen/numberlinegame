package com.wicam.numberlineweb.server.EventServiceTest;

import java.util.HashMap;
import java.util.Timer;
import com.wicam.numberlineweb.client.EventServiceTest.EventServiceTestCounterEvent;
import com.wicam.numberlineweb.client.EventServiceTest.EventServiceTestCommunicationService;
import com.wicam.numberlineweb.client.EventServiceTest.EventServiceTestFontColorEvent;
import com.wicam.numberlineweb.server.CustomRemoteServiceServlet;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;
import de.novanic.eventservice.service.EventExecutorService;
import de.novanic.eventservice.service.EventExecutorServiceFactory;

/**
 * The EventService Test servlet that holds all the user specific and the global EventExecutorServices. 
 * @author timfissler
 *
 */

public class EventServiceTestCommunicationServiceServlet extends CustomRemoteServiceServlet implements
		EventServiceTestCommunicationService {

	private static final long serialVersionUID = 129270086406637782L;
	private static final Domain GLOBAL_DOMAIN = DomainFactory.getDomain("globaldomain");
	private String internalName = "event_service_test";
	private HashMap<String, Integer> clientID2Counter = new HashMap<String, Integer>();
	private HashMap<String, Timer> clientID2Timer = new HashMap<String, Timer>();
	private HashMap<String, EventExecutorService> clientID2EventExecutorService = new HashMap<String, EventExecutorService>();
	private EventExecutorService eventExecutorServiceGlobal;
	private String fontColor = "black";
	private Timer fontColorTimer;

	/**
	 * Create a new servlet and start the font color timer.
	 */
	public EventServiceTestCommunicationServiceServlet() {
		
		// Initialize global event executor service.
		EventExecutorServiceFactory serviceFactory = EventExecutorServiceFactory.getInstance();
		eventExecutorServiceGlobal = serviceFactory.getEventExecutorService("");
		
		// Change color every 5 seconds.
		fontColorTimer = new Timer();
		fontColorTimer.scheduleAtFixedRate(new EventServiceTestFontColorChangerTask(this), 5000, 5000);
	}
	
	/**
	 * Switches the font color of all counters and sends a global font color event.
	 */
	public void switchFontColor() {
		// Switch color.
		if (fontColor.equals("black")) {
			fontColor = "red";
		} else if (fontColor.equals("red")) {
			fontColor = "black";
		}
		
		// Send global event.
		EventServiceTestFontColorEvent fontEvent = new EventServiceTestFontColorEvent();
		fontEvent.setFontColor(fontColor);
		eventExecutorServiceGlobal.addEvent(GLOBAL_DOMAIN, fontEvent);
	}
	
	/**
	 * Retrieve the client ID and store it, create a new timer, store it.
	 * @return
	 */
	public void registerClient(String clientID) {
		
		// Add counter for client.
		clientID2Counter.put(clientID, 0);
		
		System.out.println("Registering client with ID: " + clientID);

		// Add event executor service for client.
		EventExecutorServiceFactory serviceFactory = EventExecutorServiceFactory.getInstance();
		// Use client id for initialization of the event executor service.
		clientID2EventExecutorService.put(clientID, serviceFactory.getEventExecutorService(clientID));
	}

	/**
	 *  Start the client counter.
	 */
	@Override
	public void startCounter(String clientID) {
		
		System.out.println("Starting counter for client ID: " + clientID);
		
		// Send event to client.
		EventServiceTestCounterEvent event = new EventServiceTestCounterEvent();
		event.setCounter(0);
		clientID2EventExecutorService.get(clientID).addEventUserSpecific(event);
		
		// Add timer for client and start it.
		Timer timer = new Timer();
		EventServiceTestCounterTask task = new EventServiceTestCounterTask(clientID, this);
		timer.scheduleAtFixedRate(task, 1000, 1000);
		clientID2Timer.put(clientID, timer);
		
	}
	
	/**
	 * Increase the appropriate counter and send a message containing the counter.
	 * @param clientID
	 */
	public void count(String clientID) {
		
		int counter = clientID2Counter.get(clientID) + 1;
		EventServiceTestCounterEvent event = new EventServiceTestCounterEvent();
		event.setCounter(counter);
		
		clientID2EventExecutorService.get(clientID).addEventUserSpecific(event);
		clientID2Counter.put(clientID, counter);
		
		System.out.println("Counting for client ID: " + clientID + " : " + counter);
	}

	/**
	 * Stop the timer and remove it and the client ID.
	 */
	@Override
	public void stopCounter(String clientID) {
		
		System.out.println("Stopping counter for client ID: " + clientID);

		Timer timer = clientID2Timer.get(clientID);
		
		// Clear up stuff if this hasn't already been made.
		if (timer != null) {
			timer.cancel();
		}
		
		if (clientID2Timer.containsKey(clientID)) {
			clientID2Timer.remove(clientID);
		}
		
		if (clientID2Counter.containsKey(clientID)) {
			clientID2Counter.remove(clientID);
		}
	}

}
