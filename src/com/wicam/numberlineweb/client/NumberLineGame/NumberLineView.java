package com.wicam.numberlineweb.client.NumberLineGame;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.wicam.numberlineweb.client.GameView;
import com.wicam.numberlineweb.client.MouseHandler;
import com.wicam.numberlineweb.client.Player;

/**
 * The game view.
 * @author patrick
 *
 */

public class NumberLineView extends GameView  {

	final HorizontalPanel motherPanel = new HorizontalPanel();
	final AbsolutePanel p = new AbsolutePanel();
	final AbsolutePanel playerPanel = new AbsolutePanel();

	/**
	 * TODO: create own Composite-Classes for elements
	 */

	private int numberOfNPCs;
	final FlexTable playerNamesFlexTable = new FlexTable();
	final HTML labelLeft = new HTML();
	final HTML labelRight = new HTML();
	ArrayList<NumberLineGamePointer> pointerList = new ArrayList<NumberLineGamePointer>();
	final NumberLineGamePointer correctPositionPointer = new NumberLineGamePointer(6, 50,"Chartreuse");
	final HTML infoText = new HTML();
	final HTML exerciseText = new HTML();
	final HTML infoBox = new HTML();
	private MouseHandler mouseHandler;

	private int leftNumber;
	private int rightNumber;
	private int pointerWidth;

	public NumberLineView(int numberOfPlayers, int numberOfNPCs) {
		super(numberOfPlayers);
		this.numberOfNPCs = numberOfNPCs;
		init();
		sinkEvents(Event.MOUSEEVENTS);
		this.initWidget(motherPanel);

	}

	public void addMouseHandler(MouseHandler h) {

		this.mouseHandler = h;

	}


	private void init() {

		// initialize pointers
		int numberOfPointers = numberOfPlayers+numberOfNPCs;
		for (int i = 0; i < numberOfPointers; i++){
			final NumberLineGamePointer pointer = new NumberLineGamePointer(2,this.playerColors[i]);
			pointerList.add(pointer);
		}

		//draw everthing

		p.getElement().getStyle().setPosition(Position.RELATIVE);

	
		// IE compatible canvas
		p.add(new HTML("<div style='border:none; background-color:black;width:400px;height:6px;overflow:hidden;position:absolute;left:100px;top:192px'></div>"));
		p.add(new HTML("<div style='border:none; background-color:black;width:6px;height:28px;overflow:hidden;position:absolute;left:97px;top:181px'></div>"));
		p.add(new HTML("<div style='border:none; background-color:black;width:6px;height:28px;overflow:hidden;position:absolute;left:497px;top:181px'></div>"));

		exerciseText.setHTML("<div id='exercise' style='border:solid black 4px;padding:5px 20px;font-size:25px'>---</div>");
		infoBox.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>Awaiting Signal...</div>");

		HTML c = new HTML("<div id='canvas' style='width:600px;height:400px'></div>");

		p.add(exerciseText);

		p.setWidgetPosition(exerciseText, 260, 50);

		p.add(infoBox);

		p.setWidgetPosition(infoBox, 10, 290);

		p.add(c);
		


		p.add(labelLeft);
		p.add(labelRight);

		p.setWidgetPosition(labelLeft, 40, 177);
		p.setWidgetPosition(labelRight, 510, 177);


		motherPanel.add(p);

		playerNamesFlexTable.setStyleName("playerList");
		playerNamesFlexTable.setCellPadding(5);
		

		playerPanel.add(playerNamesFlexTable);

		motherPanel.add(playerPanel);

		RootPanel.get().add(motherPanel);
	}

	public void setExerciseNumber(int n) {

		exerciseText.setHTML("<div id='exercise' style='border:solid black 4px;padding:5px 20px;font-size:25px'>" + 
				Integer.toString(n) + "</div>");
	}

	public void setPoints(int playerid, int p,String name) {
		playerNamesFlexTable.setHTML(playerid, 1, "<div style='font-size:30px;color:" + playerColors[playerid-1] + "'>" + Integer.toString(p) +"<span style='font-size:14px'> " + name +"</span></div>");
	}

	public void deletePlayerFromPointList(int playerid) {
		playerNamesFlexTable.clearCell(playerid, 1);
		playerNamesFlexTable.removeCell(playerid, 1);
	}


	public void setInfoText(String text) {

		infoBox.setHTML("<div id='infoText' style='width:500px;padding:5px 20px;'>" + text +"</div>");

	}

	/**
	 * Reset the view
	 */

	public void clear() {

		for (int i = 0; i < pointerList.size(); i++){
			p.remove(pointerList.get(i));
			//p.remove(pointerTextList.get(i));
		}
		p.remove(this.correctPositionPointer);
	}

	public void setPointer(int playerid, int x)  {
		p.add(pointerList.get(playerid-1));
		setPointerPos(x,pointerList.get(playerid-1));

	}

	public void setPointerWidth(int width) {


		this.pointerWidth = width;
		for (int i = 0; i < pointerList.size(); i++)
			this.pointerList.get(i).setWidth(width);		

	}

	public int getPointerWidth() {

		return pointerWidth;


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

	public void showRankingTable(ArrayList<? extends Player> players){
		playerPanel.clear();
		p.clear();
		
		FlexTable rankingTable = new FlexTable();
		rankingTable.setCellPadding(5);
		
		p.add(rankingTable);
		Collections.sort(players);
		int lastPoints = -1;
		int position = 0;
		for (int i = 0; i < players.size(); i++){
			if (!(lastPoints == players.get(i).getPoints())){
				lastPoints = players.get(i).getPoints();
				position = i+1;
			}
			NumberLineGamePlayer player = (NumberLineGamePlayer) players.get(i);
			rankingTable.setHTML(i+1, 1, "<div style='font-size:25px;color:" + playerColors[player.getColorId()] + "'>" + position + ".</div>");
			rankingTable.setHTML(i+1, 2, "<div style='font-size:25px;color:" + playerColors[player.getColorId()] + "'>" + players.get(i).getName() + "</div>");
			rankingTable.setHTML(i+1, 3, "<div style='font-size:25px;color:" + playerColors[player.getColorId()] + "'>" + players.get(i).getPoints()  + "</div>");
		}
	}
	
	private void setPointerPos(int x,HTML pointer) {

		p.setWidgetPosition(pointer, x+100-Math.round(pointerWidth/2), 180);

	}

	public void setRightNumber(int i) {
		this.rightNumber=i;
		labelRight.setHTML("<span style='font-size:30px'>" + Integer.toString(i) + "</span>");		

	}

	public void setLeftNumber(int i) {
		this.leftNumber=i;
		labelLeft.setHTML("<span style='font-size:30px'>" + Integer.toString(i) + "</span>");		

	}


	/**
	 * mouse handling
	 */

	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
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
