package com.wicam.numberlineweb.server.LetrisPush;

import java.util.ArrayList;

import com.wicam.numberlineweb.client.GameOpenException;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Letris.LetrisGameCommunicationService;
import com.wicam.numberlineweb.client.Letris.LetrisGameLetterBlock;
import com.wicam.numberlineweb.client.Letris.LetrisGameState;
import com.wicam.numberlineweb.client.LetrisPush.LetrisPushGameCommunicationService;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.Letris.LetrisGameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.Letris.LetrisGameWordList;
import com.wicam.numberlineweb.server.logging.LetrisGameHandicap;

/**
 * LeTris game communication service.
 * @author timfissler
 *
 */

public class LetrisPushGameCommunicationServiceServlet extends
		LetrisGameCommunicationServiceServlet implements
		LetrisPushGameCommunicationService {

	// TODO Add descriptions.
	// TODO Add possibility to communicate other players achievements to add "deadlines" to the playground. 
	
	private static final long serialVersionUID = -7355416542911925385L;	
	private LetrisGameWordList wordList = new LetrisGameWordList();

	public LetrisPushGameCommunicationServiceServlet() {
		super("LeTris");
		this.handicapAdjustment = new LetrisGameHandicap();
	}

	public LetrisPushGameCommunicationServiceServlet(String internalName) {
		super(internalName);
		this.handicapAdjustment = new LetrisGameHandicap();
	}

	@Override
	public GameState openGame(GameState g) throws GameOpenException {
		GameState gameState = super.openGame(g);
		return gameState;
	}

	@Override
	public GameState updatePoints(String ids) {
		// TODO Change the behavior of this method.
//		int gameid = Integer.parseInt(ids.split(":")[0]);
//		int playerid = getPlayerId(gameid);
//		String letter = ids.split(":")[2];
//		int mcid = Integer.parseInt(ids.split(":")[3]);
//
//		int points = 0;
//		LetrisGameState g = (LetrisGameState) getGameById(gameid);
//
//		if (letter.equals(g.getCurWord().getConsonantPair()))
//			points = g.hasCorrectlyAnswered(playerid) ? 2 : 1;
//		else
//			points = -1;
//
//		int newPoints = g.getPlayerPoints(playerid) + points;
//		if (newPoints < 0)
//			newPoints = 0;
//
//		g.setPlayerPoints(playerid, newPoints);
//		g.getMovingConsonantsCoords().get(mcid).setRemoved(true);
//		g.setServerSendTime(System.currentTimeMillis());
//		return g;
		return null;
	}


	@Override
	public String getGameProperties(GameState g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<VowelGameWord> getTargetWords() {
		return LetrisGameWordList.createWordList();
	}
	
	/**
	 * Log the current target word.
	 * @param g
	 */
	public void targetWordPresented(LetrisGameState g) {
		// TODO Implement this.
		System.out.println("Logging target word: " + g.getCurrentWord());
	}
	
	/**
	 * Log the last letter block that was set by the player.
	 * @param letterBlock
	 * @param g
	 */
	public void letterBlockSet(LetrisGameLetterBlock letterBlock, LetrisGameState g) {
		// TODO Implement this.
		System.out.println("Logging set letter block: " + letterBlock);
	}
	
	/**
	 * Log the last word that was built correctly by the user and
	 * add a filler row to the playground of the other player. 
	 * @param word
	 * @param g
	 */
	public void correctWordBuilt(String word, LetrisGameState g) {
		// TODO Implement this.
		System.out.println("Logging correct word: " + word);
	}
	
	/**
	 * Log the last word that was built incorrectly by the user and
	 * remove a filler row to the playground of the other player
	 * (if possible). 
	 * @param word
	 * @param g
	 */
	public void incorrectWordBuilt(String word, LetrisGameState g) {
		// TODO Implement this.
		System.out.println("Logging incorrect word: " + word);
	}
	

}
