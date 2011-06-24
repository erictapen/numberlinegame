package com.wicam.numberlineweb.client.MathDiagnostics;

import com.google.gwt.user.client.Timer;

public class ShowNextItemTask extends Timer{

	private MathDiagnosticsCoordinator coordinator;
	private MathDiagnosticsView view;
	private isItem item;
	
	ShowNextItemTask(MathDiagnosticsCoordinator coordinator, MathDiagnosticsView view, isItem item){
		this.coordinator = coordinator;
		this.view = view;
		this.item = item;
	}
	
	@Override
	public void run() {
		coordinator.createNewDuration();
		view.showItem(item);
	}
}
