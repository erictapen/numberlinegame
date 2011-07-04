package com.wicam.numberlineweb.client.NumberLineGame;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.wicam.numberlineweb.client.GameView;

/**
 * The game view.
 * @author patrick
 *
 */

public class NumberLineGameView extends GameView  {

	final HorizontalPanel motherPanel = new HorizontalPanel();
	final NumberLineView p = new NumberLineView();
	final AbsolutePanel playerPanel = new AbsolutePanel();

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
		p.init(gameController,numberOfNPCs+numberOfNPCs);

		motherPanel.add(p);

		playerNamesFlexTable.setStyleName("playerList");
		playerNamesFlexTable.setCellPadding(5);
		

		playerPanel.add(playerNamesFlexTable);

		motherPanel.add(playerPanel);

		RootPanel.get().add(motherPanel);
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
