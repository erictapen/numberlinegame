package com.wicam.numberlineweb.client.MathDiagnostics.VerificationTask;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.wicam.numberlineweb.client.GameController;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsView;
import com.wicam.numberlineweb.client.MathDiagnostics.isItem;
import com.wicam.numberlineweb.client.MathDiagnostics.ChoiceReactionTask.ChoiceReactionTaskController;

public abstract class VerificationTaskView extends MathDiagnosticsView {

	private final HTML itemString = new HTML();
	private final FocusPanel focusPanel = new FocusPanel();
	
	public VerificationTaskView(int numberOfPlayers, GameController gameController) {
		super(numberOfPlayers, gameController);
		addFocusPanel();
	}
	
	private void addFocusPanel(){
		focusPanel.addKeyDownHandler((ChoiceReactionTaskController) gameController);
		focusPanel.setSize("750px", "400px");
	}

	public void showItem (isItem item){
		
		// show item
		VerificationTaskItem arithmeticItem = ((VerificationTaskItem)item);
		String number1 = String.valueOf(arithmeticItem.getNumber1());
		String number2 = String.valueOf(arithmeticItem.getNumber2());
		String result = String.valueOf(arithmeticItem.getResult());
		String task = number1 + "&#160;" + arithmeticItem.getOperator() + "&#160;" + number2 + "&#160;=&#160;" + result;
		itemString.setHTML("<span style='font-size:30px'>" + task + "</span>");
		gamePanel.add(itemString);
		gamePanel.setWidgetPosition(itemString, 310, 185);
		
		// add focus panel
		gamePanel.add(focusPanel);
		gamePanel.setWidgetPosition(focusPanel, 0, 0);
		focusPanel.setFocus(true);
	}
}
