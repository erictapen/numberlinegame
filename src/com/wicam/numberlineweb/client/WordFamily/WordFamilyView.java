package com.wicam.numberlineweb.client.WordFamily;


import java.util.ArrayList;
import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Random;

public class WordFamilyView extends AbsolutePanel {

	private WordFamilyGameController controller;
	private final HTML infoBox = new HTML();
	private ArrayList<Word> words;
	private Word stem;
	private final ArrayList<WordFamilyTextBox> boxes = new ArrayList<WordFamilyTextBox>();
	private final DecoratorPanel inputContainer = new DecoratorPanel();
	private final FlexTable inputTable = new FlexTable();
	private final TextBox input = new TextBox();
	private final Button submit = new Button("Abschicken");
	private final Button done = new Button("Fertig");
	
	private int wordsUsed;
	private final int OVERLAP = 500; // Overlap of the words in milliseconds
	private final int DURATION = 2500; // Duration of each word
	
	private boolean running;
	
	private com.google.gwt.user.client.Timer wordsTimer = new Timer() {
		@Override
		public void run() {
			updateWord();
		}
	};
	
	private com.google.gwt.user.client.Timer stemTimer = new Timer() {
		@Override
		public void run() {
			hideStem();
		}
	};
	
	private com.google.gwt.user.client.Timer inputTimer = new Timer() {
		@Override
		public void run() {
			showInput();
		}
	};


	public void init(final WordFamilyGameController controller, int numberOfPlayers){

		GWT.log("call to init with " + Integer.toString(numberOfPlayers) + " players.");

		this.controller = controller;

		//draw everything

		getElement().getStyle().setPosition(Position.RELATIVE);

		WordFamilyTextBox t0 = new WordFamilyTextBox();
		WordFamilyTextBox t1 = new WordFamilyTextBox();
		WordFamilyTextBox t2 = new WordFamilyTextBox();
		WordFamilyTextBox t3 = new WordFamilyTextBox();
		
		boxes.addAll(Arrays.asList(t0, t1, t2, t3));
		
		add(t0);
		setWidgetPosition(t0, 0, 20);
		add(t1);
		setWidgetPosition(t1, 0, 300);
		add(t2);
		setWidgetPosition(t2, 250, 20);
		add(t3);
		setWidgetPosition(t3, 250, 300);
		
		
		done.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				controller.clickedAt("done");
			}
		});
		
		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				submitInput();
			}
		});
		
		input.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					submitInput();
				}
			}
		});
		
		FlexCellFormatter cellFormatter = inputTable.getFlexCellFormatter();
		cellFormatter.setColSpan(0, 0, 2);
		cellFormatter.setColSpan(1, 0, 2);
		cellFormatter.setAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		cellFormatter.setAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		cellFormatter.setAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		cellFormatter.setAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		
		
		inputTable.setHTML(0,0,"Tippe die Wörter ein, die du dir gemerkt hast.<br>Klicke nach jedem Wort auf " +
				"\"abschicken\".<br>Wenn du fertig bist, klicke auf \"fertig\".");
		inputTable.setWidget(1, 0, input);
		inputTable.setWidget(2, 0, done);
		inputTable.setWidget(2, 1, submit);

		inputContainer.setVisible(false);
		inputContainer.add(inputTable);
		add(inputContainer);
		setWidgetPosition(inputContainer, 50, 130);
		
		
		setWidth("430px");
		setHeight("400px");
		
		// display status updates
		infoBox.setText("Awaiting Signal...");
		add(infoBox);
		setWidgetPosition(infoBox, 50, 250);


	}
	
	

	public void setInfoText(String text) {
	
		infoBox.setHTML(text);
	
	}
	
	

	/**
	 * Set running
	 * @param running Set running to this value
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}



	/**
	 * @return Returns running
	 */
	public boolean isRunning() {
		return running;
	}



	public void run(ArrayList<Word> words, Word stem) {
		this.words = words;
		this.stem = stem;
		this.setRunning(true);
		
		this.setInfoText("<div style=\"text-align:center;\">Merke dir alle Wörter,<br>die zur Wortfamilie von" +
				"<div style=\"text-align:center;font-weight:bold;color:red;margin:.8em 0em;\">"+
				this.stem.getWord()+"</div>gehören!</div>");
		
		// hide stem in DURATION*2 milliseconds
		stemTimer.schedule(DURATION*2);
	}

	
	
	protected void hideStem() {
		this.setInfoText("");
		// show first word
		updateWord();
		
		// schedule next words
		if (words.size()>1) wordsTimer.scheduleRepeating(DURATION-OVERLAP);
		else inputTimer.schedule(DURATION);
	}
	
	
	
	protected void updateWord() {

		if (words.size() == wordsUsed) {
			wordsTimer.cancel();
			inputTimer.schedule(DURATION);
		} else {
			showNextWord(words.get(wordsUsed).getWord());
			wordsUsed++;
		}
	}

	
	
	private synchronized void showNextWord(String text) {
		int i;
		do {
			i = Random.nextInt(boxes.size());
		} while (boxes.get(i).isUsed());
		boxes.get(i).show(text, DURATION);
	}

	
	
	private void showInput() {
		inputContainer.setVisible(true);
		input.setEnabled(true);
		input.setFocus(true);
		submit.setEnabled(true);
		done.setEnabled(true);
	}

	private void submitInput() {
		if (!input.getText().equals("")) {
			controller.clickedAt(input.getText());
			input.setText("");
			input.setFocus(true);
		}
	}
	
	
	public void disableInput() {
		input.setEnabled(false);
		submit.setEnabled(false);
		done.setEnabled(false);
	}

	
	
	public void reset() {
		infoBox.setVisible(true);
		inputContainer.setVisible(false);
		this.setRunning(false);
		this.wordsUsed = 0;
	}


}
