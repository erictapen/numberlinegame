package com.wicam.numberlineweb.client.BuddyNumber;


import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class BuddyNumberView extends AbsolutePanel {
	
	private BuddyNumberGameController controller;
	private final HTML infoBox = new HTML();
	//private final HTML resultBox = new HTML();
	private final FlowPanel communityBox = new FlowPanel();
	private final FlowPanel handBox = new FlowPanel();
	
	
	public void init(BuddyNumberGameController controller, int numberOfPlayers){
		
		GWT.log("call to init with " + Integer.toString(numberOfPlayers) + " players.");
		
		this.controller = controller;
		
		//draw everything
		
		getElement().getStyle().setPosition(Position.RELATIVE);
		
		// display status updates
		infoBox.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>Awaiting Signal...</div>");
		add(infoBox);
		
		// display all answers
		communityBox.setHeight("180px");
		communityBox.setWidth("370px");
		communityBox.setStyleName("community-box");
		add(communityBox);
		
		// display all answers
		handBox.setHeight("90px");
		handBox.setWidth("370px");
		handBox.setStyleName("hand-box");
		add(handBox);
//		setWidgetPosition(handBox, 0, 200);
		

		setWidgetPosition(infoBox, 30, 320);
		
		
	}
	
	public void setInfoText(String text) {

		infoBox.setHTML("<div id='resultText' style='width:500px;padding:5px 20px;'>" + text +"</div>");

	}

	public void setSecondText(int first) {

		infoBox.setHTML("<div id='resultText' style='width:500px;padding:5px 20px;'>" +
				"Welche Zahl ergibt zusammen mit "+Integer.toString(first) +" genau 10?</div>");

	}

	public void setFirstText() {

		infoBox.setHTML("<div id='resultText' style='width:500px;padding:5px 20px;'>" +
				"Wähle eine <strong>deiner</strong> Zahlen durch klicken.</div>");

	}
	
	
	
	/**
	 * Draw all the answers
	 * @param digits List of all answers
	 */
	public void drawCommunityDigits(ArrayList<BuddyNumberDigit> digits) {
		
		communityBox.clear();
		
		int counter = 0;
		
		for (final BuddyNumberDigit digit : digits) {
			
			final Button b = new Button(digit.toString());
			b.setTabIndex(counter);
			
			b.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					controller.clickedAt(digit.toString()+":"+b.getTabIndex()+":com");
					
				}
			});
			
			if (digit.isTaken()) {
				b.setStyleName("answer-Button answer-Button-chosen");				
			} else {
				b.setStyleName("answer-Button");
			}
			
			
			b.setEnabled(!digit.isTaken()); // unclickable, if already taken
			
			communityBox.add(b);
			
			counter++;
		}
		
	}
	
	public void drawHandDigits(ArrayList<BuddyNumberDigit> digits, int taken) {
		
		handBox.clear();
		
		int counter = 0;
		
		for (final BuddyNumberDigit digit : digits) {
			
			final Button b = new Button(digit.toString());
			b.setTabIndex(counter);
			
			b.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					controller.clickedAt(digit.toString()+":"+b.getTabIndex()+":hand");
					
				}
			});
			
			b.setEnabled(!digit.isTaken()); // unclickable, if round over
			
			if (taken == digit.getValue()) {
				b.setStyleName("answer-Button answer-Button-chosen");				
			} else {
				b.setStyleName("answer-Button");
			}
			
			
			
			handBox.add(b);
			
			counter++;
		}
		
	}
}
