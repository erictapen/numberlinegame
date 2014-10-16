package com.wicam.numberlineweb.client.EventServiceTest;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Event service test communication service async interface. 
 * @author timfissler
 *
 */

public interface EventServiceTestCommunicationServiceAsync {

	public void startCounter(AsyncCallback<Integer> startCallback);
	
	public void stopCounter(int clientID, AsyncCallback<Void> stopCallback);
	
}
