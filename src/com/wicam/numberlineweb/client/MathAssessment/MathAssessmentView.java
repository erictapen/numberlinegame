package com.wicam.numberlineweb.client.MathAssessment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;

/**
 * Math assessment view.
 * @author timfissler
 *
 */

public class MathAssessmentView extends Composite {

	protected MathAssessmentController controller;
	protected final HorizontalPanel motherPanel = new HorizontalPanel();
//	protected final AbsolutePanel playerPanel = new AbsolutePanel();
	protected final AbsolutePanel explanationPanel = new AbsolutePanel();
	protected final HTML explanationText = new HTML();
	protected final Button startGameButton = new Button("Spiel starten", new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			
			initGameView();
			controller.startButtonClicked();
			// We do not use audio description here.
//			try {
//				descriptionSound.pause();
//				descriptionSound.setCurrentTime(0);
//			} catch (Exception e) {
//			}
			
		}
	}); 

	final HTML infoText = new HTML();
	
//	protected Audio descriptionSound = Audio.createIfSupported();

	/**
	 * Construct a new math assessment view.
	 * @param gameController
	 */
	public MathAssessmentView(MathAssessmentController gameController) {
		// Pass the number of players to the super class.
		this.controller = gameController;
		init();
//		sinkEvents(Event.MOUSEEVENTS);
		this.initWidget(motherPanel);
	}


	/**
	 * Show explanation.
	 */
	protected void init() {
		
		explanationPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		explanationPanel.setHeight("400px");
		explanationPanel.setWidth("600px");
		setExplanationText();
		explanationPanel.add(explanationText);
		explanationPanel.setWidgetPosition(explanationText, 0, 0);
		explanationPanel.add(startGameButton);
		explanationPanel.setWidgetPosition(startGameButton, 480, 350);
		motherPanel.add(explanationPanel);
		
		// We do not use audio description here.
//		if (Audio.isSupported() && descriptionSound != null) {
//			
//			descriptionSound.addSource("desc/Multiplication.ogg", "audio/ogg; codecs=vorbis");
//			descriptionSound.addSource("desc/Multiplication.mp3", "audio/mpeg; codecs=MP3");
//			
//			descriptionSound.play();
//			
//		}
		
		RootPanel.get().add(motherPanel);
	}
	
	/**
	 * Show the game, hide explanation
	 */
	public void initGameView() {
		explanationPanel.clear();
		explanationPanel.removeFromParent();
		
		// TODO Setup the PANEL with the field for the item/task and the entry field here.
//		motherPanel.add(PANEL);
	}
	
	/**
	 * Shows the screen where the task is presented to the user and the user
	 * may enter a result to the task. 
	 */
	public void showTaskView(String task) {
		
		setTask(task);
		clearInputField();
		
		// TODO Show the task view.
	}
	
	/**
	 * Shows a white screen to the user (between two trials).
	 */
	public void showWhiteScreen() {
		// TODO Implement this.
	}
	
	/**
	 * Shows a white screen with a fixation cross in its center to the user (between two trials).
	 */
	public void showFixationScreen() {
		// TODO Implement this.
	}
	
	/**
	 * Sets the new task in the view.
	 * @param task
	 */
	private void setTask(String task) {
		// TODO Implement this.
	}
	
	/**
	 * Clear the input field at the beginning of a new round.
	 */
	private void clearInputField() {
		// TODO Implement this.
	}
	
	
	
	/**
	 * Set the explanation-text
	 */
	public void setExplanationText(){
		// TODO Change description appropriately.
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Multiplikationsspiel - Beschreibung</b></div>" +
				"<div style='padding:5px 20px;font-size:12px'>" +
				"Du siehst gleich ein gewünschtes Ergebnis, und darüber 12 verschiedene Multiplikationsaufgaben. " +
				"Klicke so schnell wie möglich auf die Rechnung, die das gewünschte Ergebnis hat. " +
				"Es können auch mehrere Aufgaben richtig sein. Ist dein Gegenspieler schneller als du, " +
				"ist die Rechnung, die er angeklickt hat, aus dem Spiel. " +
				"</div>");
	}
	
}
