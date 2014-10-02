package com.wicam.numberlineweb.client.MathAssessment;

import com.google.gwt.user.client.ui.Widget;
import com.wicam.numberlineweb.client.GameController;

public class MathAssessmentController extends GameController {



	public MathAssessmentController(MathAssessmentCoordinator coordinator) {
		super(coordinator);
	}

	
	
	public void clickedAt(String answer) {
		((MathAssessmentCoordinator) coordinator).clickAt(answer);
	}
	
	
	/**
	 * Forwards mouse-clicks to the coordinator
	 * @param who Widget, that was clicked
	 */
	@Override
	public void onMouseDown(Widget who, int x, int y) {
		
		((MathAssessmentCoordinator) coordinator).clickAt(who,x,y);
		
	}
	
	

	/**
	 * Forwards mouse movements to the coordinator
	 * @param who Widget, that was hovered
	 */
	@Override
	public void onMouseMove(Widget who, int x, int y) {
		
		((MathAssessmentCoordinator) coordinator).mouseMovedTo(who,x,y);

	}
	
	

	/**
	 * Forwards mouse releases to the coordinator
	 */
	@Override
	public void onMouseUp(Widget who, int x, int y) {
		// TODO Auto-generated method stub

	}



	/**
	 * User clicked on "Start game"
	 */
	public void startButtonClicked() {
		((MathAssessmentCoordinator) coordinator).startButtonClicked();
	}





}
