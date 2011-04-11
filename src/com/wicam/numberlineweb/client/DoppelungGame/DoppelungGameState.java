package com.wicam.numberlineweb.client.DoppelungGame;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;

public class DoppelungGameState extends GameState {

	private DoppelungGameWord curWord = new DoppelungGameWord();
	private boolean correctAnswered = false; // for the feedback
	
	public void setNextWord(DoppelungGameWord word){
		curWord = word;
	}
	
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
	
	public void setStartButtonClicked(int playerid, boolean startButtonClicked) {
		((DoppelungGamePlayer)players.get(playerid-1)).setStartButtonClicked(startButtonClicked);
	}

	public boolean hasStartButtonClicked(int playerid) {
		if (playerid-1 < players.size())
			((DoppelungGamePlayer)players.get(playerid-1)).isStartButtonClicked();
		return false;
	}

	public void setCurWord(DoppelungGameWord curWord) {
		this.curWord = curWord;
	}

	public DoppelungGameWord getCurWord() {
		return curWord;
	}

	public void setCorrectAnswered(boolean correctAnswered) {
		this.correctAnswered = correctAnswered;
	}

	public boolean isCorrectAnswered() {
		return correctAnswered;
	}

}
