package com.wicam.numberlineweb.client.BuddyNumber;

import java.util.ArrayList;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.media.client.Audio;
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

public class BuddyNumberGameView extends GameView  {

	final HorizontalPanel motherPanel = new HorizontalPanel();
	final BuddyNumberView p = new BuddyNumberView();
	final AbsolutePanel playerPanel = new AbsolutePanel();
	final AbsolutePanel explanationPanel = new AbsolutePanel();
	final HTML explanationText = new HTML();
	final Button startGameButton = new Button("Spiel starten", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			initGameView();
			try {
				descriptionSound.pause();
				descriptionSound.setCurrentTime(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			((BuddyNumberGameController) gameController).startButtonClicked();
		}
	});
	
	private int numberOfNPCs;
	final FlexTable playerNamesFlexTable = new FlexTable();
	final HTML infoText = new HTML();
	
	protected Audio descriptionSound = Audio.createIfSupported();

	public BuddyNumberGameView(BuddyNumberGameController gameController, int numberOfPlayers, int numberOfNPCs) {
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
		
		if (Audio.isSupported() && descriptionSound != null) {
			
			descriptionSound.addSource("desc/BuddyNumber.ogg", "audio/ogg; codecs=vorbis");
			descriptionSound.addSource("desc/BuddyNumber.mp3", "audio/mpeg; codecs=MP3");
			
			descriptionSound.play();
			
		}
		
		RootPanel.get().add(motherPanel);
	}
	
	/**
	 * Show the game, hide explanation
	 */
	public void initGameView() {
		explanationPanel.clear();
		explanationPanel.removeFromParent();
		
		p.init((BuddyNumberGameController) this.gameController, numberOfPlayers+numberOfNPCs);
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

	
	public void setSecondText(int res) {
		p.setSecondText(res);
	}
	
	public void setFirstText() {
		p.setFirstText();
	}

	/**
	 * Draw all community digits
	 * @param digits Digits to draw
	 */
	public void drawCommunityDigits(ArrayList<BuddyNumberDigit> digits) {
		p.drawCommunityDigits(digits);
	}
	
	
	/**
	 * Draw all hand digits
	 * @param digits Digits to draw
	 */
	public void drawHandDigits(ArrayList<BuddyNumberDigit> digits, int taken) {
		p.drawHandDigits(digits, taken);
	}
	
	/**
	 * Set the explanation-text
	 */
	public void setExplanationText(){
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Partnerzahlen - Beschreibung</b></div>" +
				"<div style='padding:5px 20px;font-size:12px'>" +
				"Gleich siehst du 2 Kasten. Im oberen Kasten sind die Zahlen, die allen Spielern zur Verfügung stehen. " +
				"Im unteren Kasten stehen Zahlen, die nur du sehen und benutzen kannst.<br>" +
				"Das Ziel des Spiels ist es, möglichst schnell erst unten, danach oben eine Zahl anzuklicken, so dass beide " +
				"Zahlen gemeinsam genau 10 ergeben.<br>" +
				"Danach ist die angeklickte Zahl im oberen Kasten aus dem Spiel, das heißt, keiner der Spieler kann sie mehr anklicken. " +
				"Die Zahlen im unteren Kasten jedoch kannst du benutzen, so oft du willst. Sieger ist, wer als Schnellster die " +
				"meisten richtigen Paare findet. Viel Spaß!" +
				"</div>");
	}
	
	public void updateInfoText(int playerClickedOn) {
		if (playerClickedOn == 0) {
			this.setFirstText();
		} else {
			this.setSecondText(playerClickedOn);
		}
	}
	

}
