package com.wicam.numberlineweb.server.NumberLineGame;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;
import com.wicam.numberlineweb.server.CommunicationServiceServlet;

public class NumberLineGameNPC {

	private CommunicationServiceServlet comm;
	private int gameid;
	private int playerid;
	boolean makeClick = false;
	
	public NumberLineGameNPC(CommunicationServiceServlet comm, int gameid, int playerid){
		this.comm = comm;
		this.gameid = gameid;
		this.playerid = playerid;
		new CPUBehavior().run();
	}
	
	protected int realPosToRaw(int pos, int leftNumber, int rightNumber) {

		return (int)((pos -leftNumber) /  ((double)(rightNumber -leftNumber)/400));

	}
	
	private class CPUBehavior extends TimerTask {
		
		@Override
		synchronized public void run() {
			Timer t = new Timer();
			NumberLineGameState game = (NumberLineGameState) comm.getGameComm().getGameById(gameid);
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
							int x = realPosToRaw(game.getExerciseNumber(), game.getLeftNumber(), game.getRightNumber());
							x = (int)(x +  new Random().nextGaussian()*50);
							if (x < 0)
								x = 0;
							if (x > 400)
								x = 400;
							NumberLineGameState g = comm.getNumberLineGameComm().clickedAt(Integer.toString(game.getId()) + ":" + Integer.toString(playerid) + ":" + Integer.toString(x));
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
			}
			
			if (state == 7 || state == 99){
				comm.leaveGame(game.getId() + ":" + Integer.toString(playerid));
			}
			else {
				t.schedule(new CPUBehavior(), time);
			}
		}
		
	}
}
