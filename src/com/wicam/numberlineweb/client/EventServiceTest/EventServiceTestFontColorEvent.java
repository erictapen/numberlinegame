package com.wicam.numberlineweb.client.EventServiceTest;

import de.novanic.eventservice.client.event.Event;

public class EventServiceTestFontColorEvent implements Event {

	private static final long serialVersionUID = -4904847822914129800L;
	private String fontColor;
	
	/**
	 * @return the fontColor
	 */
	public String getFontColor() {
		return fontColor;
	}
	/**
	 * @param fontColor the fontColor to set
	 */
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

}
