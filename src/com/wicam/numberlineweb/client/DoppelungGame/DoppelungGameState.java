package com.wicam.numberlineweb.client.DoppelungGame;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;

public class DoppelungGameState extends GameState {

	private DoppelungGameWord curWord = new DoppelungGameWord();
	public String player1movingto;
	public String player2movingto;
	
	public String enemyMovingTo(int playerid) {
		
		if (playerid==1) return player1movingto;
		else return player2movingto;
		
		
	}
	
	public void setEnemyMovingTo(int playerid, String to) {
		
		if (playerid==1) player1movingto = to;
		else player2movingto = to;
		
		
	}
	 
	
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
			return ((DoppelungGamePlayer)players.get(playerid-1)).isStartButtonClicked();
		return false;
	}

	public void setCurWord(DoppelungGameWord curWord) {
		this.curWord = curWord;
	}

	public DoppelungGameWord getCurWord() {
		return curWord;
	}

	public void setAnswer(int playerid, boolean answer) {
		if (playerid-1 < players.size())
			((DoppelungGamePlayer)players.get(playerid-1)).setAnswer(answer);
	}
	
	public boolean hasCorrectlyAnswered(int playerid) {
		if (playerid-1 < players.size())
			return ((DoppelungGamePlayer)players.get(playerid-1)).hasCorrectlyAnswered();
		return false;
	}
	
	public int getSoundTries(int playerid){
		if (playerid-1 < players.size())
			return ((DoppelungGamePlayer)players.get(playerid-1)).getSoundTries();
		return -1;
	}
	
	public void incSoundTries(int playerid){
		if (playerid-1 < players.size())
			((DoppelungGamePlayer)players.get(playerid-1)).incSoundTries();
	}
	
	public void resetSoundTries(int playerid){
		if (playerid-1 < players.size())
			((DoppelungGamePlayer)players.get(playerid-1)).resetSoundTries();
	}
	
	public int getWordTries(int playerid){
		if (playerid-1 < players.size())
			return ((DoppelungGamePlayer)players.get(playerid-1)).getWordTries();
		return -1;
	}
	
	public void incWordTries(int playerid){
		if (playerid-1 < players.size())
			((DoppelungGamePlayer)players.get(playerid-1)).incWordTries();
	}
	
	public void resetWordTries(int playerid){
		if (playerid-1 < players.size())
			((DoppelungGamePlayer)players.get(playerid-1)).resetWordTries();
	}
	
	public boolean getShowSoundFeedback(int playerid){
		if (playerid-1 < players.size())
			return ((DoppelungGamePlayer)players.get(playerid-1)).isShowSoundFeedback();
		return false;
	}
	
	public void setShowSoundFeedback(int playerid, boolean showSoundFeedback){
		if (playerid-1 < players.size())
			((DoppelungGamePlayer)players.get(playerid-1)).setShowSoundFeedback(showSoundFeedback);
	}
	
	public boolean getShowWordFeedback(int playerid){
		if (playerid-1 < players.size())
			return ((DoppelungGamePlayer)players.get(playerid-1)).isShowWordFeedback();
		return false;
	}
	
	public void setShowWordFeedback(int playerid, boolean showWordFeedback){
		if (playerid-1 < players.size())
			((DoppelungGamePlayer)players.get(playerid-1)).setShowWordFeedback(showWordFeedback);
	}
	
	public void setEndedShortVowelGame(int playerid, boolean endedShortVowelGame){
		if (playerid-1 < players.size())
			((DoppelungGamePlayer)players.get(playerid-1)).setEndedShortVowelGame(endedShortVowelGame);
	}
	
	public boolean isEndedShortVowelGame(int playerid){
		if (playerid-1 < players.size())
			return ((DoppelungGamePlayer)players.get(playerid-1)).isEndedShortVowelGame();
		return false;
	}
}
