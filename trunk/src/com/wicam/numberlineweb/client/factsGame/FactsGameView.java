package com.wicam.numberlineweb.client.factsGame;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.wicam.numberlineweb.client.GameView;

public class FactsGameView extends GameView {

	private FactsGameController controller;
	private final HorizontalPanel motherPanel = new HorizontalPanel();
	private final AbsolutePanel gamePanel = new AbsolutePanel();
	private final AbsolutePanel pointsPanel = new AbsolutePanel();
	
	private final HTML canvas = new HTML("<div id='canvas' style='width:600px;height:400px;'></div>");
	private final HTML canvasScore = new HTML("<div id='canvas' style='width:150px;height:30px;'></div>");
	
	protected FactsGameView(int numberOfPlayers, FactsGameController controller) {
		super(numberOfPlayers);
		this.controller = controller;
		init();
		sinkEvents(Event.MOUSEEVENTS);
		this.initWidget(motherPanel);
	}
	
	private void init() {

		//draw everthing
		gamePanel.getElement().getStyle().setPosition(Position.RELATIVE);
		gamePanel.add(canvas);
		pointsPanel.add(canvasScore);
		
		motherPanel.add(gamePanel);
		motherPanel.add(pointsPanel);
	}
	
	/**
	 * mouse handling
	 */

	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		if (controller != null) {
			int x = event.getClientX() - getAbsoluteLeft();
			int y = event.getClientY() - getAbsoluteTop();
			switch (event.getTypeInt()) {
			case Event.ONMOUSEDOWN:
				controller.onMouseDown(this, x, y);
				break;
			case Event.ONMOUSEMOVE:
				controller.onMouseMove(this, x, y);
				break;
			case Event.ONMOUSEUP:
				controller.onMouseUp(this, x, y);
				break;
			}                           
		}
	}
}
