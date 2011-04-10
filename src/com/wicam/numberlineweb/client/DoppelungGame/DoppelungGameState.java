package com.wicam.numberlineweb.client.DoppelungGame;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;

public class DoppelungGameState extends GameState {

	@Override
	public int addPlayer(String newName) {
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
		DoppelungGamePlayer newPlayer = new DoppelungGamePlayer();
		newPlayer.setName(newName);
		players.add(newPlayer);
		return players.size();
	}

}
