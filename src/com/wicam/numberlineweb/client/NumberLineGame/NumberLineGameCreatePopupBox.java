package com.wicam.numberlineweb.client.NumberLineGame;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;

public class NumberLineGameCreatePopupBox extends com.wicam.numberlineweb.client.GameCreatePopupBox {

	
	ListBox roundCount = new ListBox();
	ListBox npcsCount = new ListBox();
	ListBox possibleRanges = new ListBox();
	
	public NumberLineGameCreatePopupBox (String msg, String def) {
		
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
		
		for (int i = 1; i <= 4; i++)
			npcsCount.addItem(String.valueOf(i));
		
		playerCount.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				npcsCount.clear();
				int numberOfPlayers = getPlayerCount();
				int startItem = 0;
				if (numberOfPlayers == 1)
					startItem = 1;
				npcsCount.addItem(String.valueOf(startItem));
				for (int i = startItem+1; i <= 5-numberOfPlayers; i++)
					npcsCount.addItem(String.valueOf(i));
			}
		});
		
		possibleRanges.addItem("0 bis 100");
		possibleRanges.addItem("0 bis 1000");
		possibleRanges.addItem("zweistellig");
		possibleRanges.addItem("dreistellig");
		
		HorizontalPanel f = new HorizontalPanel();
		f.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		f.setSpacing(5);
		f.add(new HTML("Anzahl Spieler: "));
		f.add(playerCount);
		
		super.p.add(f);
		
		HorizontalPanel g = new HorizontalPanel();
		g.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		g.setSpacing(5);
		g.add(new HTML("Anzahl NPC: "));
		g.add(npcsCount);
		
		super.p.add(g);
		
		HorizontalPanel h = new HorizontalPanel();
		h.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		h.setSpacing(5);
		h.add(new HTML("Anzahl Runden: "));
		h.add(roundCount);
		
		super.p.add(h);
		
		HorizontalPanel i = new HorizontalPanel();
		i.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		i.setSpacing(5);
		i.add(new HTML("Zahlenbereich: "));
		i.add(possibleRanges);
		
		super.p.add(i);
	}
	
	public int getRoundCount() {
		
		return Integer.parseInt(roundCount.getItemText(roundCount.getSelectedIndex()));
		
		
	}
	
	public int getNPCsCount() {
		
		return Integer.parseInt(npcsCount.getItemText(npcsCount.getSelectedIndex()));
		
		
	}
	
	public NumberRange getNumberRange() {
		NumberRange nr = new NumberRange();
		
		if (possibleRanges.getSelectedIndex()== 0)
			nr.setRange(0, 100, false);
		
		if (possibleRanges.getSelectedIndex()== 1)
			nr.setRange(0, 1000, false);
		
		if (possibleRanges.getSelectedIndex()== 2)
			nr.setRange(10, 99, true);
	
		if (possibleRanges.getSelectedIndex()== 3)
			nr.setRange(100, 999, true);
			
		return nr;
	}
	
}
