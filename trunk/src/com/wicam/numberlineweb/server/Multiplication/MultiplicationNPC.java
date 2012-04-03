package com.wicam.numberlineweb.server.Multiplication;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.wicam.numberlineweb.client.Multiplication.MultiplicationAnswer;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameState;
import com.wicam.numberlineweb.server.NPC;

public class MultiplicationNPC extends NPC{

	private MultiplicationGameCommunicationServiceServlet comm;
	private int gameid;
	private int playerid;
	boolean makeClick = false;
	private double skill = .7; // 70% correct clicks
	
	public MultiplicationNPC(MultiplicationGameCommunicationServiceServlet comm, int gameid, int playerid){
		this.comm = comm;
		this.gameid = gameid;
		this.playerid = playerid;
		
		t = new Timer("TimerNPCMultiplication", true);
		
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
	 * @return Returns skill
	 */
	public double getSkill() {
		return skill;
	}



	/**
	 * @return Returns the MultiplicationGameState
	 */
	private MultiplicationGameState getGameState() {
		return ((MultiplicationGameState) comm.getGameById(gameid));
	}
	
	
	
	/**
	 * @param correct Specifies, if the answer has to be correct
	 * @param taken Specifies, if the answer has to be taken
	 * @return Gives you a specific answer, randomly picked
	 */
	private String getSpecificAnswer(boolean correct, boolean taken) {
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
	
	
	private class CPUBehavior extends TimerTask {
		
		@Override
		synchronized public void run() {
			MultiplicationGameState game = (MultiplicationGameState) comm.getGameById(gameid);
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
							if (!makeClick){
								time = 5000 + (int)(new Random().nextGaussian()*500);
								if (time < 5000)
									time = 5000;
								makeClick = true;
							}
							else{
								makeClick = false;
								String answer;
								if (new Random().nextDouble() < skill) { 
									answer = getSpecificAnswer(true, false);
								} else {
									answer = getSpecificAnswer(false, false);
								}
								
								if (!answer.equals("fail")) {									
									@SuppressWarnings("unused")
									MultiplicationGameState temp = comm.clickedAt(Integer.toString(game.getId()) + ":" + Integer.toString(playerid) + ":" + answer, playerid);
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
					t.schedule(new CPUBehavior(), time);
				}
			}
		}		
	}
}
