package com.wicam.numberlineweb.server.logging;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;


public class NumberLineGameHandicap implements IHandicap{
	
	@Override
	public void adjustGameSetting(int eloValue, GameState gameState) {
		NumberLineGameState numberLineGameState = (NumberLineGameState) gameState;
		
		/*
		 * ELO value | Pointer width
		 * 		   0 | 22  (minimal ELO Value)
		 *      1000 | 14  (average skill)
		 *      2500 |  2  (maximal ELO value)
		 * 
		 */
		int pointerWidth = (int) (22 - 0.008 * eloValue);
		
		numberLineGameState.setPointerWidth(pointerWidth);
		
		System.out.println("New pointerwidth: " + pointerWidth);
		
	}

}

