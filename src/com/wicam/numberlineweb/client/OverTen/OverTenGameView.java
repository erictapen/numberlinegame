package com.wicam.numberlineweb.client.OverTen;

import java.util.ArrayList;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.wicam.numberlineweb.client.GameView;

/**
 * The game view.
 * @author alex
 *
 */

public class OverTenGameView extends GameView  {

	final HorizontalPanel motherPanel = new HorizontalPanel();
	final OverTenView p = new OverTenView();
	final AbsolutePanel playerPanel = new AbsolutePanel();
	final AbsolutePanel explanationPanel = new AbsolutePanel();
	final HTML explanationText = new HTML();
	final Button startGameButton = new Button("Spiel starten", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			initGameView();
			((OverTenGameController) gameController).startButtonClicked();
			try {
				descriptionSound.stop();
			} catch (Exception e) {
			}
		}
	});
	
	private int numberOfNPCs;
	final FlexTable playerNamesFlexTable = new FlexTable();
	final HTML infoText = new HTML();
	
	protected SoundController soundController = new SoundController();
	protected Sound descriptionSound = soundController.createSound(Sound.MIME_TYPE_AUDIO_OGG_VORBIS,"desc/OverTen.ogg");
	

	public OverTenGameView(OverTenGameController gameController, int numberOfPlayers, int numberOfNPCs) {
		super(numberOfPlayers);
		this.gameController = gameController;
		this.numberOfNPCs = numberOfNPCs;
		init();
		sinkEvents(Event.MOUSEEVENTS);
		this.initWidget(motherPanel);
	}


	/**
	 * Show explanation
	 */
	private void init() {
		
		explanationPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		explanationPanel.setHeight("400px");
		explanationPanel.setWidth("600px");
		setExplanationText();
		explanationPanel.add(explanationText);
		explanationPanel.setWidgetPosition(explanationText, 0, 0);
		explanationPanel.add(startGameButton);
		explanationPanel.setWidgetPosition(startGameButton, 480, 350);
		motherPanel.add(explanationPanel);

		descriptionSound.play();
		
		RootPanel.get().add(motherPanel);
	}
	
	/**
	 * Show the game, hide explanation
	 */
	public void initGameView() {
		explanationPanel.clear();
		explanationPanel.removeFromParent();
		
		p.init((OverTenGameController) this.gameController, numberOfPlayers+numberOfNPCs);
		p.setStyleName("BuddyNumber-box");
		motherPanel.add(p);

		playerNamesFlexTable.setStyleName("playerList");
		playerNamesFlexTable.setCellPadding(5);
		

		playerPanel.add(playerNamesFlexTable);

		motherPanel.add(playerPanel);
	}

	public void setPoints(int playerid, int p,String name) {
		playerNamesFlexTable.setHTML(playerid, 1, "<div style='font-size:30px;color:" + playerColors[playerid-1] + "'>" + Integer.toString(p) +"<span style='font-size:14px'> " + name +"</span></div>");
	}

	public void deletePlayerFromPointList(int playerid) {
		playerNamesFlexTable.clearCell(playerid, 1);
		playerNamesFlexTable.removeCell(playerid, 1);
	}


	public void setInfoText(String text) {
		p.setInfoText(text);
	}

	public void setThirdText(int result) {
		p.setThirdText(result);
	}
	
	public void setSecondText(int first) {
		p.setSecondText(first);
	}
	
	public void setFirstText() {
		p.setFirstText();
	}

	/**
	 * Draw all community digits
	 * @param digits Digits to draw
	 */
	public void drawCommunityDigits(ArrayList<OverTenDigit> digits) {
		p.drawCommunityDigits(digits);
	}
	
	
	/**
	 * Draw all hand digits
	 * @param digits Digits to draw
	 */
	public void drawCalculations(ArrayList<OverTenCalculation> calcs, String taken) {
		p.drawCalculations(calcs, taken);
	}
	
	
	
	/**
	 * Set the explanation-text
	 */
	public void setExplanationText(){
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Über Zehn - Beschreibung</b></div>" +
				"<div style='padding:5px 20px;font-size:12px'>" +
				"Gleich siehst du 2 Kasten. Im oberen sind die Zahlen, die allen Spielern zur Verfügung stehen. " +
				"Im unteren Kasten stehen Rechenaufgaben, die nur du sehen und benutzen kannst.<br>" +
				"Das Ziel des Spiels ist es, möglichst schnell erst unten eine Rechnung auszuwählen, " +
				"danach oben eine Zahl anzuklicken, so dass die <strong>erste Zahl</strong> deiner " +
				"Rechnung und die <strong>angeklickte Zahl</strong> genau <strong>10</strong> ergeben. " +
				"Danach suchst du eine weitere Zahl, die die ursprüngliche Rechnung auflöst. Beispiel: " +
				"Du wählst unten \"7 + 5\" (=12) aus. Dann musst du oben erst die <strong>3</strong> " +
				"suchen (<strong>7 + 3 = 10</strong>) und danach die 2 (10 + 2 = 12).<br>" +
				"Danach ist die angeklickte Zahl im oberen Kasten aus dem Spiel, das heißt weder du " +
				"noch deine Gegenspieler können sie verwenden, alle deine Rechnungen im unteren Kasten " +
				"kannst du aber benutzen, so oft du möchtest.<br>" +
				"Sieger ist, wer als Schnellster am meisten richtige Rechnungen vervollständigt. Viel Spaß!" +
				"</div>");
	}


}
