package com.wicam.numberlineweb.client.SpellingAssessment;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Line;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.wicam.numberlineweb.client.SpellingAssessment.SpellingAssessmentItem;

/**
 * Math assessment view.
 * @author timfissler
 *
 */

public class SpellingAssessmentView extends Composite {

	/**
	 * Time between end of sentence playback and start of result playback.
	 */
	private final int sentenceResultOffset = 500; // ms
	private SpellingAssessmentItem currentItem;
	protected SpellingAssessmentController controller;
	protected final HorizontalPanel motherPanel = new HorizontalPanel();
	protected final AbsolutePanel explanationPanel = new AbsolutePanel();
	protected final AbsolutePanel endPanel = new AbsolutePanel();
	protected final HorizontalPanel taskPanel = new HorizontalPanel();
	protected final AbsolutePanel whitePanel = new AbsolutePanel();
	protected final HorizontalPanel fixationPanel = new HorizontalPanel();
	protected final DrawingArea fixationDrawing = new DrawingArea(50, 50);
	protected final HTML taskText = new HTML();
	protected final TextBox resultBox = new TextBox();
	protected final HTML explanationText = new HTML();
	protected final HTML endText = new HTML();
	protected final HorizontalPanel taskWrapper = new HorizontalPanel();
	protected final Button startButton = new Button("Test starten", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			controller.startButtonClicked();			
		}
	}); 
	protected final Button endButton = new Button("Schließen", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			controller.endButtonClicked();			
		}
	});
	final HTML infoText = new HTML();

	/**
	 * Construct a new math assessment view.
	 * @param gameController
	 */
	public SpellingAssessmentView(SpellingAssessmentController gameController) {
		this.controller = gameController;
		init();
		this.initWidget(motherPanel);
	}
	
	/**
	 * Plays the result (word) of a spelling assessment item
	 * @param item
	 */
	private void playResult(SpellingAssessmentItem item){
		Audio audio = SpellingAssessmentSoundRetriever.getAudioElement(item, false);
		if (audio == null) {
			try {
				throw new Exception("No audio found for \"" + item.getResult() + "\"!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// Enable the input box and log the item presentation after the playback of the result.
			Timer timer = new Timer() {
				
				@Override
				public void run() {
					// TODO Change color of result box.
					resultBox.setEnabled(true);
					resultBox.setFocus(true);
					long timestamp = System.currentTimeMillis();
					controller.playbackEnded(timestamp);
				}
				
			};
			timer.schedule(item.getResultDuration());
			audio.play();
		}
	}
	
	/**
	 * Plays the sentence of a spelling assessment item
	 * @param item
	 */
	private void playSentence(SpellingAssessmentItem item){
		this.currentItem = item;
		Audio audio = SpellingAssessmentSoundRetriever.getAudioElement(item, true);
		if (audio == null) {
			try {
				throw new Exception("No audio found for \"" + item.getResult() + "\"!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			/*
			 * Creates a pause between sentence playback and result playback, 
			 * so that the user has time to settle.
			 */
			Timer timer = new Timer() {
				
				@Override
				public void run() {
					playResult(currentItem);
				}
				
			};
			// Start playback of result 500 ms after playback of sentence.
			timer.schedule(item.getSentenceDuration() + sentenceResultOffset);
			audio.play();
		}
	}

	/**
	 * Initialize the views.
	 */
	protected void init() {
		
		this.currentItem = null;
		
		// Setup the explanation panel.
		explanationPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		explanationPanel.setHeight("400px");
		explanationPanel.setWidth("750px");
		setExplanationText();
		explanationPanel.add(explanationText);
		explanationPanel.setWidgetPosition(explanationText, 0, 0);
		explanationPanel.add(startButton);
		explanationPanel.setWidgetPosition(startButton, 600, 350);
		explanationPanel.setVisible(false);
		motherPanel.add(explanationPanel);
		
		// Setup the panel with the field for the item/taskText and the entry field.
		taskPanel.setHeight("400px");
		taskPanel.setWidth("750px");
		
		taskPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		taskPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		setTask(new SpellingAssessmentItem());
		taskWrapper.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		taskWrapper.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		resultBox.getElement().getStyle().setProperty("display", "inline");
		resultBox.setWidth("100px");
		resultBox.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				
//				GWT.log(String.valueOf(event.getCharCode()));
//				GWT.log(String.valueOf(event.getUnicodeCharCode()));
//				GWT.log(String.valueOf(event.getNativeEvent().getKeyCode()));
				
				// User hit "Enter".
				if (event.getNativeEvent().getKeyCode() == 13) { 
					// Pass the entered result and the current time stamp to the controller.
					controller.userAnswerComplete(resultBox.getText(), System.currentTimeMillis());
				// User hit some other key.
				} else {
					// Pass the incomplete user answer and the current time stamp to the controller.
					controller.userAnswered(resultBox.getText(), System.currentTimeMillis());
				}
			}
		});
		resultBox.setStyleName("resultBox");
		//taskPanel.add(resultBox);
		taskWrapper.add(taskText);
		taskWrapper.add(resultBox);
		taskPanel.add(taskWrapper);
		taskPanel.setVisible(false);
		motherPanel.add(taskPanel);
		
		// Setup white panel.
		whitePanel.setHeight("400px");
		whitePanel.setWidth("750px");
		whitePanel.setVisible(false);
		motherPanel.add(whitePanel);
		
		// Setup fixation panel.
		fixationPanel.setHeight("400px");
		fixationPanel.setWidth("750px");
		Line hLine = new Line(0, 25, 50, 25);
		hLine.setStrokeWidth(3);
		Line vLine = new Line(25, 0, 25, 50);
		vLine.setStrokeWidth(3);
		fixationDrawing.add(vLine);
		fixationDrawing.add(hLine);
		fixationPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		fixationPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		fixationPanel.add(fixationDrawing);
		fixationPanel.setVisible(false);
		motherPanel.add(fixationPanel);
		
		// Setup end panel.
		endPanel.setHeight("400px");
		endPanel.setWidth("750px");
		setEndText();
		endPanel.add(endText);
		endPanel.setWidgetPosition(endText, 0, 0);
		endPanel.add(endButton);
		endPanel.setWidgetPosition(endButton, 600, 350);
		endPanel.setVisible(false);
		motherPanel.add(endPanel);
		
		RootPanel.get().add(motherPanel);
	}
	
	/**
	 * Show explanation.
	 */
	public void showExplanationScreen() {
		explanationPanel.setVisible(true);
		startButton.setFocus(true);
	}
	
	/**
	 * Hide explanation
	 */
	public void hideExplanationScreen() {
		explanationPanel.setVisible(false);
	}
	
	/**
	 * Shows the screen where the taskText is presented to the user and the user
	 * may enter a result to the taskText. 
	 */
	public void showTaskScreen(SpellingAssessmentItem currentItem) {
		this.currentItem = currentItem;
		setTask(currentItem);
		clearResultBox();
		// TODO Change color of result box.
		resultBox.setEnabled(false);
		
		// Show the task view.
		taskPanel.setVisible(true);
		
		// Start sentence playback
		playSentence(currentItem);
		
	}
	
	/**
	 * Hide the task view.
	 */
	public void hideTaskScreen() {
		taskPanel.setVisible(false);
	}
	
	/**
	 * Shows a white screen to the user (between two trials).
	 */
	public void showWhiteScreen() {
		whitePanel.setVisible(true);
	}
	
	/**
	 * Hide the white screen.
	 */
	public void hideWhiteScreen() {
		whitePanel.setVisible(false);
	}
	
	/**
	 * Shows a white screen with a fixation cross in its center to the user (between two trials).
	 */
	public void showFixationScreen() {
		fixationPanel.setVisible(true);
	}
	
	/**
	 * Hide the fixation screen.
	 */
	public void hideFixationScreen() {
		fixationPanel.setVisible(false);
	}
	
	/**
	 * Show the end screen.
	 */
	public void showEndScreen() {
		endPanel.setVisible(true);
		endButton.setFocus(true);
	}
	
	/**
	 * Hide the end screen.
	 */
	public void hideEndScreen() {
		endPanel.setVisible(false);
	}
	
	/**
	 * Sets the new taskText in the view.
	 * @param taskText
	 */
	private void setTask(SpellingAssessmentItem currentItem) {
		
		taskText.setHTML("<div style='font-size:17px'>" + currentItem.getSentence() + " = </div>"); 
	}
	
	
	/**
	 * Highlight the user answer in the task screen.
	 */
	public void highlightUserAnswer() {
		String result = resultBox.getText();
		resultBox.setSelectionRange(0, result.length());
	}
	
	/**
	 * Clear the input field at the beginning of a new round.
	 */
	private void clearResultBox() {
		resultBox.setText("");
	}
	
	/**
	 * Set the explanation-text
	 */
	public void setExplanationText(){
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Rechtschreibe-Test - Beschreibung</b></div>" +
				"<div style='padding:5px 20px;font-size:12px'>" +
				"Um dich ein wenig mit dem Test vertraut zu machen, darfst du zunächst einmal spielerisch " +
				"ein paar Aufgaben versuchen, ohne dass es dabei wichtig ist wie schnell du bist." +
				"<br><br><strong>Ablauf des Tests</strong><br>"+
				"Dir wird zunächst ein Satz angezeigt. Ein Wort im Satz fehlt und das sollst du einsetzen." +
				"Gleichzeitig wird dir der Satz mit dem fehlenden Wort vorgelesen. Nach dem Vorlesen " + 
				"sollst du das gesuchte Wort so schnell wie möglich richtig eingeben. Bist du fertig, kannst " + 
				"du mit der \"Enter\"-Taste die Aufgabe beenden. "+
				"<br><br><strong>Bist du bereit?</strong><br>"+
				"Dann klicke auf \"Test starten\"."+
				"</div>");
	}
	
	/**
	 * Set the ending-text
	 */
	public void setEndText(){
		endText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Mathe-Test - Ende</b></div>" +
				"<div style='padding:5px 20px;font-size:12px'>" +
				"Vielen Dank für's Mitmachen. Der Test ist nun zu Ende.</div>");
	}
	
}
