package com.wicam.numberlineweb.server.logging;

import com.wicam.numberlineweb.client.GameState;

public interface IHandicap {
	
	
	/**
	 * Adjust game setting for a given ELO value.
	 * 
	 */
	
	public void adjustGameSetting(int eloValue, GameState gameState);

}
