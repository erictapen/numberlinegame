package com.wicam.numberlineweb.client.MultiplicationInverse;


import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameController;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameView;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationView;

/**
 * The game view.
 * @author alex
 *
 */

public class MultiplicationInverseGameView extends MultiplicationGameView  {
	
	final MultiplicationInverseView p = new MultiplicationInverseView();

	public MultiplicationInverseGameView(MultiplicationGameController gameController,
			int numberOfPlayers, int numberOfNPCs) {
		super(gameController, numberOfPlayers, numberOfNPCs);
	}
	
	public void setTaskText(String task) {
		p.setTaskText(task);
	}
	
	/**
	 * Set the explanation-text
	 */
	@Override
	public void setExplanationText(){
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Multiplikationsspiel - Beschreibung</b></div>" +
				"<div style='padding:5px 20px;font-size:12px'>" +
				"Du siehst gleich eine Multiplikationsaufgabe, und darüber 12 verschiedene mögliche Ergebnisse. " +
				"Klicke so schnell wie möglich auf das richtige Ergbnis der Rechenaufgabe. " +
				"Ist dein Gegenspieler schneller als du, " +
				"dann hat er die Runde gewonnen. " +
				"</div>");
	}
	
}