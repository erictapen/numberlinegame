// Alexander von Bernuth

// 
package com.wicam.numberlineweb.client.WordStem;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.HTML;

public class Pot extends HTML {

	
	private String stem = "";
	private ArrayList<String> eaten = new ArrayList<String>();
	
	public Pot(int width, int height, String stem) {
		setPixelSize(width, height);
		this.stem = stem;
	}


	/**
	 * Consumes a Label, adding its text to itself
	 * @param s String to eat
	 */
	public void eatWidget(String s) {
		eaten.add(s);
		this.updateText();
	}
	
	
	/**
	 * Update the text shown in here, including all eaten words
	 */
	public void updateText() {
		String eaten = "";
		for (String s : this.eaten) {
			eaten.concat(s+"<br>");
		}
		this.setHTML("<strong>"+this.stem+"</strong><br>");
	}


	/**
	 * @return Returns stem
	 */
	public String getStem() {
		return stem;
	}


	/**
	 * @return Returns eaten
	 */
	public ArrayList<String> getEaten() {
		return eaten;
	}
	
	
	

}
