package com.wicam.numberlineweb.client.NumberLineGame;

import com.google.gwt.user.client.ui.HTML;

public class NumberLineGamePointer extends HTML{


	int width;
	String color;
	
	
	public NumberLineGamePointer(int width,String colorString) {

		this.color = colorString;
		this.width = width;
		reRender();


	}


	public void setColor(String colorString) {

		this.color = colorString;
		reRender();

	}
	
	public void setWidth(int width) {

		this.width = width;
		reRender();

	}
	
	private void reRender() {
		
		this.setHTML("<div style='width:" + width + "px;height:30px;background-color:" + color + "'></div>");

		
	}


}
