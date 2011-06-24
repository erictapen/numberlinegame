package com.wicam.numberlineweb.client.MathDiagnostics;

import com.google.gwt.user.client.ui.Widget;
import com.wicam.numberlineweb.client.GameController;
import com.wicam.numberlineweb.client.GameCoordinator;

public class MathDiagnosticsController extends GameController {

	public MathDiagnosticsController(GameCoordinator coordinator) {
		super(coordinator);
		// TODO Auto-generated constructor stub
	}
	
	public void onStartButtonClick(){
		((MathDiagnosticsCoordinator) coordinator).startButtonClicked();
	}

	@Override
	public void onMouseDown(Widget who, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseUp(Widget who, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseMove(Widget who, int x, int y) {
		// TODO Auto-generated method stub

	}

}
