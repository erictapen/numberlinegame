package com.wicam.numberlineweb.client;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.wicam.numberlineweb.client.Resources.ImageResources;

/**
 * An extendible list-view for game types
 * @author patrick
 *
 */

public class GameTypeSelector extends Composite implements ValueChangeHandler<String>, IsSerializable {


	protected FlowPanel p = new FlowPanel();
	
	public static enum GameType {CAT, MATH, GRAMMAR};
	
	private ArrayList<GameTypeSelectorItem> cats = new ArrayList<GameTypeSelectorItem>();
	private ArrayList<GameTypeSelectorItem> games = new ArrayList<GameTypeSelectorItem>();
	private ArrayList<GameTypeSelectorItem> mathGames = new ArrayList<GameTypeSelectorItem>();
	private ArrayList<GameTypeSelectorItem> grammarGames = new ArrayList<GameTypeSelectorItem>();
	
	GameTypeSelectorItem backButton = new GameTypeSelectorItem();
	
	protected HandlerRegistration handlerReg;

	public GameTypeSelector() {

		resetContent();
		p.add(new GameTypeSelectorItem());
		p.setStyleName("gametypeselector");

		backButton.setDesc("Zurück");
		backButton.setTitle("Zurück");
		backButton.setStarter(new GameItemStarter() {
			@Override
			public void run() {
				showCats();
			}
		});
		backButton.setImage(ImageResources.INSTANCE.pre_backButton().getSafeUri().asString());

		RootPanel.get().add(p);
		this.initWidget(p);

	}

	/**
	 * Adds the header to this panel
	 */
	private void resetContent() {
		p.clear();
		p.add(new HTML("<div class='selectorTitle'>Wähle ein Spiel</div>"));
	}

	/**
	 * Initializes the selector on the given panel.
	 * @param panel
	 */

	public void init(Panel panel) {
		
		// TODO Check if that works.
//		this.handlerReg = History.addValueChangeHandler(this);
		
		History.newItem("");
		
		panel.add(this);
	}
	
	/**
	 * Action in case of history back event.
	 * @param event
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		GWT.log("onValueChange fired in " + this.getClass());
		GWT.log("Histroy token: " + event.getValue());
		if (event.getValue().matches("gameSelector.*")) {
			
			// TODO Check if the value change handler has to be removed. 

			String gameName = event.getValue().split("-")[1];

			getGameSelectorItemByGameName(gameName).start();

		}			
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
	 * @param cat cat or (math or grammar)
	 * @param title
	 * @param img
	 * @param desc
	 * @param starter
	 */

	public void addGame(GameType cat, String title, String img, String desc, GameItemStarter starter) {

		GameTypeSelectorItem s = new GameTypeSelectorItem();
		s.setStarter(starter);
		s.setTitle(title);
		s.setImage(img);
		s.setDesc(desc);

		if (cat == GameType.CAT) {
			cats.add(s);
			p.add(s);
		} else if (cat == GameType.MATH) {
			mathGames.add(s);
			games.add(s);
		} else {
			games.add(s);
			grammarGames.add(s);
		}
		

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

	
	/**
	 * Show all categories 
	 */
	public void showCats() {
		resetContent();
		for (GameTypeSelectorItem s : this.cats) {
			p.add(s);
		}
	}
	
	/**
	 * Show all math games
	 */
	public void showMath() {
		resetContent();
		p.add(backButton);
		for (GameTypeSelectorItem s : this.mathGames) {
			p.add(s);
		}
	}

	/**
	 * show all grammar games
	 */
	public void showGrammar() {
		resetContent();
		p.add(backButton);
		for (GameTypeSelectorItem s : this.grammarGames) {
			p.add(s);
		}
	}


}
