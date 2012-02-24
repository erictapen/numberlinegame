package com.wicam.numberlineweb.client.Multiplication;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.wicam.numberlineweb.client.GameView;

public class MultiplicationView extends AbsolutePanel {
	
	private final HTML infoBox = new HTML();
	private final HTML labelLeft = new HTML();
	private final HTML labelRight = new HTML();
	
	
	public void init(int numberOfPlayers){
		
		GWT.log("call to init with " + Integer.toString(numberOfPlayers) + " players.");
		
		//draw everything
		
		getElement().getStyle().setPosition(Position.RELATIVE);

	
		// IE compatible canvas
		add(new HTML("<div style='border:none; background-color:black;width:400px;height:6px;overflow:hidden;position:absolute;left:100px;top:192px'></div>"));
		add(new HTML("<div style='border:none; background-color:black;width:6px;height:28px;overflow:hidden;position:absolute;left:97px;top:181px'></div>"));
		add(new HTML("<div style='border:none; background-color:black;width:6px;height:28px;overflow:hidden;position:absolute;left:497px;top:181px'></div>"));
		
		infoBox.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>Awaiting Signal...</div>");
		
		add(infoBox);

		setWidgetPosition(infoBox, 10, 290);
		
		HTML c = new HTML("<div id='canvas' style='width:600px;height:400px'></div>");


		add(c);
		
		add(labelLeft);
		add(labelRight);

		setWidgetPosition(labelLeft, 40, 177);
		setWidgetPosition(labelRight, 510, 177);
	}
	
	public void setInfoText(String text) {

		infoBox.setHTML("<div id='infoText' style='width:500px;padding:5px 20px;'>" + text +"</div>");

	}
}
