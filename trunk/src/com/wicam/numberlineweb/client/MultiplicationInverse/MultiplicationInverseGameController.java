package com.wicam.numberlineweb.client.MultiplicationInverse;

import com.google.gwt.user.client.ui.Widget;
import com.wicam.numberlineweb.client.GameController;

public class MultiplicationInverseGameController extends GameController {



	public MultiplicationInverseGameController(MultiplicationInverseGameCoordinator coordinator) {
		super(coordinator);
	}

	
	
	public void clickedAt(String answer) {
		((MultiplicationInverseGameCoordinator) coordinator).clickAt(answer);
	}
	
	
	/**
	 * Forwards mouse-clicks to the coordinator
	 * @param who Widget, that was clicked
	 */
	@Override
	public void onMouseDown(Widget who, int x, int y) {
		
		((MultiplicationInverseGameCoordinator) coordinator).clickAt(who,x,y);
		
	}
	
	

	/**
	 * Forwards mouse movements to the coordinator
	 * @param who Widget, that was hovered
	 */
	@Override
	public void onMouseMove(Widget who, int x, int y) {
		
		((MultiplicationInverseGameCoordinator) coordinator).mouseMovedTo(who,x,y);

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
		((MultiplicationInverseGameCoordinator) coordinator).startButtonClicked();
	}





}
