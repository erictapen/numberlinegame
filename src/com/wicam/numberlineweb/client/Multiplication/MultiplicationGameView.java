package com.wicam.numberlineweb.client.Multiplication;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Event;
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

	/**
	 * TODO: create own Composite-Classes for elements
	 */

	private int numberOfNPCs;
	final FlexTable playerNamesFlexTable = new FlexTable();
	final MultiplicationPointer correctPositionPointer = new MultiplicationPointer(6, 50,"Chartreuse");
	final HTML infoText = new HTML();
	

	public MultiplicationGameView(MultiplicationGameController gameController, int numberOfPlayers, int numberOfNPCs) {
		super(numberOfPlayers);
		this.gameController = gameController;
		this.numberOfNPCs = numberOfNPCs;
		init();
		sinkEvents(Event.MOUSEEVENTS);
		this.initWidget(motherPanel);
	}


	private void init() {
		
		p.init(numberOfPlayers+numberOfNPCs);

		motherPanel.add(p);

		playerNamesFlexTable.setStyleName("playerList");
		playerNamesFlexTable.setCellPadding(5);
		

		playerPanel.add(playerNamesFlexTable);

		motherPanel.add(playerPanel);

		RootPanel.get().add(motherPanel);
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
	
	
	/**
	 * Draw all the answers
	 * @param answers List of all answers
	 */
	public void drawAnwers(ArrayList<MultiplicationAnswer> answers) {
		
		for (MultiplicationAnswer answer : answers) {
			HTML w = new HTML();
			
			w.setText(answer.getAnswer());
			w.setHorizontalAlignment(HTML.ALIGN_CENTER);
			//TODO vertical alignment
			w.setWidth("100");
			w.setHeight("50");
			w.setVisible(!answer.isTaken()); // invisible, if already taken
			
			w.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					//TODO Implement whatever happens at mouseover
				}
			});
			
			motherPanel.add(w);
		}
		
	}

}
