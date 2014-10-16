package com.wicam.numberlineweb.server.EventServiceTest;

import java.util.HashMap;
import java.util.Timer;

import com.wicam.numberlineweb.client.EventServiceTest.EventServiceTestCounterEvent;
import com.wicam.numberlineweb.client.EventServiceTest.EventServiceTestCommunicationService;
import com.wicam.numberlineweb.server.CustomRemoteServiceServlet;

import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.service.EventExecutorService;
import de.novanic.eventservice.service.EventExecutorServiceFactory;

/**
 * The EventService Test servlet that - over CustomRemoteServiceServlet - extends RemoteEventServiceServlet
 * and thereby implements e.g. the addEvent()-Method of the EventExecutorService class. 
 * @author timfissler
 *
 */

public class EventServiceTestCommunicationServiceServlet extends CustomRemoteServiceServlet implements
		EventServiceTestCommunicationService {

	private static final long serialVersionUID = 129270086406637782L;
	private static final Domain TEST_DOMAIN = DomainFactory.getDomain("testdomain");
	private String internalName = "event_service_test";
	private int currentClientID = 0;
	private HashMap<Integer, Integer> clientID2Counter = new HashMap<Integer, Integer>();
	private HashMap<Integer, Timer> clientID2Timer = new HashMap<Integer, Timer>();
//	private EventExecutorService eventExecutorService;
	
	/**
	 * Create a new servlet and also an event service.
	 */
	public EventServiceTestCommunicationServiceServlet() {
		// TODO Add client ID.
//		EventExecutorServiceFactory serviceFactory = EventExecutorServiceFactory.getInstance();
//		eventExecutorService = serviceFactory.getEventExecutorService("TODO");
	}

	/**
	 * Return the client ID and store it, create a new timer, store it and start it.
	 */
	@Override
	public int startCounter() {
		
		currentClientID++;
		clientID2Counter.put(currentClientID, 0);

		EventServiceTestCounterEvent event = new EventServiceTestCounterEvent();
		event.setCounter(0);
		this.addEvent(TEST_DOMAIN, event);
		
		Timer timer = new Timer();
		EventServiceTestCounterTask task = new EventServiceTestCounterTask(currentClientID, this);
		timer.scheduleAtFixedRate(task, 1000, 1000);
		clientID2Timer.put(currentClientID, timer);
		
		return currentClientID;
	}
	
	/**
	 * Increase the appropriate counter and send a message containing the counter.
	 * @param clientID
	 */
	public void count(int clientID) {

		int counter = clientID2Counter.get(clientID) + 1;
		EventServiceTestCounterEvent event = new EventServiceTestCounterEvent();
		event.setCounter(counter);
		this.addEvent(TEST_DOMAIN, event);
		clientID2Counter.put(clientID, counter);
	}

	/**
	 * Stop the timer and remove it and the client ID.
	 */
	@Override
	public void stopCounter(int clientID) {

		Timer timer = clientID2Timer.get(clientID);
		timer.cancel();
		clientID2Timer.remove(clientID);
		clientID2Counter.remove(clientID);
	}

}
