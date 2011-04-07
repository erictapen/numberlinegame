package com.wicam.numberlineweb.client.NumberLineGame;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.wicam.numberlineweb.client.TextPopupBox;

public class GameCreatePopupBox extends TextPopupBox {

	
	ListBox playerCount = new ListBox();
	ListBox roundCount = new ListBox();
	
	public GameCreatePopupBox (String msg, String def) {
		
		super(msg,def);
		
		playerCount.addItem("2");
		playerCount.addItem("3");
		playerCount.addItem("4");
		playerCount.addItem("5");
		
		roundCount.addItem("5");
		roundCount.addItem("10");
		roundCount.addItem("15");
		roundCount.addItem("20");
		roundCount.addItem("30");
		
		HorizontalPanel f = new HorizontalPanel();
		f.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		f.setSpacing(5);
		f.add(new HTML("Anzahl Spieler: "));
		f.add(playerCount);
		
		super.p.add(f);
		
		HorizontalPanel g = new HorizontalPanel();
		g.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		g.setSpacing(5);
		g.add(new HTML("Anzahl Runden: "));
		g.add(roundCount);
		
		super.p.add(g);
	}
	
	public int getRoundCount() {
		
		return Integer.parseInt(roundCount.getItemText(roundCount.getSelectedIndex()));
		
		
	}
	
	public int getPlayerCount() {
		
		return Integer.parseInt(playerCount.getItemText(playerCount.getSelectedIndex()));
		
		
	}
	
	
}
