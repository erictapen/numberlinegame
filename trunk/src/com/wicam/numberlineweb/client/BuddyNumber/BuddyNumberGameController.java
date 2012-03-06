package com.wicam.numberlineweb.client.BuddyNumber;

import com.google.gwt.user.client.ui.Widget;
import com.wicam.numberlineweb.client.GameController;

public class BuddyNumberGameController extends GameController {



	public BuddyNumberGameController(BuddyNumberGameCoordinator coordinator) {
		super(coordinator);
	}

	
	
	public void clickedAt(String digit) {
		((BuddyNumberGameCoordinator) coordinator).clickAt(digit);
	}
	
	
	/**
	 * Forwards mouse-clicks to the coordinator
	 * @param who Widget, that was clicked
	 */
	@Override
	public void onMouseDown(Widget who, int x, int y) {
		
		((BuddyNumberGameCoordinator) coordinator).clickAt(who,x,y);
		
	}
	
	

	/**
	 * Forwards mouse movements to the coordinator
	 * @param who Widget, that was hovered
	 */
	@Override
	public void onMouseMove(Widget who, int x, int y) {
		
		((BuddyNumberGameCoordinator) coordinator).mouseMovedTo(who,x,y);

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
		((BuddyNumberGameCoordinator) coordinator).startButtonClicked();
	}





}
