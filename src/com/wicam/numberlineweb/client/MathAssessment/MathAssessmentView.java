package com.wicam.numberlineweb.client.MathAssessment;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Line;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Math assessment view.
 * @author timfissler
 *
 */

public class MathAssessmentView extends Composite {

	protected MathAssessmentController controller;
	protected final HorizontalPanel motherPanel = new HorizontalPanel();
	protected final AbsolutePanel explanationPanel = new AbsolutePanel();
	protected final AbsolutePanel endPanel = new AbsolutePanel();
	protected final AbsolutePanel taskPanel = new AbsolutePanel();
	protected final AbsolutePanel whitePanel = new AbsolutePanel();
	protected final HorizontalPanel fixationPanel = new HorizontalPanel();
	protected final DrawingArea fixationDrawing = new DrawingArea(50, 50);
	protected final HTML taskText = new HTML();
	protected final TextBox resultBox = new TextBox();
	protected final HTML explanationText = new HTML();
	protected final HTML endText = new HTML();
	protected final HTML nonNumericWarningText = new HTML();
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
	public MathAssessmentView(MathAssessmentController gameController) {
		this.controller = gameController;
		init();
		this.initWidget(motherPanel);
	}


	/**
	 * Initialize the views.
	 */
	protected void init() {
		
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
		setTask("");
		taskPanel.add(taskText);
		taskPanel.setWidgetPosition(taskText, 310, 194);
		setNonNumericWarning();
		nonNumericWarningText.setVisible(false);
		taskPanel.add(nonNumericWarningText);
		taskPanel.setWidgetPosition(nonNumericWarningText, 100, 230);
		resultBox.setWidth("60px");
		resultBox.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
			    int keyCode = event.getUnicodeCharCode();
			    // Use Workaround otherwise for Firefox.
			    if (keyCode == 0) {
			        // Probably Firefox
			        keyCode = event.getNativeEvent().getKeyCode();
			    }
			    // Do something when Enter is pressed.
			    if (keyCode == KeyCodes.KEY_ENTER) {
			    	// Pass the entered result and the current time stamp to the controller.
					controller.userAnswered(resultBox.getText(), System.currentTimeMillis());
			    }
			}
		});		
		resultBox.setStyleName("resultBox");
		taskPanel.add(resultBox);
		taskPanel.setWidgetPosition(resultBox, 420, 190);
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
	public void showTaskScreen(String task) {
		setTask(task);
		clearResultBox();
		
		// Show the task view.
		taskPanel.setVisible(true);
		resultBox.setFocus(true);
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
	private void setTask(String task) {
		taskText.setHTML("<div style='font-size:24px'>" + task + " = </div>"); 
	}
	
	/**
	 * Sets the non numeric warning text in the view.
	 * @param nonNumericWarningText
	 */
	private void setNonNumericWarning() {
		nonNumericWarningText.setHTML("<div style='font-size:12px;color:red'>" + 
										"Bitte gib nur Nummern (0-9) und den Punkt '.' " +
										"für Kommazahlen, sowie das Minus '-' für negative Zahlen ein!" +
										"</div>"); 
	}
	
	/**
	 * Switch warning for non numeric user answers in the task screen.
	 * @param isShowing
	 */
	public void showNotNumericWarning(boolean isShowing) {
		nonNumericWarningText.setVisible(isShowing);
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
		// TODO Change description appropriately.
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Mathe-Test - Beschreibung</b></div>" +
				"<div style='padding:5px 20px;font-size:12px'>" +
				"Dir werden gleich mehrere Matheaufgaben hintereinander gezeigt. Zwischen den Aufgaben " +
				"siehst du ein schwarzes Kreuz. Versuche es mit deinen Augen zu fixieren und dann die Aufgabe so schnell " +
				"und exakt wie möglich zu lösen, indem du die Lösung eingibst und anschließend "+
				"die \"Enter\"-Taste drückst." +
				"</div>");
	}
	
	/**
	 * Set the ending-text
	 */
	public void setEndText(){
		// TODO Change text appropriately.
		endText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Mathe-Test - Ende</b></div>" +
				"<div style='padding:5px 20px;font-size:12px'>" +
				"Vielen Dank für's Mitmachen. Der Test ist nun zu Ende.</div>");
	}
	
}
