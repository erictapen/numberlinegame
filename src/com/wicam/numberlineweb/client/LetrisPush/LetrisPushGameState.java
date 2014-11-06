package com.wicam.numberlineweb.client.LetrisPush;

import java.util.ArrayList;
import java.util.HashMap;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;

/**
 * Class that carries the game state of the LeTris game with its letter blocks.
 * @author timfissler
 *
 */

public class LetrisPushGameState extends GameState {
	
	public int addPlayer(String newName, int uid, String connectionID) {
		newName = checkDuplicateName(newName);
		LetrisPushGamePlayer newPlayer = new LetrisPushGamePlayer();
		newPlayer.setName(newName);
		newPlayer.setUid(uid);
		newPlayer.setConnectionID(connectionID);
		players.add(newPlayer);
		return players.size();
	}

	@Override
	public int addPlayer(String newName, int uid) {
		return addPlayer(newName, uid, "");
	}
	
}
