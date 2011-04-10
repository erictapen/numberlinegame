package com.wicam.numberlineweb.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class Player implements IsSerializable, Comparable<Player>{

	private String name = "Spieler";
	private int points = 0;
	private boolean leftGame = false;
	private boolean ready = false; // for synchronization
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	public boolean isReady() {
		return ready;
	}
	
	public void setLeftGame(boolean leftGame) {
		this.leftGame = leftGame;
	}
	
	public boolean hasLeftGame() {
		return leftGame;
	}
	public int compareTo(Player p) {
		return p.getPoints() - this.getPoints();
	}
}
