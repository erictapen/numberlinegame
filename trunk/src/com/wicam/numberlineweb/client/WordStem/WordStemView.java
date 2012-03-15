package com.wicam.numberlineweb.client.WordStem;


import java.util.ArrayList;
import java.util.NoSuchElementException;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class WordStemView extends AbsolutePanel {
	
	private WordStemGameController controller;
	private final HTML infoBox = new HTML();
	private final FlowPanel potContainer = new FlowPanel();
	private final ArrayList<Pot> pots = new ArrayList<Pot>();
	private ArrayList<String> stems = new ArrayList<String>();
	private final ArrayList<Label> wordLabels = new ArrayList<Label>();
	private final AbsolutePanel wordsContainer = new AbsolutePanel();
	private PickupDragController dragController = new PickupDragController(this, true);
	
	
	public void init(WordStemGameController controller, int numberOfPlayers){
		
		GWT.log("call to init with " + Integer.toString(numberOfPlayers) + " players.");
		
		this.controller = controller;
		
		//draw everything
		
		getElement().getStyle().setPosition(Position.RELATIVE);
		
		// display status updates
		infoBox.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>Awaiting Signal...</div>");
		add(infoBox);
		
		// display all pots
		potContainer.setHeight("80px");
		potContainer.setWidth("400px");
		potContainer.setStyleName("pot-container");
		add(potContainer);
		setWidgetPosition(potContainer, 30, 10);
		
		// display all words
		wordsContainer.setHeight("250px");
		wordsContainer.setWidth("400px");
		wordsContainer.setStyleName("words-container");
		add(wordsContainer);
		setWidgetPosition(wordsContainer, 180, 90);

		setWidgetPosition(infoBox, 30, 320);
		
		// Drag & Drop
		this.dragController.setBehaviorConstrainedToBoundaryPanel(false);
		
		
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
				"Ordne die Wörter ihren Wortstämmen zu.</div>");

	}
	
	
	/**
	 * Add new pots to the view, no drawing, see drawPots()
	 * @param sets WordSets to be accepted by the pots
	 */
	public ArrayList<String> addNewPots(ArrayList<String> stems) {
		
		pots.clear();
		this.stems = stems;
		this.dragController.unregisterDropControllers();
		
		for (String stem : stems) {
			final Pot pot = new Pot(70, 70, stem);
			pots.add(pot);
			
			// dnd
			DropController dropController = new PotDropController(pot, controller);
			
			this.dragController.registerDropController(dropController);
			
		}
		
		return stems;
		
	}
	
	
	/**
	 * Draw all pots with wordstems as label
	 */
	public void updatePots() {
		
		this.potContainer.clear();
		for (Pot pot : this.pots) {
			pot.updateText();
			potContainer.add(pot);
		}
		
	}
	
	
	/**
	 * Draw all the answers once
	 * @param digits List of all answers
	 */
	public void addNewWords(ArrayList<Word> words) {
		
		this.wordsContainer.clear();
		this.wordLabels.clear();
		int counter = 0;
		
		for (Word word : words) {
			Label w = new Label(word.getWord());
			w.setTitle(Integer.toString(counter));
			this.dragController.makeDraggable(w);
			this.wordsContainer.add(w);
			this.wordLabels.add(w);
			word.setBasicPosition(w.getAbsoluteLeft(), w.getAbsoluteTop());
			word.setPosition(w.getAbsoluteLeft(), w.getAbsoluteTop());
			
			counter++;
		}
		
	}
	
	
	/**
	 * @param s String that the label has
	 * @return Returns the Label of the word with the id
	 */
	private Label getWordLabelByValue(String s) throws NoSuchElementException {
		for (Widget w : this.wordLabels) {
			Label l = (Label) w;
			if (l.getText().equals(s)) {
				return l;
			}
		}
		throw new NoSuchElementException("No label with the word "+s);
	}
	
	
	
	/**
	 * Updates the position/visibility of the words
	 * @param words all words to update
	 */
	public void updateWords(ArrayList<Word> words) {
		
//		for (Word word : words) {
//			Label l = getWordLabelByValue(word.getWord());
//			
//			if (word.isTaken()) {
//				l.setVisible(false);
//				this.dragController.makeNotDraggable(l);
//			}
//		}
		
	}

	public ArrayList<String> getPots() {
		return this.stems;
	}
	

}
