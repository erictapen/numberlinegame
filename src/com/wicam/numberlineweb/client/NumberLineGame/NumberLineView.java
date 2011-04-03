package com.wicam.numberlineweb.client.NumberLineGame;

import java.util.ArrayList;

import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import com.google.gwt.user.client.ui.AbsolutePanel;

/**
 * The game view.
 * @author patrick
 *
 */

public class NumberLineView extends Composite  {

	final FlowPanel motherPanel = new FlowPanel();
	final GWTCanvas canvas = new GWTCanvas(600,400);
	final AbsolutePanel p = new AbsolutePanel();
	
	/**
	 * TODO: create own Composite-Classes for elements
	 */
	
	private int numberOfPlayers;
	final HTML labelLeft = new HTML();
	final HTML labelRight = new HTML();
	ArrayList<NumberLineGamePointer> pointerList = new ArrayList<NumberLineGamePointer>();
	ArrayList<HTML> pointerTextList = new ArrayList<HTML>();
	final HTML infoText = new HTML();
	final HTML exerciseText = new HTML();
	final HTML infoBox = new HTML();
	ArrayList<HTML> pointsList = new ArrayList<HTML>();
	//final HTML points = new HTML();
	//final HTML enemyPoints = new HTML();
	private MouseHandler mouseHandler;

	private int leftNumber;
	private int rightNumber;
	private int pointerWidth;

	public NumberLineView(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
		init();
		sinkEvents(Event.MOUSEEVENTS);
		this.initWidget(motherPanel);

	}

	public void addMouseHandler(MouseHandler h) {

		this.mouseHandler = h;

	}


	private void init() {

		// initialize pointers and pointersText
		// TODO: only three players
		for (int i = 0; i < numberOfPlayers; i++){
			final HTML pointerText = new HTML();
			final HTML points = new HTML();
			pointerTextList.add(pointerText);
			pointsList.add(points);
			if (i == 0){
				final NumberLineGamePointer pointer = new NumberLineGamePointer(2,"blue");
				pointerList.add(pointer);
			}
			if (i == 1){
				final NumberLineGamePointer pointer = new NumberLineGamePointer(2,"red");
				pointerList.add(pointer);
			}
			if (i == 2){
				final NumberLineGamePointer pointer = new NumberLineGamePointer(2,"yellow");
				pointerList.add(pointer);
			}
				
		}
		
		//draw everthing
		
		p.getElement().getStyle().setPosition(Position.RELATIVE);

		canvas.setLineWidth(6);
		canvas.setStrokeStyle(Color.BLACK);
		canvas.beginPath();
		canvas.moveTo(100,180);
		canvas.lineTo(100,210);
		canvas.moveTo(100,195);
		canvas.lineTo(500,195);
		canvas.moveTo(500,182);
		canvas.lineTo(500,208);
		canvas.closePath();
		canvas.stroke();


		//pointsList.get(0).setHTML("<div style='font-size:30px;color:blue'></div>");
		//pointsList.get(1).setHTML("<div style='width:50px;text-align:right;font-size:30px;color:red'></div>");

		for (int i = 0; i < pointerTextList.size(); i++){
			pointerTextList.get(i).setHTML("<div style='font-size:16px;border:solid black 2px'></div>");
		}

		exerciseText.setHTML("<div id='exercise' style='border:solid black 4px;padding:5px 20px;font-size:25px'>---</div>");
		infoBox.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>Awaiting Signal...</div>");


		p.add(exerciseText);

		p.setWidgetPosition(exerciseText, 260, 50);

		p.add(infoBox);

		p.setWidgetPosition(infoBox, 10, 290);

		p.add(canvas);

		for (int i = 0; i < pointsList.size(); i++){
			p.add(pointsList.get(i));
		}
		
		if (pointsList.size() == 2){
			p.setWidgetPosition(pointsList.get(1), 415, 347);
			p.setWidgetPosition(pointsList.get(0), 30, 347);
		}
		if (pointsList.size() == 3){
			p.setWidgetPosition(pointsList.get(2), 222, 347);
			p.setWidgetPosition(pointsList.get(1), 415, 347);
			p.setWidgetPosition(pointsList.get(0), 30, 347);
		}
		

		p.add(labelLeft);
		p.add(labelRight);

		p.setWidgetPosition(labelLeft, 40, 177);
		p.setWidgetPosition(labelRight, 510, 177);

		motherPanel.add(p);

		RootPanel.get().add(motherPanel);
	}

	public void setExerciseNumber(int n) {

		exerciseText.setHTML("<div id='exercise' style='border:solid black 4px;padding:5px 20px;font-size:25px'>" + 
				Integer.toString(n) + "</div>");
	}

	public void setPoints(int playerid, int p,String name) {
		if (playerid == 1)
			pointsList.get(0).setHTML("<div style='font-size:30px;color:blue'>" + Integer.toString(p) +"<span style='font-size:14px'> " + name +"</span></div>");
		if (playerid == 2)
			pointsList.get(1).setHTML("<div style='width:150px;text-align:right;font-size:30px;color:red'><span style='font-size:14px'> " + name + "</span> " + Integer.toString(p) +"</div>");
		if (playerid == 3)
			pointsList.get(2).setHTML("<div style='width:75px;text-align:right;font-size:30px;color:yellow'>" + Integer.toString(p) +"<span style='font-size:14px'> " + name +"</span></div>");

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
			p.remove(pointerTextList.get(i));
		}
		
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
	
	

	public void showPointerText(int playerid, int x, int correct)  {

		p.add(pointerTextList.get(playerid-1));
		setPointerText(playerid, rawPosToReal(x),correct,pointerTextList.get(playerid-1));
		// TODO: position
		p.setWidgetPosition(pointerTextList.get(playerid-1), x+100, 159);

	}
	
	public int realPosToRaw(int pos) {

		return (int)((pos -leftNumber) /  ((double)(rightNumber -leftNumber)/400));

	}

	public int rawPosToReal(int pos) {

		return leftNumber + (int) ((pos) *  ((double)(rightNumber - leftNumber)/400));

	}


	private void setPointerText(int playerid, int x, int correct, HTML text) {

		String html;

		if (playerid == 1)
			html = "<div style='color:blue;font-size:16px;border:solid blue 2px'>";
		else if (playerid == 2)
			html = "<div style='color:red;font-size:16px;border:solid red 2px'>";
		else {
			html = "<div style='color:yellow;font-size:16px;border:solid yellow 2px'>";
		}
		
		

		html +=  Integer.toString(x) +  " (" +  Integer.toString(Math.abs(x-correct)) + ")</div>";

		text.setHTML(html);

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
