package com.wicam.numberlineweb.client.OverTen;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.wicam.numberlineweb.client.GameCell;
import com.wicam.numberlineweb.client.GameState;

public class OverTenGameCell extends GameCell {

	@Override
	public void render(Context context,	GameState game, SafeHtmlBuilder sb) {

		sb.appendHtmlConstant("<div style='padding:4px;font-size:14px'>");
		sb.appendEscaped(game.getName() + "       (" + game.getPlayerCount() + "/" + game.getMaxNumberOfPlayers() + " Spieler, " + ((OverTenGameState) game).getRound() + " Runden)");
		sb.appendHtmlConstant("</div>");

	}
}
