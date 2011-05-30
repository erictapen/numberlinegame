package com.wicam.numberlineweb.client.factsGame;

import com.wicam.numberlineweb.client.GameState;

public class FactsGameState extends GameState {

	@Override
	public int addPlayer(String newName) {
		newName = checkDuplicateName(newName);
		FactsGamePlayer newPlayer = new FactsGamePlayer();
		newPlayer.setName(newName);
		players.add(newPlayer);
		return players.size();
	}

}
