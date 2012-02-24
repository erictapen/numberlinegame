package com.wicam.numberlineweb.client.Multiplication;

import com.google.gwt.user.client.ui.HTML;

public class MultiplicationPointer extends HTML{


	int width;
	int height;
	String color;
	
	
	public MultiplicationPointer(int width,String colorString) {

		this.color = colorString;
		this.width = width;
		this.height = 30;
		reRender();


	}

	public MultiplicationPointer(int width, int height, String colorString) {

		this.color = colorString;
		this.width = width;
		this.height = height;
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
	
	public void setHeight(int height) {

		this.height = height;
		reRender();

	}
	
	private void reRender() {
		
		this.setHTML("<div style='width:" + width + "px;height:" + height + "px;background-color:" + color + "'></div>");

		
	}


}
