package com.wicam.numberlineweb.client.VowelGame.DehnungGame;

import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameController;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameState;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.DoppelungGameView;

public class DehnungGameView extends DoppelungGameView {

	public DehnungGameView(int numberOfPlayers,
			DoppelungGameController doppelungGameController, DoppelungGameState gameState) {
		super(numberOfPlayers, doppelungGameController);
		longVowelImage.setUrl("doppelungGame/ziehen2.jpg");
	}
	
	@Override
	protected void setExplanationText(){
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Dehnungspiel - Erklärung</b></div>" +
				"<p>" + 
				"<div style='padding:5px 20px;font-size:12px'>" +
				"In diesem Spiel geht es um das Dehnungs-h. Wenn du nach einem langen Vokal „l, m, n, oder r“ hörst, dann wird der Vokal meistens mit einem Dehnungs- h gedehnt. Wenn du zum Beispiel das Wort „dehnen“ hörst, dann schreibt man nach dem „e“ ein „h“, weil der Vokal „e“ lang ist und weil nach dem langen „e“ ein „n“ folgt (de<b>hn</b>en). " + 
				"Wenn nach einem langen Vokal weder „l“ noch „m“ noch „n“ noch „r“ folgen, dann muss man meistens kein Dehnungs- h schreiben. Wenn du zum Beispiel das Wort „Vater“ hörst, dann musst du kein „h“ schreiben, weil nach dem langen „a“ ein „t“ folgt (V<b>at</b>er). "+
				"Einfach ist es, wenn du einen kurzen Vokal hörst, wie zum Beispiel im Wort „knallen“. Nach einem kurzen Vokal muss  man nie ein Dehnungs- h schreiben. <br /><br /> "+

				"Im Spiel funktioniert das so: Du hörst ein Wort. Wenn der Vokal im Wort kurz ist, klickst du auf den Knall (Zeichen), der mit seinem kurzen Vokal für alle kurzen Vokale steht. Wenn der Vokal lang ist, klickst du die Männchen an, die am Seil ziehen (Zeichen), sie stehen mit dem langen i in „ziehen“ für alle langen Vokale. Wenn der Vokal lang war, fallen hl, hm, hn, hr oder auch „kein h“ vom Bildschirm. Deine Aufgabe ist es, so viele hl, hm, hn oder hr einzusammeln, wie du kannst. Dazu musst du gut aufpassen und heraushören, ob ein l, ein m, ein n, oder ein r nach dem langen Vokal kam. Wenn du zum Beispiel in „Höhle“ ein l nach dem langen ö hörst, sammelst du so viele hl ein, wie möglich."+
		"</div>");
	}
}
