package com.wicam.numberlineweb.client.SpellingAssessment;

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
import com.wicam.numberlineweb.client.SpellingAssessment.SpellingAssessmentItem;

/**
 * Coordinator of the math assessment.
 * @author timfissler
 *
 */

public class SpellingAssessmentCoordinator implements ValueChangeHandler<String> {	
	
	protected SpellingAssessmentController controller;
	protected Panel rootPanel;
	protected GameTypeSelector gts;
	protected SpellingAssessmentState state;
	protected SpellingAssessmentCommunicationServiceAsync commServ;
	protected SpellingAssessmentView view;
	protected ArrayList<SpellingAssessmentItem> itemList;
	protected Iterator<SpellingAssessmentItem> itemListIterator;
	protected SpellingAssessmentItem currentItem;
	protected long itemPresentedTimeStamp;
	protected long userAnsweredTimeStamp;
	protected long reactionMilliSeconds;
	protected WhiteScreenTimer whiteScreenTimer = new WhiteScreenTimer();
	protected FixationScreenTimer fixationScreenTimer = new FixationScreenTimer();
	protected int whiteScreenDuration = 500; //ms
	protected int fixationScreenDuration = 500; //ms
	protected SpellingAssessmentNumericChecker numericChecker = new SpellingAssessmentNumericChecker();
	protected HandlerRegistration handlerReg;
	protected boolean assessmentFinished = false;
	protected boolean assessmentStarted = false;
	
	/**
	 * Constructs a new math assessment coordinator.
	 * @param commServ
	 * @param root
	 * @param gts
	 */
	public SpellingAssessmentCoordinator(SpellingAssessmentCommunicationServiceAsync commServ, 
			Panel root, GameTypeSelector gts) {
		this.commServ = commServ;
		this.rootPanel = root;
		this.gts = gts;
		this.state = null;
		this.view = null;
		this.controller = null;
		this.itemList = null;
		this.currentItem = null;
		this.itemPresentedTimeStamp = 0;
		this.userAnsweredTimeStamp = 0;
		this.reactionMilliSeconds = 0;
	}
	
	/**
	 * Initializes the coordinator.
	 */
	public void init() {
		
		// Construct the assessment.
		controller = new SpellingAssessmentController(this);
		view = new SpellingAssessmentView(controller);
		
		// Add history item and register value change handler.
		History.newItem("spelling_assessment",false);
		handlerReg = History.addValueChangeHandler(this);

		// Download the shuffled list of math items.
		commServ.loadShuffledItemList(itemListCallback);	
	}
	
	AsyncCallback<SpellingAssessmentState> initCallback = new AsyncCallback<SpellingAssessmentState>() {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log("Wasn't able to start a new spelling assessment.");
			GWT.log(caught.getMessage());
			
		}

		@Override
		public void onSuccess(SpellingAssessmentState result) {
			
			state = result;
			
			GWT.log("New spelling assessment started for player \"" + state.getPlayerName() + "\".");
			
			// Start the assessment.
			view.hideExplanationScreen();
			nextTrial();
		}
		
	};
	
	AsyncCallback<ArrayList<SpellingAssessmentItem>> itemListCallback = new AsyncCallback<ArrayList<SpellingAssessmentItem>>() {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log("Wasn't able to download the item list for the spelling assessment.");
			GWT.log(caught.getMessage());			
		}

		@Override
		public void onSuccess(ArrayList<SpellingAssessmentItem> result) {
			GWT.log("Downloaded the item list for the spelling assessment.");
			
			itemList = result;
			itemListIterator = itemList.iterator();
			
			// Clear the root panel and draw the game.
			rootPanel.clear();
			rootPanel.add(view);
			view.showExplanationScreen();
		}
		
	};
	
	/**
	 * Is being called when the user has entered a result to the current taskText.
	 * @param answer
	 * @param timestamp
	 */
	public void userAnswered(String answer, long timestamp) {
		
		
		boolean isCorrect;
		// Check the correctness of the given answer.
		if(answer == currentItem.result()){
			isCorrect = true;
		} else{
			isCorrect = false;
		}
		
		// Pass the item, the result, the correctness, the reaction time and the time stamp to the server. 
		userAnsweredTimeStamp = timestamp;
		reactionMilliSeconds = userAnsweredTimeStamp - itemPresentedTimeStamp;
		String message = state.getAssessmentID() + ":" + currentItem + ":" + answer + ":" + isCorrect + ":" +
				Long.toString(reactionMilliSeconds) + ":" + Long.toString(timestamp);
		commServ.userAnswered(message, voidCallback);

		// Hide task screen ...
		view.hideTaskScreen();
		// ... and start the next trial if there is one left, ...
		if (itemListIterator.hasNext()) {
			nextTrial();
		}
		// ... if not show end screen.
		else {
			// Now the assessment has finished.
			assessmentFinished = true;
			view.showEndScreen();
		}
	}	
	
	/**
	 * User clicked on "Test starten". Start the assessment.
	 */
	public void startAssessment() {
		// Mark the assessment as started.
		assessmentStarted = true;
		
		// Send the user ID so that the server can retrieve an appropriate player ID.
		commServ.startAssessment(NumberLineWeb.USERID, initCallback);
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
			SpellingAssessmentCoordinator.this.view.hideWhiteScreen();
			SpellingAssessmentCoordinator.this.view.showFixationScreen();
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
			SpellingAssessmentCoordinator.this.view.hideFixationScreen();
			
			// Get next item.
			currentItem = itemListIterator.next();
			SpellingAssessmentCoordinator.this.view.showTaskScreen(currentItem);

			// Send the item and the current time stamp to the server.
			SpellingAssessmentCoordinator.this.itemPresentedTimeStamp = System.currentTimeMillis();
			String message = state.getAssessmentID() + ":" + currentItem + ":" + SpellingAssessmentCoordinator.this.itemPresentedTimeStamp;
			SpellingAssessmentCoordinator.this.commServ.itemPresented(message, voidCallback);
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
			new SpellingAssessmentCloseQuestion(this);
		}	
	}
}
