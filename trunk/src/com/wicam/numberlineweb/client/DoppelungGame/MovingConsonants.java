package com.wicam.numberlineweb.client.DoppelungGame;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;

public class MovingConsonants extends Image{

	private Move move;
	private DoppelungGameCoordinator coordinator;
	private String consonants;
	private int x;
	private int y;
	private boolean removed = false;
	
	public MovingConsonants(String consonants, DoppelungGameCoordinator coordinator, int x, int y){
		super("doppelungGame/coins/coin_" + consonants + ".png");
		
		GWT.log(consonants);
		
		this.consonants = consonants;
		this.coordinator = coordinator;
		this.x = x;
		this.y = y;
		move = new Move(this);
	}
	
	public void setSpeed( int spaceSpeed){
		move.setSpaceSpeed(spaceSpeed);
	}
	
	public void startMoving(){
		
		coordinator.registerAniTask(move);

	}
	
	public void startMoving(int delay){
		move.setDelay(delay);
		coordinator.registerAniTask(move);
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


	class Move extends AnimationTimerTask{
		
		MovingConsonants mc;

		int spaceSpeed;
		
		Move(MovingConsonants mc){
			this.mc = mc;
			this.spaceSpeed = 24;
		}
		

		void setSpaceSpeed(int spaceSpeed){
			this.spaceSpeed = spaceSpeed;
		}
		@Override
		public void run() {
			y = y + spaceSpeed;
			coordinator.setMovingConsonantsPosition(mc, x, y);
			coordinator.checkForCollision(mc);
			
			if (removed){
				this.markForDelete();
			}
		}
	}
}
