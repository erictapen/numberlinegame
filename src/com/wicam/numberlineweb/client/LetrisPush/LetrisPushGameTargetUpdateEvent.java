package com.wicam.numberlineweb.client.LetrisPush;

import de.novanic.eventservice.client.event.Event;

/**
 * Event that is fired whenever a player drops a letter block
 * and so the static letter blocks on the playground (and
 * the points) change. With this event the opponent player is
 * informed about these updates.
 * @author timfissler
 *
 */

public class LetrisPushGameTargetUpdateEvent implements Event {

	private static final long serialVersionUID = 98726821011073958L;
	private LetrisPushGamePlaygroundState playgroundState;

	/**
	 * @return the playgroundState
	 */
	public LetrisPushGamePlaygroundState getPlaygroundState() {
		return playgroundState;
	}
	/**
	 * @param playgroundState the playgroundState to set
	 */
	public void setPlaygroundState(LetrisPushGamePlaygroundState playgroundState) {
		this.playgroundState = playgroundState;
	}

}
