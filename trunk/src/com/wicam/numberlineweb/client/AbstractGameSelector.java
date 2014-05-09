package com.wicam.numberlineweb.client;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;

public abstract class AbstractGameSelector extends Composite implements IsSerializable {

	protected final AbsolutePanel motherPanel = new AbsolutePanel();
	protected GameCoordinator coordinator;

	protected Timer t;
	final TextCell textCell = new TextCell();
	protected Button createGameButton;
	protected final SingleSelectionModel<GameState> selectionModel = new SingleSelectionModel<GameState>();
	protected final GameCreatePopupBox gamePopUp = new GameCreatePopupBox("Neues Spiel erstellen", "Mein Spiel");
	protected final int boxWidth = 750;
	protected Button joinGameButton;

	protected final ArrayList<GameState> openGames = new ArrayList<GameState>();

	/**
	 * keyProvider, which simply uses the game id of a game state as key
	 */

	protected ProvidesKey<GameState> keyProvider = new ProvidesKey<GameState>() {
		@Override
		public Object getKey(GameState game) {

			return (game == null) ? null : game.getId();
		}
	};

	protected final CellList<GameState> cellList = new CellList<GameState>(new GameCell(),keyProvider);

	abstract protected void addGameCreationHandler();

	protected GameState getGameById(int id, ArrayList<GameState> games) {

		Iterator<GameState> i = games.iterator();

		while (i.hasNext()) {

			GameState current= i.next();

			if (current.getId() == id) return current;

		}

		return null;

	}
	
	
	public void joinGame() {

		if (this.getSelectedGameId() < 0) return;

		if (NumberLineWeb.USERID == -1) {

			final TextPopupBox b = new TextPopupBox("Bitte gib deinen Namen ein", "Spieler");

			b.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {


					b.hide();
					coordinator.joinGame(AbstractGameSelector.this.getSelectedGameId(), b.getTextValue(),getMaxNumberOfPlayers(),
							getMaxNumberOfNPCs());
					t.cancel();
				}

			});
			b.show();

		}else{

			coordinator.joinGame(AbstractGameSelector.this.getSelectedGameId(), "___ID/" + NumberLineWeb.USERID,getMaxNumberOfPlayers(),
					getMaxNumberOfNPCs());
			t.cancel();

		}

		gamePopUp.hide();

	
	}
	


	public void setGameList(ArrayList<? extends GameState> result) {



		if (result != null) {


			//first, remove closed game



			Iterator<? extends GameState> i = openGames.iterator();



			while (i.hasNext()) {


				GameState g = i.next();

				if (!gameInList(g,result)) i.remove();

			}

			//now add new games...

			i = result.iterator();
			while(i.hasNext()) {

				GameState g = i.next();

				//we dont want to display full or ended games here...
				if ((g.isFree() && g.getState() < 2) ) {

					if (!gameInList(g,openGames)) {
						openGames.add(g);
					}else if (getGameById(g.getId(),openGames).getPlayerCount() != g.getPlayerCount()) {
						openGames.set(openGames.indexOf(getGameById(g.getId(),openGames)), g);
					}
				}
			}

			cellList.setRowData(openGames);


		}



	}

	private boolean gameInList(GameState g, ArrayList<? extends GameState> games) {

		Iterator<? extends GameState> it = games.iterator();


		while (it.hasNext()) {


			if (it.next().getId() == g.getId()) return true;


		}

		return false;

	}

	public int getSelectedGameId() {

		if (selectionModel.getSelectedObject()!=null) {
			return selectionModel.getSelectedObject().getId();
		}else{
			GWT.log("none selected");
			return -1;
		}
	}

	public void clearGameList() {

		openGames.clear();
		cellList.setRowData(openGames);


	}

	public void setSelected (GameState g) {
		selectionModel.setSelected(g, true);

	}

	public void setFocus() {

		cellList.setFocus(true);

	}


	protected int getMaxNumberOfPlayers() {
		return selectionModel.getSelectedObject().getMaxNumberOfPlayers();
	}

	protected int getMaxNumberOfNPCs() {
		return selectionModel.getSelectedObject().getNumberOfMaxNPCs();
	}
}
