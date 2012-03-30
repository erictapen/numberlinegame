package com.wicam.numberlineweb.client.OverTen;


import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class OverTenView extends AbsolutePanel {
	
	private OverTenGameController controller;
	private final HTML infoBox = new HTML();
	//private final HTML resultBox = new HTML();
	private final FlowPanel communityBox = new FlowPanel();
	private final FlowPanel calcBox = new FlowPanel();
	
	
	public void init(OverTenGameController controller, int numberOfPlayers){
		
		GWT.log("call to init with " + Integer.toString(numberOfPlayers) + " players.");
		
		this.controller = controller;
		
		//draw everything
		
		getElement().getStyle().setPosition(Position.RELATIVE);
		
		// display status updates
		infoBox.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>Awaiting Signal...</div>");
		add(infoBox);
		
		// display all answers
		communityBox.setWidth("400px");
		communityBox.setStyleName("community-box center-text");
		add(communityBox);
		
		// display all answers
		calcBox.setWidth("400px");
		calcBox.setStyleName("hand-box center-text");
		add(calcBox);
		

		setWidgetPosition(infoBox, 30, 320);
		
		
	}
	
	public void setInfoText(String text) {

		infoBox.setHTML("<div id='resultText' style='width:500px;padding:5px 20px;'>" + text +"</div>");

	}

	
	public void setThirdText(int result) {
		infoBox.setHTML("<div id='resultText' style='width:500px;padding:5px 20px;'>" +
				"Welche Zahl ergibt zusammen mit 10 genau "+result+"?</div>");
	}
	
	public void setSecondText(int first) {
		infoBox.setHTML("<div id='resultText' style='width:500px;padding:5px 20px;'>" +
				"Welche Zahl ergibt zusammen mit "+first+" genau 10?</div>");
	}

	public void setFirstText() {
		infoBox.setHTML("<div id='resultText' style='width:500px;padding:5px 20px;'>" +
				"Wähle eine der Rechnungen durch klicken aus.</div>");
	}
	
	
	
	/**
	 * Draw all the answers
	 * @param digits List of all answers
	 */
	public void drawCommunityDigits(ArrayList<OverTenDigit> digits) {
		
		communityBox.clear();
		
		int counter = 0;
		
		for (final OverTenDigit digit : digits) {
			
			final Button b = new Button(digit.toString());
			b.setTabIndex(counter);
			
			b.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					controller.clickedAt(digit.toString()+":"+b.getTabIndex()+":com");
				}
			});
			
			if (digit.isTaken() || digit.isChosen()) {
				b.setStyleName("answer-Button answer-Button-chosen");				
			} else {
				b.setStyleName("answer-Button");
			}
			
			
			b.setEnabled(!digit.isTaken() && !digit.isChosen()); // unclickable, if already taken
			
			communityBox.add(b);
			
			counter++;
		}
		
	}
	
	public void drawCalculations(ArrayList<OverTenCalculation> calcs, String taken) {
		
		calcBox.clear();
		
		int counter = 0;
		
		for (final OverTenCalculation calc : calcs) {
			
			final Button b = new Button(calc.toString());
			b.setTabIndex(counter);
			
			b.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					controller.clickedAt(calc.toString()+":"+b.getTabIndex()+":calc");
				}
			});
			
			b.setEnabled(!calc.isTaken()); // unclickable, if round over
			
			if (taken.equals(calc.toString())) {
				b.setStyleName("answer-Button answer-Button-chosen");				
			} else {
				b.setStyleName("answer-Button");
			}
			
			
			
			calcBox.add(b);
			
			counter++;
		}
		
	}
}
