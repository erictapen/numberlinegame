package com.wicam.numberlineweb.client.MathDiagnostics.NumberComparison;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.wicam.numberlineweb.client.GameController;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsController;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsView;
import com.wicam.numberlineweb.client.MathDiagnostics.isItem;

public class NumberComparisonView extends MathDiagnosticsView {

	private final HTML numberTop = new HTML();
	private final HTML numberBottom = new HTML();
	private final FocusPanel focusPanel = new FocusPanel();
	
	public NumberComparisonView(int numberOfPlayers, GameController gameController) {
		super(numberOfPlayers, gameController);
	}
	
	@Override
	public void initKeyboardDependentElements(boolean hasKeyboard){
		super.initKeyboardDependentElements(hasKeyboard);
		if (hasKeyboard){
			focusPanel.addKeyDownHandler((NumberComparisonController) gameController);
			focusPanel.setSize("750px", "400px");
		}
		else {
			final MathDiagnosticsController controller = (MathDiagnosticsController)gameController;
			numberTop.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					controller.onClick(event, MathDiagnosticsController.KEYTOP);
					
				}
				
			});
			
			numberBottom.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					controller.onClick(event, MathDiagnosticsController.KEYBOTTOM);
					
				}
				
			});
		}
	}
	
	public void setExplanationText(){
		if (hasKeyboard){
			explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Zahlenvergleich - Beschreibung</b></div>" +
					"<p>" + 
					"<div style='padding:5px 20px;font-size:18px; line-height: 1.5'>" +
					"Du siehst gleich eine Zahlenvergleichsaufgabe.<br />" +
					"Wenn die obere Zahl größer ist, drücke \"Z\",<br />" +
					"wenn die untere Zahl größer ist, drücke \"B\"!" +
					"</div>");
		}
		else {
			explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Zahlenvergleich - Beschreibung</b></div>" +
					"<p>" + 
					"<div style='padding:5px 20px;font-size:18px; line-height: 1.5'>" +
					"Du siehst gleich eine Zahlenvergleichsaufgabe.<br />" +
					"Wenn die obere Zahl größer ist, dann drücke auf die obere Zahl,<br />" +
					"wenn die untere Zahl größer ist, dann drücke auf die untere Zahl!" +
					"</div>");
		}
	}

	public void showItem (isItem item){
		
		// show item
		NumberComparisonItem numberComparisonItem = ((NumberComparisonItem)item);
		numberTop.setHTML("<span style='font-size:30px'>" + addSpace(numberComparisonItem.getNumberTop()) + "</span>");
		numberBottom.setHTML("<span style='font-size:30px'>" + addSpace(numberComparisonItem.getNumberBottom()) + "</span>");
		gamePanel.add(numberTop);
		gamePanel.add(numberBottom);
		gamePanel.setWidgetPosition(numberTop, 355, 85);
		gamePanel.setWidgetPosition(numberBottom, 355, 285);
		
		if (hasKeyboard){
			// add focus panel
			gamePanel.add(focusPanel);
			gamePanel.setWidgetPosition(focusPanel, 0, 0);
			focusPanel.setFocus(true);
		}
	}
	
	private String addSpace(int number){
		if (number < 10)
			return "&#160;&#160;" + number;
		else
			if (number < 100)
				return "&#160;" + number;
			else
				return "" + number;
	}
}
