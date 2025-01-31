package com.wicam.numberlineweb.client;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class Player implements IsSerializable, Comparable<Player>{

	private String name = "Spieler";
	private int points = 0;
	private boolean leftGame = false;
	private boolean ready = false; // for synchronization
	private int colorId = 1;
	private int uid = -2;
	private List<Double> reactionTimes = new LinkedList<Double>(); 
	
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
	@Override
	public int compareTo(Player p) {
		return p.getPoints() - this.getPoints();
	}
	
	public void setColorId(int colorId) {
		this.colorId = colorId;
	}
	
	public int getColorId() {
		return colorId;
	}
	
	public int getUid() {
		return uid;
	}
	
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public void addReactionTime(Double reactionTime){
		this.reactionTimes.add(reactionTime);
	}
	
	public List<Double> getReactionTimes() {
		return this.reactionTimes;
	}
	
	public String toString() {
		String s = "name: " + getName();
		s += ", points: " + getPoints();
		s += ", left game: " + hasLeftGame();
		s += ", ready: " + isReady();
		s += ", color ID: " + getColorId();
		s += ", user ID: " + getUid();
		s += ", reaction times: " + getReactionTimes();
		return s;
	}
	
}
