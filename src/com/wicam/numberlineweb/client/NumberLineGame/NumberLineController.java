package com.wicam.numberlineweb.client.NumberLineGame;

import com.google.gwt.user.client.ui.Widget;

public class NumberLineController implements MouseHandler {


	private NumberLineGameCoordinator coordinator;

	public NumberLineController(NumberLineGameCoordinator coordinator) {

		this.coordinator=coordinator;
	

	}


	//modell stuff


	


	@Override
	public void onMouseDown(Widget who, int x, int y) {
		
		if (x>100 && x<500) {
			
			x=x-100;
			
		}else if (x<100) {

			x=0;

		}else{
			x=400;
		}

		
		
		coordinator.clickAt(x);

	}

	@Override
	public void onMouseMove(Widget who, int x, int y) {
		
		coordinator.mouseMovedTo(x,y);

	}

	@Override
	public void onMouseUp(Widget who, int x, int y) {
		// TODO Auto-generated method stub

	}





}
