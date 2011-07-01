package com.wicam.numberlineweb.client.VowelGame;

import com.google.gwt.user.client.ui.Image;

public class ShortVowelImage extends Image {
	
	private int x;
	private int y;
	
	public ShortVowelImage(String string, int x, int y) {
		super(string);
		this.setX(x);
		this.setY(y);
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
}
