package com.wicam.numberlineweb.client.MultiplicationInverse;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameController;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameView;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationView;

/**
 * The game view.
 * @author alex
 *
 */

public class MultiplicationInverseGameView extends MultiplicationGameView  {
	
//	final MultiplicationInverseView p = new MultiplicationInverseView();
//	final Button startGameButton = new Button("Spiel starten", new ClickHandler() {
//		@Override
//		public void onClick(ClickEvent event) {
//			initGameView();
//			((MultiplicationGameController) gameController).startButtonClicked();
//			// In the inverse multiplication game no description sound is used.
////			try {
////				descriptionSound.pause();
////				descriptionSound.setCurrentTime(0);
////			} catch (Exception e) {
////			}
//		}
//	});

	public MultiplicationInverseGameView(MultiplicationGameController gameController,
			int numberOfPlayers, int numberOfNPCs) {
		super(gameController, numberOfPlayers, numberOfNPCs);
		// Change the initialization of the view and the start game button.
		this.p = new MultiplicationInverseView();
		this.startGameButton = new Button("Spiel starten", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				initGameView();
				((MultiplicationGameController) MultiplicationInverseGameView.this.gameController).startButtonClicked();
				// In the inverse multiplication game no description sound is used.
//				try {
//					descriptionSound.pause();
//					descriptionSound.setCurrentTime(0);
//				} catch (Exception e) {
//				}
			}
		});
	}
	
	public void setTaskText(String task) {
		((MultiplicationInverseView) p).setTaskText(task);
	}
	
	/**
	 * Show explanation
	 */
	@Override
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
		
		GWT.log("executing inverse init");
		
		// In the inverse multiplication game no description sound is used.
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
	 * Set the explanation-text
	 */
	@Override
	public void setExplanationText(){
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Multiplikationsspiel - Beschreibung</b></div>" +
				"<div style='padding:5px 20px;font-size:12px'>" +
				"Du siehst gleich eine Multiplikationsaufgabe, und darüber 12 verschiedene mögliche Ergebnisse. " +
				"Klicke so schnell wie möglich auf das richtige Ergbnis der Rechenaufgabe. " +
				"Ist dein Gegenspieler schneller als du, " +
				"dann hat er die Runde gewonnen. " +
				"</div>");
	}
	
}