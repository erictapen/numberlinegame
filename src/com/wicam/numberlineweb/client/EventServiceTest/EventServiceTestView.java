package com.wicam.numberlineweb.client.EventServiceTest;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Event service test view.
 * @author timfissler
 *
 */

public class EventServiceTestView extends Composite {

	private EventServiceTestCoordinator coord;
	private final HorizontalPanel motherPanel = new HorizontalPanel();
	private final AbsolutePanel testPanel = new AbsolutePanel();
	private final HTML counterText = new HTML();
	private final Button startButton = new Button("Starten", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			coord.startCounter();
		}
	});
	private final Button endButton = new Button("Beenden", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			coord.stopCounter();
		}
	});

	/**
	 * Construct a new math assessment view.
	 * @param gameController
	 */
	public EventServiceTestView(EventServiceTestCoordinator coord) {
		this.coord = coord;
		init();
		this.initWidget(motherPanel);
	}
	
	/**
	 * Initialize the view.
	 */
	private void init() {
		
		// Setup the mother panel.
		testPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		testPanel.setHeight("400px");
		testPanel.setWidth("750px");
		setCounter("");
		testPanel.add(counterText);
		testPanel.setWidgetPosition(counterText, 350, 190);
		testPanel.add(startButton);
		testPanel.setWidgetPosition(startButton, 30, 350);
		testPanel.add(endButton);
		testPanel.setWidgetPosition(endButton, 600, 350);
		motherPanel.add(testPanel);
		
		RootPanel.get().add(motherPanel);
	}
	
	/**
	 * Sets the new counter in the view.
	 * @param taskText
	 */
	public void setCounter(String counter) {
		counterText.setHTML("<div style='font-size:32px'>" + counter + "</div>"); 
	}
	
}
