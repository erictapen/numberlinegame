package com.wicam.numberlineweb.client.MathDiagnostics.VerificationTask;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.wicam.numberlineweb.client.GameController;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsController;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsView;
import com.wicam.numberlineweb.client.MathDiagnostics.isItem;
import com.wicam.numberlineweb.client.MathDiagnostics.ChoiceReactionTask.ChoiceReactionTaskController;

public abstract class VerificationTaskView extends MathDiagnosticsView {

	private final HTML itemString = new HTML();
	private final FocusPanel focusPanel = new FocusPanel();
	private final Image correctImage = new Image("MathDiagnostics/Apply_big.png");
	private final Image falseImage = new Image("MathDiagnostics/Delete_big.png");
	
	public VerificationTaskView(int numberOfPlayers, GameController gameController) {
		super(numberOfPlayers, gameController);
	}
	
	@Override
	public void initKeyboardDependentElements(boolean hasKeyboard){
		super.initKeyboardDependentElements(hasKeyboard);
		if (hasKeyboard){
			focusPanel.addKeyDownHandler((ChoiceReactionTaskController) gameController);
			focusPanel.setSize("750px", "400px");
		}
		else {
			final MathDiagnosticsController controller = (MathDiagnosticsController)gameController;
			correctImage.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					controller.onClick(event, MathDiagnosticsController.KEYRIGHTSIDE);
					
				}
				
			});
			
			falseImage.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					controller.onClick(event, MathDiagnosticsController.KEYLEFTSIDE);
					
				}
				
			});
		}
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
		
		if (hasKeyboard){
			gamePanel.setWidgetPosition(itemString, 310, 185);
			
			// add focus panel
			gamePanel.add(focusPanel);
			gamePanel.setWidgetPosition(focusPanel, 0, 0);
			focusPanel.setFocus(true);
		}
		else {
			gamePanel.setWidgetPosition(itemString, 310, 85);
			gamePanel.add(correctImage);
			gamePanel.add(falseImage);
			gamePanel.setWidgetPosition(correctImage, 580, 260);
			gamePanel.setWidgetPosition(falseImage, 150, 260);
		}
	}
}
