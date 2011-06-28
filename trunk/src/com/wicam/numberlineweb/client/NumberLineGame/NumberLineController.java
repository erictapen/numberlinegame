package com.wicam.numberlineweb.client.NumberLineGame;

import com.google.gwt.user.client.ui.Widget;
import com.wicam.numberlineweb.client.GameController;
import com.wicam.numberlineweb.client.GameCoordinator;

public class NumberLineController extends GameController {



	public NumberLineController(NumberLineGameCoordinator coordinator) {
		super(coordinator);
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

		
		
		((NumberLineGameCoordinator) coordinator).clickAt(x);

	}

	@Override
	public void onMouseMove(Widget who, int x, int y) {
		
		((NumberLineGameCoordinator) coordinator).mouseMovedTo(x,y);

	}

	@Override
	public void onMouseUp(Widget who, int x, int y) {
		// TODO Auto-generated method stub

	}





}
