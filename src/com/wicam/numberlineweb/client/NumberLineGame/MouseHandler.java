package com.wicam.numberlineweb.client.NumberLineGame;

import com.google.gwt.user.client.ui.Widget;

public interface MouseHandler {
	
	public void onMouseDown(Widget who,int x, int y);
	public void onMouseUp(Widget who,int x, int y);
	public void onMouseMove(Widget who,int x, int y);

}
