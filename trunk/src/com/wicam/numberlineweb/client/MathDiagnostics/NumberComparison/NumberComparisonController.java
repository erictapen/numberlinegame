package com.wicam.numberlineweb.client.MathDiagnostics.NumberComparison;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsController;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsCoordinator;

public class NumberComparisonController extends MathDiagnosticsController implements KeyDownHandler {

	public NumberComparisonController(GameCoordinator coordinator) {
		super(coordinator);
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		event.preventDefault();
		MathDiagnosticsCoordinator mathCoordinator = (MathDiagnosticsCoordinator)coordinator;
		if (event.getNativeKeyCode() == 90 || event.getNativeKeyCode() == 66){ 
			if (event.getNativeKeyCode() == 90) // 90 == z
				mathCoordinator.recordItemInformation(MathDiagnosticsController.KEYTOP);
			if (event.getNativeKeyCode() == 66) // 66 == b
				mathCoordinator.recordItemInformation(MathDiagnosticsController.KEYBOTTOM);
			mathCoordinator.presentNextItem();
		}
	}
}
