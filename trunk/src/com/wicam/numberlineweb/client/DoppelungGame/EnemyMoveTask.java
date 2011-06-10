package com.wicam.numberlineweb.client.DoppelungGame;

import com.google.gwt.core.client.GWT;

public class EnemyMoveTask extends AnimationTimerTask {
	
	private int spaceSpeedX;
	private int spaceSpeedY;
	private DoppelungGameCoordinator coordinator;
	private int toX;
	private int toY;
	
	public EnemyMoveTask(DoppelungGameCoordinator coordinator){
		this.coordinator = coordinator;
		this.spaceSpeedX = 6;
		this.spaceSpeedY = 6;
	}

	public void setSpaceSpeedX(int spaceSpeed){
		this.spaceSpeedX = spaceSpeed;
	}
	
	public void setSpaceSpeedY(int spaceSpeed){
		this.spaceSpeedY = spaceSpeed;
	}
	
	public void setToX(int x){
		this.toX = x;
	}
	
	public void setToY(int y){
		this.toY = y;
	}
	
	@Override
	public void run() {
		
		
		
		int x = coordinator.getEnemyImageX();
		int y = coordinator.getEnemyImageY();
		if ((spaceSpeedY >= 0 && y + spaceSpeedY < (toY)) ||
		(spaceSpeedY < 0 && y + spaceSpeedY > (toY)))
			y = y + spaceSpeedY;
		else
			y = toY;	
		if ((spaceSpeedX >= 0 && x + spaceSpeedX < (toX)) ||
				(spaceSpeedX < 0 && x + spaceSpeedX > (toX)))
			x = x + spaceSpeedX;
		else
			x = toX;
		
		
		coordinator.makeEnemyMove(x, y);
		
	}
}
