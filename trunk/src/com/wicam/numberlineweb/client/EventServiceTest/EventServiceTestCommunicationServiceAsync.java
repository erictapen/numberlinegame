package com.wicam.numberlineweb.client.EventServiceTest;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Event service test communication service async interface. 
 * @author timfissler
 *
 */

public interface EventServiceTestCommunicationServiceAsync {
	
	public void registerClient(String clientID, AsyncCallback<Void> registerCallback);

	public void startCounter(String clientID, AsyncCallback<Void> startCallback);
	
	public void stopCounter(String clientID, AsyncCallback<Void> stopCallback);
	
}
