package com.wicam.numberlineweb.server.MathDiagnostics;

import java.util.ArrayList;
import java.util.Iterator;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.MathDiagnostics.AdditionItem;
import com.wicam.numberlineweb.client.MathDiagnostics.ItemInformation;
import com.wicam.numberlineweb.client.MathDiagnostics.ItemTypes;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsCommonicationService;
import com.wicam.numberlineweb.client.MathDiagnostics.isItem;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;

public class MathDiagnosticsCommunicationServiceServlet extends
		GameCommunicationServiceServlet implements  MathDiagnosticsCommonicationService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1718259689021463189L;

	@Override
	public GameState openGame(GameState g) {
		
		// initialize game list
	
		GameState gameState =  super.openGame(g);
		
		return gameState;
		

	}

	@Override
	public void startGame(int id) {}

	@Override
	public ArrayList<isItem> retrieveItemList(int gameId, int itemType) {
		ArrayList<isItem> itemList = new ArrayList<isItem>();
		// TODO: create list
		if (itemType == ItemTypes.ADDITIONITEM){
			AdditionItem additionItem = new AdditionItem();
			additionItem.setAdditionItem(1, 1, 1, 2);
			itemList.add(additionItem);
			additionItem = new AdditionItem();
			additionItem.setAdditionItem(12, 7, 19, 8);
			itemList.add(additionItem);
			additionItem = new AdditionItem();
			additionItem.setAdditionItem(45, 12, 53, 57);
			itemList.add(additionItem);
		}
		return itemList;
	}

	@Override
	public Boolean sendKeyCodeList(int gameId, ArrayList<ItemInformation> itemInformationList) {
		//TODO: interpret list
		GameState g = this.getGameById(gameId);
		Iterator<ItemInformation> it = itemInformationList.iterator();
		while (it.hasNext()){
			it.next();
			g.setPlayerPoints(1, g.getPlayerPoints(1)+1);
		}
		endGame(gameId);
		return true;
	}
}
