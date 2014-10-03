package com.wicam.numberlineweb.client.MathAssessment;

import java.io.Serializable;
import java.util.ArrayList;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;


/**
 * This is the state sent by the server. Holds all game information.
 * @author alex
 *
 */

public class MathAssessmentState extends GameState implements Serializable{

	// we need a serial number for GWT to identify sent instances
	private static final long serialVersionUID = 846864907276321588L;	
	
	public MathAssessmentState() {
		
	}	
	
	/**
	 * Returns a list of player names
	 * @return Player names as List
	 */
	public ArrayList<String> getPlayerNamesList() {
		ArrayList<String> playerNamesList = new ArrayList<String>();
		for (Player player: players)
			playerNamesList.add(player.getName());
		return playerNamesList;
	}

	/**
	 * We do not need this method in this package.
	 */
	@Override
	public int addPlayer(String newName, int uid) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}