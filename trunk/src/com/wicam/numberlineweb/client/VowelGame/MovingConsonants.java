package com.wicam.numberlineweb.client.VowelGame;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameCoordinator;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.Resources.DoppelungGameResourcesImages;

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
	private boolean collected = false;
	
	
	public MovingConsonants(String consonants, DoppelungGameCoordinator coordinator, int x, int y, int id){
		
		//super("doppelungGame/coins/coin_" + consonants + ".png");
		
		if (consonants.equals("ff"))
			this.setUrl((DoppelungGameResourcesImages.INSTANCE.coin_ff().getSafeUri()));
		if (consonants.equals("tt"))
			this.setUrl((DoppelungGameResourcesImages.INSTANCE.coin_tt().getSafeUri()));
		if (consonants.equals("ss"))
			this.setUrl((DoppelungGameResourcesImages.INSTANCE.coin_ss().getSafeUri()));
		if (consonants.equals("mm"))
			this.setUrl((DoppelungGameResourcesImages.INSTANCE.coin_mm().getSafeUri()));
		if (consonants.equals("ll"))
			this.setUrl((DoppelungGameResourcesImages.INSTANCE.coin_ll().getSafeUri()));
		if (consonants.equals("nn"))
			this.setUrl((DoppelungGameResourcesImages.INSTANCE.coin_nn().getSafeUri()));
		if (consonants.equals("pp"))
			this.setUrl((DoppelungGameResourcesImages.INSTANCE.coin_pp().getSafeUri()));
		if (consonants.equals("rr"))
			this.setUrl((DoppelungGameResourcesImages.INSTANCE.coin_rr().getSafeUri()));
		if (consonants.equals("ck"))
			this.setUrl((DoppelungGameResourcesImages.INSTANCE.coin_ck().getSafeUri()));
		if (consonants.equals("lt"))
			this.setUrl((DoppelungGameResourcesImages.INSTANCE.coin_lt().getSafeUri()));
		if (consonants.equals("rt"))
			this.setUrl((DoppelungGameResourcesImages.INSTANCE.coin_rt().getSafeUri()));
		
		
		GWT.log(consonants);
		
		this.consonants = consonants;
		this.coordinator = coordinator;
		this.x = x;
		this.y = y;
		this.id = id;
		move = new Move(this);
		
		//Image.prefetch("doppelungGame/knall_small.png");
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

	public void setCollected(int playerNumber) {
		
		this.collected = true;
		
		if (playerNumber == 1)
			this.setUrl(DoppelungGameResourcesImages.INSTANCE.coin_red().getSafeUri());
		else
			this.setUrl(DoppelungGameResourcesImages.INSTANCE.coin_blue().getSafeUri());
		
	}
	
	public boolean isCollected() {
		
		return this.collected;
		
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
