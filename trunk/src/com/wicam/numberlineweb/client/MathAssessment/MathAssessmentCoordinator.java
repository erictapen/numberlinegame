package com.wicam.numberlineweb.client.MathAssessment;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.wicam.numberlineweb.client.GameTypeSelector;

/**
 * Coordinator of the math assessment.
 * @author timfissler
 *
 */

public class MathAssessmentCoordinator {
	
	/* 
	 * TODO Add functionality for handling the trial rounds and the (time dependent) switching between
	 * the three different views.
	 */	
	
	protected MathAssessmentController controller;
	protected Panel rootPanel;
	protected GameTypeSelector gts;
	protected int playerID;
	protected MathAssessmentCommunicationServiceAsync commServ;
	protected MathAssessmentView view;
	protected ArrayList<String> itemList;
	
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
		this.playerID = 0;
		this.view = null;
		this.controller = null;
		this.itemList = null;
	}
	
	/**
	 * Initializes the coordinator.
	 */
	public void init() {

		// TODO Send client ID so that the server can retrieve an appropriate player ID.
		commServ.startAssessment("0", initCallback);
		
	}
	
	AsyncCallback<Integer> initCallback = new AsyncCallback<Integer>() {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log("Wasn't able to start a new math assessment.");
			GWT.log(caught.getMessage());
			
		}

		@Override
		public void onSuccess(Integer result) {
			GWT.log("New math assessment started.");
			
			playerID = result;
			
			// Construct the assessment.
			controller = new MathAssessmentController(MathAssessmentCoordinator.this);
			view = new MathAssessmentView(controller);
			
			// Download the shuffled list of math items.
			commServ.loadShuffledItemList(itemListCallback);
		}
		
	};
	
	AsyncCallback<ArrayList<String>> itemListCallback = new AsyncCallback<ArrayList<String>>() {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log("Wasn't able to download the item list for the math assessment.");
			GWT.log(caught.getMessage());			
		}

		@Override
		public void onSuccess(ArrayList<String> result) {
			GWT.log("Downloaded the item list for the math assessment.");
			
			itemList = result;
			
			// Clear the root panel and draw the game.
			rootPanel.clear();
			rootPanel.add(view);
		}
		
	};
	
	/**
	 * Is being called when the user has entered a result to the current task.
	 * @param answer
	 * @param timestamp
	 */
	public void userAnswered(double answer, long timestamp) {
		// TODO Implement this.
	}	
	
	/**
	 * User clicked on "Start game"
	 */
	public void startButtonClicked() {
		// TODO Start the game.	
	}

}
