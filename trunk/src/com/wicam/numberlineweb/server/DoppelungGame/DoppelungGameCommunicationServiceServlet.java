package com.wicam.numberlineweb.server.DoppelungGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;

import com.google.gwt.event.dom.client.KeyCodes;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameCommunicationService;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameController;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameState;
import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameWord;
import com.wicam.numberlineweb.client.DoppelungGame.Point2D;
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
		g.setShowSoundFeedback(playerid, true);
		g.incSoundTries(playerid);
		
		// short vowel
		if (g.getCurWord().isShortVowel())
			if (bottonid == DoppelungGameController.SHORTVOWELBUTTON)
				g.setAnswer(playerid,true);
			else
				g.setAnswer(playerid,false);
		// long vowel
		else 
			if (bottonid == DoppelungGameController.LONGVOWELBUTTON)
				g.setAnswer(playerid,true);
			else
				g.setAnswer(playerid,false);
		
		Timer t = new Timer();
		
		// next step if correct answer or already tried once
		if (g.hasCorrectlyAnswered(playerid) || g.getSoundTries(playerid) >= 2){
			// check if other player has already answered
			if (g.getPlayerCount() == 1 ||
					g.hasCorrectlyAnswered((playerid%2+1)) || g.getSoundTries((playerid%2+1)) >= 2){
				int feedbackTime = 500;
				if (g.getSoundTries(playerid) < 2)
					feedbackTime = 3000;
				
				// short vowel: start short vowel sub game
				if(g.getCurWord().isShortVowel()){
					initializeMovingConsonantsPoints(gameid);
					t.schedule(new SetGameStateTask(gameid, 5, this), feedbackTime);
				}
				// long vowel: show next word if has next
				else {
					// points for correct answer
					if (g.hasCorrectlyAnswered(playerid) && g.getSoundTries(playerid) < 2)
						g.setPlayerPoints(playerid, g.getPlayerPoints(playerid) + 5);
					if (this.hasNextWord(gameid)){
						t.schedule(new SetDoppelungGameStateTask(gameid, 21, this), feedbackTime);
					}
					else {
						this.endGame(gameid);
					}
				}
			}
			// wait for other player
			else {
				// add points for correct answer
				if (!g.getCurWord().isShortVowel()){
					if (g.hasCorrectlyAnswered(playerid) && g.getSoundTries(playerid) < 2)
						g.setPlayerPoints(playerid, g.getPlayerPoints(playerid) + 5);
				}
			}
		}
		// retry
		else{
			t.schedule(new ResetSoundFeedbackStateTask(gameid,playerid,3,this), 3000);
		}
		
		return g;
		
	}
	
	private void initializeMovingConsonantsPoints(int gameid){
		DoppelungGameState g = (DoppelungGameState) getGameById(gameid);
		ArrayList<Point2D> movingConsonantsCoords = new ArrayList<Point2D>();
		int oldX = -100;
		for (int i = 0; i < g.getNumberOfConsonants(); i++){
			Point2D point = new Point2D();
			int newX = 50-(int)(Math.random()*7)+(int)(Math.random()*9)%9*50;
			while (Math.abs(oldX-newX) < 50){
				newX = 50-(int)(Math.random()*7)+(int)(Math.random()*9)%9*50;
			}
			point.setX(newX);
			point.setY(-50);
			movingConsonantsCoords.add(point);
			oldX = newX;
		}
		g.setMovingConsonantsCoords(movingConsonantsCoords);
	}

	@Override
	public GameState updatePoints(String ids) {
		int gameid = Integer.parseInt(ids.split(":")[0]);
		int playerid = Integer.parseInt(ids.split(":")[1]);
		String consonants = ids.split(":")[2];
		
		int points = 0;
		DoppelungGameState g = (DoppelungGameState) getGameById(gameid);
		
		if (consonants.equals(g.getCurWord().getConsonantPair()))
			points = g.hasCorrectlyAnswered(playerid)?2:1;
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
		g.setShowWordFeedback(playerid, true);
		g.incWordTries(playerid);
		
		if (enteredWord.equals(g.getCurWord().getWordString()))
			g.setAnswer(playerid, true);
		else
			g.setAnswer(playerid, false);

		Timer t = new Timer();
		if (g.hasCorrectlyAnswered(playerid) || g.getWordTries(playerid) >= 2){
			// add points for correct answer
			if (g.getWordTries(playerid) <2)
				g.setPlayerPoints(playerid, g.getPlayerPoints(playerid) + 2);
			
			// check if other player has already answered
			if (g.getPlayerCount() == 1 ||
					g.hasCorrectlyAnswered((playerid%2+1)) || g.getWordTries((playerid%2+1)) >= 2){
				if (this.hasNextWord(gameid)){
					int feedbackTime = 500;
					if (g.getWordTries(playerid) < 2)
						feedbackTime = 3000;
					t.schedule(new SetDoppelungGameStateTask(gameid, 21, this), feedbackTime);
				}
				else
					this.endGame(gameid);
			}
		}
		// retry
		else {
			t.schedule(new ResetWordFeedbackStateTask(gameid, playerid, 6, this), 3000);
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

	@Override
	public GameState enableWordInput(String ids) {
		int gameid = Integer.parseInt(ids.split(":")[0]);
		int playerid = Integer.parseInt(ids.split(":")[1]);
		DoppelungGameState g = (DoppelungGameState) getGameById(gameid);
		// reset answer of sound choice
		g.setAnswer(playerid, false);
		g.setEndedShortVowelGame(playerid, true);
		
		// wait for other player
		if (g.getPlayerCount() == 1 || 
				g.isEndedShortVowelGame(playerid%2+1)){
			Timer t = new Timer();
			t.schedule(new SetGameStateTask(gameid, 6, this), 500);
			this.setChanged(gameid);
		}
		return g;
	}

}
