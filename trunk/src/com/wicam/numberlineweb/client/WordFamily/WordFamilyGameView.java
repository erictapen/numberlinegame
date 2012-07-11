package com.wicam.numberlineweb.client.WordFamily;

import java.util.ArrayList;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;
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

public class WordFamilyGameView extends GameView  {

	final HorizontalPanel motherPanel = new HorizontalPanel();
	final WordFamilyView p = new WordFamilyView();
	final AbsolutePanel playerPanel = new AbsolutePanel();
	final AbsolutePanel explanationPanel = new AbsolutePanel();
	final HTML explanationText = new HTML();
	
	protected SoundController soundController = new SoundController();
	protected Sound descriptionSound = soundController.createSound(sr.getMimeType(), 
			sr.getInstance().wortfamilienspiel().getSafeUri().asString());
	
	final Button startGameButton = new Button("Spiel starten", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			initGameView();
			((WordFamilyGameController) gameController).startButtonClicked();
			try {
				descriptionSound.stop();
			} catch (Exception e) {
			}
		}
	});
	
	private int numberOfNPCs;
	final FlexTable playerNamesFlexTable = new FlexTable();
	final HTML infoText = new HTML();
	

	public WordFamilyGameView(WordFamilyGameController gameController, int numberOfPlayers, int numberOfNPCs) {
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
		
		descriptionSound.addEventHandler(new SoundHandler() {
			
			@Override
			public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
				descriptionSound.play();
				
			}
			
			@Override
			public void onPlaybackComplete(PlaybackCompleteEvent event) {
				
			}
		});
	}
	
	/**
	 * Show the game, hide explanation
	 */
	public void initGameView() {
		explanationPanel.clear();
		explanationPanel.removeFromParent();
		
		p.init((WordFamilyGameController) this.gameController, numberOfPlayers+numberOfNPCs);
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
	
	public void run(ArrayList<Word> words, Word stem) {
		p.run(words, stem);
	}
	
	public void reset() {
		p.reset();
	}
	
	public boolean isRunning() {
		return p.isRunning();
	}
	
	/**
	 * Set the explanation-text
	 */
	public void setExplanationText(){
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Wortfamilien - Beschreibung</b></div>" +
				"<div style='padding:5px 20px;font-size:12px'>" +
				"Zuerst siehst du in der Mitte des Bildschirms ein Wort. Achte auf die Wortfamilie, zu der das Wort gehört. " +
				"Dann öffnet sich ein Minispiel, in dem du dir alle Wörter merken sollst, die zur gesuchten Wortfamilie gehören. " +
				"Wenn du zum Beispiel zuerst „Mann“ siehst, dann kannst du dir im Minispiel Wörter wie „Mannschaft“ oder " +
				"„Männer“ merken. Am Schluss kannst du alle Wörter eintippen, die du dir gemerkt hast.<br><br>Viel Spaß!" + 
				"</div>");
	}


	public void disableInput() {
		p.disableInput();
	}

}
