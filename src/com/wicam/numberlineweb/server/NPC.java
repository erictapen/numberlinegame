package com.wicam.numberlineweb.server;

import java.util.Timer;

public abstract class NPC {
	
	protected Timer t;
	
	public void terminateTimer() {

		t.cancel();
		
	}

}
