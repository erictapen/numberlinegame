package com.wicam.numberlineweb.client.MathDiagnostics.ChoiceReactionTask;

import com.wicam.numberlineweb.client.GameController;

public class AdditionView extends ChoiceReactionTaskView{
	
	public AdditionView(int numberOfPlayers, GameController gameController) {
		super(numberOfPlayers, gameController);
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
}
