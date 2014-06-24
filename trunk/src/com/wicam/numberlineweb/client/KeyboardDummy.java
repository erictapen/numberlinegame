package com.wicam.numberlineweb.client;

import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class KeyboardDummy extends Composite {



	private HTML upArrow = 
		new HTML("<div style='width:50px;height:50px'>" +
				
				"<div style='-moz-border-radius:5px;border-radius:5px;position:absolute;width:40px;height:40px;top:5px;left:5px;background-color:grey;opacity:0.7;'></div>" +
				
				"</div>");
	private HTML downArrow = new HTML("<div style='width:50px;height:50px'>" +
			
			"<div style='-moz-border-radius:5px;border-radius:5px;position:absolute;width:40px;height:40px;top:5px;left:5px;background-color:grey;opacity:0.7;'></div>" +
			
			"</div>");
	private HTML leftArrow = new HTML("<div style='width:50px;height:50px'>" +
			
			"<div style='-moz-border-radius:5px;border-radius:5px;position:absolute;width:40px;height:40px;top:5px;left:5px;background-color:grey;opacity:0.7;'></div>" +
			
			"</div>");
	private HTML rightArrow = new HTML("<div style='width:50px;height:50px'>" +
			
			"<div style='-moz-border-radius:5px;border-radius:5px;position:absolute;width:40px;height:40px;top:5px;left:5px;background-color:grey;opacity:0.7;'></div>" +
			
			"</div>");



	public KeyboardDummy(final DirectionHandler target) {

		upArrow.addTouchStartHandler(new TouchStartHandler() {
			
			@Override
			public void onTouchStart(TouchStartEvent event) {
				event.preventDefault();
				target.keyDown(2);
				
			}
		});
		
		upArrow.addTouchEndHandler(new TouchEndHandler() {

			@Override
			public void onTouchEnd(TouchEndEvent event) {
				event.preventDefault();
				target.keyUp(2);
				
			}
			
		});
		
		downArrow.addTouchStartHandler(new TouchStartHandler() {
			
			@Override
			public void onTouchStart(TouchStartEvent event) {
				event.preventDefault();
				target.keyDown(1);
				
			}
		});
		
		downArrow.addTouchEndHandler(new TouchEndHandler() {

			@Override
			public void onTouchEnd(TouchEndEvent event) {
				event.preventDefault();
				target.keyUp(1);
				
			}
			
		});
		
		leftArrow.addTouchStartHandler(new TouchStartHandler() {
			
			@Override
			public void onTouchStart(TouchStartEvent event) {
				event.preventDefault();
				target.keyDown(4);
				
			}
		});
		
		leftArrow.addTouchEndHandler(new TouchEndHandler() {

			@Override
			public void onTouchEnd(TouchEndEvent event) {
				event.preventDefault();
				target.keyUp(4);
				
			}
			
		});
		
		rightArrow.addTouchStartHandler(new TouchStartHandler() {
			
			@Override
			public void onTouchStart(TouchStartEvent event) {
				event.preventDefault();
				target.keyDown(3);
				
			}
		});
		
		rightArrow.addTouchEndHandler(new TouchEndHandler() {

			@Override
			public void onTouchEnd(TouchEndEvent event) {
				event.preventDefault();
				target.keyUp(3);
				
			}
			
		});
		
		// Place the check above the text box using a vertical panel.
		AbsolutePanel panel = new AbsolutePanel();

		panel.setSize("150px", "150px");

		panel.add(upArrow, 50, 0);

		panel.add(downArrow, 50, 100);
		panel.add(leftArrow, 0, 50);
		panel.add(rightArrow, 100, 50);

		// All composites must call initWidget() in their constructors.
		initWidget(panel);


	}


}
