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
	
	public void registerClient(String clientID);

	public void startCounter(String clientID);
	
	public void stopCounter(String clientID);
	
}
