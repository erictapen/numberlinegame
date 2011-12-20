package com.wicam.numberlineweb.server.logging;

import com.wicam.numberlineweb.client.GameState;

public interface IHandicap {
	
	/**
	 * Calculate the handicap for a given user based on the log
	 * data.
	 * @param uid 
	 * 
	 * @return User handicap as a value between
	 * 0.0 and 1.0 where 0.0 is the best and 1.0 the worst possible
	 * handicap.
	 * @throws NoHandicapDataException 
	 */
	
	public double calculcateUserHandicap(int uid) throws NoHandicapDataException;
	
	/**
	 * Adjust game setting for a given handicap.
	 * 
	 * @params User handicap as a value between
	 * 0.0 and 1.0 where 0.0 is the best and 1.0 the worst possible
	 * handicap.
	 */
	
	public void adjustGameSetting(double handicap, GameState gameState);

}
