package com.wicam.numberlineweb.client.OverTen;

import java.io.Serializable;
import java.util.ArrayList;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;


/**
 * This is the state sent by the server. Holds all game information.
 * @author alex
 *
 */

public class OverTenGameState extends GameState implements Serializable{

	// we need a serial number for GWT to identify sent instances
	private static final long serialVersionUID = 846864907276321588L;

	// All community-digits
	private ArrayList<OverTenDigit> communityDigits = new ArrayList<OverTenDigit>();

	// All hand-digits
	private ArrayList<OverTenCalculation> calculations = new ArrayList<OverTenCalculation>();
	
	// Round-counter
	private int round = 0;
	// Max number of rounds to play
	private int maxRound;
	
	
	public OverTenGameState() {
		
	}
	
	
	
	/**
	 * @return Returns communityDigits
	 */
	public ArrayList<OverTenDigit> getCommunityDigits() {
		return communityDigits;
	}



	/**
	 * Set communityDigits
	 * @param communityDigits Set communityDigits to this value
	 */
	public void setCommunityDigits(ArrayList<OverTenDigit> communityDigits) {
		this.communityDigits = communityDigits;
	}
	

	
	/**
	 * @return Returns calculations
	 */
	public ArrayList<OverTenCalculation> getCalculations() {
		return calculations;
	}



	/**
	 * Set calculations
	 * @param calculations Set calculations to this value
	 */
	public void setCalculations(ArrayList<OverTenCalculation> calculations) {
		this.calculations = calculations;
	}



	/**
	 * @param playerID ID of the player to be looked up
	 * @return Returns the calculation the user has clicked on
	 */
	public OverTenCalculation getPlayerCalculation(int playerID) {
		return ((OverTenPlayer) players.get(playerID-1)).getCalculation();
	}
	
	
	/**
	 * Set the hand-digit a user clicked on
	 * @param playerID ID of the player to be updated
	 * @param calculation calculation the player clicked on
	 */
	public void setPlayerCalculation(int playerID, OverTenCalculation calculation) {
		((OverTenPlayer) players.get(playerID-1)).setCalculation(calculation);
	}
	
	
	/**
	 * @param playerID ID of the player to be looked up
	 * @return Returns the digit-index the user has clicked on
	 */
	public int getPlayerDigit(int playerID) {
		return ((OverTenPlayer) players.get(playerID-1)).getFirstDigit();
	}
	
	
	/**
	 * Set the digit a user clicked on
	 * @param playerID ID of the player to be updated
	 * @param index digit-index the player clicked on
	 */
	public void setPlayerDigit(int playerID, int index) {
		((OverTenPlayer) players.get(playerID-1)).setFirstDigit(index);
	}
	
	
	
	/**
	 * Disables/Enables all digits in a set of digits
	 * @param digits Set of digits to disable/enable
	 * @param taken Define, if all digits are taken or not
	 */
	public void setAllDigitsTakenIn(ArrayList<OverTenDigit> digits, boolean taken) {
		for (OverTenDigit digit : digits) {
			digit.setTaken(taken);
		}
	}
	
	
	/**
	 * Disables/Enables all calculations in a set of digits
	 * @param calcs Set of calcs to disable/enable
	 * @param taken Define, if all calcs are taken or not
	 */
	public void setAllCalculationsTakenIn(ArrayList<OverTenCalculation> calcs, boolean taken) {
		for (OverTenCalculation calc : calcs) {
			calc.setTaken(taken);
		}
	}



	/**
	 * Disables/Enables one digit in a set of digits
	 * @param digits Set of digits to disable/enable from
	 * @param digitID Define, what digit should be disabled
	 */
	public void setDigitTaken(ArrayList<OverTenDigit> digits, int digitID) {
		digits.get(digitID).setTaken(true);
	}

	
	

	/**
	 * @param r the new round
	 */
	public void setRound(int r) {
		this.round = r;
	}
	
	
	
	/**
	 * @return Returns the round
	 */
	public int getRound() {
		return this.round;
	}
	
	
	
	/**
	 * Set maximal number of rounds to play
	 * @param rounds Max rounds to play
	 */
	public void setMaxRound(int rounds) {
		this.maxRound = rounds;
	}
	
	
	
	/**
	 * Returns maximal number of rounds to play
	 * @return max rounds to play
	 */
	public int getMaxRound() {
		return this.maxRound;
	}


	
	/**
	 * Adds a new player to the game
	 * @param newName The new player's name
	 * @param uid The new player's ID
	 * @return Returns the new number of players
	 */
	public int addPlayer(String newName, int uid) {
		int countSameName = 1;
		for (Player player: players){
			// to ensure different namesremovePlayer
			if (newName.equals(player.getName())){
				countSameName++;
				// TODO: only a solution if number of players with same name < 10
				if (countSameName > 2)
					newName = newName.substring(0, newName.length()-2) + " " + countSameName;
				else
					newName = newName + " " + countSameName;
			}
		}
		OverTenPlayer newPlayer = new OverTenPlayer();
		newPlayer.setName(newName);
		((OverTenPlayer) newPlayer).setColorId(players.size());
		newPlayer.setUid(uid);
		players.add(newPlayer);
		return players.size();
	}
	
	
	/**
	 * Returns a list of playernames
	 * @return Playernames as List
	 */
	public ArrayList<String> getPlayerNamesList() {
		ArrayList<String> playerNamesList = new ArrayList<String>();
		for (Player player: players)
			playerNamesList.add(player.getName());
		return playerNamesList;
	}


	
}
