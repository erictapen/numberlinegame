package com.wicam.numberlineweb.server.DoppelungGame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameCommunicationService;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameState;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameWord;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;

public class DoppelungGameCommunicationServiceServlet extends
		GameCommunicationServiceServlet implements DoppelungGameCommunicationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7200332323767902482L;
	
	HashMap<Integer,Iterator<DoppelungGameWord>> wordLists = new HashMap<Integer,Iterator<DoppelungGameWord>>();
	
	@Override
	public GameState openGame(GameState g) {
		currentId++;

		g.setGameId(currentId);

		// initialize game list
		wordLists.put(currentId, DoppelungGameWordList.createWordList().iterator());
		
		// set first word
		((DoppelungGameState)g).setCurWord(getNextWord(currentId));
		
		openGames.add(g);

		System.out.println("Opend Game " + Integer.toString(currentId));
		
		return g;
	}
	
	public boolean hasNextWord(int gameid){
		return wordLists.get(gameid).hasNext();
	}
	
	public DoppelungGameWord getNextWord(int gameid){
		if (hasNextWord(gameid))
			return wordLists.get(gameid).next();
		else
			return null;
	}
	
	
	@Override
	public void startGame(int id) {
		Timer t = new Timer();

		//wait 6 seconds for users to be ready
		t.schedule(new SetDoppelungGameStateTask(id, 21, this), 6000);
	}

}
