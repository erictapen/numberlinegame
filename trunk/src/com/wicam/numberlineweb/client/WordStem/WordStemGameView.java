package com.wicam.numberlineweb.client.WordStem;

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

public class WordStemGameView extends GameView  {

	final HorizontalPanel motherPanel = new HorizontalPanel();
	final WordStemView p = new WordStemView();
	final AbsolutePanel playerPanel = new AbsolutePanel();
	final AbsolutePanel explanationPanel = new AbsolutePanel();
	final HTML explanationText = new HTML();
	
	protected SoundController soundController = new SoundController();
	protected Sound descriptionSound = soundController.createSound(Sound.MIME_TYPE_AUDIO_OGG_VORBIS,"desc/WortbausteinespielVersion1.ogg");
	
	final Button startGameButton = new Button("Spiel starten", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			initGameView();
			((WordStemGameController) gameController).startButtonClicked();
			try {
				descriptionSound.stop();
			} catch (Exception e) {
			}
		}
	});
	
	private int numberOfNPCs;
	final FlexTable playerNamesFlexTable = new FlexTable();
	final HTML infoText = new HTML();
	

	public WordStemGameView(WordStemGameController gameController, int numberOfPlayers, int numberOfNPCs) {
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

		RootPanel.get().add(motherPanel);
		
		descriptionSound.play();
	}
	
	/**
	 * Show the game, hide explanation
	 */
	public void initGameView() {
		explanationPanel.clear();
		explanationPanel.removeFromParent();
		
		p.init((WordStemGameController) this.gameController, numberOfPlayers+numberOfNPCs);
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

	
	public void setSecondText(String word) {
		p.setSecondText(word);
	}
	
	public void setFirstText() {
		p.setFirstText();
	}

	/**
	 * Draw all community digits
	 * @param digits Digits to draw
	 */
	public void drawStems(ArrayList<Word> stems) {
		p.drawStems(stems);
	}
	
	
	/**
	 * Draw all hand digits
	 * @param digits Digits to draw
	 */
	public void drawWords(ArrayList<Word> words, String taken) {
		p.drawWords(words, taken);
	}
	
	
	
	/**
	 * Set the explanation-text
	 */
	public void setExplanationText(){
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Wortbausteine - Beschreibung</b></div>" +
				"<div style='padding:5px 20px;font-size:12px'>" +
				"Du siehst gleich zwei Kästchen. Im oberen stehen Wortbausteine, im unteren stehen Wörter. Deine Aufgabe " +
				"ist es, die Wörter den passenden Wortbausteinen zuzuordnen. Dazu klickst du zuerst auf ein Wort und dann " +
				"klickst du auf den Wortbaustein, zu dem das Wort passt. Wer am Ende die meisten Wörter richtig zugeordnet " +
				"hat, gewinnt.<br><br>" +
				"Viel Spaß!" +
				"</div>");
	}


	public void updateInfoText(String playerClickedOn) {
		if (playerClickedOn.equals("")) {
			this.setFirstText();
		} else {
			this.setSecondText(playerClickedOn);
		}
	}
	

}
