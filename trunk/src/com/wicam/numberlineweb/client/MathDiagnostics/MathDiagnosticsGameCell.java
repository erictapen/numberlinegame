package com.wicam.numberlineweb.client.MathDiagnostics;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.wicam.numberlineweb.client.GameCell;
import com.wicam.numberlineweb.client.GameState;

public class MathDiagnosticsGameCell extends GameCell {

	@Override
	public void render(Context context,	GameState game, SafeHtmlBuilder sb) {

		String task = ItemTypes.getTypeString(((MathDiagnosticsGameState)game).getTask());
		sb.appendHtmlConstant("<div style='padding:4px;font-size:14px'>");
		sb.appendEscaped(game.getName() + "       (" + game.getPlayerCount() + "/" + game.getMaxNumberOfPlayers() + " Spieler, " + task);
		sb.appendHtmlConstant("</div>");

	}
}
