package com.wicam.numberlineweb.server.BuddyNumber;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.wicam.numberlineweb.client.BuddyNumber.BuddyNumberDigit;
import com.wicam.numberlineweb.client.BuddyNumber.BuddyNumberGameState;

public class BuddyNumberNPC {

	private BuddyNumberGameCommunicationServiceServlet comm;
	private int gameid;
	private int playerid;
	boolean makeClick = false;
	private double skill = .7; // 70% correct clicks
	
	public BuddyNumberNPC(BuddyNumberGameCommunicationServiceServlet comm, int gameid, int playerid){
		this.comm = comm;
		this.gameid = gameid;
		this.playerid = playerid;
		new CPUBehavior().run();
	}
	
	
	
	/**
	 * Set skill
	 * @param skill Set skill to this value
	 */
	public void setSkill(double skill) {
		this.skill = skill;
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
	private BuddyNumberGameState getGameState() {
		return ((BuddyNumberGameState) comm.getGameById(gameid));
	}
	
	
	
	/**
	 * @param correct Specifies, if the answer has to be correct
	 * @return Gives you a specific answer, randomly picked, handValue:handID:communityValue:communityID
	 */
	private String getSpecificAnswer(boolean correct) {
		ArrayList<String> answers = getGameState().getSpecificAnswers(correct);
		int id = new Random().nextInt(answers.size());
		for (String answer : answers) {
			if (id == 0) {
				return answer;
			}
			id--;
		}
		return "fail";
	}
	
	
	private class CPUBehavior extends TimerTask {
		
		@Override
		synchronized public void run() {
			Timer t = new Timer();
			BuddyNumberGameState game = (BuddyNumberGameState) comm.getGameById(gameid);
			if (game != null){
				int state = game.getState();
				int time = 500;
				switch (state){
					case 1:
					case 2:
						time = 1000;
						if (!game.isPlayerReady(playerid))
							comm.updateReadyness(Integer.toString(gameid) + ":" + Integer.toString(playerid),playerid);
					break;
					case 3:
					case 4:
//							if (!makeClick){
//								time = 5000 + (int)(new Random().nextGaussian()*500);
//								if (time < 5000)
//									time = 5000;
//								makeClick = true;
//							}
//							else{
//								makeClick = false;
//								String answer;
//								if (new Random().nextDouble() < skill) { 
//									answer = getSpecificAnswer(true);
//								} else {
//									answer = getSpecificAnswer(false);
//								}
//								
//								if (!answer.equals("fail")) {
//									String handValue = answer.split(":")[0];
//									String handID = answer.split(":")[1];
//									String communityValue = answer.split(":")[2];
//									String communityID = answer.split(":")[3];
//									
//									String first = "" + handValue + ":" + handID + ":hand";
//									String second = "" + communityValue + ":" + communityID + ":com";
//									
//									@SuppressWarnings("unused")
//									BuddyNumberGameState temp = comm.clickedAt(Integer.toString(game.getId()) + ":" + 
//											Integer.toString(playerid) + ":" + first, playerid);
//									@SuppressWarnings("unused")
//									BuddyNumberGameState temp2 = comm.clickedAt(Integer.toString(game.getId()) + ":" + 
//											Integer.toString(playerid) + ":" + second, playerid);
//								}
//							}
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
					t.schedule(new CPUBehavior(), time);
				}
			}
		}		
	}
}
