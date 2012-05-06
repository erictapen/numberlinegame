package com.wicam.numberlineweb.client.Multiplication;

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

public class MultiplicationGameView extends GameView  {

	final HorizontalPanel motherPanel = new HorizontalPanel();
	final MultiplicationView p = new MultiplicationView();
	final AbsolutePanel playerPanel = new AbsolutePanel();
	final AbsolutePanel explanationPanel = new AbsolutePanel();
	final HTML explanationText = new HTML();
	final Button startGameButton = new Button("Spiel starten", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			initGameView();
			((MultiplicationGameController) gameController).startButtonClicked();
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
	protected Sound descriptionSound = soundController.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM,"desc/Multiplication.wav");

	public MultiplicationGameView(MultiplicationGameController gameController, int numberOfPlayers, int numberOfNPCs) {
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
		
		p.init((MultiplicationGameController) this.gameController, numberOfPlayers+numberOfNPCs);
		p.setStyleName("multiplication-box");
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

	
	public void setResultText(int res) {
		p.setResultText(res);
	}

	/**
	 * Draw all possible answers
	 * @param answers Answers to draw
	 */
	public void drawAnwers(ArrayList<MultiplicationAnswer> answers) {
		p.drawAnwers(answers);
	}
	
	/**
	 * Set the explanation-text
	 */
	public void setExplanationText(){
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Multiplikationsspiel - Beschreibung</b></div>" +
				"<div style='padding:5px 20px;font-size:12px'>" +
				"Du siehst gleich ein gewünschtes Ergebnis, und darüber 12 verschiedene Multiplikationsaufgaben. " +
				"Klicke so schnell wie möglich auf die Rechnung, die das gewünschte Ergebnis hat. " +
				"Es können auch mehrere Aufgaben richtig sein. Ist dein Gegenspieler schneller als du, " +
				"ist die Rechnung, die er angeklickt hat, aus dem Spiel. " +
				"</div>");
	}
	

}
