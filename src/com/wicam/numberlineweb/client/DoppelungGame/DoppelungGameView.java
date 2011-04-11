package com.wicam.numberlineweb.client.DoppelungGame;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.wicam.numberlineweb.client.GameView;

public class DoppelungGameView extends GameView {

	private DoppelungGameController doppelungGameController;
	final HorizontalPanel motherPanel = new HorizontalPanel();
	final AbsolutePanel gamePanel = new AbsolutePanel();
	final AbsolutePanel pointsPanel = new AbsolutePanel();
	
	final HTML explanationText = new HTML();
	protected final Button startGameButton = new Button("Spiel Starten");
	// TODO: add images
	protected final ToggleButton shortVowelButton = new ToggleButton("kurzer Vokal");
	protected final ToggleButton longVowelButton = new ToggleButton("langer Vokal");
	
	public DoppelungGameView(int numberOfPlayers, DoppelungGameController doppelungGameController) {
		super(numberOfPlayers);
		this.doppelungGameController = doppelungGameController;
		init();
		sinkEvents(Event.MOUSEEVENTS);
		this.initWidget(motherPanel);
	}
	
	private void init() {
		
		//draw everthing
		gamePanel.getElement().getStyle().setPosition(Position.RELATIVE);
		
		explanationText.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>Erklärung</div>");
		startGameButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doppelungGameController.onStartButtonClick();
			}
		});
		
		shortVowelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doppelungGameController.onShortVowelButtonClick();
			}
		});
		
		longVowelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doppelungGameController.onLongVowelButtonClick();
			}
		});
		
		// TODO: positioning
		gamePanel.add(explanationText);
		gamePanel.add(startGameButton);
		
		motherPanel.add(gamePanel);
		RootPanel.get().add(motherPanel);
		
	}
	
	public void showVowelChoice(){
		gamePanel.clear();
		// TODO: positioning
		gamePanel.add(shortVowelButton);
		gamePanel.add(longVowelButton);
	}
}
