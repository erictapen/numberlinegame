package com.wicam.numberlineweb.client.WordStem;

import java.util.ArrayList;

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
	final Button startGameButton = new Button("Spiel starten", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			initGameView();
			((WordStemGameController) gameController).startButtonClicked();
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
	}
	
	/**
	 * Show the game, hide explanation
	 */
	public void initGameView() {
		explanationPanel.clear();
		explanationPanel.removeFromParent();
		
		p.init((WordStemGameController) this.gameController, numberOfPlayers+numberOfNPCs);
		p.setStyleName("WordStem-box");
		p.setHeight("400px");
		p.setWidth("600px");
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

	
	public void setFirstText() {
		p.setFirstText();
	}	
	
	
	/**
	 * Add new pots to the view, no drawing, see drawPots()
	 * @param sets WordSets to be accepted by the pots
	 */
	public ArrayList<String> addNewPots(ArrayList<String> stems) {
		return p.addNewPots(stems);
	}
	
	
	/**
	 * Draw all pots with wordstems as label
	 */
	public void updatePots() {
		p.updatePots();
	}
	
	
	/**
	 * Draw all the answers once
	 * @param digits List of all answers
	 */
	public void addNewWords(ArrayList<Word> words) {
		p.addNewWords(words);
	}
	
	
	/**
	 * Updates the position/visibility of the words
	 * @param words all words to update
	 */
	public void updateWords(ArrayList<Word> words) {
		p.updateWords(words);
	}
	
	
	
	/**
	 * Set the explanation-text
	 */
	public void setExplanationText(){
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Partnerzahlen - Beschreibung</b></div>" +
				"<div style='padding:5px 20px;font-size:12px'>" +
				"Gleich siehst du 2 Kasten. Im oberen sind die Zahlen die allen Spielern zur Verfügung stehen. " +
				"Im unteren Kasten stehen alle deine Zahlen.<br>Das Ziel des Spiels ist es, möglichst schnell " +
				"erst unten, danach oben eine Zahl anzucklicken, so dass beide Zahlen gemeinsam genau 10 ergeben.<br>" +
				"Danach wird die angeklickte Zahl im oberen Kasten nicht mehr zur Verfügung stehen, alle deine Zahlen " +
				"im unteren Kasten bleiben dir aber erhalten.<br>" +
				"Sieger ist, wer als schnellster am meisten richtige Paare findet. Viel Spaß!" +
				"</div>");
	}


	public ArrayList<String> getPots() {
		return p.getPots();
	}
	

}
