package com.wicam.numberlineweb.client.MathDiagnostics.Addition;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.wicam.numberlineweb.client.GameController;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsView;
import com.wicam.numberlineweb.client.MathDiagnostics.isItem;

public class AdditionView extends MathDiagnosticsView {

	private final HTML itemString = new HTML();
	private final HTML solution1 = new HTML();
	private final HTML solution2 = new HTML();
	private final FocusPanel focusPanel = new FocusPanel();
	
	public AdditionView(int numberOfPlayers, GameController gameController) {
		super(numberOfPlayers, gameController);
		addFocusPanel();
	}
	
	private void addFocusPanel(){
		focusPanel.addKeyDownHandler((AdditionController) gameController);
		focusPanel.setSize("750px", "400px");
	}
	
	public void setExplanationText(){
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Addition - Beschreibung</b></div>" +
				"<p>" + 
				"<div style='padding:5px 20px;font-size:18px; line-height: 1.5'>" +
				"Du siehst gleich eine Additionsaufgabe.<br />" + 
				"Wenn die linke Zahl richtig ist, drücke \"Y\",<br />" +
				"wenn die rechte Zahl richtig, drücke \"M\"!" +
				"</div>");
	}

	public void showItem (isItem item){
		
		// show item
		AdditionItem addItem = ((AdditionItem)item);
		String addend1 = addSpace(addItem.getAddend1());
		String addend2 = addSpace(addItem.getAddend2());
		String addition = addend1 + "&#160;+&#160;" + addend2;
		itemString.setHTML("<span style='font-size:30px'>" + addition + "</span>");
		solution1.setHTML("<span style='font-size:30px'>" + addSpace(addItem.getSolution1()) + "</span>");
		solution2.setHTML("<span style='font-size:30px'>" + addSpace(addItem.getSolution2()) + "</span>");
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
