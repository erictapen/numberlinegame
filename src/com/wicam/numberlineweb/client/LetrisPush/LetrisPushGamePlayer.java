package com.wicam.numberlineweb.client.LetrisPush;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.wicam.numberlineweb.client.Player;

public class LetrisPushGamePlayer extends Player {
	
	/**
	 * The client specific connection id is responsible for user specific
	 * events with GWTEventService.
	 */
	private String connectionID;

	/**
	 * @return the connectionID
	 */
	public String getConnectionID() {
		return connectionID;
	}

	/**
	 * @param connectionID the connectionID to set
	 */
	public void setConnectionID(String connectionID) {
		this.connectionID = connectionID;
	}
	
}
