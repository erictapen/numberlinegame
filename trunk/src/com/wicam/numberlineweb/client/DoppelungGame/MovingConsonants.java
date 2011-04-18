package com.wicam.numberlineweb.client.DoppelungGame;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;

public class MovingConsonants extends Image{

	private Move move;
	private DoppelungGameView gameView;
	private String consonants;
	private int x;
	private int y;
	private boolean removed = false;
	
	public MovingConsonants(String consonants, DoppelungGameView gameView, int x, int y){
		super("numberlineweb/doppelungGame/coins/coin_" + consonants + ".png");
		this.consonants = consonants;
		this.gameView = gameView;
		this.x = x;
		this.y = y;
		move = new Move(this);
	}
	
	public void setSpeed(int timeSpeed, int spaceSpeed){
		move.setTimeSpeed(timeSpeed);
		move.setSpaceSpeed(spaceSpeed);
	}
	
	public void startMoving(){
		move.schedule(move.timeSpeed);
	}
	
	public void startMoving(int delay){
		move.schedule(delay + move.timeSpeed);
	}
	
	public boolean removed() {
		return removed;
	}

	public void setRemoved(boolean stopped) {
		this.removed = stopped;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public String getConsonants() {
		return consonants;
	}


	class Move extends Timer{
		
		MovingConsonants mc;
		int timeSpeed;
		int spaceSpeed;
		
		Move(MovingConsonants mc){
			this.mc = mc;
			this.timeSpeed = 200;
			this.spaceSpeed = 5;
		}
		
		void setTimeSpeed(int timeSpeed){
			this.timeSpeed = timeSpeed;
		}
		
		void setSpaceSpeed(int spaceSpeed){
			this.spaceSpeed = spaceSpeed;
		}
		@Override
		public void run() {
			y = y + spaceSpeed;
			gameView.setMovingConsonantsPosition(mc, x, y);
			gameView.checkForCollision(mc);
			
			if (!removed){
				this.schedule(timeSpeed);
			}
		}
	}
}
