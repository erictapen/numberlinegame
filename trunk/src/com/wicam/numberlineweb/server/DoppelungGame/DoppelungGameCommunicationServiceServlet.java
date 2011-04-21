package com.wicam.numberlineweb.server.DoppelungGame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameCommunicationService;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameController;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameState;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameWord;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.SetGameStateTask;

public class DoppelungGameCommunicationServiceServlet extends
		GameCommunicationServiceServlet implements DoppelungGameCommunicationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7200332323767902482L;
	
	HashMap<Integer,Iterator<DoppelungGameWord>> wordLists = new HashMap<Integer,Iterator<DoppelungGameWord>>();
	
	@Override
	public GameState openGame(GameState g) {
		
		
		// initialize game list
		wordLists.put(currentId, DoppelungGameWordList.createWordList().iterator());
		
		// set first word
		((DoppelungGameState)g).setCurWord(getNextWord(currentId));
		
		return super.openGame(g);

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
	
	// we do not need startGame because we have start buttons
	@Override
	public void startGame(int id) {}

	@Override
	public GameState bottonClicked(String ids) {
		int playerid = Integer.parseInt(ids.split(":")[1]);
		int gameid = Integer.parseInt(ids.split(":")[0]);
		int bottonid = Integer.parseInt(ids.split(":")[2]);
		
		DoppelungGameState g = (DoppelungGameState) getGameById(gameid);
		
		// feedback
		this.setGameState(g, 4);
		this.setChanged(gameid);
		
		// short vowel
		if (g.getCurWord().isShortVowel()){
			if (bottonid == DoppelungGameController.SHORTVOWELBUTTON)
				g.setCorrectAnswered(true);
			else
				g.setCorrectAnswered(false);
			
			// TODO: afterwards start sub game
			
			Timer t = new Timer();
			// afterwards start short vowel sub game
			// 3 sec feedback
			t.schedule(new SetGameStateTask(gameid, 5, this), 3000);
		}
		// long vowel
		else {
			// player only gets points for correct choice
			if (bottonid == DoppelungGameController.LONGVOWELBUTTON){
				g.setPlayerPoints(playerid, g.getPlayerPoints(playerid) + 5);
				g.setCorrectAnswered(true);
			}
			else
				g.setCorrectAnswered(false);
			
			Timer t = new Timer();
			if (this.hasNextWord(gameid))
				// afterwards start next trial
				// 3 sec feedback
				t.schedule(new SetDoppelungGameStateTask(gameid, 21, this), 3000);
			else {
				this.endGame(gameid);
			}
		}
		
		return g;
	}

	@Override
	public GameState updatePoints(String ids) {
		int gameid = Integer.parseInt(ids.split(":")[0]);
		int playerid = Integer.parseInt(ids.split(":")[1]);
		String consonants = ids.split(":")[2];
		
		int points = 0;
		DoppelungGameState g = (DoppelungGameState) getGameById(gameid);
		
		if (consonants.equals(g.getCurWord().getConsonantPair()))
			points = g.isCorrectAnswered()?2:1;
		else
			points = -1;
		
		int newPoints = g.getPlayerPoints(playerid) + points;
		if (newPoints < 0)
			newPoints = 0;
		
		g.setPlayerPoints(playerid, newPoints);
		
		return g;
	}

	@Override
	public GameState wordEntered(String word) {
		int gameid = Integer.parseInt(word.split(":")[0]);
		int playerid = Integer.parseInt(word.split(":")[1]);
		String enteredWord = word.split(":")[2];
		
		DoppelungGameState g = (DoppelungGameState) getGameById(gameid);
		
		int points = 0;
		if (enteredWord.equals(g.getCurWord().getWord()))
			points = 2;
		
		g.setPlayerPoints(playerid, g.getPlayerPoints(playerid) + points);
		
		Timer t = new Timer();
		if (this.hasNextWord(gameid))
			// 3 sec feedback
			t.schedule(new SetDoppelungGameStateTask(gameid, 21, this), 3000);
		else
			this.endGame(gameid);
		
		return g;
	}

}
