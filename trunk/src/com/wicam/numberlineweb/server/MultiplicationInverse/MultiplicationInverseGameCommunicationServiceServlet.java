package com.wicam.numberlineweb.server.MultiplicationInverse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.core.shared.GWT;
import com.wicam.numberlineweb.client.GameOpenException;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationAnswer;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationPlayer;
import com.wicam.numberlineweb.client.MultiplicationInverse.MultiplicationInverseGameCommunicationService;
import com.wicam.numberlineweb.client.MultiplicationInverse.MultiplicationInverseGameState;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.SetGameStateTask;
import com.wicam.numberlineweb.server.logging.GameLogger;
import com.wicam.numberlineweb.server.logging.GameLogger.LogActionTrigger;
import com.wicam.numberlineweb.server.logging.GameLogger.LogActionType;

public class MultiplicationInverseGameCommunicationServiceServlet extends
GameCommunicationServiceServlet implements MultiplicationInverseGameCommunicationService  {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7757604373406891094L;
	protected ArrayList<Integer> npcIds = new ArrayList<Integer>();
	
	public final static String[] playerColors = {"red", "blue", "orange", "Magenta", "DarkKhaki"};
	// The string used as multiplication sign
	protected String sign = " x ";
	
	protected Map<Integer, ArrayList<MultiplicationInverseItem>> gameId2Items = new HashMap<Integer, ArrayList<MultiplicationInverseItem>>();
	
	protected int numberOfPresentationsPerItem;
	
	public MultiplicationInverseGameCommunicationServiceServlet() {
		
		super("multiplication inverse");
		//this.handicapAdjustment = new NumberLineGameHandicap();
		
		// Set the number of presentations of each item.
		numberOfPresentationsPerItem = 7;
		
//		// Test if the items are produced 112 times each 7 times.
//		MultiplicationInverseItem item = nextRandomItem();
//		int count = 1;
//		while (item != null) {
//			System.out.println(count + ". " + item);
//			count++;
//			item = nextRandomItem();
//		}
	}
	
	@Override
	public GameState openGame(GameState g) throws GameOpenException {
		
		g.setServerSendTime(System.currentTimeMillis());
		GWT.log("before opening game");
		GameState retGameState = super.openGame(g);
		GWT.log("after opening game");
		
		// Reset the item list.
		setItems(retGameState.getId());
		
		newResults((MultiplicationInverseGameState) g);
		
		return retGameState;
	}

	@Override
	public String getGameProperties(GameState gameState) {
		
		MultiplicationInverseGameState numberlineGameState = (MultiplicationInverseGameState) gameState;
		
		String gamePropertiesStr = "{";
				
		gamePropertiesStr += "num_players : " + numberlineGameState.getPlayerCount() + ", ";
		
		//TODO gameproperties
		
		gamePropertiesStr += "}";
		
		return gamePropertiesStr;
		
	}
	
	/**
	 * @param state MultiplicationGameState to alter
	 * @return The new MultiplicationGameState
	 */
	public MultiplicationInverseGameState newResults(MultiplicationInverseGameState state) {
		
		ArrayList<MultiplicationAnswer> answers = new ArrayList<MultiplicationAnswer>();
		
		GameLogger logger = this.gameId2Logger.get(state.getId());
		
		// Get next random item.
		MultiplicationInverseItem item = nextRandomItem(state.getId());
		assert item != null : "There is no item left!";
		// Log the current round and item.
		System.out.println("Round " + state.getRound() + ", " + item);
		
		// Set the multiplication task.
		String task = item.getFirstFactor() + sign + item.getSecondFactor();
		state.setTask(task);
		
		// Set the possible answers.
		ArrayList<Integer> answerNumbers = item.getShuffledPossibleAnsers();
		for (int x : answerNumbers) {
			MultiplicationAnswer newAnswer = new MultiplicationAnswer(String.valueOf(x), (x == item.getResult()));
			answers.add(newAnswer);
		}
		
		// increment the round-counter
		state.setRound(state.getRound()+1);
		
		state.setResult(item.getResult());
		
		state.setSimpleTask(item.isSimple());
		
		state.setAnswers(answers);
		
		logger.log(state.getId(), System.currentTimeMillis(), LogActionType.MULTIPLICATION_TASK_PRESENTED, 
				"{\"task\" : " + task + "}", this.getClass().getName(), LogActionTrigger.APPLICATION);
		
		return state;
	}
	
	public void showNextItem(int id) {
		
		Timer t = new Timer(true);
		t.schedule(new MultiplicationInverseGameStateTask(id, 6, this), 6000);
	}
	
	/**
	 * Set the items for the game.
	 * To generate the Java code use the script itemsToJavaCode.awk and
	 * a csv file with the items.
	 */
	private void setItems(int currentId) {
		
		ArrayList<MultiplicationInverseItem> items = new ArrayList<MultiplicationInverseItem>();
		
		// The complex items.
		MultiplicationInverseItem item1 = new MultiplicationInverseItem(13, 4, 52, false);
		item1.addPossibleAnswer(56);
		item1.addPossibleAnswer(48);
		item1.addPossibleAnswer(65);
		item1.addPossibleAnswer(39);
		item1.addPossibleAnswer(53);
		item1.addPossibleAnswer(51);
		item1.addPossibleAnswer(54);
		item1.addPossibleAnswer(50);
		item1.addPossibleAnswer(62);
		item1.addPossibleAnswer(42);
		item1.addPossibleAnswer(25);
		item1.addPossibleAnswer(52);
		items.add(item1);

		MultiplicationInverseItem item2 = new MultiplicationInverseItem(3, 19, 57, false);
		item2.addPossibleAnswer(76);
		item2.addPossibleAnswer(38);
		item2.addPossibleAnswer(60);
		item2.addPossibleAnswer(54);
		item2.addPossibleAnswer(58);
		item2.addPossibleAnswer(56);
		item2.addPossibleAnswer(59);
		item2.addPossibleAnswer(55);
		item2.addPossibleAnswer(67);
		item2.addPossibleAnswer(47);
		item2.addPossibleAnswer(75);
		item2.addPossibleAnswer(57);
		items.add(item2);

		MultiplicationInverseItem item3 = new MultiplicationInverseItem(5, 13, 65, false);
		item3.addPossibleAnswer(78);
		item3.addPossibleAnswer(52);
		item3.addPossibleAnswer(70);
		item3.addPossibleAnswer(60);
		item3.addPossibleAnswer(66);
		item3.addPossibleAnswer(64);
		item3.addPossibleAnswer(67);
		item3.addPossibleAnswer(63);
		item3.addPossibleAnswer(75);
		item3.addPossibleAnswer(55);
		item3.addPossibleAnswer(56);
		item3.addPossibleAnswer(65);
		items.add(item3);

		MultiplicationInverseItem item4 = new MultiplicationInverseItem(6, 13, 78, false);
		item4.addPossibleAnswer(91);
		item4.addPossibleAnswer(65);
		item4.addPossibleAnswer(84);
		item4.addPossibleAnswer(72);
		item4.addPossibleAnswer(79);
		item4.addPossibleAnswer(77);
		item4.addPossibleAnswer(80);
		item4.addPossibleAnswer(76);
		item4.addPossibleAnswer(88);
		item4.addPossibleAnswer(68);
		item4.addPossibleAnswer(87);
		item4.addPossibleAnswer(78);
		items.add(item4);

		MultiplicationInverseItem item5 = new MultiplicationInverseItem(7, 14, 98, false);
		item5.addPossibleAnswer(112);
		item5.addPossibleAnswer(84);
		item5.addPossibleAnswer(105);
		item5.addPossibleAnswer(91);
		item5.addPossibleAnswer(99);
		item5.addPossibleAnswer(97);
		item5.addPossibleAnswer(100);
		item5.addPossibleAnswer(96);
		item5.addPossibleAnswer(108);
		item5.addPossibleAnswer(88);
		item5.addPossibleAnswer(89);
		item5.addPossibleAnswer(98);
		items.add(item5);

		MultiplicationInverseItem item6 = new MultiplicationInverseItem(12, 8, 96, false);
		item6.addPossibleAnswer(104);
		item6.addPossibleAnswer(88);
		item6.addPossibleAnswer(108);
		item6.addPossibleAnswer(84);
		item6.addPossibleAnswer(97);
		item6.addPossibleAnswer(95);
		item6.addPossibleAnswer(98);
		item6.addPossibleAnswer(94);
		item6.addPossibleAnswer(106);
		item6.addPossibleAnswer(86);
		item6.addPossibleAnswer(69);
		item6.addPossibleAnswer(96);
		items.add(item6);

		MultiplicationInverseItem item7 = new MultiplicationInverseItem(15, 6, 90, false);
		item7.addPossibleAnswer(96);
		item7.addPossibleAnswer(84);
		item7.addPossibleAnswer(105);
		item7.addPossibleAnswer(75);
		item7.addPossibleAnswer(91);
		item7.addPossibleAnswer(89);
		item7.addPossibleAnswer(92);
		item7.addPossibleAnswer(88);
		item7.addPossibleAnswer(100);
		item7.addPossibleAnswer(80);
		item7.addPossibleAnswer(9);
		item7.addPossibleAnswer(90);
		items.add(item7);

		MultiplicationInverseItem item8 = new MultiplicationInverseItem(18, 4, 72, false);
		item8.addPossibleAnswer(76);
		item8.addPossibleAnswer(68);
		item8.addPossibleAnswer(90);
		item8.addPossibleAnswer(54);
		item8.addPossibleAnswer(73);
		item8.addPossibleAnswer(71);
		item8.addPossibleAnswer(74);
		item8.addPossibleAnswer(70);
		item8.addPossibleAnswer(82);
		item8.addPossibleAnswer(62);
		item8.addPossibleAnswer(27);
		item8.addPossibleAnswer(72);
		items.add(item8);

		// The simple items.
		MultiplicationInverseItem item9 = new MultiplicationInverseItem(5, 3, 15, true);
		item9.addPossibleAnswer(18);
		item9.addPossibleAnswer(12);
		item9.addPossibleAnswer(20);
		item9.addPossibleAnswer(10);
		item9.addPossibleAnswer(16);
		item9.addPossibleAnswer(14);
		item9.addPossibleAnswer(17);
		item9.addPossibleAnswer(13);
		item9.addPossibleAnswer(25);
		item9.addPossibleAnswer(5);
		item9.addPossibleAnswer(51);
		item9.addPossibleAnswer(15);
		items.add(item9);

		MultiplicationInverseItem item10 = new MultiplicationInverseItem(2, 8, 16, true);
		item10.addPossibleAnswer(24);
		item10.addPossibleAnswer(8);
		item10.addPossibleAnswer(18);
		item10.addPossibleAnswer(14);
		item10.addPossibleAnswer(17);
		item10.addPossibleAnswer(15);
		item10.addPossibleAnswer(18);
		item10.addPossibleAnswer(14);
		item10.addPossibleAnswer(26);
		item10.addPossibleAnswer(6);
		item10.addPossibleAnswer(61);
		item10.addPossibleAnswer(16);
		items.add(item10);

		MultiplicationInverseItem item11 = new MultiplicationInverseItem(3, 4, 12, true);
		item11.addPossibleAnswer(16);
		item11.addPossibleAnswer(8);
		item11.addPossibleAnswer(15);
		item11.addPossibleAnswer(9);
		item11.addPossibleAnswer(13);
		item11.addPossibleAnswer(11);
		item11.addPossibleAnswer(14);
		item11.addPossibleAnswer(10);
		item11.addPossibleAnswer(22);
		item11.addPossibleAnswer(2);
		item11.addPossibleAnswer(21);
		item11.addPossibleAnswer(12);
		items.add(item11);

		MultiplicationInverseItem item12 = new MultiplicationInverseItem(3, 9, 27, true);
		item12.addPossibleAnswer(36);
		item12.addPossibleAnswer(18);
		item12.addPossibleAnswer(30);
		item12.addPossibleAnswer(24);
		item12.addPossibleAnswer(28);
		item12.addPossibleAnswer(26);
		item12.addPossibleAnswer(29);
		item12.addPossibleAnswer(25);
		item12.addPossibleAnswer(37);
		item12.addPossibleAnswer(17);
		item12.addPossibleAnswer(72);
		item12.addPossibleAnswer(27);
		items.add(item12);

		MultiplicationInverseItem item13 = new MultiplicationInverseItem(5, 6, 30, true);
		item13.addPossibleAnswer(36);
		item13.addPossibleAnswer(24);
		item13.addPossibleAnswer(35);
		item13.addPossibleAnswer(25);
		item13.addPossibleAnswer(31);
		item13.addPossibleAnswer(29);
		item13.addPossibleAnswer(32);
		item13.addPossibleAnswer(28);
		item13.addPossibleAnswer(40);
		item13.addPossibleAnswer(20);
		item13.addPossibleAnswer(3);
		item13.addPossibleAnswer(30);
		items.add(item13);

		MultiplicationInverseItem item14 = new MultiplicationInverseItem(6, 3, 18, true);
		item14.addPossibleAnswer(21);
		item14.addPossibleAnswer(15);
		item14.addPossibleAnswer(24);
		item14.addPossibleAnswer(12);
		item14.addPossibleAnswer(19);
		item14.addPossibleAnswer(17);
		item14.addPossibleAnswer(20);
		item14.addPossibleAnswer(16);
		item14.addPossibleAnswer(28);
		item14.addPossibleAnswer(8);
		item14.addPossibleAnswer(81);
		item14.addPossibleAnswer(18);
		items.add(item14);

		MultiplicationInverseItem item15 = new MultiplicationInverseItem(7, 4, 28, true);
		item15.addPossibleAnswer(32);
		item15.addPossibleAnswer(24);
		item15.addPossibleAnswer(35);
		item15.addPossibleAnswer(21);
		item15.addPossibleAnswer(29);
		item15.addPossibleAnswer(27);
		item15.addPossibleAnswer(30);
		item15.addPossibleAnswer(26);
		item15.addPossibleAnswer(38);
		item15.addPossibleAnswer(18);
		item15.addPossibleAnswer(82);
		item15.addPossibleAnswer(28);
		items.add(item15);

		MultiplicationInverseItem item16 = new MultiplicationInverseItem(8, 4, 32, true);
		item16.addPossibleAnswer(36);
		item16.addPossibleAnswer(28);
		item16.addPossibleAnswer(40);
		item16.addPossibleAnswer(24);
		item16.addPossibleAnswer(33);
		item16.addPossibleAnswer(31);
		item16.addPossibleAnswer(34);
		item16.addPossibleAnswer(30);
		item16.addPossibleAnswer(42);
		item16.addPossibleAnswer(22);
		item16.addPossibleAnswer(23);
		item16.addPossibleAnswer(32);
		items.add(item16);
		
		this.gameId2Items.put(currentId, items);
	}
	
	/**
	 * Return the next random item that hasn't reached the numberOfPresentationsPerItem.
	 * Increase its numberOfPresenation counter. Return null if there isn't any further item
	 * with a counter less than the numberOfPresentationsPerItem.
	 * @return
	 */
	private MultiplicationInverseItem nextRandomItem(int gameId) {
		ArrayList<MultiplicationInverseItem> availableItems = new ArrayList<MultiplicationInverseItem>();
		ArrayList<MultiplicationInverseItem> items = this.gameId2Items.get(gameId);
		
		// Find the items that havn't reached the max number of presentations.
		for (MultiplicationInverseItem item : items) {
			if (item.getNumberOfPresentations() < numberOfPresentationsPerItem) {
				availableItems.add(item);
			}
		}
		// Choose a random item of the remaining ones.
		Collections.shuffle(availableItems);
		MultiplicationInverseItem item = null;
		if (!availableItems.isEmpty()){
			item = availableItems.get(0);
			item.increaseNumberOfPresentations();
		}
		return item;
	}
	
	/**
	 * Test if items are left
	 * @return boolean value whether items are left
	 */
	private boolean itemListIsEmpty(int gameId) {
		ArrayList<MultiplicationInverseItem> availableItems = new ArrayList<MultiplicationInverseItem>();
		ArrayList<MultiplicationInverseItem> items = this.gameId2Items.get(gameId);
		
		// Find the items that havn't reached the max number of presentations.
		for (MultiplicationInverseItem item : items) {
			System.out.println(item.getFirstFactor() + " x " + item.getFirstFactor() + ": " + item.getNumberOfPresentations());
			if (item.getNumberOfPresentations() < numberOfPresentationsPerItem) {
				availableItems.add(item);
			}
		}
		System.out.println("availableItems: " + availableItems.size());
		return availableItems.isEmpty();
	}
	
	@Override
	protected void addNPC(GameState game){
		int playerid = game.addPlayer("NPC", -2);
		npcIds.add(playerid);
		GWT.log("Trying to build an NPC");
		npcs.add(new MultiplicationInverseNPC(this, game.getId(), playerid));
	}
	
	@Override
	protected boolean isNPC(int playerId){
		return npcIds.contains(playerId);
	}	
	
	/**
	 * @param newAnswer Answer to check
	 * @param answers All answers
	 * @return Returns true, if the newAnswer is in answers
	 */
	protected boolean answerExists(MultiplicationAnswer newAnswer, ArrayList<MultiplicationAnswer> answers) {
		for (MultiplicationAnswer answer : answers) {
			if (answer.equals(newAnswer)) {
				return true;
			}
		}
		return false;
	}
		
	/**
	 * Disables all answers
	 * @param answers Set of answers to delete from
	 */
	protected void disableAllAnswers(ArrayList<MultiplicationAnswer> answers) {
		for (MultiplicationAnswer answer : answers) {
			answer.setTaken();
		}
	}
		
	/**
	 * @param toFind Answer to be checked
	 * @param answers All answers
	 * @return Returns 1, if the given answer was a correct and free one. 
	 * 		   Returns 0, if user was too slow and answer is already taken. 
	 * 		   Returns -1, if answer was a false and free one
	 */
	protected int checkAnswer(String toFind, ArrayList<MultiplicationAnswer> answers, MultiplicationPlayer player) {
		for (MultiplicationAnswer answer : answers) {
			if (answer.getAnswer().equals(toFind)) {				
				if (answer.isTaken()) {
					return 0;
				} else {
					answer.setTaken();
					answer.setColor(playerColors[player.getColorId()]);
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
	
	/**
	 * @param answers Set of answers to check
	 * @return True, if at least one correct answer is not yet taken
	 */
	protected boolean oneCorrectLeft(ArrayList<MultiplicationAnswer> answers) {
		Boolean res = false;
		for (MultiplicationAnswer answer : answers) {
			if (answer.isCorrect()) {
				res = res || !answer.isTaken();				
			}
		}
		return res;
	}	
	
	/**
	 * @param clicked gameid:playerid:clickedAnswer
	 * @return New MultiplicationGameState
	 */
	@Override
	synchronized public MultiplicationInverseGameState clickedAt(String clicked) {
		
		int gameid = Integer.parseInt(clicked.split(":")[0]);
		return clickedAt(clicked,getPlayerId(gameid));
		
	}		

	/**
	 * User has clicked.
	 * @param clicked gameid:playerid:clickedAnswer
	 * @param playerid Playerid
	 * @return New MultiplicationGameState
	 */
	synchronized public MultiplicationInverseGameState clickedAt(String clicked, int playerid) {
		
		int gameid = Integer.parseInt(clicked.split(":")[0]);
		
		GameLogger logger = this.gameId2Logger.get(gameid);

		String answer = clicked.split(":")[2];
		MultiplicationInverseGameState g = (MultiplicationInverseGameState) this.getGameById(gameid);
		
		HttpServletRequest request = this.getThreadLocalRequest();
		int uid = -2;
		if(request != null){
			HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>> map = (HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>) 
					request.getSession().getAttribute("game2pid2uid");
			uid = map.get(internalName).get(gameid).get(playerid);
		}
		
		// check answers and flag/colorize taken answers
		int answerState = checkAnswer(answer, g.getAnswers(), (MultiplicationPlayer) g.getPlayers().get(playerid-1));
		
		// Do the logging of the clicked answer.
		if ((!this.isNPC(playerid)) && (uid != -2)) {
			// Human player clicked.
			logger.log(g.getId(), uid, System.currentTimeMillis(), LogActionType.MULTIPLICATION_USER_PICKED_ANSWER,
					"{\"answer\" : " + answer + ", {\"was_right\" : " + answerState + "}", this.getClass().getName(), LogActionTrigger.USER);
		} else {
			// NPC clicked.
			logger.log(g.getId(), uid, System.currentTimeMillis(), LogActionType.MULTIPLICATION_NPC_PICKED_ANSWER,
					"{\"answer\" : " + answer + ", {\"was_right\" : " + answerState + "}", this.getClass().getName(), LogActionTrigger.NPC);
		}

		// give/take points
		this.getGameById(gameid).setPlayerPoints(playerid,this.getGameById(gameid).getPlayerPoints(playerid) + answerState);
		
		if (oneCorrectLeft(g.getAnswers())) { // keep going
			this.setGameState(this.getGameById(gameid),3);
			this.setChanged(gameid);
		} else { // New round or end of game
			
			disableAllAnswers(g.getAnswers());
			
			this.setGameState(this.getGameById(gameid),5);
			
			//TODO: change to test whether items are left
			boolean isEmpty = itemListIsEmpty(gameid);
			
			if (isEmpty){
				this.endGame(gameid);
				this.handicapAction(gameid);
			}
			else {
				this.showNextItem(gameid);			
			}
			
		}

		g.setServerSendTime(System.currentTimeMillis());
		return g;
	}

	protected void handicapAction(int gameid) {
		
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
		
		MultiplicationInverseGameState numberlineGameState = (MultiplicationInverseGameState) this.getGameById(gameid);
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
	public void endGame(int id) {

		Timer t = new Timer("TimerEndGame", true);
		
		String gamePropertiesStr = this.getGameProperties(this.getGameById(id));
		
		this.gameId2Logger.get(id).updateGameProperties(id, this.getClass().getName(), gamePropertiesStr);
		
		this.gameId2Logger.get(id).log(id, System.currentTimeMillis(), LogActionType.GAME_ENDED, "", 
				this.getClass().getName(), LogActionTrigger.APPLICATION);

		this.writeLogToDatabase(id);
		
		this.terminateNPCTimers();
		
		// winner screen
		t.schedule(new SetGameStateTask(id, 97, this), 6000);
		
	}
	
	@Override
	protected void removeGame(int gameid) {
		super.removeGame(gameid);
		// remove items
		this.gameId2Items.remove(gameid);
	}
	
}