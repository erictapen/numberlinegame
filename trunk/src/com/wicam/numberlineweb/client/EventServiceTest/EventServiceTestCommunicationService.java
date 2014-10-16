package com.wicam.numberlineweb.client.EventServiceTest;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("eventServiceTestCommunication")

/**
 * Event service test communication service interface.
 * @author timfissler
 *
 */

public interface EventServiceTestCommunicationService extends RemoteService {

	public int startCounter();
	
	public void stopCounter(int clientID);
	
}
