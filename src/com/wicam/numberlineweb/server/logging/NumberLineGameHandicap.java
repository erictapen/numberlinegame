package com.wicam.numberlineweb.server.logging;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;


public class NumberLineGameHandicap extends GameHandicap{
	
	public NumberLineGameHandicap(){
		
			super(GameType.NUMBER_LINE_GAME);
		
	}
	
	public void adjustGameSetting(double handicap, GameState gameState) {
		NumberLineGameState numberLineGameState = (NumberLineGameState) gameState;
		
		int minimalPointerWidth = 1;
		int maximalPointerWidth = 27;
		
		int pointerWidth = (int) (handicap * (maximalPointerWidth - minimalPointerWidth) + minimalPointerWidth);
		
		numberLineGameState.setPointerWidth(pointerWidth);
		
		System.out.println("New pointerwidth: " + pointerWidth);
		
	}

}

