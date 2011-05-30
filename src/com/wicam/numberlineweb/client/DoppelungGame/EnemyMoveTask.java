package com.wicam.numberlineweb.client.DoppelungGame;

public class EnemyMoveTask extends AnimationTimerTask {
	
	private int spaceSpeed;
	private DoppelungGameCoordinator coordinator;
	private int toX;
	private int toY;
	
	public EnemyMoveTask(DoppelungGameCoordinator coordinator){
		this.coordinator = coordinator;
		this.spaceSpeed = 6;
	}

	public void setSpaceSpeed(int spaceSpeed){
		this.spaceSpeed = spaceSpeed;
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
		if (y + spaceSpeed < toY)
			y = y + spaceSpeed;
		else
			y = toY;	
		if (x + spaceSpeed < toX)
			x = x + spaceSpeed;
		else
			x = toX;
		coordinator.makeEnemyMove(x, y);
	}
}
