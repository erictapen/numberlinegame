package com.wicam.numberlineweb.client.NumberLineGame;

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
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineController;

/**
 * The game view.
 * @author patrick
 *
 */

public class NumberLineGameView extends GameView  {

	final HorizontalPanel motherPanel = new HorizontalPanel();
	final NumberLineView p = new NumberLineView();
	final AbsolutePanel playerPanel = new AbsolutePanel();
	final AbsolutePanel explanationPanel = new AbsolutePanel();
	final HTML explanationText = new HTML();
	final Button startGameButton = new Button("Spiel starten", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			initGameView();
			((NumberLineController) gameController).startButtonClicked();
		}
	});

	/**
	 * TODO: create own Composite-Classes for elements
	 */

	private int numberOfNPCs;
	final FlexTable playerNamesFlexTable = new FlexTable();
	final NumberLineGamePointer correctPositionPointer = new NumberLineGamePointer(6, 50,"Chartreuse");
	final HTML infoText = new HTML();
	

	private int leftNumber;
	private int rightNumber;

	public NumberLineGameView(NumberLineController gameController, int numberOfPlayers, int numberOfNPCs) {
		super(numberOfPlayers);
		this.gameController = gameController;
		this.numberOfNPCs = numberOfNPCs;
		init();
		sinkEvents(Event.MOUSEEVENTS);
		this.initWidget(motherPanel);
	}


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
		
		p.init(gameController,numberOfPlayers+numberOfNPCs);
		motherPanel.add(p);

		playerNamesFlexTable.setStyleName("playerList");
		playerNamesFlexTable.setCellPadding(5);
		

		playerPanel.add(playerNamesFlexTable);

		motherPanel.add(playerPanel);
		p.setExerciseNumberText("---");
	}

	public void setExerciseNumber(int n) {
		p.setExerciseNumber(n);
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
	 * Set the explanation-text
	 */
	public void setExplanationText(){
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>Zahlenstrahl - Beschreibung</b></div>" +
				"<div style='padding:5px 20px;font-size:12px'>" +
				"Versuche so schnell wie möglich, die Zahlen richtig auf dem Zahlenstrahl anzuordnen." +
				"</div>");
	}
	

	/**
	 * Reset the view
	 */

	public void clear() {
		
		p.removePointer();
		p.remove(this.correctPositionPointer);
	}

	public void setPointer(int playerid, int x)  {
		
		p.setPointer(playerid, x);

	}

	public void setPointerWidth(int width) {
		
		p.setPointerWidth(width);
		
	}
	
	public int getPointerWidth() {
		
		return p.getPointerWidth();

	}

	public void showCorrectPositionPointer(int x){
		p.add(correctPositionPointer);
		p.setWidgetPosition(correctPositionPointer, realPosToRaw(x)+100-Math.round(3), 170);
	}

	public int realPosToRaw(int pos) {

		return (int)((pos -leftNumber) /  ((double)(rightNumber -leftNumber)/400));

	}

	public int rawPosToReal(int pos) {

		return leftNumber + (int) ((pos) *  ((double)(rightNumber - leftNumber)/400));

	}

	public void setRightNumber(int i) {
		this.rightNumber=i;
		p.setRightNumber(i);

	}

	public void setLeftNumber(int i) {
		this.leftNumber=i;
		p.setLeftNumber(i);	

	}


	/**
	 * mouse handling
	 */

	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		NumberLineController mouseHandler = (NumberLineController) gameController;
		if (mouseHandler != null) {
			int x = event.getClientX() - getAbsoluteLeft();
			int y = event.getClientY() - getAbsoluteTop();
			switch (event.getTypeInt()) {
			case Event.ONMOUSEDOWN:
				mouseHandler.onMouseDown(this, x, y);
				break;
			case Event.ONMOUSEMOVE:
				mouseHandler.onMouseMove(this, x, y);
				break;
			case Event.ONMOUSEUP:
				mouseHandler.onMouseUp(this, x, y);
				break;
			}                           
		}
	}

}
