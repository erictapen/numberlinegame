package com.wicam.numberlineweb.server.factsGame;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.factsGame.FactsGameCommunicationService;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;

public class FactsGameCommunicationServiceServlet extends
		GameCommunicationServiceServlet implements FactsGameCommunicationService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1765772725542837585L;

	@Override
	public GameState openGame(GameState g) {
		
		// initialize game list
	
		GameState gameState =  super.openGame(g);
		
		return gameState;
		

	}
}
