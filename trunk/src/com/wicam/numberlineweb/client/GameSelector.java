package com.wicam.numberlineweb.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;

public abstract class GameSelector extends Composite {

	protected final AbsolutePanel motherPanel = new AbsolutePanel();
	protected GameCoordinator coordinator;
	
	abstract protected void init();
	abstract public void joinGame();
	abstract public void setGameList(ArrayList<? extends GameState> result);
	abstract public void setSelected (GameState g);
}
