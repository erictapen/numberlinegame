package com.wicam.numberlineweb.client.MathDiagnostics.Addition;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.wicam.numberlineweb.client.GameCoordinator;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsController;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsCoordinator;

public class AdditionController extends MathDiagnosticsController implements KeyDownHandler {

	public AdditionController(GameCoordinator coordinator) {
		super(coordinator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		event.preventDefault();
		MathDiagnosticsCoordinator mathCoordinator = (MathDiagnosticsCoordinator)coordinator;
		if (event.getNativeKeyCode() == 89 || event.getNativeKeyCode() == 77){ 
			if (event.getNativeKeyCode() == 89) // 89 == y
				mathCoordinator.recordItemInformation(MathDiagnosticsController.KEYLEFTSIDE);
			if (event.getNativeKeyCode() == 77) // 77 == m
				mathCoordinator.recordItemInformation(MathDiagnosticsController.KEYRIGHTSIDE);
			mathCoordinator.presentNextItem();
		}
	}
}
