package com.wicam.numberlineweb.client.WordStem;


import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class WordStemView extends AbsolutePanel {

	private WordStemGameController controller;
	//	private final HTML infoBox = new HTML();
	private final Label infoBox = new Label();
	private final FlowPanel stemBox = new FlowPanel();
	private final FlowPanel wordBox = new FlowPanel();


	public void init(WordStemGameController controller, int numberOfPlayers){

		GWT.log("call to init with " + Integer.toString(numberOfPlayers) + " players.");

		this.controller = controller;

		//draw everything

		getElement().getStyle().setPosition(Position.RELATIVE);

		// display all answers
		stemBox.setWidth("450px");
		stemBox.setStyleName("community-box stem-box");
		add(stemBox);

		// display all answers
		wordBox.setWidth("450px");
		wordBox.setStyleName("hand-box word-box");
		add(wordBox);
		//		setWidgetPosition(handBox, 0, 200);


		// display status updates
		infoBox.setText("Awaiting Signal...");
		infoBox.setStyleName("word-info-box");
		wordBox.add(infoBox);

		//		setWidgetPosition(infoBox, 30, 415);


	}

	public void setInfoText(String text) {

		infoBox.setText(text);

	}

	public void setSecondText(String word) {

		infoBox.setText("Zu welchem Wortstamm gehört "+word+"?");

	}

	public void setFirstText() {

		infoBox.setText("Wähle eines der Wörter aus dem unteren Feld.");

	}



	/**
	 * Draw all the answers
	 * @param digits List of all answers
	 */
	public void drawStems(ArrayList<Word> stems) {

		stemBox.clear();

		int counter = 0;

		for (final Word stem : stems) {

			final Button b = new Button(stem.getWord());
			b.setTabIndex(counter);

			b.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					controller.clickedAt(stem.getWord()+":"+b.getTabIndex()+":stem");

				}
			});

			stemBox.add(b);

			b.setStyleName("stem-Button");

			counter++;
		}

	}

	public void drawWords(ArrayList<Word> words, String taken) {

		wordBox.clear();

		int counter = 0;

		for (final Word word : words) {

			final Button b = new Button(word.getWord());
			b.setTabIndex(counter);

			b.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					controller.clickedAt(word.getWord()+":"+b.getTabIndex()+":word");
				}
			});

			b.setEnabled(!word.isTaken() && !word.isSelected());

			if (taken.equals(word.getWord())) {
				b.setStyleName("word-Button word-Button-chosen");				
			} else {
				b.setStyleName("word-Button");
			}

			wordBox.add(b);

			counter++;
		}
		
		wordBox.add(infoBox);

	}
}
