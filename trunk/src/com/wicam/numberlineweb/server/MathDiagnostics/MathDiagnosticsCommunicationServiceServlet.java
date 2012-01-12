package com.wicam.numberlineweb.server.MathDiagnostics;

import java.util.ArrayList;
import java.util.Iterator;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;
import com.wicam.numberlineweb.client.MathDiagnostics.ChoiceTaskItemInformation;
import com.wicam.numberlineweb.client.MathDiagnostics.ItemInformation;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsCommonicationService;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsGameState;
import com.wicam.numberlineweb.client.MathDiagnostics.isItem;
import com.wicam.numberlineweb.client.MathDiagnostics.NumberLine.NumberLineItemInformation;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.logging.MathDiagnosticsHandicap;

/**
 * Class for the math diagnostic experiments
 * Responsible for retrieving the item list from the server and
 * interpreting the response of the user
 * 
 * @author shuber
 *
 */

public class MathDiagnosticsCommunicationServiceServlet extends
		GameCommunicationServiceServlet implements  MathDiagnosticsCommonicationService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1718259689021463189L;

	
	
	public MathDiagnosticsCommunicationServiceServlet() {
		
		super("mathdiagnostics");
		this.handicapAdjustment = new MathDiagnosticsHandicap();
		
	}
	
	
	/**
	 * basic implementation of openGame
	 */
	@Override
	public GameState openGame(GameState g) {
		
		// initialize game list
	
		GameState gameState =  super.openGame(g);
		
		return gameState;
		

	}

	
	/**
	 * function for retrieving the item list from the server
	 */
	@Override
	public ArrayList<isItem> retrieveItemList(int gameId, int itemType) {
		ArrayList<isItem> itemList = new ArrayList<isItem>();
		ItemListReader itemListReader = new ItemListReader(getServletContext().getRealPath("/") + "MathDiagnostics/itemlist.xml");
		itemList = itemListReader.getList(itemType);
		return itemList;
	}

	
	/**
	 * function for sending the response of the user to the server
	 * additionally the response is interpreted which means mean RT and points
	 * are calculated
	 */
	@Override
	public Boolean sendItemInformationList(int gameId, ArrayList<ItemInformation> itemInformationList) {
		GameState g = this.getGameById(gameId);
		Iterator<ItemInformation> it = itemInformationList.iterator();
		int meanRT = 0;
		
		// calculate mean RT and points
		while (it.hasNext()){
			ItemInformation inf = it.next();
			meanRT += inf.getRt();
			addPoints(inf, g);
		}
		
		// avoid division by zero
		if (itemInformationList.size() != 0)
			meanRT = meanRT/itemInformationList.size();
		else
			meanRT = 0;
		
		((MathDiagnosticsGameState)g).setPlayerMeanRT(1, meanRT); // playerid = 1 because we have only one player
		endGame(gameId);
		this.handicapAction(gameId);
		return true;
	}
	
	
	/**
	 * Adds a point when item was correctly answered
	 * 
	 * @param inf	holds the information about the response
	 * @param g		GameState of the game
	 */
	private void addPoints(ItemInformation inf, GameState g){
		if (inf instanceof ChoiceTaskItemInformation){
			if (((ChoiceTaskItemInformation)inf).isCorrect())
				g.setPlayerPoints(1, g.getPlayerPoints(1)+1);
		}
		if (inf instanceof NumberLineItemInformation){
			double relDeviation = ((NumberLineItemInformation)inf).getRelDeviation();
			if (relDeviation < .01)
				g.setPlayerPoints(1, g.getPlayerPoints(1)+5);
			else if (relDeviation < .05)
				g.setPlayerPoints(1, g.getPlayerPoints(1)+3);
			else if (relDeviation < .10)
				g.setPlayerPoints(1, g.getPlayerPoints(1)+1);
		}
	}
	
	private void handicapAction(int gameid){
		
		MathDiagnosticsGameState mathDiagnosticsGameState = (MathDiagnosticsGameState) this.getGameById(gameid);
		
		Player player = mathDiagnosticsGameState.getPlayers().get(0);
		
		if (player.getUid() != -2) {
			
			System.out.println("User with id " + player.getUid() + " scored " + player.getPoints());
			
		}
		
	}
}
