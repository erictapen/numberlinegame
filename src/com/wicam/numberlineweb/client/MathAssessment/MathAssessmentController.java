package com.wicam.numberlineweb.client.MathAssessment;

import com.google.gwt.user.client.ui.Widget;
import com.wicam.numberlineweb.client.GameController;

public class MathAssessmentController extends GameController {

	public MathAssessmentController(MathAssessmentCoordinator coordinator) {
		super(coordinator);
	}

	/**
	 * User clicked on "Start game"
	 */
	public void startButtonClicked() {
		((MathAssessmentCoordinator) coordinator).startButtonClicked();
	}
	
	/**
	 * User entered the possible result to the given math task.
	 * @param answer possible result
	 * @param timestamp the system time stamp at the moment the answer was given
	 */
	public void userAnswered(double answer, long timestamp) {
		((MathAssessmentCoordinator) coordinator).userAnswered(answer, timestamp);
	}

	/**
	 * We do not need this method in this package.
	 */
	@Override
	public void onMouseDown(Widget who, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * We do not need this method in this package.
	 */
	@Override
	public void onMouseUp(Widget who, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * We do not need this method in this package.
	 */
	@Override
	public void onMouseMove(Widget who, int x, int y) {
		// TODO Auto-generated method stub
		
	}





}
