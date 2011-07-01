package com.wicam.numberlineweb.client.VowelGame;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameCoordinator;

public class MovingConsonants extends Image{

	private Move move;
	private DoppelungGameCoordinator coordinator;
	private String consonants;
	private int x;
	private int y;
	private int toY;
	private boolean removed = false;
	private int id;
	private boolean startedMoving = false;
	
	public MovingConsonants(String consonants, DoppelungGameCoordinator coordinator, int x, int y, int id){
		super("doppelungGame/coins/coin_" + consonants + ".png");
		
		GWT.log(consonants);
		
		this.consonants = consonants;
		this.coordinator = coordinator;
		this.x = x;
		this.y = y;
		this.id = id;
		move = new Move(this);
	}
	
	public void setSpeed( int spaceSpeed){
		move.setSpaceSpeed(spaceSpeed);
	}
	
	public void startMoving(){
		
		coordinator.registerAniTask(move);

	}
	
	public void startMoving(int delay){
		//move.setDelay(delay);
		coordinator.registerAniTask(move);
	}
	
	public void moveTo(int toY){
		this.toY = toY;
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


	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}


	public void setStartedMoving(boolean startedMoving) {
		this.startedMoving = startedMoving;
	}

	public boolean isStartedMoving() {
		return startedMoving;
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
			if (!removed){
				if (y + spaceSpeed < toY)
					y = y + spaceSpeed;
				else
					y = toY;
				coordinator.setMovingConsonantsPosition(mc, x, y);
				coordinator.checkForCollision(mc);
				
			}
			
			if (removed){
				this.markForDelete();
			}
		}
	}
}
