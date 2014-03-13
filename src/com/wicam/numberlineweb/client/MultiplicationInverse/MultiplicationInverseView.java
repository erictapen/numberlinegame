package com.wicam.numberlineweb.client.MultiplicationInverse;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationAnswer;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationView;

public class MultiplicationInverseView extends MultiplicationView {

	
	public void setTaskText(String task) {
		GWT.log("write " + task);
		infoBox.setHTML("<div id='resultText' style='width:500px;padding:5px 20px;'>" +
				"Gesucht wird <strong style=\"font-size:30px;\">" + task + "</strong></div>");
		
	}
	
	/**
	 * Draw all the answers
	 * @param answers List of all answers
	 */
	@Override
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
			// Adjust the size to a fixed width.
			b.setWidth("100px");
			b.setEnabled(!answer.isTaken()); // unclickable, if already taken
			
			
			answersBox.add(b);
		}
		
	}
	
}
