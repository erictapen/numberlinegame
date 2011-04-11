package com.wicam.numberlineweb.client.DoppelungGame;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.wicam.numberlineweb.client.GameView;

public class DoppelungGameView extends GameView {

	final HorizontalPanel motherPanel = new HorizontalPanel();
	final AbsolutePanel gamePanel = new AbsolutePanel();
	final AbsolutePanel pointsPanel = new AbsolutePanel();
	
	final HTML explanationText = new HTML();
	
	public DoppelungGameView(int numberOfPlayers) {
		super(numberOfPlayers);
		init();
		sinkEvents(Event.MOUSEEVENTS);
		this.initWidget(motherPanel);
	}
	
	private void init() {
		
		//draw everthing
		gamePanel.getElement().getStyle().setPosition(Position.RELATIVE);
		
		explanationText.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>Explanation</div>");
		gamePanel.add(explanationText);
		
		motherPanel.add(gamePanel);
		RootPanel.get().add(motherPanel);
		
	}
	
}
