package com.wicam.numberlineweb.client.OverTen;

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
		}
	});
	
	private int numberOfNPCs;
	final FlexTable playerNamesFlexTable = new FlexTable();
	final HTML infoText = new HTML();
	

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
				"Gleich siehst du 2 Kasten. Im oberen sind die Zahlen die allen Spielern zur Verfügung stehen. " +
				"Im unteren Kasten stehen alle deine Rechenaufgaben.<br>Das Ziel des Spiels ist es, möglichst schnell " +
				"erst unten eine Rechnung auszuwählen, danach oben eine Zahl anzucklicken, so dass die erste Zahl deiner Rechnung " +
				"und die angeklickte Zahl genau 10 ergeben. Danach suchst du eine weitere Zahl, die die Rechnung auflöst. " +
				"Beispiel: Du wählst unten \"7 + 5\" aus. Dann musst du oben erst die 3 suchen (7 + 3 = 10) und danach " +
				"die 2 (10 + 2 = 12).<br>" +
				"Danach wird die angeklickte Zahl im oberen Kasten nicht mehr zur Verfügung stehen, alle deine Rechnungen " +
				"im unteren Kasten bleiben dir aber erhalten.<br>" +
				"Sieger ist, wer als schnellster am meisten richtige Rechnungen vervollständigt.. Viel Spaß!" +
				"</div>");
	}


}
