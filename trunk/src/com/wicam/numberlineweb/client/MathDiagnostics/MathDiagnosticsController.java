package com.wicam.numberlineweb.client.MathDiagnostics;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Widget;
import com.wicam.numberlineweb.client.GameController;
import com.wicam.numberlineweb.client.GameCoordinator;

public class MathDiagnosticsController extends GameController {

	public static final int KEYLEFTSIDE = 1;
	public static final int KEYRIGHTSIDE = 2;
	public static final int KEYTOP = 3;
	public static final int KEYBOTTOM = 4;
	
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
	

	public void onClick(ClickEvent event, int side) {
		MathDiagnosticsCoordinator mathCoordinator = (MathDiagnosticsCoordinator)coordinator;
		mathCoordinator.recordItemInformation(side);
		mathCoordinator.presentNextItem();
		
	}

}
