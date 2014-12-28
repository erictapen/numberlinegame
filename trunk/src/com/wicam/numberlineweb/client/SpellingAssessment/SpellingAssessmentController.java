package com.wicam.numberlineweb.client.SpellingAssessment;

/**
 * Controller of the math assessment.
 * @author timfissler
 *
 */

public class SpellingAssessmentController {
	
	
	protected SpellingAssessmentCoordinator coordinator;
	
	/**
	 * Playback of the result word has finished
	 */
	public void playbackEnded(long timestamp){
		coordinator.itemPresented(timestamp);
	}
	
	
	/**
	 * Construct a new controller object.
	 * @param coordinator
	 */
	public SpellingAssessmentController(SpellingAssessmentCoordinator coordinator) {
		this.coordinator = coordinator;
	}

	/**
	 * User clicked on "Test starten".
	 */
	public void startButtonClicked() {
		coordinator.startAssessment();
	}
	
	/**
	 * User clicked on "Test beenden".
	 */
	public void endButtonClicked() {
		coordinator.endAssessment();
	}
	
	/**
	 * User entered the possible result to the given math taskText.
	 * @param answer possible result
	 * @param timestamp the system time stamp at the moment the answer was given
	 */
	public void userAnswered(String answer, long timestamp) {
		coordinator.userAnswered(answer, timestamp);
	}

}
