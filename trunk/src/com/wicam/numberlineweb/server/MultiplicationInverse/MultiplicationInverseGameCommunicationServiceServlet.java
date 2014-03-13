package com.wicam.numberlineweb.server.MultiplicationInverse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.core.shared.GWT;
import com.wicam.numberlineweb.client.GameOpenException;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationAnswer;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameCommunicationService;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameState;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationPlayer;
import com.wicam.numberlineweb.client.MultiplicationInverse.MultiplicationInverseGameCommunicationService;
import com.wicam.numberlineweb.client.MultiplicationInverse.MultiplicationInverseGameState;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.Multiplication.MultiplicationGameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.Multiplication.MultiplicationGameStateTask;
import com.wicam.numberlineweb.server.Multiplication.MultiplicationNPC;

public class MultiplicationInverseGameCommunicationServiceServlet extends
	MultiplicationGameCommunicationServiceServlet implements MultiplicationInverseGameCommunicationService  {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7757604373406891094L;
	protected ArrayList<Integer> npcIds = new ArrayList<Integer>();
	
	// The response times from which to chose from.
	protected ArrayList<Integer> simpleResponseTimes = new ArrayList<Integer>();
	protected ArrayList<Integer> complexResponseTimes = new ArrayList<Integer>();
	
	protected ArrayList<MultiplicationInverseItem> items = new ArrayList<MultiplicationInverseItem>();
	
	protected int numberOfPresentationsPerItem;
	
	public MultiplicationInverseGameCommunicationServiceServlet() {
		
		super("multiplication");
		//this.handicapAdjustment = new NumberLineGameHandicap();
		
		// Set the response times.
		setResponseTimes();
		// Set the items.
		setItems();
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
		//return super.openGame(g);
		newResults((MultiplicationInverseGameState) g);
		
		return retGameState;
	}
	
	/**
	 * @param state MultiplicationGameState to alter
	 * @return The new MultiplicationGameState
	 */
	public MultiplicationInverseGameState newResults(MultiplicationInverseGameState state) {
		
		ArrayList<MultiplicationAnswer> answers = new ArrayList<MultiplicationAnswer>();
		
		// Get next random item.
		MultiplicationInverseItem item = nextRandomItem();
		assert item != null : "There is no item left!";
		// Log the current round and item.
		System.out.println("Round " + state.getRound() + ", " + item);
		
		// Set the multiplication task.
		state.setTask(item.getFirstFactor() + sign + item.getSecondFactor());
		
		// Set the possible answers.
		ArrayList<Integer> answerNumbers = item.getShuffledPossibleAnsers();
		for (int x : answerNumbers) {
			// Add a space to answers less than 10.
//			String answerStr = "";
//			if (x < 10) {
//				answerStr = " " + x;
//			} else {
//				answerStr = String.valueOf(x);
//			}
			MultiplicationAnswer newAnswer = new MultiplicationAnswer(String.valueOf(x), (x == item.getResult()));
			answers.add(newAnswer);
		}
		
		// increment the round-counter
		state.setRound(state.getRound()+1);
		
		state.setResult(item.getResult());
		
		state.setAnswers(answers);
		
		state.setNPCResponseTime(getRandomResponseTimeForItem(item));
		
		return state;
	}
	
	@Override
	public void showNextItem(int id) {
		
		Timer t = new Timer(true);
		t.schedule(new MultiplicationInverseGameStateTask(id, 6, this), 6000);
	}
	
	/**
	 * Set the response times of the trials.
	 */
	private void setResponseTimes() {
		simpleResponseTimes.add(3000);
		simpleResponseTimes.add(3400);
		simpleResponseTimes.add(3800);
		simpleResponseTimes.add(4200);
		simpleResponseTimes.add(4600);
		simpleResponseTimes.add(5000);
		simpleResponseTimes.add(5400);
		simpleResponseTimes.add(5800);
		simpleResponseTimes.add(6200);
		simpleResponseTimes.add(6600);
		simpleResponseTimes.add(7000);
		complexResponseTimes.add(8500);
		complexResponseTimes.add(10100);
		complexResponseTimes.add(11700);
		complexResponseTimes.add(13300);
		complexResponseTimes.add(14900);
		complexResponseTimes.add(16500);
		complexResponseTimes.add(18100);
		complexResponseTimes.add(19700);
		complexResponseTimes.add(21300);
		complexResponseTimes.add(22900);
		complexResponseTimes.add(24500);
	}
	
	/**
	 * Set the items for the game.
	 * To generate the Java code use the script itemsToJavaCode.awk and
	 * a csv file with the items.
	 */
	private void setItems() {
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
	}
	
	/**
	 * Return the next random item that hasn't reached the numberOfPresentationsPerItem.
	 * Increase its numberOfPresenation counter. Return null if there isn't any further item
	 * with a counter less than the numberOfPresentationsPerItem.
	 * @return
	 */
	private MultiplicationInverseItem nextRandomItem() {
		ArrayList<MultiplicationInverseItem> availableItems = new ArrayList<MultiplicationInverseItem>();
		// Find the items that havn't reached the max number of presentations.
		for (MultiplicationInverseItem item : items) {
			if (item.getNumberOfPresentations() < numberOfPresentationsPerItem) {
				availableItems.add(item);
			}
		}
		if (availableItems.isEmpty()) {
			return null;
		}
		// Choose a random item of the remaining ones.
		Collections.shuffle(availableItems);
		MultiplicationInverseItem item = availableItems.get(0);
		item.increaseNumberOfPresentations();
		return item;
	}
	
	/**
	 * Returns a random response time depending on the given difficulty of the item.
	 * @param item
	 * @return
	 */
	private int getRandomResponseTimeForItem(MultiplicationInverseItem item) {
		ArrayList<Integer> responseTimes = new ArrayList<Integer>();
		// Choose the corresponding response time list and clone it.
		if (item.isSimple()) {
			for (int x : simpleResponseTimes) {
				responseTimes.add(x);
			}
		}
		else {
			for (int x : complexResponseTimes) {
				responseTimes.add(x);
			}
		}
		// Choose a random time and return it.
		Collections.shuffle(responseTimes);
		return responseTimes.get(0);
	}
	
	@Override
	protected void addNPC(GameState game){
		int playerid = game.addPlayer("NPC", -2);
		npcIds.add(playerid);
		npcs.add(new MultiplicationInverseNPC(this, game.getId(), playerid, game.getNPCResponseTime()));
	}
	
}