package com.wicam.numberlineweb.client.MathDiagnostics.NumberLine;

import com.google.gwt.user.client.ui.Widget;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsController;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsCoordinator;

public class NumberLineTaskController extends MathDiagnosticsController {


	private boolean keysEnabled = false;

	public NumberLineTaskController(MathDiagnosticsCoordinator coordinator) {
		super(coordinator);
	}
	

	@Override
	public void onMouseDown(Widget who, int x, int y) {
		
		if (x>175 && x<575) {
			
			x=x-175;
			
		}else if (x<175) {

			x=0;

		}else{
			x=400;
		}

		
		if (keysEnabled){
			((MathDiagnosticsCoordinator) coordinator).recordItemInformation(x);
			((MathDiagnosticsCoordinator) coordinator).presentNextItem();
		}
		keysEnabled = false;

	}

	@Override
	public void onMouseMove(Widget who, int x, int y) {
		((MathDiagnosticsCoordinator) coordinator).mouseMovedTo(x,y);

	}


	public void setKeysEnabled(boolean keysEnabled) {
		this.keysEnabled = keysEnabled;
	}


	public boolean isKeysEnabled() {
		return keysEnabled;
	}

	
}
