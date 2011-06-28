package com.wicam.numberlineweb.client.MathDiagnostics;

import com.google.gwt.user.client.Timer;

public class ShowResultTask extends Timer {

	private MathDiagnosticsPresentation view;
	
	ShowResultTask(MathDiagnosticsPresentation view){
		this.view = view;
	}
	
	@Override
	public void run() {
		view.showCalculatingResult();
	}

}
