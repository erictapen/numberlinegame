package com.wicam.numberlineweb.client.MathDiagnostics.ChoiceReactionTask;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.wicam.numberlineweb.client.GameController;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsView;
import com.wicam.numberlineweb.client.MathDiagnostics.isItem;

public abstract class ChoiceReactionTaskView extends MathDiagnosticsView {

	private final HTML itemString = new HTML();
	private final HTML solution1 = new HTML();
	private final HTML solution2 = new HTML();
	private final FocusPanel focusPanel = new FocusPanel();
	
	public ChoiceReactionTaskView(int numberOfPlayers, GameController gameController) {
		super(numberOfPlayers, gameController);
		addFocusPanel();
	}
	
	private void addFocusPanel(){
		focusPanel.addKeyDownHandler((ChoiceReactionTaskController) gameController);
		focusPanel.setSize("750px", "400px");
	}

	public void showItem (isItem item){
		
		// show item
		ChoiceReactionTaskItem arithmeticItem = ((ChoiceReactionTaskItem)item);
		String number1 = addSpace(arithmeticItem.getNumber1());
		String number2 = addSpace(arithmeticItem.getNumber2());
		String task = number1 + "&#160;" + arithmeticItem.getOperator() + "&#160;" + number2;
		itemString.setHTML("<span style='font-size:30px'>" + task + "</span>");
		solution1.setHTML("<span style='font-size:30px'>" + addSpace(arithmeticItem.getSolution1()) + "</span>");
		solution2.setHTML("<span style='font-size:30px'>" + addSpace(arithmeticItem.getSolution2()) + "</span>");
		gamePanel.add(itemString);
		gamePanel.add(solution1);
		gamePanel.add(solution2);
		gamePanel.setWidgetPosition(itemString, 320, 75);
		gamePanel.setWidgetPosition(solution1, 150, 250);
		gamePanel.setWidgetPosition(solution2, 580, 250);
		
		// add focus panel
		gamePanel.add(focusPanel);
		gamePanel.setWidgetPosition(focusPanel, 0, 0);
		focusPanel.setFocus(true);
	}
	
	private String addSpace(int number){
		if (number < 10)
			return "&#160;" + number;
		else
			return "" + number;
	}
}
