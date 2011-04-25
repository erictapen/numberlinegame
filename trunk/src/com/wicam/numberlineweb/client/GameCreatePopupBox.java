package com.wicam.numberlineweb.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.wicam.numberlineweb.client.TextPopupBox;

public class GameCreatePopupBox extends TextPopupBox {

	
	protected ListBox playerCount = new ListBox();
	
	public GameCreatePopupBox (String msg, String def) {
		
		super(msg,def);
		
		playerCount.addItem("1");
		playerCount.addItem("2");
		
		HorizontalPanel f = new HorizontalPanel();
		f.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		f.setSpacing(5);
		f.add(new HTML("Anzahl Spieler: "));
		f.add(playerCount);
		
		super.p.add(f);
	}
	
	public int getPlayerCount() {
		
		return Integer.parseInt(playerCount.getItemText(playerCount.getSelectedIndex()));
		
		
	}
}
