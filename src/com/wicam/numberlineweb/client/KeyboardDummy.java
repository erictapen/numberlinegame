package com.wicam.numberlineweb.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.HandlesAllKeyEvents;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

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


		upArrow.addMouseDownHandler(new MouseDownHandler() {

			@Override
			public void onMouseDown(MouseDownEvent event) {
				
				event.preventDefault();
				target.directionDown(2);

			}

		});
		
		upArrow.addMouseMoveHandler(new MouseMoveHandler() {

			@Override
			public void onMouseMove(MouseMoveEvent event) {
				
				event.preventDefault();
				target.directionDown(2);

			}

		});


		upArrow.addMouseUpHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				
				event.preventDefault();
				target.directionUp(2);
			}

		});
		
		upArrow.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				
				event.preventDefault();
				target.directionUp(2);
			}

		});
		

		downArrow.addMouseDownHandler(new MouseDownHandler() {

			@Override
			public void onMouseDown(MouseDownEvent event) {
				
				event.preventDefault();
				target.directionDown(1);

			}

		});
		
		downArrow.addMouseMoveHandler(new MouseMoveHandler() {

			@Override
			public void onMouseMove(MouseMoveEvent event) {
				
				event.preventDefault();
				target.directionDown(1);

			}

		});

		downArrow.addMouseUpHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				
				event.preventDefault();
				target.directionUp(1);
			}

		});
		
		downArrow.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				
				event.preventDefault();
				target.directionUp(1);
			}

		});
		

		leftArrow.addMouseDownHandler(new MouseDownHandler() {

			@Override
			public void onMouseDown(MouseDownEvent event) {
				
				event.preventDefault();
				target.directionDown(4);

			}

		});
		
		leftArrow.addMouseMoveHandler(new MouseMoveHandler() {

			@Override
			public void onMouseMove(MouseMoveEvent event) {
				
				event.preventDefault();
				target.directionDown(4);

			}

		});

		leftArrow.addMouseUpHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				
				event.preventDefault();
				target.directionUp(4);
			}

		});
		
		leftArrow.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				
				event.preventDefault();
				target.directionUp(4);
			}

		});

		rightArrow.addMouseDownHandler(new MouseDownHandler() {

			@Override
			public void onMouseDown(MouseDownEvent event) {
				
				event.preventDefault();
				target.directionDown(3);

			}

		});
		
		rightArrow.addMouseMoveHandler(new MouseMoveHandler() {

			@Override
			public void onMouseMove(MouseMoveEvent event) {
				
				event.preventDefault();
				target.directionDown(3);

			}

		});


		rightArrow.addMouseUpHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				
				event.preventDefault();
				target.directionUp(3);
			}

		});
		
		rightArrow.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				
				event.preventDefault();
				target.directionUp(3);
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
