package com.wicam.numberlineweb.client.MathAssessment;

/**
 * Controller of the math assessment.
 * @author timfissler
 *
 */

public class MathAssessmentController {
	
	protected MathAssessmentCoordinator coordinator;
	
	/**
	 * Construct a new controller object.
	 * @param coordinator
	 */
	public MathAssessmentController(MathAssessmentCoordinator coordinator) {
		this.coordinator = coordinator;
	}

	/**
	 * User clicked on "Start game"
	 */
	public void startButtonClicked() {
		coordinator.startButtonClicked();
	}
	
	/**
	 * User entered the possible result to the given math task.
	 * @param answer possible result
	 * @param timestamp the system time stamp at the moment the answer was given
	 */
	public void userAnswered(double answer, long timestamp) {
		coordinator.userAnswered(answer, timestamp);
	}

}
