package com.wicam.numberlineweb.client;

import com.google.gwt.event.dom.client.ClickHandler;
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

	public void addGame(String title, String img, String desc, ClickHandler clickHandler) {

		GameTypeSelectorItem s = new GameTypeSelectorItem();
		s.addClickHandler(clickHandler);
		s.setTitle(title);
		s.setImage(img);
		s.setDesc(desc);

		p.add(s);

	}


}
