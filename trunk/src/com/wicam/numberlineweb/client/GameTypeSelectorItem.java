package com.wicam.numberlineweb.client;


import com.google.gwt.user.client.ui.HTML;

/**
 * An item for the GameTypeSelector
 * @author patrick
 *
 */

public class GameTypeSelectorItem extends HTML{

	private String img = "";
	private String desc = "";
	private String title = "";


	private void builtHTML() {
		
		if (img.equals("")) img = "Fragezeichen.gif";
		
		super.setHTML("<div class='gametype'>" +
				"<img alt='"+ desc + "' height='130' width='130' src='" + img + "'>" + 
				"<div>" + title + "<div>"+
		"</div>");

	}


	public void setImage(String img) {

		this.img=img;
		builtHTML();

	}

	public void setDesc(String desc) {

		this.desc=desc;
		builtHTML();
	}

	public void setTitle(String title) {

		this.title=title;
		builtHTML();
	}


}
