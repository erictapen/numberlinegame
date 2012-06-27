package com.wicam.numberlineweb.server;

import java.util.Timer;

public abstract class NPC {
	
	protected Timer t;
	protected boolean isTimerCancelled = false;
	
	public void terminateTimer() {

		this.isTimerCancelled = true;
		this.t.cancel();
		
	}

}
