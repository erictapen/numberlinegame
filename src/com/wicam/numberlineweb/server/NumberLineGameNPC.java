package com.wicam.numberlineweb.server;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;

public class NumberLineGameNPC {

	private CommunicationServiceServlet comm;
	private int gameid;
	private int playerid;
	boolean makeClick = false;
	
	NumberLineGameNPC(CommunicationServiceServlet comm, int gameid, int playerid){
		this.comm = comm;
		this.gameid = gameid;
		this.playerid = playerid;
		new CPUBehavior().run();
	}
	
	private class CPUBehavior extends TimerTask {
		
		@Override
		synchronized public void run() {
			Timer t = new Timer();
			NumberLineGameState game = comm.getGameById(gameid);
			int state = game.getState();
			int time = 500;
			switch (state){
				case 1:
				case 2:
					time = 1000;
				break;
				case 21:
					if (!game.isPlayerReady(playerid))
						comm.updateReadyness(Integer.toString(gameid) + ":" + Integer.toString(playerid));
				break;
				case 3:
				case 4:
					if (!game.isPlayerClicked(playerid)){
						if (!makeClick){
							time = 2500 + (int)(new Random().nextGaussian()*500);
							if (time < 1500)
								time = 1500;
							makeClick = true;
						}
						else{
							int x = game.getExerciseNumber() + (int) (new Random().nextGaussian() *(Math.abs(game.getRightNumber() - game.getLeftNumber()))/6.0);
							x = (int)((x -game.getLeftNumber()) /  ((double)(game.getRightNumber() -game.getLeftNumber())/400));
							if (x < game.getLeftNumber())
								x = game.getLeftNumber();
							if (x > game.getRightNumber())
								x = game.getRightNumber();
							comm.clickedAt(Integer.toString(game.getId()) + ":" + Integer.toString(playerid) + ":" + Integer.toString(x));
							if (!game.isPlayerClicked(playerid)){
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
			}
			
			if (state == 6){
				comm.leaveGame(game.getId() + ":" + Integer.toString(playerid));
			}
			else {
				t.schedule(new CPUBehavior(), time);
			}
		}
		
	}
}
