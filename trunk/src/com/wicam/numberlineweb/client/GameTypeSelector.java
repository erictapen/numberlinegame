package com.wicam.numberlineweb.client;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * An extendible list-view for game types
 * @author patrick
 *
 */

public class GameTypeSelector extends Composite{


	protected FlowPanel p = new FlowPanel();
	private ArrayList<GameTypeSelectorItem> games = new ArrayList<GameTypeSelectorItem>();

	@SuppressWarnings("deprecation")
	public GameTypeSelector() {

		p.add(new HTML("<div class='selectorTitle'>Wähle ein Spiel</div>"));
		p.add(new GameTypeSelectorItem());
		p.setStyleName("gametypeselector");


	

		RootPanel.get().add(p);
		this.initWidget(p);

	}

	/**
	 * Initializes the selector on the given panel.
	 * @param panel
	 */

	public void init(Panel panel) {
		
		
		
		HistoryChangeHandler.setHistoryListener(new HistoryListener() {

			@Override
			public void onHistoryChanged(String historyToken) {
				if (historyToken.matches("gameSelector.*")) {

					String gameName = historyToken.split("-")[1];

					
					getGameSelectorItemByGameName(gameName).start();

				}

			}

		});
		
		History.newItem("");
		
		panel.add(this);
	}

	/**
	 * Hides the selector from the given panel.
	 * @param panel
	 */

	public void hide(Panel panel) {

	
		panel.remove(this);

	}

	/**
	 * Adds a gametype to the selector.
	 * @param title
	 * @param img
	 * @param desc
	 * @param clickHandler
	 */

	public void addGame(String title, String img, String desc, GameItemStarter starter) {

		GameTypeSelectorItem s = new GameTypeSelectorItem();
		s.setStarter(starter);
		s.setTitle(title);
		s.setImage(img);
		s.setDesc(desc);

		p.add(s);
		games.add(s);

	}


	public GameTypeSelectorItem getGameSelectorItemByGameName(String gameName) {

		Iterator<GameTypeSelectorItem> it = games.iterator();


		while (it.hasNext()) {

			GameTypeSelectorItem current = it.next();
			GWT.log(current.getTitle());
			if (current.getTitle().equals(gameName)) return current;

		}

		return null;
	}


}
