package com.wicam.numberlineweb.client.NumberLineGame;


import java.util.ArrayList;
import java.util.Iterator;


import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.wicam.numberlineweb.client.TextPopupBox;



/**
 * A game browser, which can later be used for other games as well.
 * @author patrick
 *
 */

public class NumberLineGameSelector extends Composite  {

	final AbsolutePanel motherPanel = new AbsolutePanel();
	final TextCell textCell = new TextCell();
	private Button createGameButton;
	private Button refreshButton;
	final SingleSelectionModel<NumberLineGameState> selectionModel = new SingleSelectionModel<NumberLineGameState>();
	final GameCreatePopupBox gamePopUp = new GameCreatePopupBox("Neues Spiel erstellen", "Mein Spiel");
	private NumberLineGameCoordinator coordinator;
	private Timer t;
	private final int boxWidth = 750;
	private Button joinGameButton;

	/**
	 * A cell for our open games list
	 * @author patrick
	 *
	 */
	private static class GameCell extends AbstractCell<NumberLineGameState> {

		@Override
		public void render(Context context,	NumberLineGameState game, SafeHtmlBuilder sb) {

			sb.appendHtmlConstant("<div style='padding:4px;font-size:14px'>");
			sb.appendEscaped(game.getName() + "       (" + game.getPlayerCount() + "/" + game.getMaxNumberOfPlayers() + " Spieler, " + game.getMaxItems() + " Runden)");
			sb.appendHtmlConstant("</div>");

		}
	}

	/**
	 * keyProvider, which simply uses the game id of a game state as key
	 */

	ProvidesKey<NumberLineGameState> keyProvider = new ProvidesKey<NumberLineGameState>() {
		public Object getKey(NumberLineGameState game) {

			return (game == null) ? null : game.getId();
		}
	};

	final CellList<NumberLineGameState> cellList = new CellList<NumberLineGameState>(new GameCell(),keyProvider);

	final ArrayList<NumberLineGameState> openGames = new ArrayList<NumberLineGameState>();


	public NumberLineGameSelector(NumberLineGameCoordinator coordinator) {

		init();

		this.coordinator = coordinator;
		sinkEvents(Event.MOUSEEVENTS);
		this.initWidget(motherPanel);

	}


	private void init() {

		RootPanel.get().add(motherPanel);

		joinGameButton = new Button("Mitspielen");

		joinGameButton.setEnabled(false);

		createGameButton = new Button("Neues Spiel");

		
		cellList.setSelectionModel(selectionModel);
		cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler(){

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				// TODO Auto-generated method stub
				if (selectionModel.getSelectedObject() != null) {
					
					joinGameButton.setEnabled(true);
					
				}else{
					joinGameButton.setEnabled(false);
				}
			}
			
			
		});

		ScrollPanel s = new ScrollPanel(cellList);

		s.setWidth("400px");
		s.setHeight("300px");


	
		joinGameButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				joinGame();

			}
		});



		createGameButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				gamePopUp.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						gamePopUp.setEnabled(false);
						coordinator.openGame(gamePopUp.getTextValue(), gamePopUp.getPlayerCount(), 
												gamePopUp.getNPCsCount(), gamePopUp.getRoundCount(),
												gamePopUp.getNumberRange());
					}

				});

				gamePopUp.show();

			}
		});


		motherPanel.setWidth("600px");
		motherPanel.setHeight("400px");
		motherPanel.add(s);

		HTML title = new HTML();

		title.setHTML("<div id='selectorTitle'>Offene Spiele");

		motherPanel.setWidgetPosition(s, 25, 70);

		motherPanel.add(title);
		motherPanel.setWidgetPosition(title, 15, 20);


		createGameButton.setHeight("30px");
		createGameButton.setWidth("220px");


		joinGameButton.setHeight("30px");
		joinGameButton.setWidth("220px");



		motherPanel.add(createGameButton);

		motherPanel.add(joinGameButton);
		//motherPanel.add(refreshButton);

		motherPanel.setWidgetPosition(joinGameButton, boxWidth-250, 30);
		//motherPanel.setWidgetPosition(refreshButton, 15, 340);

		motherPanel.setWidgetPosition(createGameButton, boxWidth-250, 60);


		clearGameList();

		t = new Timer() {

			public void run() {

				coordinator.refreshGameList();
			}
		};

		//main loop-timer
		t.scheduleRepeating(2000);

	}


	public void clearGameList() {

		openGames.clear();
		cellList.setRowData(openGames);


	}



	public void setGameList(ArrayList<NumberLineGameState> games) {



		if (games != null) {


			//first, remove closed game



			Iterator<NumberLineGameState> i = openGames.iterator();



			while (i.hasNext()) {


				NumberLineGameState g = i.next();

				if (!gameInList(g,games)) i.remove();

			}

			//now add new games...

			i = games.iterator();
			while(i.hasNext()) {

				NumberLineGameState g = i.next();

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


	private NumberLineGameState getGameById(int id, ArrayList<NumberLineGameState> games) {

		Iterator<NumberLineGameState> i = games.iterator();

		while (i.hasNext()) {

			NumberLineGameState current= i.next();

			if (current.getId() == id) return current;

		}

		return null;

	}


	private boolean gameInList(NumberLineGameState g, ArrayList<NumberLineGameState> games) {

		Iterator<NumberLineGameState> it = games.iterator();


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


	public void setSelected (NumberLineGameState g) {
		selectionModel.setSelected(g, true);

	}

	public void setFocus() {

		cellList.setFocus(true);

	}

	protected void joinGame() {

		if (this.getSelectedGameId() < 0) return;

		final TextPopupBox b = new TextPopupBox("Bitte gib deinen Namen ein", "Spieler");

		b.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {


				b.hide();
				coordinator.joinGame(NumberLineGameSelector.this.getSelectedGameId(), b.getTextValue(),selectionModel.getSelectedObject().getMaxNumberOfPlayers(),
										selectionModel.getSelectedObject().getNumberOfMaxNPCs());
				t.cancel();
			}

		});

		gamePopUp.hide();
		b.show();
	}


}
