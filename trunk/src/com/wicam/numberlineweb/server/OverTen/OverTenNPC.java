package com.wicam.numberlineweb.server.OverTen;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.wicam.numberlineweb.client.OverTen.OverTenGameState;
import com.wicam.numberlineweb.server.NPC;

public class OverTenNPC extends NPC{

	private OverTenGameCommunicationServiceServlet comm;
	private int gameid;
	private int playerid;
	boolean makeClick = false;
	private double skill = .7; // 70% correct clicks
	
	public OverTenNPC(OverTenGameCommunicationServiceServlet comm, int gameid, int playerid){
		this.comm = comm;
		this.gameid = gameid;
		this.playerid = playerid;
		
		t = new Timer("TimerNPCOverTen", true);
		
		t.schedule(new CPUBehavior(), 0);
	}
	
	
	
	/**
	 * Set skill
	 * @param skill Set skill to this value, range 0.0 (very unskilled) to 1.0 (winner-type)
	 */
	public void setSkill(double skill) {
		this.skill = skill;
	}



	/**
	 * @return Returns skill, range 0.0 (very unskilled) to 1.0 (winner-type)
	 */
	public double getSkill() {
		return skill;
	}



	/**
	 * @return Returns the OverTenGameState
	 */
	private OverTenGameState getGameState() {
		return ((OverTenGameState) comm.getGameById(gameid));
	}
	
	
	
	/**
	 * @param correct Specifies, if the answer has to be correct
	 * @return Gives you a specific answer, randomly picked, firstID:secondID
	 */
	private String getSpecificAnswer(boolean correct) {
		try {			
			ArrayList<String> answers = comm.getSpecificAnswers(getGameState(), correct);
			int id = new Random().nextInt(answers.size());
			for (String answer : answers) {
				if (id == 0) {
					return answer;
				}
				id--;
			}
			return "fail";
		} catch (IllegalArgumentException e) {			
			return "fail";
		}
	}
	
	
	private class CPUBehavior extends TimerTask {
		
		@Override
		synchronized public void run() {
			OverTenGameState game = (OverTenGameState) comm.getGameById(gameid);
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
								String answer;
								Boolean wasRight = (new Random().nextDouble() < skill);
								answer = getSpecificAnswer(wasRight);
								
								if (!answer.equals("fail")) {								
									
									makeClick = !comm.npcClicked(Integer.toString(game.getId()) + ":" + 
											Integer.toString(playerid) + ":" + answer, wasRight);
								
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
