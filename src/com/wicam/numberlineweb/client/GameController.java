package com.wicam.numberlineweb.client;


public abstract class GameController implements MouseHandler{

	protected GameCoordinator coordinator;

	public GameController(GameCoordinator coordinator) {

		this.coordinator=coordinator;
	

	}
}
