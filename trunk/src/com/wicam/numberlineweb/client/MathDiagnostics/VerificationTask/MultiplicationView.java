package com.wicam.numberlineweb.client.MathDiagnostics.VerificationTask;

import com.wicam.numberlineweb.client.GameController;

public class MultiplicationView extends VerificationTaskView {

	public MultiplicationView(int numberOfPlayers, GameController gameController) {
		super(numberOfPlayers, gameController);
	}

	@Override
	public void setExplanationText() {
		if (hasKeyboard)
			explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Multiplikation - Beschreibung</b></div>" +
					"<p>" + 
					"<div style='padding:5px 20px;font-size:18px; line-height: 1.5;'>" +
					"Du siehst gleich eine Multiplikationsaufgabe.<br />" +
					"Wenn das Ergebnis falsch ist, drücke \"X\"!<br />" +
					"Wenn das Ergebnis richtig ist, drücke \"M\"!<br />" +
					"</div>");
		else
			explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Multiplikation - Beschreibung</b></div>" +
					"<p>" + 
					"<div style='padding:5px 20px;font-size:18px; line-height: 1.5;'>" +
					"Du siehst gleich eine Multiplikationsaufgabe.<br />" +
					"Wenn das Ergebnis falsch ist, drücke auf das linke Symbol!<br />" +
					"Wenn das Ergebnis richtig ist, drücke auf das rechte Symbol!<br />" +
					"</div>");
	}

}
