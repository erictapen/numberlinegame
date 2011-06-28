package com.wicam.numberlineweb.client.MathDiagnostics.NumberLine;

import com.google.gwt.user.client.Event;
import com.wicam.numberlineweb.client.GameController;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsPresentation;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsView;
import com.wicam.numberlineweb.client.MathDiagnostics.isItem;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineView;

public class NumberLineTaskView extends MathDiagnosticsView implements MathDiagnosticsPresentation{
	
	private final NumberLineView numberLineView  = new NumberLineView();
	
	public NumberLineTaskView(int numberOfPlayers,
			GameController gameController) {
		super(numberOfPlayers, gameController);
		numberLineView.init(gameController, numberOfPlayers);
		numberLineView.setInfoText("");
		numberLineView.setPointerWidth(10);
		sinkEvents(Event.MOUSEEVENTS);
	}

	@Override
	public void setExplanationText() {
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Zahlenstrahl - Beschreibung</b></div>" +
				"<p>" + 
				"<div style='padding:5px 20px;font-size:18px; line-height: 1.5;'>" +
				"Du siehst gleich eine Zahlenstrahlaufgabe.<br />" +
				"Bewege den roten Zeiger mit der Maus zu<br />" +
				"der Position, wo die eingekreiste Zahl liegt und<br />" +
				"drücke dann die linke Maustaste!" +
				"</div>");
		
	}

	@Override
	public void showItem(isItem item) {
		((NumberLineTaskController) gameController).setKeysEnabled(true);
		NumberLineItem numberLineItem = (NumberLineItem)item;
		gamePanel.add(numberLineView);
		gamePanel.setWidgetPosition(numberLineView, 75, 50);
		numberLineView.setExerciseNumber(numberLineItem.getExerciseNumber());
		numberLineView.setLeftNumber(numberLineItem.getLeftNumber());
		numberLineView.setRightNumber(numberLineItem.getRightNumber());
	}
	
	/**
	 * mouse handling
	 */

	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		NumberLineTaskController mouseHandler = (NumberLineTaskController) gameController;
		if (mouseHandler != null) {
			int x = event.getClientX() - getAbsoluteLeft();
			int y = event.getClientY() - getAbsoluteTop();
			switch (event.getTypeInt()) {
			case Event.ONMOUSEDOWN:
				mouseHandler.onMouseDown(this, x, y);
				break;
			case Event.ONMOUSEMOVE:
				mouseHandler.onMouseMove(this, x, y);
				break;
			case Event.ONMOUSEUP:
				mouseHandler.onMouseUp(this, x, y);
				break;
			}                           
		}
	}
	
	public void setPointer(int playerid, int x)  {
		numberLineView.setPointer(playerid, x);
	}
}
