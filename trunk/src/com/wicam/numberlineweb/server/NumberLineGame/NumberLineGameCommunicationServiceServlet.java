package com.wicam.numberlineweb.server.NumberLineGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCommunicationService;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.logging.Logger.LogActionTrigger;
import com.wicam.numberlineweb.server.logging.Logger.LogActionType;
import com.wicam.numberlineweb.server.logging.Logger.LogGame;
import com.wicam.numberlineweb.server.logging.NumberLineGameHandicap;

public class NumberLineGameCommunicationServiceServlet extends
		GameCommunicationServiceServlet implements NumberLineGameCommunicationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7200332323767902482L;
	private ArrayList<Integer> npcIds = new ArrayList<Integer>();
	
	public NumberLineGameCommunicationServiceServlet() {
		
		super("numerlinegame");
		this.handicapAdjustment = new NumberLineGameHandicap();
		
	}
	
	@Override
	protected void addNPC(GameState game){
		int playerid = game.addPlayer("NPC", -2);
		npcIds.add(playerid);
		new NumberLineGameNPC(this, game.getId(), playerid);
	}
	
	protected boolean isNPC(int playerId){
		return npcIds.contains(playerId);
	}
	
	/**
	 * Calculate new number & new exercise
	 * @param game
	 */
	public void newNumbers(NumberLineGameState game) {

		int leftNumber;
		int rightNumber;
		int exerciseNumber;

		if (game.getNumberRange().isRandom()){
			// not the full range for minNumber
			leftNumber = game.getNumberRange().getMinNumber() + (int) (Math.random() * (game.getNumberRange().getMaxNumber()*9/10 - game.getNumberRange().getMinNumber()));
			rightNumber = (int) (Math.random() * (game.getNumberRange().getMaxNumber()*9/10-leftNumber)) + game.getNumberRange().getMaxNumber()/10 + leftNumber;
		}
		else {
			leftNumber = game.getNumberRange().getMinNumber();
			rightNumber = game.getNumberRange().getMaxNumber();
		}
		
		exerciseNumber = leftNumber + ((int) (Math.random() * (rightNumber - leftNumber))) ;

		game.setLeftNumber(leftNumber);
		game.setRightNumber(rightNumber);
		game.setExerciseNumber(exerciseNumber);
		
		this.logger.log(game.getId(), System.currentTimeMillis(), LogActionType.NUMBERLINE_NUMBER_PRESENTED, 
				"{\"number\" : " + exerciseNumber + "}", this.getClass().getName(), LogActionTrigger.APPLICATION);
		
		
	}
	
	
	synchronized public NumberLineGameState clickedAt(String clicked) {
		
		int gameid = Integer.parseInt(clicked.split(":")[0]);
		return clickedAt(clicked,getPlayerId(gameid));
		
	}

		

	/**
	 * User has clicked. Again, the format for clicked is
	 * <gameid>:<playerid>:<clickPosition>
	 */
	synchronized public NumberLineGameState clickedAt(String clicked, int playerid) {

	
		int gameid = Integer.parseInt(clicked.split(":")[0]);

		int clickedAt = Integer.parseInt(clicked.split(":")[2]);
		NumberLineGameState g = (NumberLineGameState) this.getGameById(gameid);

		HttpServletRequest request = this.getThreadLocalRequest();
		int uid = -2;
		if(request != null){
			HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>> map = (HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>) 
					request.getSession().getAttribute("game2pid2uid");
			uid = map.get(internalName).get(gameid).get(playerid);
		}
		
		if (!g.isPlayerClicked(playerid)){
			
			int number = this.rawPosToReal(clickedAt, g);
			if(!this.isNPC(playerid)){
				if(uid != -2)
					this.logger.log(g.getId(), uid, System.currentTimeMillis(), LogActionType.NUMBERLINE_SUCCESSFUL_CLICK,
							"{\"number\" : " + number + "}", this.getClass().getName(), LogActionTrigger.USER);
			}
			else
				this.logger.log(g.getId(), System.currentTimeMillis(), LogActionType.NUMBERLINE_NPC_GUESS, 
						"{\"number\" : " + number + "}", this.getClass().getName(), LogActionTrigger.NPC);
				
			
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
					this.setGameState(this.getGameById(gameid),4);
					this.setChanged(gameid);
				}
			}
			else {
				this.setGameState(this.getGameById(gameid),4);
				this.setChanged(gameid);
				
				if(uid != -2)
					this.logger.log(uid, System.currentTimeMillis(), LogActionType.NUMBERLINE_POSITION_TAKEN,
							"{\"number\" : " + number + "}", this.getClass().getName(), LogActionTrigger.USER);
			}
		}

		boolean allPlayersClicked = true;
		for (int i = 0; i < g.getPlayers().size(); i++)
			if (!g.isPlayerClicked(i+1))
				allPlayersClicked = false;
		
		if (allPlayersClicked){
			
			this.setGameState(this.getGameById(gameid),5);

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
					this.getGameById(gameid).setPlayerPoints(i,this.getGameById(gameid).getPlayerPoints(i) +1);
				}
			}else {
				// one player best
				
				g.setWinnerOfLastRound(playersWithMinDiff.get(0));
				this.getGameById(gameid).setPlayerPoints(playersWithMinDiff.get(0),this.getGameById(gameid).getPlayerPoints(playersWithMinDiff.get(0)) +1);
				System.out.println(this.getGameById(gameid).getPlayerName(playersWithMinDiff.get(0))+ " hat gewonnen");
			}
			
			//restart 
			if (g.getItemCount() == g.getMaxItems()){
				this.endGame(gameid);
				this.handicapAction(gameid);
			}
			else
				this.showNextItem(gameid);

		}

		g.setServerSendTime(System.currentTimeMillis());
		return g;
	}


	private void handicapAction(int gameid) {
		
		//TODO At the moment, this formula will produce bad handicaps for
		//simpler games (e.g., when there are only few players and rounds) and good handicaps
		//for more complex games. 
		
		/*
		 * A user score is calculated by taking into account the following factors:
		 * 
		 * 1) Number of players (2 - 5)
		 *    Weight: 5
		 * 
		 * 2) Number of rounds (5 - 30)
		 *    Weight: 2
		 * 
		 * 3) Number of points (0 - 30)
		 *    Weight: 10
		 * 
		 * 4) Number range: A smaller number range makes increases handicap.
		 *    Weight: 1
		 * 
		 * 5) The user gets 30 points if he won the game.
		 * 
		 * 
		 * ==> This results in a minimal score of 20 and a maximal score of 415 points. Normalizing this value yields
		 * the handicap where 1.0 is the best and 0.0 the worst possible value.
		 */
		
		NumberLineGameState numberlineGameState = (NumberLineGameState) this.getGameById(gameid);
		ArrayList<? extends Player> players = numberlineGameState.getPlayers();
		Collections.sort(players);
		
		int winnerUid = players.get(0).getUid();
		
		int numRange = (numberlineGameState.getNumberRange().getMaxNumber() - numberlineGameState.getNumberRange().getMinNumber());
		int numPlayers = numberlineGameState.getPlayerCount();
		int numRounds = numberlineGameState.getMaxItems(); //Number of items equals number of rounds
		
		//General game properties that are not influenced by user's performance
		//but contribute to the score
		double gamePropertyScore = 5*numPlayers + 2*numRounds + (1 / numRange);
		
		for(Player player : numberlineGameState.getPlayers()){
			if(player.getUid() != -2){
				
				
				double hasWon = (player.getUid() == winnerUid) ? 30 : 0;
				double points = player.getPoints();
				
				double minimalScore = 20;
				double maximalScore = 415;
				
				double userScore = hasWon + 10*points + gamePropertyScore;
				
				/*
				 * Normalize score to get handicap.
				 */
				double userHandicapNormalized = (userScore - minimalScore) / (maximalScore - minimalScore);
				
				this.logger.log(gameid, player.getUid(), System.currentTimeMillis(), LogActionType.NUMBERLINE_HANDICAP,
					"{\"handicap\" :" + userHandicapNormalized + "}", this.getClass().getName(), LogActionTrigger.USER);
			}
		}
		
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

	@Override
	public GameState openGame(GameState g) {
		
		// initialize pointerwidth
		((NumberLineGameState) g).setPointerWidth(14);
		
		g.setServerSendTime(System.currentTimeMillis());
		
		GameState retGameState = super.openGame(g);
		
		//initialize first item
		newNumbers((NumberLineGameState) g);
		
		//return super.openGame(g);
		return retGameState;

	}
	
	public void showNextItem(int id) {
		
		Timer t = new Timer();
		t.schedule(new SetNumberLineGameStateTask(id, 6, this), 6000);
	}
}
