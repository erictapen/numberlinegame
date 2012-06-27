package com.wicam.numberlineweb.server.NumberLineGame;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;
import com.wicam.numberlineweb.server.NPC;

public class NumberLineGameNPC extends NPC{

	private NumberLineGameCommunicationServiceServlet comm;
	private int gameid;
	private int playerid;
	private int eloNumber;
	
	boolean makeClick = false;
	
	double accuracyRange = 50;
	double reactionTimeRange = 1000;
	
	public NumberLineGameNPC(NumberLineGameCommunicationServiceServlet comm, int gameid, int playerid){
		this.comm = comm;
		this.gameid = gameid;
		this.playerid = playerid;
		
		t = new Timer("TimerNPCNumberLine", true);
		
		t.schedule(new CPUBehavior(), 0);
		
	}
	
	public NumberLineGameNPC(NumberLineGameCommunicationServiceServlet comm, int gameid, int playerid, int eloNumber){
		this.comm = comm;
		this.gameid = gameid;
		this.playerid = playerid;
		this.eloNumber = eloNumber;
		
		accuracyRange = 90 - 0.04 * this.eloNumber;
		
		reactionTimeRange = 1800 - 0.8 * this.eloNumber;
		
		t = new Timer("TimerNPCNumberLine", true);
		
		t.schedule(new CPUBehavior(), 0);
		
	}
	
	protected int realPosToRaw(int pos, int leftNumber, int rightNumber) {

		return (int)((pos -leftNumber) /  ((double)(rightNumber -leftNumber)/400));

	}
	
	private class CPUBehavior extends TimerTask {
		
		@Override
		synchronized public void run() {
			
			NumberLineGameState game = (NumberLineGameState) comm.getGameById(gameid);
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
						if (!game.isPlayerClicked(playerid)){
							if (!makeClick){
								time = 1500 + ((int) reactionTimeRange) + (int)(new Random().nextGaussian() * 500);
								
								if (time < 1500)
									time = 1500;
								
								makeClick = true;
							}
							else{
								int x = realPosToRaw(game.getExerciseNumber(), game.getLeftNumber(), game.getRightNumber());
								x = (int)(x +  new Random().nextGaussian() * accuracyRange);
								if (x < 0)
									x = 0;
								if (x > 400)
									x = 400;
								NumberLineGameState g = comm.clickedAt(Integer.toString(game.getId()) + ":" + Integer.toString(playerid) + ":" + Integer.toString(x),playerid);
								if (!g.isPlayerClicked(playerid)){
									// position was not available => retry
									time = 700 + (int)(new Random().nextGaussian()*100);
									if (time < 500)
										time = 500;
								}
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
