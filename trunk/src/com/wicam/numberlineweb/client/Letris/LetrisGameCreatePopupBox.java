package com.wicam.numberlineweb.client.Letris;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.wicam.numberlineweb.client.GameCreatePopupBox;

/**
 * Set up a popup-box for the player to choose how many human players (1-2) and how many
 * npcs (0-1) there should be in the game. Restrict the choice to a maximum of two players
 * (one or two of them human). 
 * @author timfissler
 *
 */

public class LetrisGameCreatePopupBox extends GameCreatePopupBox {
	
	ListBox npcsCount = new ListBox();
	
	public LetrisGameCreatePopupBox (String msg, String def) {
		
		super(msg,def);
		
		npcsCount.addItem("0");
		npcsCount.addItem("1");
		
		playerCount.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				npcsCount.clear();
				int numberOfPlayers = getPlayerCount();
				if (numberOfPlayers == 1) {
					npcsCount.addItem("0");
					npcsCount.addItem("1");
				} else if (numberOfPlayers == 2) {
					npcsCount.addItem("0");
				}
			}
		});
		
		HorizontalPanel g = new HorizontalPanel();
		g.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		g.setSpacing(5);
		g.add(new HTML("Anzahl NPC: "));
		g.add(npcsCount);
		
		super.p.add(g);
		
	}
	
	public int getNPCsCount() {
		return Integer.parseInt(npcsCount.getItemText(npcsCount.getSelectedIndex()));	
	}
	
}
