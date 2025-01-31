package com.wicam.numberlineweb.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.SelectionChangeEvent;

public abstract class GameSelector extends AbstractGameSelector implements ValueChangeHandler<String> {
	
	protected HandlerRegistration handlerReg;

	public GameSelector(GameCoordinator coordinator) {

		this.coordinator = coordinator;

		init();
		sinkEvents(Event.MOUSEEVENTS);
		this.initWidget(motherPanel);

	}


	protected void init() {

		RootPanel.get().add(motherPanel);
		
		this.handlerReg = History.addValueChangeHandler(this);

		History.newItem("gameSelector-" + coordinator.getGameName(),false);

		joinGameButton = new Button("Mitspielen");


		joinGameButton.setEnabled(false);

		PushButton homeButton = new PushButton("");
		homeButton.addStyleName("homebutton");

		createGameButton = new Button("Neues Spiel");

		cellList.setSelectionModel(selectionModel);
		cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler(){

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				// TODO Auto-generated method stub
				if (selectionModel.getSelectedObject() != null) {

					joinGameButton.setEnabled(true);

				}else{
					joinGameButton.setEnabled(false);
				}
			}


		});

		ScrollPanel s = new ScrollPanel(cellList);

		s.setWidth("400px");
		s.setHeight("300px");



		joinGameButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				joinGame();

			}
		});

		homeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				History.back();
			}
		});

		addGameCreationHandler();

		motherPanel.setWidth("600px");
		motherPanel.setHeight("400px");
		motherPanel.add(s);

		HTML title = new HTML();

		title.setHTML("<div class='selectorTitle'>" + coordinator.getGameName() + " - Offene Spiele");

		motherPanel.setWidgetPosition(s, 25, 70);

		motherPanel.add(title);
		motherPanel.setWidgetPosition(title, 15, 20);


		createGameButton.setHeight("30px");
		createGameButton.setWidth("220px");


		joinGameButton.setHeight("30px");
		joinGameButton.setWidth("220px");


		motherPanel.add(createGameButton);

		motherPanel.add(joinGameButton);
		motherPanel.add(homeButton);


		motherPanel.setWidgetPosition(joinGameButton, boxWidth-250, 30);
		//motherPanel.setWidgetPosition(refreshButton, 15, 340);

		motherPanel.setWidgetPosition(createGameButton, boxWidth-250, 60);


		clearGameList();

		t = new Timer() {

			@Override
			public void run() {

				coordinator.refreshGameList();
			}
		};

		//main loop-timer
		t.scheduleRepeating(2000);

	}
	
	/**
	 * Action in case of history back event.
	 * @param event
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if (event.getValue().equals("")) {
			this.handlerReg.removeHandler();
			coordinator.rootPanel.clear();
			coordinator.getGTS().init(coordinator.rootPanel);
		}
	}

}