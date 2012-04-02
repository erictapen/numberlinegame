package com.wicam.numberlineweb.client.WordFamily;

import com.google.gwt.user.client.ui.Widget;
import com.wicam.numberlineweb.client.GameController;

public class WordFamilyGameController extends GameController {



	public WordFamilyGameController(WordFamilyGameCoordinator coordinator) {
		super(coordinator);
	}

	
	
	public void clickedAt(String digit) {
		((WordFamilyGameCoordinator) coordinator).clickAt(digit);
	}
	
	
	/**
	 * Forwards mouse-clicks to the coordinator
	 * @param who Widget, that was clicked
	 */
	@Override
	public void onMouseDown(Widget who, int x, int y) {
		
		((WordFamilyGameCoordinator) coordinator).clickAt(who,x,y);
		
	}
	
	

	/**
	 * Forwards mouse movements to the coordinator
	 * @param who Widget, that was hovered
	 */
	@Override
	public void onMouseMove(Widget who, int x, int y) {
		
		((WordFamilyGameCoordinator) coordinator).mouseMovedTo(who,x,y);

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
		((WordFamilyGameCoordinator) coordinator).startButtonClicked();
	}





}
