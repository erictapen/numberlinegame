package com.wicam.numberlineweb.server.MultiplicationInverse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gwt.core.shared.GWT;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationAnswer;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameState;
import com.wicam.numberlineweb.client.MultiplicationInverse.MultiplicationInverseGameState;
import com.wicam.numberlineweb.server.NPC;

public class MultiplicationInverseNPC extends NPC{

	protected MultiplicationInverseGameCommunicationServiceServlet comm;
	protected int gameid;
	protected int playerid;
	boolean makeClick = false;
	protected double skill = .7; // 70% correct clicks
	
	// The response times from which to chose from.
	protected ArrayList<Integer> simpleResponseTimes = new ArrayList<Integer>();
	protected ArrayList<Integer> complexResponseTimes = new ArrayList<Integer>();
	
	public MultiplicationInverseNPC(MultiplicationInverseGameCommunicationServiceServlet comm, int gameid, int playerid){
		this.comm = comm;
		this.gameid = gameid;
		this.playerid = playerid;
		setResponseTimes();
		
		t = new Timer("TimerNPCMultiplicationInverse", true);
		
		t.schedule(new CPUBehavior(), 0);
	}
	
	
	
	/**
	 * Set skill
	 * @param skill Set skill to this value
	 */
	public void setSkill(double skill) {
		this.skill = skill;
	}
	
	
	/**
	 * Returns a random response time depending on the given difficulty of the item.
	 * @param isSimpleTask
	 * @return
	 */
	private int getRandomResponseTime(boolean isSimpleTask) {
		ArrayList<Integer> responseTimes = new ArrayList<Integer>();
		// Choose the corresponding response time list and clone it.
		if (isSimpleTask) {
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
	 * @return Returns skill
	 */
	public double getSkill() {
		return skill;
	}



	/**
	 * @return Returns the MultiplicationGameState
	 */
	protected MultiplicationInverseGameState getGameState() {
		return ((MultiplicationInverseGameState) comm.getGameById(gameid));
	}
	
	
	
	/**
	 * @param correct Specifies, if the answer has to be correct
	 * @param taken Specifies, if the answer has to be taken
	 * @return Gives you a specific answer, randomly picked
	 */
	protected String getSpecificAnswer(boolean correct, boolean taken) {
		ArrayList<MultiplicationAnswer> answers = getGameState().getSpecificAnswers(correct, taken);
		int id = new Random().nextInt(answers.size());
		for (MultiplicationAnswer answer : answers) {
			if (!answer.isTaken() && id == 0) {
				return answer.getAnswer();
			}
			id--;
		}
		return "fail";
	}
	
	
	protected class CPUBehavior extends TimerTask {
		
		@Override
		synchronized public void run() {
			MultiplicationInverseGameState game = (MultiplicationInverseGameState) comm.getGameById(gameid);
			if (game != null){
				int state = game.getState();
				int time = 50; //Reduce the refresh time to increase response time accuracy. Old value: 500.
				switch (state){
					case 1:
					case 2:
						time = 1000;
						if (!game.isPlayerReady(playerid))
							comm.updateReadyness(Integer.toString(gameid) + ":" + Integer.toString(playerid),playerid);
					break;
					case 3:
					case 4:
							if (!makeClick){
								// Change the NPC response time.
//								time = 5000 + (int)(new Random().nextGaussian()*500);
//								if (time < 5000)
//									time = 5000;
								// Find a random response time.
								time = getRandomResponseTime(game.isSimpleTask());
//								System.out.println("Time set to: " + time);
//								System.out.println("Task set: " + System.currentTimeMillis());
								makeClick = true;
							}
							else{
//								System.out.println("NPC clicks: " + System.currentTimeMillis());
								makeClick = false;
								String answer;
								if (new Random().nextDouble() < skill) { 
									answer = getSpecificAnswer(true, false);
								} else {
									answer = getSpecificAnswer(false, false);
								}
								
								if (!answer.equals("fail")) {									
									@SuppressWarnings("unused")
									MultiplicationInverseGameState temp = comm.clickedAt(Integer.toString(game.getId()) + ":" + Integer.toString(playerid) + ":" + answer, playerid);
								}
							}
					break;
					case 5:
						makeClick = false;
					break;
					case 6:
						if (!game.isPlayerReady(playerid))
							comm.updateReadyness(Integer.toString(gameid) + ":" + Integer.toString(playerid),playerid);
					break;
				}
				
				if (state == 7 || state == 99){
					comm.leaveGame(game.getId() + ":" + Integer.toString(playerid),playerid);
				}
				else {
					if (!isTimerCancelled)
						t.schedule(new CPUBehavior(), time);
				}
			}
		}		
	}
}
