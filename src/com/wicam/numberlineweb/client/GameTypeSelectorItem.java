package com.wicam.numberlineweb.client;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.wicam.numberlineweb.client.Resources.ImageResources;

/**
 * An item for the GameTypeSelector
 * @author patrick
 *
 */

public class GameTypeSelectorItem extends HTML{

	private String img = "";
	private String desc = "";
	private String title = "";
	
	private GameItemStarter s;


	private void builtHTML() {
		
		if (img.equals("")) img = ImageResources.INSTANCE.fragezeichen().getSafeUri().asString();
		
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

	@Override
	public void setTitle(String title) {

		this.title=title;
		builtHTML();
	}
	
	@Override
	public String getTitle() {

		return title;
	}
	
	
	public void setStarter(final GameItemStarter s) {
		
		this.s=s;
		
		this.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				start();
				
			}
			
		});
		
		
	}
	
	public void start() {
		
		s.run();
		
		
	}


}
