package com.wicam.numberlineweb.client.Multiplication;


import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class MultiplicationView extends AbsolutePanel {
	
	private MultiplicationGameController controller;
	private final HTML infoBox = new HTML();
	//private final HTML resultBox = new HTML();
	private final FlowPanel answersBox = new FlowPanel();
	
	
	public void init(MultiplicationGameController controller, int numberOfPlayers){
		
		GWT.log("call to init with " + Integer.toString(numberOfPlayers) + " players.");
		
		this.controller = controller;
		
		//draw everything
		
		getElement().getStyle().setPosition(Position.RELATIVE);
		
		// display status updates
		infoBox.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>Awaiting Signal...</div>");
		add(infoBox);
		
		// display all answers
		answersBox.setHeight("400px");
		answersBox.setWidth("400px");
		answersBox.setStyleName("answer-box");
		add(answersBox);
		
		
		setWidgetPosition(infoBox, 80, 360);
		
	}
	
	public void setInfoText(String text) {

		infoBox.setHTML("<div id='resultText' style='width:500px;padding:5px 20px;'>" + text +"</div>");

	}
	
	public void setResultText(int res) {
		
		infoBox.setHTML("<div id='resultText' style='width:500px;padding:5px 20px;'>" +
				"Gesucht wird <strong style=\"font-size:1.2em;\">"+Integer.toString(res) +"</strong></div>");
		
	}
	
	
	
	/**
	 * Draw all the answers
	 * @param answers List of all answers
	 */
	public void drawAnwers(ArrayList<MultiplicationAnswer> answers) {
		
		answersBox.clear();
		
		for (final MultiplicationAnswer answer : answers) {
			Button b = new Button(answer.getAnswer(), new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					controller.clickedAt(answer.getAnswer());
					
				}
			});

			String color = "";
			if (answer.isTaken()) {
				String back;
				if (answer.isCorrect()) {
					//color = " correct";
					back = answer.getColor();
				} else {
					//color = " wrong";
					back = "black";
				}
				DOM.setElementAttribute(b.getElement(), "style", "background-color:"+back+";color:white;");
			}
			
			b.setStyleName("answer-Button"+color);
			b.setEnabled(!answer.isTaken()); // unclickable, if already taken
			
			
			answersBox.add(b);
		}
		
	}
}
