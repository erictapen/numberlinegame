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
	final HTML feedBackText = new HTML();
	final HTML wordText = new HTML(); // TODO: only for test implementation without sound
	final HTML pointsText = new HTML(); // TODO: only test implementation
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
	
	public void showVowelChoice(String word){
		gamePanel.clear();
		// TODO: positioning
		wordText.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>" + word + "</div>");
		gamePanel.add(wordText);
		gamePanel.add(shortVowelButton);
		gamePanel.add(longVowelButton);
	}
	
	// TODO: real implementation
	public void showFeedback(boolean correctAnswer){
		// reset buttons
		shortVowelButton.setDown(false);
		longVowelButton.setDown(false);
		gamePanel.clear();
		if (correctAnswer)
			feedBackText.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>Richtig!</div>");
		else
			feedBackText.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>Falsch!</div>");
		gamePanel.add(feedBackText);
	}
	
	public void startShortVowelGame(){
		gamePanel.clear();
	}
	
	// TODO: real implementation
	public void showEndScreen(int points){
		gamePanel.clear();
		pointsText.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>Punkte: " + points + "</div>");
		gamePanel.add(pointsText);
	}
}
