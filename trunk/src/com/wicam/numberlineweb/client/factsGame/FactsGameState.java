package com.wicam.numberlineweb.client.factsGame;

import com.wicam.numberlineweb.client.GameState;

public class FactsGameState extends GameState {

	@Override
	public int addPlayer(String newName, int uid) {
		newName = checkDuplicateName(newName);
		FactsGamePlayer newPlayer = new FactsGamePlayer();
		newPlayer.setName(newName);
		newPlayer.setUid(uid);
		players.add(newPlayer);
		return players.size();
	}

}
