package com.wicam.numberlineweb.server.NumberLineGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import com.wicam.numberlineweb.client.GameJoinException;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameCommunicationService;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGamePlayer;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.logging.GameLogger;
import com.wicam.numberlineweb.server.logging.GameLogger.LogActionTrigger;
import com.wicam.numberlineweb.server.logging.GameLogger.LogActionType;
import com.wicam.numberlineweb.server.logging.NumberLineGameHandicap;

public class NumberLineGameCommunicationServiceServlet extends
		GameCommunicationServiceServlet implements NumberLineGameCommunicationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7200332323767902482L;
	private ArrayList<Integer> npcIds = new ArrayList<Integer>();
	private Map<Integer, Integer> npcId2Elo = new HashMap<Integer, Integer>();
	
	public NumberLineGameCommunicationServiceServlet() {
		
		super("numerlinegame");
		this.handicapAdjustment = new NumberLineGameHandicap();
		
	}
	
	@Override
	protected void addNPC(GameState game){
		
		//TODO NPC is assigned an user ID of -5. A better
		//solution would be using reserved user IDs.
		
		int playerid = game.addPlayer("NPC", -5);
		
		npcIds.add(playerid);
		
		int[] eloNumbers = {500, 750, 1000, 1250, 1500};
		
		int npcEloNumber  = eloNumbers[(int) (Math.random() * eloNumbers.length)];
		
		npcId2Elo.put(playerid, npcEloNumber);
		
		new NumberLineGameNPC(this, game.getId(), playerid, npcEloNumber);
		
		System.out.println("NPC with ELO " + npcEloNumber + " was selected.");
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

		GameLogger logger = this.gameId2Logger.get(game.getId());
		
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
		
		logger.log(game.getId(), System.currentTimeMillis(), LogActionType.NUMBERLINE_NUMBER_PRESENTED, 
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

		GameLogger logger = this.gameId2Logger.get(gameid);
		
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
					logger.log(g.getId(), uid, System.currentTimeMillis(), LogActionType.NUMBERLINE_SUCCESSFUL_CLICK,
							"{\"number\" : " + number + "}", this.getClass().getName(), LogActionTrigger.USER);
			}
			else
				logger.log(g.getId(), System.currentTimeMillis(), LogActionType.NUMBERLINE_NPC_GUESS, 
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
					logger.log(g.getId(), uid, System.currentTimeMillis(), LogActionType.NUMBERLINE_POSITION_TAKEN,
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
				this.updateEloRating(gameid);
				this.endGame(gameid);
			}
			else
				this.showNextItem(gameid);

		}

		g.setServerSendTime(System.currentTimeMillis());
		return g;
	}
	
	private void updateEloRating(int gameid) {
		
		NumberLineGameState numberlineGameState = (NumberLineGameState) this.getGameById(gameid);
		ArrayList<? extends Player> players = numberlineGameState.getPlayers();
		
		GameLogger logger = this.gameId2Logger.get(gameid);
		
		//Two players
		if (players.size() == 2){
		
			Player player1 = players.get(0);
			Player player2 = players.get(1);
			
			int player1Elo;
			if (!this.isNPC(player1.getColorId() + 1))
				player1Elo = logger.getEloRating(player1.getUid());
			else
				player1Elo = this.npcId2Elo.get(player1.getColorId() + 1);
			
			int player2Elo;
			if (!this.isNPC(player2.getColorId() + 1))
				player2Elo = logger.getEloRating(player2.getUid());
			else
				player2Elo = this.npcId2Elo.get(player2.getColorId() + 1);
			
			this.calculateElo(gameid, player1, player2, player1Elo, player2Elo, false);
		
		}
		//More than two players
		else {
			
			//Compute the arithmetic mean of ELO values of all other players
			
			double sumEloValues = 0.0;
			double sumPoints = 0.0;
			int averageElo = 0; 
			double averagePoints = 0.0;
			
			//Create a dummy player
			Player dummyPlayer = new NumberLineGamePlayer();
			dummyPlayer.setUid(-5);
			
			//Copy list of players to avoid concurrent modification exception
			ArrayList<Player> playersCopy = (ArrayList<Player>) players.clone();
			
			for (Player player : players) {
				
				//If player is not NPC
				if (player.getUid() != -5) {
				
					playersCopy.remove(player);
					
					for (Player otherPlayer : playersCopy) {
						if (!this.isNPC(otherPlayer.getColorId() + 1))
							sumEloValues += logger.getEloRating(otherPlayer.getUid());
						else
							sumEloValues += this.npcId2Elo.get(otherPlayer.getColorId() + 1);
						sumPoints += otherPlayer.getPoints();
					}
					
					averageElo = (int) (sumEloValues / (double) playersCopy.size()); 
					averagePoints = sumPoints / (double) playersCopy.size();
					
					dummyPlayer.setPoints((int) averagePoints);
					
					int playerElo = logger.getEloRating(player.getUid());
						
					this.calculateElo(gameid, player, dummyPlayer, playerElo, averageElo, true);
					
					playersCopy.add(player);
					
					sumEloValues = 0.0;
					sumPoints = 0.0;
					averageElo = 0; 
					averagePoints = 0.0;
				
				}
				
			}
				
		}
		
	}
	
	private void calculateElo(int gameid, Player player1, Player player2, int player1Elo, int player2Elo, boolean isMultiplayer){
		
		GameLogger logger = this.gameId2Logger.get(gameid);
		
		/* 
		 * Computation of new ELO numbers according to http://en.wikipedia.org/wiki/Elo_rating_system
		 * 
		 */
		
		//Factor k used in calculating the updated ELO number
		int k = 15;
		
		//Expected score for first player
		double exp1 = (player2Elo - player1Elo) / 400.0;
		double player1ExpectedScore = 1 / (1 + (Math.pow(10, exp1)));
		
		double player1GameOutcome;
		if (player1.getPoints() > player2.getPoints())
			player1GameOutcome = 1;
		else {
			if (player1.getPoints() < player2.getPoints())
				player1GameOutcome = 0;
			else
				player1GameOutcome = 0.5;
			
		}
		
		int player1NewEloNumber = (int) (player1Elo + k * (player1GameOutcome - player1ExpectedScore));
		
		//Expected score for second player
		double exp2 = (player1Elo - player2Elo) / 400.0;
		double player2ExpectedScore = 1 / (1 + (Math.pow(10, exp2)));
	
		double player2GameOutcome = 1.0 - player1GameOutcome;
		
		int player2NewEloNumber = (int) (player2Elo + k * (player2GameOutcome - player2ExpectedScore));
		
		System.out.println("Expected score for second player with user ID " + player2.getUid() + ": "
				+ player2ExpectedScore);
		System.out.println("Game outcome for second player: " + player2GameOutcome);
		System.out.println("Updated ELO value for second player: " + player2NewEloNumber);
		
		if (!this.isNPC(player1.getColorId() + 1) && !this.isNPC(player2.getColorId() + 1)) {
			logger.updateEloRating(player1.getUid(), player1NewEloNumber);
			
			//By default, the second player in a multiplayer game is a dummy player
			if (!isMultiplayer)
				logger.updateEloRating(player2.getUid(), player2NewEloNumber);
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
		
		Timer t = new Timer(true);
		t.schedule(new SetNumberLineGameStateTask(id, 6, this), 6000);
	}

	public String getGameProperties(GameState gameState) {
		
		NumberLineGameState numberlineGameState = (NumberLineGameState) gameState;
		
		String gamePropertiesStr = "{";
				
		gamePropertiesStr += "num_players : " + numberlineGameState.getPlayerCount() + ", ";
		
		gamePropertiesStr += "item_count : " + numberlineGameState.getItemCount() + ", ";
		
		gamePropertiesStr += "min_number : " + numberlineGameState.getNumberRange().getMinNumber() + ", ";
		
		gamePropertiesStr += "max_number : " + numberlineGameState.getNumberRange().getMaxNumber();
		
		gamePropertiesStr += "}";
		
		return gamePropertiesStr;
		
	}

	public boolean startBenchmarkGame(GameState gameState) {
		
		GameState g = this.openGame(gameState);

		String ids = null;
		try {
			ids = this.joinGameWithoutTimeout(g.getId() + ":" + "___ID/" + g.getGameOpenedUserId());
		} catch (GameJoinException e) {
			e.printStackTrace();
			return false;
		}
		int gameid = Integer.parseInt(ids.split(":")[0]);
		int playerid = Integer.parseInt(ids.split(":")[1]);
		
		new NumberLineGameNPC(this, gameid, playerid);
		
		return true;
	}


}
