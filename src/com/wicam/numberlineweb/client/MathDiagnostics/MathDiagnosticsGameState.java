package com.wicam.numberlineweb.client.MathDiagnostics;

import com.wicam.numberlineweb.client.GameState;

public class MathDiagnosticsGameState extends GameState{
	
	// indicate which task is played
	private int task = 0;
	
	@Override
	public int addPlayer(String newName) {
		newName = checkDuplicateName(newName);
		MathDiagnosticsPlayer newPlayer = new MathDiagnosticsPlayer();
		newPlayer.setName(newName);
		players.add(newPlayer);
		return players.size();
	}

	public void setTask(int task) {
		this.task = task;
	}

	public int getTask() {
		return task;
	}
	
	public void setPlayerMeanRT(int playerid, int meanRT) {
		if(playerid >= 1 && playerid-1 < players.size()){
			MathDiagnosticsPlayer player = (MathDiagnosticsPlayer) players.get(playerid-1);
			player.setMeanRT(meanRT);
		}
	}
	
	public int getPlayerMeanRT(int playerid) {
		if(playerid >= 1 && playerid-1 < players.size()){
			MathDiagnosticsPlayer player = (MathDiagnosticsPlayer) players.get(playerid-1);
			return player.getMeanRT();
		}
		return -1;
	}

}
