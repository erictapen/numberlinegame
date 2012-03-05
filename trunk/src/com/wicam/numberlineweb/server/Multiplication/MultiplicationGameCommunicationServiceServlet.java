package com.wicam.numberlineweb.server.Multiplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.core.client.GWT;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationAnswer;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameCommunicationService;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameState;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
//import com.wicam.numberlineweb.server.logging.Logger.LogActionTrigger;
//import com.wicam.numberlineweb.server.logging.Logger.LogActionType;
//import com.wicam.numberlineweb.server.logging.NumberLineGameHandicap;

public class MultiplicationGameCommunicationServiceServlet extends
		GameCommunicationServiceServlet implements MultiplicationGameCommunicationService {
	
	
	// Random generator
	private Random rand = new Random();
	
	// The string used as multiplication sign
	private String sign = " x ";
	
	// All possible divisors
	private int[] possibleDivisors = {2,3,4,5,6,7,8,9};
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7200332323767902482L;
	private ArrayList<Integer> npcIds = new ArrayList<Integer>();
	
	public MultiplicationGameCommunicationServiceServlet() {
		
		super("multiplication");
		//this.handicapAdjustment = new NumberLineGameHandicap();
		
	}
	
	@Override
	protected void addNPC(GameState game){
		int playerid = game.addPlayer("NPC", -2);
		npcIds.add(playerid);
		new MultiplicationNPC(this, game.getId(), playerid);
	}
	
	protected boolean isNPC(int playerId){
		return npcIds.contains(playerId);
	}
	
	
	/**
	 * @param state MultiplicationGameState to alter
	 * @return The new MultiplicationGameState
	 */
	public MultiplicationGameState newResults(MultiplicationGameState state) {
		
		ArrayList<MultiplicationAnswer> answers = new ArrayList<MultiplicationAnswer>();
		
		int first = getRandomDivisor();
		int second = getRandomDivisor();
		int result = first * second;
		
		int noOfAnswers = 0;
		int randomNumber = rand.nextInt(10);
		
		// generate new answers as long there are less than 12
		while (noOfAnswers < 12) {
			int a = getRandomDivisor();
			int b = getRandomDivisor();
			
			// insert at least one right answer by chance
			if (noOfAnswers == randomNumber) {
				answers.add(new MultiplicationAnswer(first+this.sign+second, true));
				noOfAnswers++;
			}
			
			// genarate new answer
			MultiplicationAnswer newAnswer = new MultiplicationAnswer(a+this.sign+b, (a*b == result));
			
			// check, if the answer exists. if not, add to list
			if (!answerExists(newAnswer, answers)) {
				answers.add(newAnswer);
				noOfAnswers++;
			}
			
		}
		
		// increment the round-counter
		state.setRound(state.getRound()+1);
		
		state.setResult(result);
		
		state.setAnswers(answers);
		
		return state;
	}
	
	
	/**
	 * @return Returns a random divisor
	 */
	private int getRandomDivisor() {
		return this.possibleDivisors[this.rand.nextInt(this.possibleDivisors.length)];
	}
	
	
	
	/**
	 * @param newAnswer Answer to check
	 * @param answers All answers
	 * @return Returns true, if the newAnswer is in answers
	 */
	private boolean answerExists(MultiplicationAnswer newAnswer, ArrayList<MultiplicationAnswer> answers) {
		for (MultiplicationAnswer answer : answers) {
			if (answer.isEqualTo(newAnswer)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * Deletes a given answer
	 * @param toDelete The answer to delete
	 * @param answers Set of answers to delete from
	 * @return Returns, if the answer was deleted
	 */
	private ArrayList<MultiplicationAnswer> deleteAnswer(String toDelete, ArrayList<MultiplicationAnswer> answers) {
		for (MultiplicationAnswer answer : answers) {
			if (answer.getAnswer() == toDelete) {
				answer.setTaken();
			}
		}
		return answers;
	}
	
	
	/**
	 * @param toDelete Answer to delete
	 * @param state GameState to delete from
	 */
	public void deleteAnswer(String toDelete, MultiplicationGameState state) {
		state.setAnswers(deleteAnswer(toDelete, state.getAnswers()));
	}
	
	
	/**
	 * @param toFind Answer to be checked
	 * @param answers All answers
	 * @return Returns 1, if the given answer was a correct and free one. 
	 * 		   Returns 0, if user was too slow and answer is already taken. 
	 * 		   Returns -1, if answer was a false and free one
	 */
	private int checkAnswer(String toFind, ArrayList<MultiplicationAnswer> answers) {
		for (MultiplicationAnswer answer : answers) {
			if (answer.getAnswer().equals(toFind)) {				
				if (answer.isTaken()) {
					return 0;
				} else {
					if (answer.isCorrect()) {
						return 1;
					} else {
						return -1;
					}
				}
			}
		}
		return 0;
	}
	
	
	private boolean allCorrectTaken(ArrayList<MultiplicationAnswer> answers) {
		Boolean res = false;
		for (MultiplicationAnswer answer : answers) {
			res = res || (answer.isCorrect() && !answer.isTaken());
		}
		return res;
	}
	
	
	/**
	 * @param clicked gameid:playerid:clickedAnswer
	 * @return New MultiplicationGameState
	 */
	synchronized public MultiplicationGameState clickedAt(String clicked) {
		
		int gameid = Integer.parseInt(clicked.split(":")[0]);
		return clickedAt(clicked,getPlayerId(gameid));
		
	}

		

	/**
	 * User has clicked.
	 * @param clicked gameid:playerid:clickedAnswer
	 * @param playerid Playerid
	 * @return New MultiplicationGameState
	 */
	synchronized public MultiplicationGameState clickedAt(String clicked, int playerid) {

	
		int gameid = Integer.parseInt(clicked.split(":")[0]);

		String answer = clicked.split(":")[2];
		MultiplicationGameState g = (MultiplicationGameState) this.getGameById(gameid);

		HttpServletRequest request = this.getThreadLocalRequest();
		int uid = -2;
		if(request != null){
			HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>> map = (HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>) 
			request.getSession().getAttribute("game2pid2uid");
			uid = map.get(internalName).get(gameid).get(playerid);
		}
		
		// check answers an flag taken answers
		int answerState = checkAnswer(answer, g.getAnswers());
		
		System.out.println(answerState + ":" + answer);

		// give/take points
		this.getGameById(gameid).setPlayerPoints(playerid,this.getGameById(gameid).getPlayerPoints(playerid) + answerState);

		
		if (!allCorrectTaken(g.getAnswers())) { // keep it going
			this.setGameState(this.getGameById(gameid),3);
			this.setChanged(gameid);
		} else { // New round
			this.setGameState(this.getGameById(gameid),5);
		}
		
		if (g.getRound() == g.getMaxRound()){
			this.endGame(gameid);
			this.handicapAction(gameid);
		}
		else
			this.showNextItem(gameid);
		
		
//		if (!g.isPlayerClicked(playerid)){
//			
//			//int number = this.rawPosToReal(clickedAt, g);
//			/*if(!this.isNPC(playerid)){
//				if(uid != -2)
//					this.logger.log(g.getId(), uid, System.currentTimeMillis(), LogActionType.NUMBERLINE_SUCCESSFUL_CLICK,
//							"{\"number\" : " + number + "}", this.getClass().getName(), LogActionTrigger.USER);
//			}
//			else
//				this.logger.log(g.getId(), System.currentTimeMillis(), LogActionType.NUMBERLINE_NPC_GUESS, 
//						"{\"number\" : " + number + "}", this.getClass().getName(), LogActionTrigger.NPC);
//				*/
//			
//			boolean posIsFree = true;
//			for (int i = 0; i < g.getPlayers().size(); i++){
//				if (i+1 != playerid){
//					//int posOtherPlayer = g.getPlayerActPos(i+1);
//				
////					if (!(posOtherPlayer == Integer.MIN_VALUE) && Math.abs(clickedAt - posOtherPlayer) < g.getPointerWidth())
////						posIsFree = false;
//						
//				}
//			}
//			if (posIsFree){
//				//save position for playerid
//				g.setPlayerActPos(playerid,clickedAt);
//				g.setPlayerClicked(playerid);
//				// change state if waiting for other player
//				boolean otherPlayersClicked = true;
//				for (int i = 0; i < g.getPlayers().size(); i++){
//					if (i+1 != playerid)
//						if (!g.isPlayerClicked(i+1))
//							otherPlayersClicked = false;
//				}
//				if (!otherPlayersClicked){
//					this.setGameState(this.getGameById(gameid),4);
//					this.setChanged(gameid);
//				}
//			}
//			else {
//				this.setGameState(this.getGameById(gameid),4);
//				this.setChanged(gameid);
//				
//				/*if(uid != -2)
//					this.logger.log(uid, System.currentTimeMillis(), LogActionType.NUMBERLINE_POSITION_TAKEN,
//							"{\"number\" : " + number + "}", this.getClass().getName(), LogActionTrigger.USER);
//			*/}
//		}
//
//		boolean allPlayersClicked = true;
//		for (int i = 0; i < g.getPlayers().size(); i++)
//			if (!g.isPlayerClicked(i+1))
//				allPlayersClicked = false;
//		
//		if (allPlayersClicked){
//			
//			this.setGameState(this.getGameById(gameid),5);
//
//			// reset clicked state
//			g.resetPlayersClicked();
//
//			// reset ready state
//			g.resetReadyness();
//
//			//who has won?
//
//			ArrayList<Integer> playersWithMinDiff = new ArrayList<Integer>();
//			double minDiff = Double.MAX_VALUE;
//			for (int i = 0; i < g.getPlayers().size(); i++){
//				// operate on real position
//				double curDiff = Math.abs(g.getPlayerActPos(i+1)- this.realPosToRaw(g.getExerciseNumber(),g));
//				if (curDiff < minDiff){
//					minDiff = curDiff;
//					// reset list
//					playersWithMinDiff = new ArrayList<Integer>();
//					// add player
//					playersWithMinDiff.add(i+1);
//				}
//				// add other player
//				else if (curDiff == minDiff)
//					playersWithMinDiff.add(i+1);
//			}
//			
//			if (playersWithMinDiff.size() > 1){
//				//draw
//				g.setWinnerOfLastRound(0);
//				System.out.println("Unentschieden :)");
//				for (Integer i: playersWithMinDiff){
//					this.getGameById(gameid).setPlayerPoints(i,this.getGameById(gameid).getPlayerPoints(i) +1);
//				}
//			}else {
//				// one player best
//				
//				g.setWinnerOfLastRound(playersWithMinDiff.get(0));
//				this.getGameById(gameid).setPlayerPoints(playersWithMinDiff.get(0),this.getGameById(gameid).getPlayerPoints(playersWithMinDiff.get(0)) +1);
//				System.out.println(this.getGameById(gameid).getPlayerName(playersWithMinDiff.get(0))+ " hat gewonnen");
//			}
//			
//			//restart 
//			if (g.getRound() == g.getMaxRound()){
//				this.endGame(gameid);
//				this.handicapAction(gameid);
//			}
//			else
//				this.showNextItem(gameid);
//
//		}

		g.setServerSendTime(System.currentTimeMillis());
		return g;
	}


	private void handicapAction(int gameid) {
		
		//TODO The current formula is just a proof of concept. 
		//At the moment, it will produce bad handicaps for
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
		 * 4) The user gets 30 points if he won the game.
		 * 
		 * 
		 * ==> This results in a minimal score of 20 and a maximal score of 415 points. Normalizing this value yields
		 * the handicap where 1.0 is the best and 0.0 the worst possible value.
		 */
		
		MultiplicationGameState numberlineGameState = (MultiplicationGameState) this.getGameById(gameid);
		ArrayList<? extends Player> players = numberlineGameState.getPlayers();
		Collections.sort(players);
		
		int winnerUid = players.get(0).getUid();
		
		//TODO Find a reasonable use for number range.
		//int numRange = (numberlineGameState.getNumberRange().getMaxNumber() - numberlineGameState.getNumberRange().getMinNumber());
		
		int numPlayers = numberlineGameState.getPlayerCount();
		int numRounds = numberlineGameState.getMaxRound(); //Number of items equals number of rounds
		
		//General game properties that are not influenced by user performance
		//but contribute to the score
		double gamePropertyScore = 5*numPlayers + 2*numRounds;
		
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
				
				//this.logger.log(gameid, player.getUid(), System.currentTimeMillis(), LogActionType.NUMBERLINE_HANDICAP,
					//"{\"handicap\" :" + userHandicapNormalized + "}", this.getClass().getName(), LogActionTrigger.USER);
			}
		}
		
	}


	@Override
	public GameState openGame(GameState g) {
		
		g.setServerSendTime(System.currentTimeMillis());
		GWT.log("before opening game");
		GameState retGameState = super.openGame(g);
		GWT.log("after opening game");
		//return super.openGame(g);
		
		newResults((MultiplicationGameState) g);
		
		return retGameState;

	}
	
	public void showNextItem(int id) {
		
		Timer t = new Timer();
		t.schedule(new MultiplicationGameStateTask(id, 6, this), 6000);
	}

	public String getGameProperties(GameState gameState) {
		
		MultiplicationGameState numberlineGameState = (MultiplicationGameState) gameState;
		
		String gamePropertiesStr = "{";
				
		gamePropertiesStr += "num_players : " + numberlineGameState.getPlayerCount() + ", ";
		
		//TODO gameproperties
		
		gamePropertiesStr += "}";
		
		return gamePropertiesStr;
		
	}
	
	
}
