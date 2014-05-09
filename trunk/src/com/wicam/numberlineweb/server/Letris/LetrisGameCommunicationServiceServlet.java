package com.wicam.numberlineweb.server.Letris;

import java.util.ArrayList;
import com.wicam.numberlineweb.client.GameOpenException;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Letris.LetrisGameCommunicationService;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.logging.LetrisGameHandicap;

/**
 * LeTris game communication service.
 * @author timfissler
 *
 */

public class LetrisGameCommunicationServiceServlet extends
		GameCommunicationServiceServlet implements
		LetrisGameCommunicationService {

	// TODO Add descriptions.
	// TODO Add possibility to communicate other players achievements to add "deadlines" to the playground. 
	
	private static final long serialVersionUID = -7355416542911925385L;	
	private LetrisGameWordList wordList = new LetrisGameWordList();

	public LetrisGameCommunicationServiceServlet() {
		super("LeTris");
		this.handicapAdjustment = new LetrisGameHandicap();
	}

	public LetrisGameCommunicationServiceServlet(String internalName) {
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
	public ArrayList<String> getTargetWords() {
		return LetrisGameWordList.createWordList();
	}

}
