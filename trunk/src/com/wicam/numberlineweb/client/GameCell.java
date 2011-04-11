package com.wicam.numberlineweb.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * A cell for our open games list
 * @author patrick
 *
 */

public class GameCell extends AbstractCell<GameState> {

	@Override
	public void render(Context context,	GameState game, SafeHtmlBuilder sb) {

		sb.appendHtmlConstant("<div style='padding:4px;font-size:14px'>");
		sb.appendEscaped(game.getName() + "       (" + game.getPlayerCount() + "/" + game.getMaxNumberOfPlayers() + " Spieler");
		sb.appendHtmlConstant("</div>");

	}
}
