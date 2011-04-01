package com.wicam.numberlineweb.client.NumberLineGame;

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
	
	final HTML labelLeft = new HTML();
	final HTML labelRight = new HTML();
	final HTML pointer = new NumberLineGamePointer(22,"blue");
	final HTML pointerEnemy = new NumberLineGamePointer(22,"red");
	final HTML pointerTextEnemy = new HTML();
	final HTML pointerText = new HTML();
	final HTML infoText = new HTML();
	final HTML exerciseText = new HTML();
	final HTML infoBox = new HTML();
	final HTML points = new HTML();
	final HTML enemyPoints = new HTML();
	private MouseHandler mouseHandler;

	private int leftNumber;
	private int rightNumber;

	public NumberLineView() {

		init();
		sinkEvents(Event.MOUSEEVENTS);
		this.initWidget(motherPanel);

	}

	public void addMouseHandler(MouseHandler h) {

		this.mouseHandler = h;

	}


	private void init() {

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


		points.setHTML("<div style='font-size:30px;color:blue'></div>");
		enemyPoints.setHTML("<div style='width:50px;text-align:right;font-size:30px;color:red'></div>");

		pointerText.setHTML("<div style='font-size:16px;border:solid black 2px'></div>");
		pointerTextEnemy.setHTML("<div style='font-size:16px;border:solid black 2px'></div>");

		exerciseText.setHTML("<div id='exercise' style='border:solid black 4px;padding:5px 20px;font-size:25px'>---</div>");
		infoBox.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>Awaiting Signal...</div>");


		p.add(exerciseText);

		p.setWidgetPosition(exerciseText, 260, 50);

		p.add(infoBox);

		p.setWidgetPosition(infoBox, 10, 290);

		p.add(canvas);

		p.add(enemyPoints);
		p.add(points);

		p.setWidgetPosition(enemyPoints, 415, 347);
		p.setWidgetPosition(points, 30, 347);

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

	public void setPoints(int p,String name) {

		points.setHTML("<div style='font-size:30px;color:blue'>" + Integer.toString(p) +"<span style='font-size:14px'> " + name +"</span></div>");

	}

	public void setEnemyPoints(int p, String name) {

		enemyPoints.setHTML("<div style='width:150px;text-align:right;font-size:30px;color:red'><span style='font-size:14px'> " + name + "</span> " + Integer.toString(p) +"</div>");

	}


	public void setInfoText(String text) {

		infoBox.setHTML("<div id='infoText' style='width:500px;padding:5px 20px;'>" + text +"</div>");

	}


	public void setOwnPointer(int x)  {

		p.add(pointer);
		setPointerPos(x,pointer);

	}

	/**
	 * Reset the view
	 */

	public void clear() {

		p.remove(pointerEnemy);
		p.remove(pointer);
		p.remove(pointerTextEnemy);
		p.remove(pointerText);

	}

	public void setEnemyPointer(int x)  {

		p.add(pointerEnemy);
		setPointerPos(x,pointerEnemy);

	}

	public void showEnemyPointerText(int x, int correct)  {

		p.add(pointerTextEnemy);
		setPointerText(rawPosToReal(x),correct,pointerTextEnemy);
		p.setWidgetPosition(pointerTextEnemy, x+100, 209);

	}

	
	public void setPointerSize(int width) {
		
		
		
		
		
		
	}
	

	public void showPointerText(int x, int correct)  {

		p.add(pointerText);
		setPointerText(rawPosToReal(x),correct,pointerText);
		p.setWidgetPosition(pointerText, x+100, 159);

	}

	public int realPosToRaw(int pos) {

		return (int)((pos -leftNumber) /  ((double)(rightNumber -leftNumber)/400));

	}

	public int rawPosToReal(int pos) {

		return leftNumber + (int) ((pos) *  ((double)(rightNumber - leftNumber)/400));

	}


	private void setPointerText(int x, int correct, HTML text) {

		String html;

		if (text == pointerTextEnemy) {
			html = "<div style='color:red;font-size:16px;border:solid red 2px'>";
		}else{
			html = "<div style='color:blue;font-size:16px;border:solid blue 2px'>";
		}

		html +=  Integer.toString(x) +  " (" +  Integer.toString(Math.abs(x-correct)) + ")</div>";

		text.setHTML(html);

	}


	private void setPointerPos(int x,HTML pointer) {

		p.setWidgetPosition(pointer, x+100, 180);

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
