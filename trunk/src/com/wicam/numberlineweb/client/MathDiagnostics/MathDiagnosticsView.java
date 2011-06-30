package com.wicam.numberlineweb.client.MathDiagnostics;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.wicam.numberlineweb.client.GameController;
import com.wicam.numberlineweb.client.GameView;

public abstract class MathDiagnosticsView extends GameView implements MathDiagnosticsPresentation{

	private final HorizontalPanel motherPanel = new HorizontalPanel();
	protected final AbsolutePanel gamePanel = new AbsolutePanel();
	protected final HTML explanationText = new HTML();
	private final HTML resultScreen = new HTML();
	private final Button startGameButton = new Button("Spiel Starten");
	final FlexTable playerNamesFlexTable = new FlexTable();
	protected boolean hasKeyboard = false;
	
	private final HTML canvas = new HTML("<div id='canvas' style='width:750px;height:400px;'></div>");
	
	protected MathDiagnosticsView(int numberOfPlayers,
			GameController gameController) {
		super(numberOfPlayers, gameController);
		this.gameController = gameController;
		init();
		this.initWidget(motherPanel);
	}
	
	public void initKeyboardDependentElements(boolean hasKeyboard){
		this.hasKeyboard = hasKeyboard;
		setExplanationText();
	}

	protected void init() {

		final MathDiagnosticsController mathDiagnosticsController = (MathDiagnosticsController) gameController;
		//draw everthing
		gamePanel.getElement().getStyle().setPosition(Position.RELATIVE);
		startGameButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mathDiagnosticsController.onStartButtonClick();
			}
		});
			
		gamePanel.add(startGameButton);
		gamePanel.setWidgetPosition(startGameButton, 630, 350);
		gamePanel.add(explanationText);
		gamePanel.setWidgetPosition(explanationText, 0, 0);
		gamePanel.add(canvas);
		
		motherPanel.add(gamePanel);
	}
	
	@Override
	abstract public void setExplanationText();
	
	@Override
	abstract public void showItem (isItem item);
	
	@Override
	public void clearGamePanel(){
		gamePanel.clear();
		gamePanel.add(this.canvas);
	}
	
	@Override
	public void showCalculatingResult(){
		resultScreen.setHTML("<span style='font-size:30px'>Berechne Ergebnis...</span>");
		gamePanel.add(resultScreen);
		gamePanel.setWidgetPosition(resultScreen, 210, 175);
	}
}
