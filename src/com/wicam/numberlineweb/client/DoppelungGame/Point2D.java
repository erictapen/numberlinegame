package com.wicam.numberlineweb.client.DoppelungGame;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Point2D implements IsSerializable {

	private int x = 0;
	private int y = 0;
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	
}
