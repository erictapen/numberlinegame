package com.wicam.numberlineweb.client.BuddyNumber;

import java.io.Serializable;
import java.util.ArrayList;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;


/**
 * This is the state sent by the server. Holds all game information.
 * @author alex
 *
 */

public class BuddyNumberGameState extends GameState implements Serializable{

	// we need a serial number for GWT to identify sent instances
	private static final long serialVersionUID = 846864907276321588L;

	// All community-digits
	private ArrayList<BuddyNumberDigit> communityDigits = new ArrayList<BuddyNumberDigit>();

	// All hand-digits
	private ArrayList<BuddyNumberDigit> handDigits = new ArrayList<BuddyNumberDigit>();
	
	// Round-counter
	private int round = 0;
	// Max number of rounds to play
	private int maxRound;
	
	public final static String[] playerColors = {"blue", "Magenta", "orange", "DarkGreen", "DarkCyan"};
	
	
	public BuddyNumberGameState() {
		
	}
	
	
	
	/**
	 * @return Returns communityDigits
	 */
	public ArrayList<BuddyNumberDigit> getCommunityDigits() {
		return communityDigits;
	}



	/**
	 * Set communityDigits
	 * @param communityDigits Set communityDigits to this value
	 */
	public void setCommunityDigits(ArrayList<BuddyNumberDigit> communityDigits) {
		this.communityDigits = communityDigits;
	}



	/**
	 * @return Returns handDigits
	 */
	public ArrayList<BuddyNumberDigit> getHandDigits() {
		return handDigits;
	}



	/**
	 * Set handDigits
	 * @param handDigits Set handDigits to this value
	 */
	public void setHandDigits(ArrayList<BuddyNumberDigit> handDigits) {
		this.handDigits = handDigits;
	}


	
	/**
	 * @param state ID of the player to be looked up
	 * @return Returns the hand-digit the user has clicked on
	 */
	public int getPlayerClickedOn(int playerID) {
		return ((BuddyNumberPlayer) players.get(playerID-1)).getClickedOn();
	}
	
	
	/**
	 * Set the hand-digit a user clicked on
	 * @param state ID of the player to be updated
	 * @param clickedOn hand-digit the player clicked on
	 */
	public void setPlayerClickedOn(int playerID, int clickedOn) {
		((BuddyNumberPlayer) players.get(playerID-1)).setClickedOn(clickedOn);
	}
	
	
	
	
	/**
	 * @param correct Need correct answers?
	 * @return All "correct" answers as handValue:handID:communityValue:communityID
	 */
	public ArrayList<String> getSpecificAnswers(boolean correct) {
		
		ArrayList<String> res = new ArrayList<String>();
		
		for (BuddyNumberDigit hand : handDigits) {
			int communityID = 0;
			for (BuddyNumberDigit community : communityDigits) {
				int handID = 0;
				if (!community.isTaken()) {
					if ((hand.getValue() + community.getValue() == 10) == correct) {
						res.add(hand.toString() + ":" + handID + ":" + community.toString() + ":" + communityID);
					}
					handID++;
				}
				communityID++;
			}
		}
		
		return res;
	}
	
	
	
	/**
	 * Disables/Enables all digits in a set of digits
	 * @param digits Set of digits to disable/enable
	 * @param taken Define, if all digits are taken or not
	 */
	public void setAllDigitsTakenIn(ArrayList<BuddyNumberDigit> digits, boolean taken) {
		for (BuddyNumberDigit digit : digits) {
			digit.setTaken(taken);
		}
	}



	/**
	 * Disables/Enables one digit in a set of digits
	 * @param digits Set of digits to disable/enable from
	 * @param digitID Define, what digit should be disabled
	 */
	public void setDigitTaken(ArrayList<BuddyNumberDigit> digits, int digitID, int uid) {
		digits.get(digitID).setTaken(true);
		int temp = uid;
		while (temp>playerColors.length) {
			temp -= playerColors.length;
		}
		digits.get(digitID).setColor(playerColors[temp-1]);
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
	@Override
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
		BuddyNumberPlayer newPlayer = new BuddyNumberPlayer();
		newPlayer.setName(newName);
		newPlayer.setColorId(players.size());
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
