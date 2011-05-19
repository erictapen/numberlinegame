package com.wicam.numberlineweb.server.DoppelungGame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;

import com.google.gwt.event.dom.client.KeyCodes;
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
	
		GameState gameState =  super.openGame(g);
		
		wordLists.put(gameState.getId(), DoppelungGameWordList.createWordList().iterator());
		
		// set first word
		((DoppelungGameState)gameState).setCurWord(getNextWord(currentId));
		
		return gameState;
		

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
	public GameState buttonClicked(String ids) {
		int playerid = Integer.parseInt(ids.split(":")[1]);
		int gameid = Integer.parseInt(ids.split(":")[0]);
		int bottonid = Integer.parseInt(ids.split(":")[2]);
		
		DoppelungGameState g = (DoppelungGameState) getGameById(gameid);
		int feedbackTime = 0;
		
		if (g.getCurWord().getSoundTries() < 1){
			// feedback
			this.setGameState(g, 4);
			this.setChanged(gameid);	
			feedbackTime = 3000; // 3 sec feedback
		}
		else {
			this.setGameState(g, 7);
			this.setChanged(gameid);
			feedbackTime = 500;
		}
		
		// short vowel
		if (g.getCurWord().isShortVowel())
			if (bottonid == DoppelungGameController.SHORTVOWELBUTTON)
				g.setCorrectAnswered(true);
			else
				g.setCorrectAnswered(false);
		// long vowel
		else 
			if (bottonid == DoppelungGameController.LONGVOWELBUTTON)
				g.setCorrectAnswered(true);
			else
				g.setCorrectAnswered(false);
		
		// only add points if answered correctly in the first time
		if (g.isCorrectAnswered() && g.getCurWord().getSoundTries() > 0)
			g.setCorrectAnswered(false);
		
		Timer t = new Timer();
		// next step if correct answer or already tried once
		if (g.isCorrectAnswered() || g.getCurWord().getSoundTries() >= 1){
			// short vowel: start short vowel sub game
			if(g.getCurWord().isShortVowel())
				t.schedule(new SetGameStateTask(gameid, 5, this), feedbackTime);
			// long vowel: show next word if has next
			else {
				// points for correct answer
				if (g.isCorrectAnswered())
					g.setPlayerPoints(playerid, g.getPlayerPoints(playerid) + 5);
				if (this.hasNextWord(gameid))
					t.schedule(new SetDoppelungGameStateTask(gameid, 21, this), feedbackTime);
				else {
					this.endGame(gameid);
				}
			}
		}
		// retry
		else{
			g.getCurWord().incSoundTries();
			t.schedule(new SetGameStateTask(gameid, 41, this), feedbackTime);
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
		// feedback
		int feedbackTime = 0;
		if (g.getCurWord().getWordTries() < 1){
			this.setGameState(g, 42);
			this.setChanged(gameid);
			feedbackTime = 3000;
		}
		else {
			this.setGameState(g, 7);
			this.setChanged(gameid);
			feedbackTime = 500;
		}
		
		if (enteredWord.equals(g.getCurWord().getWordString()))
			g.setCorrectAnswered(true);
		else
			g.setCorrectAnswered(false);
		
		Timer t = new Timer();
		if (g.isCorrectAnswered() || g.getCurWord().getWordTries() >= 1){
			g.setPlayerPoints(playerid, g.getPlayerPoints(playerid) + 2);
			
			if (this.hasNextWord(gameid))
				// 3 sec feedback
				t.schedule(new SetDoppelungGameStateTask(gameid, 21, this), feedbackTime);
			else
				this.endGame(gameid);
		}
		// retry
		else {
			g.getCurWord().incWordTries();
			t.schedule(new SetGameStateTask(gameid, 6, this), feedbackTime);
		}
		
		return g;
	}

	@Override
	public DoppelungGameState keyEvent(String ids) {
		int gameid = Integer.parseInt(ids.split(":")[0]);
		int playerid = Integer.parseInt(ids.split(":")[1]);
		String event = ids.split(":")[2];
		int keyCode = Integer.parseInt( ids.split(":")[3]);
		
		if (event.equals("stop")) {
			
			((DoppelungGameState)this.getGameById(gameid)).setEnemyMovingTo(playerid, "stop");
			
		}
		
		if (event.equals("down")) {
		
		switch(keyCode){

			case KeyCodes.KEY_DOWN:
	
				((DoppelungGameState)this.getGameById(gameid)).setEnemyMovingTo(playerid, "down");
				break;
			case KeyCodes.KEY_RIGHT:
			
				((DoppelungGameState)this.getGameById(gameid)).setEnemyMovingTo(playerid, "right");
	
				break;
			case KeyCodes.KEY_UP:
		
				((DoppelungGameState)this.getGameById(gameid)).setEnemyMovingTo(playerid,"up");
	
				break;
			case KeyCodes.KEY_LEFT:
			
				((DoppelungGameState)this.getGameById(gameid)).setEnemyMovingTo(playerid,"left");
	
				break;
			
			}
		}
		
		
		
		System.out.println("Player #" + playerid + " in game #" + gameid + " '" + event + "'ed " + keyCode);
		
		this.setChanged(gameid);
		return ((DoppelungGameState)this.getGameById(gameid));
	}

}
