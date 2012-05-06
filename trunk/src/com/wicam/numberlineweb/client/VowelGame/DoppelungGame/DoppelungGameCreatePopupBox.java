package com.wicam.numberlineweb.client.VowelGame.DoppelungGame;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.wicam.numberlineweb.client.GameCreatePopupBox;

public class DoppelungGameCreatePopupBox extends GameCreatePopupBox {

	
	ListBox roundCount = new ListBox();
	
	public DoppelungGameCreatePopupBox (String msg, String def) {
		
		super(msg,def);
		
		roundCount.addItem("3");
		roundCount.addItem("5");
		roundCount.addItem("7");
		roundCount.addItem("10");
		roundCount.addItem("15");
		
		
		HorizontalPanel h = new HorizontalPanel();
		h.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		h.setSpacing(5);
		h.add(new HTML("Anzahl Runden: "));
		h.add(roundCount);
		
		super.p.add(h);
		
	}
	
	
	
	public int getRoundCount() {
		
		return Integer.parseInt(roundCount.getItemText(roundCount.getSelectedIndex()));
		
	}
	
}
