package com.wicam.numberlineweb.server.VowelGame.DehnungGame;

import java.util.ArrayList;

import com.wicam.numberlineweb.client.GameOpenException;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.VowelGame.DehnungGame.DehnungGameCommunicationService;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameState;

public class DehnungGameCommunicationServiceServlet extends
		com.wicam.numberlineweb.server.VowelGame.DoppelungGame.DoppelungGameCommunicationServiceServlet implements DehnungGameCommunicationService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5110227034606661459L;
	
	public DehnungGameCommunicationServiceServlet() {
		
		super("dehnungsgame");
		
	}
	
	@Override
	public GameState openGame(GameState g) throws GameOpenException {
		
		// we want the mini game to start when short vowel is false
		this.setMiniGameStartsWhenShortVowel(false);
		
		// initialize game list
	
		GameState gameState =  super.openGame(g);
		
		wordLists.put(gameState.getId(), DehnungGameWordList.createWordList().iterator());
		
		// set first word
		((DoppelungGameState)gameState).setCurWord(getNextWord(currentId));
		
		return gameState;
		

	}
	
	@Override
	protected ArrayList<String> getConsonantsList(int gameid){
		DoppelungGameState g = (com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameState) getGameById(gameid);
		return new DehnungGameTargetConsonantListCreater().createTargetConsonantList(
				g.getCurWord().getConsonantPair(),
				g.getNumberOfCorrectConsonants(),
				g.getNumberOfConsonants());
	}
}
