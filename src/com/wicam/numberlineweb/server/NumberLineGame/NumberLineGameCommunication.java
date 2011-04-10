package com.wicam.numberlineweb.server.NumberLineGame;

import java.util.ArrayList;

import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;
import com.wicam.numberlineweb.server.GameCommunication;

public class NumberLineGameCommunication {

	private GameCommunication gameComm;
	
	public NumberLineGameCommunication(GameCommunication gameComm) {
		this.gameComm = gameComm;
	}
	
	
	/**
	 * Calculate new number & new exercise
	 * @param game
	 */
	static void newNumbers(NumberLineGameState game) {

		int leftNumber;
		int rightNumber;
		int exerciseNumber;

		if (game.getNumberRange().isRandom()){
			// not the full range for minNumber
			leftNumber = game.getNumberRange().getMinNumber() + (int) (Math.random() * (game.getNumberRange().getMaxNumber()*9/10 - game.getNumberRange().getMinNumber()));
			rightNumber = (int) (Math.random() * (game.getNumberRange().getMaxNumber()-leftNumber)) + leftNumber;
		}
		else {
			leftNumber = game.getNumberRange().getMinNumber();
			rightNumber = game.getNumberRange().getMaxNumber();
		}
		
		exerciseNumber = leftNumber + ((int) (Math.random() * (rightNumber - leftNumber))) ;

		game.setLeftNumber(leftNumber);
		game.setRightNumber(rightNumber);
		game.setExerciseNumber(exerciseNumber);

	}

	/**
	 * User has clicked. Again, the format for clicked ist
	 * <gameid>:<playerid>:<clickPosition>
	 */
	synchronized public NumberLineGameState clickedAt(String clicked) {

		int playerid = Integer.parseInt(clicked.split(":")[1]);
		int gameid = Integer.parseInt(clicked.split(":")[0]);
		int clickedAt = Integer.parseInt(clicked.split(":")[2]);
		NumberLineGameState g = (NumberLineGameState) gameComm.getGameById(gameid);

		
		
		if (!g.isPlayerClicked(playerid)){
			boolean posIsFree = true;
			for (int i = 0; i < g.getPlayers().size(); i++){
				if (i+1 != playerid){
					int posOtherPlayer = g.getPlayerActPos(i+1);
				
					if (!(posOtherPlayer == Integer.MIN_VALUE) && Math.abs(clickedAt - posOtherPlayer) < g.getPointerWidth())
						posIsFree = false;
						
				}
			}
			if (posIsFree){
				//save position for playerid
				g.setPlayerActPos(playerid,clickedAt);
				g.setPlayerClicked(playerid);
				// change state if waiting for other player
				boolean otherPlayersClicked = true;
				for (int i = 0; i < g.getPlayers().size(); i++){
					if (i+1 != playerid)
						if (!g.isPlayerClicked(i+1))
							otherPlayersClicked = false;
				}
				if (!otherPlayersClicked){
					gameComm.setGameState(gameComm.getGameById(gameid),4);
					gameComm.setChanged(gameid);
				}
			}
			else {
				gameComm.setGameState(gameComm.getGameById(gameid),4);
				gameComm.setChanged(gameid);
			}
		}

		boolean allPlayersClicked = true;
		for (int i = 0; i < g.getPlayers().size(); i++)
			if (!g.isPlayerClicked(i+1))
				allPlayersClicked = false;
		
		if (allPlayersClicked){
			gameComm.setGameState(gameComm.getGameById(gameid),5);

			// reset clicked state
			g.resetPlayersClicked();

			// reset ready state
			g.resetReadyness();

			//who has won?

			ArrayList<Integer> playersWithMinDiff = new ArrayList<Integer>();
			double minDiff = Double.MAX_VALUE;
			for (int i = 0; i < g.getPlayers().size(); i++){
				// operate on real position
				double curDiff = Math.abs(g.getPlayerActPos(i+1)- this.realPosToRaw(g.getExerciseNumber(),g));
				if (curDiff < minDiff){
					minDiff = curDiff;
					// reset list
					playersWithMinDiff = new ArrayList<Integer>();
					// add player
					playersWithMinDiff.add(i+1);
				}
				// add other player
				else if (curDiff == minDiff)
					playersWithMinDiff.add(i+1);
			}
			
			if (playersWithMinDiff.size() > 1){
				//draw
				g.setWinnerOfLastRound(0);
				System.out.println("Unentschieden :)");
				for (Integer i: playersWithMinDiff){
					gameComm.getGameById(gameid).setPlayerPoints(i,gameComm.getGameById(gameid).getPlayerPoints(i) +1);
				}
			}else {
				// one player best
				
				g.setWinnerOfLastRound(playersWithMinDiff.get(0));
				gameComm.getGameById(gameid).setPlayerPoints(playersWithMinDiff.get(0),gameComm.getGameById(gameid).getPlayerPoints(playersWithMinDiff.get(0)) +1);
				System.out.println(gameComm.getGameById(gameid).getPlayerName(playersWithMinDiff.get(0))+ " hat gewonnen");
			}

			//restart 
			if (g.getItemCount() == g.getMaxItems()){
				gameComm.endGame(gameid);
			}
			else
				gameComm.startGame(gameid);
		}

		return g;
	}


	/**
	 * converts a real cursor-position (the one that is displayed to the user) into
	 * a raw pixel-position
	 * @param pos
	 * @param g
	 * @return
	 */

	public int realPosToRaw(int pos, NumberLineGameState g) {

		return (int)((pos -g.getLeftNumber()) /  ((double)(g.getRightNumber() -g.getLeftNumber())/400));

	}

	/**
	 * converts the raw pixel-position to a user-readable value.
	 * @param pos
	 * @param g
	 * @return
	 */

	public int rawPosToReal(int pos,NumberLineGameState g) {

		return g.getLeftNumber() + (int) ((pos) *  ((double)(g.getRightNumber() - g.getLeftNumber())/400));


	}
}
