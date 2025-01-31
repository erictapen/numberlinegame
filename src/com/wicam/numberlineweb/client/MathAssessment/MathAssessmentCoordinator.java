package com.wicam.numberlineweb.client.MathAssessment;

import java.util.ArrayList;
import java.util.Iterator;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameTypeSelector;
import com.wicam.numberlineweb.client.NumberLineWeb;

/**
 * Coordinator of the math assessment.
 * @author timfissler
 *
 */

public class MathAssessmentCoordinator implements ValueChangeHandler<String> {	
	
	protected MathAssessmentController controller;
	protected Panel rootPanel;
	protected GameTypeSelector gts;
	protected MathAssessmentState state;
	protected MathAssessmentCommunicationServiceAsync commServ;
	protected MathAssessmentView view;
	protected String currentItem;
	protected long itemPresentedTimeStamp;
	protected long userAnsweredTimeStamp;
	protected long reactionMilliSeconds;
	protected MathAssessmentTaskResultParser taskResultParser = new MathAssessmentTaskResultParser();
	protected WhiteScreenTimer whiteScreenTimer = new WhiteScreenTimer();
	protected FixationScreenTimer fixationScreenTimer = new FixationScreenTimer();
	protected int whiteScreenDuration = 500; //ms
	protected int fixationScreenDuration = 500; //ms
	protected MathAssessmentNumericChecker numericChecker = new MathAssessmentNumericChecker();
	protected HandlerRegistration handlerReg;
	protected boolean assessmentFinished = false;
	protected boolean assessmentStarted = false;
	
	/**
	 * Constructs a new math assessment coordinator.
	 * @param commServ
	 * @param root
	 * @param gts
	 */
	public MathAssessmentCoordinator(MathAssessmentCommunicationServiceAsync commServ, 
			Panel root, GameTypeSelector gts) {
		this.commServ = commServ;
		this.rootPanel = root;
		this.gts = gts;
		this.state = null;
		this.view = null;
		this.controller = null;
		this.currentItem = "";
		this.itemPresentedTimeStamp = 0;
		this.userAnsweredTimeStamp = 0;
		this.reactionMilliSeconds = 0;
	}
	
	/**
	 * Initializes the coordinator.
	 */
	public void init() {
		
		// Construct the assessment.
		controller = new MathAssessmentController(MathAssessmentCoordinator.this);
		view = new MathAssessmentView(controller);
		
		// Add history item and register value change handler.
		History.newItem("math_assessment",false);
		handlerReg = History.addValueChangeHandler(this);	
		
		// Clear the root panel and draw the game.
		rootPanel.clear();
		rootPanel.add(view);
		view.showExplanationScreen();
	}
	
	// Get the assessment state from the server and register the assessment id.
	AsyncCallback<MathAssessmentState> startCallback = new AsyncCallback<MathAssessmentState>() {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log("Wasn't able to start a new math assessment.");
			GWT.log(caught.getMessage());
			
		}

		@Override
		public void onSuccess(MathAssessmentState result) {
			
			state = result;
			
			GWT.log("New math assessment started for player \"" + state.getPlayerName() + "\".");
			
			// Start the assessment.
			view.hideExplanationScreen();
			commServ.getNextItem(state.getAssessmentID(), nextItemCallback);
		}
		
	};
	
	/**
	 * Is being called when the user has entered a result to the current taskText.
	 * @param answer
	 * @param timestamp
	 */
	public void userAnswered(String answer, long timestamp) {
		
		// Handle results that are NOT numeric.
		if (!numericChecker.isDoubleConvertible(answer)) {
			view.showNotNumericWarning(true);
			view.highlightUserAnswer();
			return;
		}
		
		// Check the correctness of the given answer.
		boolean isCorrect = taskResultParser.checkTaskResult(currentItem, answer);
		
		// Pass the item, the result, the correctness, the reaction time and the time stamp to the server. 
		userAnsweredTimeStamp = timestamp;
		reactionMilliSeconds = userAnsweredTimeStamp - itemPresentedTimeStamp;
		String message = state.getAssessmentID() + ":" + currentItem + ":" + answer + ":" + isCorrect + ":" +
				Long.toString(reactionMilliSeconds) + ":" + Long.toString(timestamp);
		commServ.userAnswered(message, voidCallback);

		// Hide task screen ...
		view.hideTaskScreen();
		view.showNotNumericWarning(false);
		
		// Get next item if there is one else finish assessment.
		commServ.getNextItem(state.getAssessmentID(), nextItemCallback);
	}	
	
	/**
	 * User clicked on "Test starten". Start the assessment.
	 */
	public void startAssessment() {
		// Mark the assessment as started.
		assessmentStarted = true;
		
		// Send the user ID so that the server can retrieve an appropriate player ID.
		commServ.startAssessment(NumberLineWeb.USERID, startCallback);
	}
	
	/**
	 * User clicked on "Test beenden". End the assessment.
	 */
	public void endAssessment() {
		if (assessmentStarted) {
			commServ.endAssessment(state.getAssessmentID(), voidCallback);
		}
		view.hideEndScreen();
		
		// Clean up and start the game type selector.
		handlerReg.removeHandler();
		rootPanel.clear();
		gts.init(rootPanel);
	}
	
	/**
	 * Start the presentation of the next math task.
	 * 1. White screen, 2. Fixation screen, 3. Task screen.
	 */
	public void nextTrial() {
		view.showWhiteScreen();
		whiteScreenTimer.schedule(whiteScreenDuration);
	}
	
	// Get next item if there is one else finish assessment.
	public AsyncCallback<String> nextItemCallback = new AsyncCallback<String>() {
		
		@Override
		public void onFailure(Throwable caught) {
			GWT.log("Something went wrong while retrieving the next item!");
			GWT.log(caught.getMessage());
		}

		@Override
		public void onSuccess(String result) {
			// Start the next trial if there another item left.
			if (result != null) {
				// Next trial ...
				currentItem = result;
				nextTrial();
			}
			// ... if not show end screen.
			else {
				// Now the assessment has finished.
				assessmentFinished = true;
				view.showEndScreen();
			}
		}
		
	};
	
	// Empty callback, do nothing.
	public AsyncCallback<Void> voidCallback = new AsyncCallback<Void>() {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log("Something went wrong with the server communication!");
			GWT.log(caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			// Do nothing.
		}
		
	};
	
	/**
	 * A timer class for hiding the white screen and showing the fixation screen.
	 * @author timfissler
	 *
	 */
	public class WhiteScreenTimer extends Timer {

		@Override
		public void run() {
			MathAssessmentCoordinator.this.view.hideWhiteScreen();
			MathAssessmentCoordinator.this.view.showFixationScreen();
			// Schedule fixation timer.
			fixationScreenTimer.schedule(fixationScreenDuration);
		}

	}

	/**
	 * A timer class for hiding the fixation screen and showing the next task item.
	 * @author timfissler
	 *
	 */
	public class FixationScreenTimer extends Timer {

		@Override
		public void run() {
			MathAssessmentCoordinator.this.view.hideFixationScreen();
			
			// Show next item.
			MathAssessmentCoordinator.this.view.showTaskScreen(currentItem);

			// Send the item and the current time stamp to the server.
			MathAssessmentCoordinator.this.itemPresentedTimeStamp = System.currentTimeMillis();
			String message = state.getAssessmentID() + ":" + currentItem + ":" + MathAssessmentCoordinator.this.itemPresentedTimeStamp;
			MathAssessmentCoordinator.this.commServ.itemPresented(message, voidCallback);
		}

	}
	
	/**
	 * Handle user abortion of the assessment via user hitting server 'back'-button. 
	 */
	public void handleAssessmentAbortion() {
		
		/*
		 *  If the assessment is started but NOT finished yet, this is an abortion and
		 *  this should be logged by the server.
		 */
		if (assessmentStarted && !assessmentFinished) {
			String message = state.getAssessmentID() +
					":" + currentItem + ":" + System.currentTimeMillis();
			commServ.userAborted(message, voidCallback);
		}
		
		endAssessment();
	}

	/**
	 * Action in case of history back event.
	 * @param event
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if (event.getValue().matches("")) {
			// Ask the user if he really wants to abort the assessment.
			new MathAssessmentCloseQuestion(this);
		}	
	}
}
