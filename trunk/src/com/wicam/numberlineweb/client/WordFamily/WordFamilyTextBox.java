package com.wicam.numberlineweb.client.WordFamily;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.Timer; //simple browser-safe timer

public class WordFamilyTextBox extends Label {

	private int state;
	private final int UPDATE_STEPS = 4;
	private boolean used;
	
	private com.google.gwt.user.client.Timer t = new Timer() {
		@Override
		public void run() {
			updateStyle();
		}
	};
	
	public WordFamilyTextBox() {
		super();
		this.clear();
	}
	
	public void clear() {		
		this.setUsed(false);
		this.setVisible(false);
		this.setStyleName("wordfamily-textbox wf-zero");
	}

	public void hide() {
		this.t.cancel();
		this.clear();
	}
	
	public void show(String text, int duration) {
		this.setUsed(true);
		this.setText(text);
		this.setVisible(true);
		this.state = UPDATE_STEPS;
		this.t.scheduleRepeating((int)(duration/UPDATE_STEPS));
	}
	
	public void updateStyle() {
		
		if (state == 4) {
			this.setStyleName("wordfamily-textbox wf-zero");
		} else if (state == 3) {
			this.setStyleName("wordfamily-textbox wf-twentyfive");
		} else if (state == 2) {
			this.setStyleName("wordfamily-textbox wf-fifty");
		} else if (state == 1) {
			this.setStyleName("wordfamily-textbox wf-seventyfive");			
		} else {
			this.hide();
		}
		
		state--;
		
	}

	/**
	 * Set used
	 * @param used Set used to this value
	 */
	public void setUsed(boolean used) {
		this.used = used;
	}

	/**
	 * @return Returns used
	 */
	public boolean isUsed() {
		return used;
	}
	
	
}
