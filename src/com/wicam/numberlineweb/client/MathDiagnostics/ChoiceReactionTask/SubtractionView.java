package com.wicam.numberlineweb.client.MathDiagnostics.ChoiceReactionTask;

import com.wicam.numberlineweb.client.GameController;

public class SubtractionView extends ChoiceReactionTaskView{
	
	public SubtractionView(int numberOfPlayers, GameController gameController) {
		super(numberOfPlayers, gameController);
	}
	
	@Override
	public void setExplanationText(){
		if (hasKeyboard)
			explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Subtrakion - Beschreibung</b></div>" +
					"<p>" + 
					"<div style='padding:5px 20px;font-size:18px; line-height: 1.5'>" +
					"Du siehst gleich eine Subtraktionsaufgabe.<br />" + 
					"Wenn die linke Zahl richtig ist, drücke \"Y\",<br />" +
					"wenn die rechte Zahl richtig, drücke \"M\"!" +
					"</div>");
		else {
			explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Subtrakion - Beschreibung</b></div>" +
					"<p>" + 
					"<div style='padding:5px 20px;font-size:18px; line-height: 1.5'>" +
					"Du siehst gleich eine Subtraktionsaufgabe.<br />" + 
					"Wenn die linke Zahl richtig ist, dann drücke auf die linke Zahl,<br />" +
					"wenn die rechte Zahl richtig, dann drücke auf die rechte Zahl!" +
					"</div>");
		}
	}
}
