package com.wicam.numberlineweb.client.NumberLineGame;

import java.util.ArrayList;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.wicam.numberlineweb.client.GameController;
import com.wicam.numberlineweb.client.GameView;
import com.wicam.numberlineweb.client.MouseHandler;

public class NumberLineView extends AbsolutePanel {
	
	private MouseHandler mouseHandler;
	private final HTML infoBox = new HTML();
	private final NumberLineExercise exerciseText = new NumberLineExercise();
	private final HTML labelLeft = new HTML();
	private final HTML labelRight = new HTML();
	
	ArrayList<NumberLineGamePointer> pointerList = new ArrayList<NumberLineGamePointer>();
	private int pointerWidth;
	
	public void init(MouseHandler mouseHandler, int numberOfPlayers){
		this.mouseHandler = mouseHandler;
		// initialize pointers
		int numberOfPointers = numberOfPlayers;
		for (int i = 0; i < numberOfPointers; i++){
			final NumberLineGamePointer pointer = new NumberLineGamePointer(2,GameView.playerColors[i]);
			pointerList.add(pointer);
		}
		
		//draw everthing

		getElement().getStyle().setPosition(Position.RELATIVE);

	
		// IE compatible canvas
		add(new HTML("<div style='border:none; background-color:black;width:400px;height:6px;overflow:hidden;position:absolute;left:100px;top:192px'></div>"));
		add(new HTML("<div style='border:none; background-color:black;width:6px;height:28px;overflow:hidden;position:absolute;left:97px;top:181px'></div>"));
		add(new HTML("<div style='border:none; background-color:black;width:6px;height:28px;overflow:hidden;position:absolute;left:497px;top:181px'></div>"));
		
		infoBox.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>Awaiting Signal...</div>");
		
		add(infoBox);

		setWidgetPosition(infoBox, 10, 290);
		
		HTML c = new HTML("<div id='canvas' style='width:600px;height:400px'></div>");

		add(exerciseText);
	
		setWidgetPosition(exerciseText, 210, -10);

		add(c);
		
		add(labelLeft);
		add(labelRight);

		setWidgetPosition(labelLeft, 40, 177);
		setWidgetPosition(labelRight, 510, 177);
	}
	
	public void setInfoText(String text) {

		infoBox.setHTML("<div id='infoText' style='width:500px;padding:5px 20px;'>" + text +"</div>");

	}
	
	public void setExerciseNumber(int n) {

		exerciseText.setText(Integer.toString(n));
		exerciseText.booooing();
	}
	
	public void setExerciseNumberText(String s) {

		exerciseText.setText(s);
	}
	
	public void setRightNumber(int i) {
		labelRight.setHTML("<span style='font-size:30px'>" + Integer.toString(i) + "</span>");		

	}

	public void setLeftNumber(int i) {
		labelLeft.setHTML("<span style='font-size:30px'>" + Integer.toString(i) + "</span>");		

	}
	
	/**
	 * Reset the view
	 */

	public void removePointer() {
		for (int i = 0; i < pointerList.size(); i++){
			remove(pointerList.get(i));
			//p.remove(pointerTextList.get(i));
		}
	}

	public void setPointer(int playerid, int x)  {
		add(pointerList.get(playerid-1));
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
	
	private void setPointerPos(int x,HTML pointer) {

		setWidgetPosition(pointer, x+100-Math.round(pointerWidth/2), 180);

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
