package com.wicam.numberlineweb.client.DoppelungGame;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.wicam.numberlineweb.client.GameView;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.FocusPanel;


public class DoppelungGameView extends GameView {

	private DoppelungGameController doppelungGameController;
	private final HorizontalPanel motherPanel = new HorizontalPanel();
	private final AbsolutePanel gamePanel = new AbsolutePanel();
	private final AbsolutePanel pointsPanel = new AbsolutePanel();

	private final HTML explanationText = new HTML();
	private final HTML feedBackText = new HTML();
	private final HTML wordText = new HTML(); // TODO: only for test implementation without sound
	private final HTML pointsText = new HTML(); // TODO: only test implementation
	private final HTML canvas = new HTML("<div id='canvas' style='width:600px;height:400px;border-right:solid #333 1px'></div>");
	private final HTML canvasScore = new HTML("<div id='canvas' style='width:150px;height:400px;'></div>");
	private final HTML pointsBarBorder = new HTML("<div id='canvas' style='width:25px;height:280px;border:solid #333 1px'></div>");
	private final HTML pointsBar2Border = new HTML("<div id='canvas' style='width:25px;height:280px;border:solid #333 1px'></div>");
	private final HTML pointsBar = new HTML("<div id='canvas' style='width:25px;height:0px;background-color:" +  this.playerColors[0] + "'></div>");
	private final HTML pointsBar2 = new HTML("<div id='canvas' style='width:25px;height:0px;background-color:" +  this.playerColors[0] + "'></div>");
	ArrayList<HTML> playerNames = new ArrayList<HTML>();

	protected final Button startGameButton = new Button("Spiel Starten");
	protected final ShortVowelImage shortVowelImage = new ShortVowelImage("numberlineweb/doppelungGame/knall_small.jpg", 270, 330);
	protected final Image longVowelImage = new Image("numberlineweb/doppelungGame/ziehen1.jpg");
	private final FocusPanel focusPanel = new FocusPanel();
	private final HTML textBoxLabel = new HTML("<div style='font-size:18px'>Gib das zuletzt gehörte Wort ein!</div>");
	private final TextBox textBox = new TextBox();
	private AnimationTimer aniTimer = new AnimationTimer();

	private ArrayList<MovingConsonants> movingConsonantsList = new ArrayList<MovingConsonants>();

	public DoppelungGameView(int numberOfPlayers, DoppelungGameController doppelungGameController) {
		super(numberOfPlayers);
		this.doppelungGameController = doppelungGameController;
		init();
		sinkEvents(Event.MOUSEEVENTS);
		this.initWidget(motherPanel);
	}


	private void init() {

		//draw everthing
		gamePanel.getElement().getStyle().setPosition(Position.RELATIVE);

		explanationText.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>Erklärung</div>");
		startGameButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doppelungGameController.onStartButtonClick();
			}
		});

		shortVowelImage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doppelungGameController.onShortVowelButtonClick();
			}
		});

		longVowelImage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doppelungGameController.onLongVowelButtonClick();
			}
		});

		gamePanel.add(startGameButton);
		gamePanel.setWidgetPosition(startGameButton, 480, 350);
		gamePanel.add(explanationText);
		gamePanel.setWidgetPosition(explanationText, 0, 0);
		gamePanel.add(canvas);
		focusPanel.setSize("600px", "400px");

		textBox.setMaxLength(10);
		textBox.addKeyPressHandler(new KeyPressHandler(){

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER){
					String enteredWord = textBox.getText().trim();
					if (!enteredWord.equals("")){
						gamePanel.remove(textBoxLabel);
						gamePanel.remove(textBox);
						doppelungGameController.wordEntered(enteredWord);
					}
				}
			}

		});

		pointsPanel.add(canvasScore);

		int top = 60;
		pointsPanel.add(pointsBarBorder, 64, top);
		pointsPanel.add(pointsBar, 65, top);
		final HTML htmlSpieler1 = new HTML();
		playerNames.add(htmlSpieler1);
		pointsPanel.add(htmlSpieler1, 55, 355);

		if (numberOfPlayers == 2){
			// TODO: positioning
			pointsPanel.add(pointsBar2Border, 100, top);
			pointsPanel.add(pointsBar2, 101, top);
			pointsBar2Border.setSize("25", "330");
			final HTML htmlSpieler2 = new HTML();
			playerNames.add(htmlSpieler2);
			pointsPanel.add(htmlSpieler2, 89, 355);
		}

		final HTML htmlPunkte = new HTML("<div style='font-size:14px'>Punkte</div>");
		pointsPanel.add(htmlPunkte, 58, 21);

		focusPanel.addKeyDownHandler(doppelungGameController);

		focusPanel.addKeyUpHandler(doppelungGameController);

		motherPanel.add(gamePanel);
		motherPanel.add(pointsPanel);
	}


	public void registerAniTask(AnimationTimerTask t) {
		aniTimer.registerTask(t);
	}

	public void showPlayerName(int playerid, String name){
		playerNames.get(playerid-1).setHTML("<div style='font-size:14px;color:" + playerColors[playerid-1] + "'>" + name + "</div>");
	}

	public void showVowelChoice(String word){
		gamePanel.clear();
		gamePanel.add(canvas);
		wordText.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>" + word + "</div>");
		gamePanel.add(wordText);
		gamePanel.setWidgetPosition(wordText, 230, 33);
		gamePanel.add(shortVowelImage);
		gamePanel.setWidgetPosition(shortVowelImage, 125, 187);
		gamePanel.add(longVowelImage);
		gamePanel.setWidgetPosition(longVowelImage, 395, 187);
	}

	// TODO: real implementation
	public void showFeedback(boolean correctAnswer){
		gamePanel.clear();
		gamePanel.add(canvas);
		if (correctAnswer)
			feedBackText.setHTML("<div style='font-size:25px'>Richtig!</div>");
		else
			feedBackText.setHTML("<div style='font-size:25px'>Falsch!</div>");
		gamePanel.add(feedBackText);
		gamePanel.setWidgetPosition(feedBackText, 250, 175);
	}

	public void startShortVowelGame(DoppelungGameWord word){
		gamePanel.clear();
		textBox.setText("");
		gamePanel.add(shortVowelImage);
		initializeMovingConsonantList(word);
		gamePanel.add(canvas);
		gamePanel.add(focusPanel, 0, 0); // additionally the focus panel makes the short vowel image unclickable
		shortVowelImage.setX(270);
		shortVowelImage.setY(330);
		gamePanel.setWidgetPosition(shortVowelImage, 270, 330);
		focusPanel.setFocus(true);
	}

	// TODO: real implementation
	public void showEndScreen(int points){
		gamePanel.clear();
		pointsText.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>Punkte: " + points + "</div>");
		gamePanel.add(pointsText);
	}

	// TODO: max points?
	public void actualizePointsBar(int playerid, int points){
		if (playerid == 1){
			this.pointsBar.setHTML("<div id='canvas' style='width:25px;height:" + points + "px;background-color:" +  this.playerColors[0] + "'></div>");
			pointsPanel.setWidgetPosition(pointsBar, 65, 60+pointsBarBorder.getOffsetHeight()-1-points);
		}

		if (playerid == 2){
			// TODO: positioning
			this.pointsBar2.setHTML("<div id='canvas' style='width:25px;height:" + points + "px;background-color:" +  this.playerColors[1] + "'></div>");
			pointsPanel.setWidgetPosition(pointsBar2, 26, 60+pointsBar2Border.getOffsetHeight()-1-points);
		}
	}


	public void moveStepLeft() {

		if (shortVowelImage.getX()-20 > 0) {
			shortVowelImage.setX(shortVowelImage.getX()-20);
		}else{
			shortVowelImage.setX(0);
		}

		gamePanel.setWidgetPosition(shortVowelImage, shortVowelImage.getX(), shortVowelImage.getY());


	}

	public void moveStepUp() {

		if (shortVowelImage.getY()-20 > 0)
			shortVowelImage.setY(shortVowelImage.getY()-20);
		else
			shortVowelImage.setY(0);

		gamePanel.setWidgetPosition(shortVowelImage, shortVowelImage.getX(), shortVowelImage.getY());


	}

	public void moveStepRight() {

		int imgWidth = shortVowelImage.getOffsetWidth();

		if (shortVowelImage.getX()+20+imgWidth < canvas.getOffsetWidth()-1)
			shortVowelImage.setX(shortVowelImage.getX()+20);
		else
			shortVowelImage.setX(canvas.getOffsetWidth() - 1 - imgWidth);

		gamePanel.setWidgetPosition(shortVowelImage, shortVowelImage.getX(), shortVowelImage.getY());


	}

	public void moveStepDown() {
		int imgHeight = shortVowelImage.getOffsetHeight();

		if (shortVowelImage.getY()+20+imgHeight < canvas.getOffsetHeight())
			shortVowelImage.setY(shortVowelImage.getY()+20);
		else
			shortVowelImage.setY(canvas.getOffsetHeight() - imgHeight);

		gamePanel.setWidgetPosition(shortVowelImage, shortVowelImage.getX(), shortVowelImage.getY());

	}

	/**
	 * TODO: this has to be done in the controller
	 * @param word
	 */

	private void initializeMovingConsonantList(DoppelungGameWord word){
		ArrayList<String> consonantPairList = DoppelungGameConsonantPairListCreater.createConsonantPairList(word.getConsonantPair(),5,10);
		int i = 0;
		for (String consonantPair: consonantPairList){
			MovingConsonants mc = new MovingConsonants(consonantPair, this, 50+i%9*50, -50);
			gamePanel.add(mc);
			gamePanel.setWidgetPosition(mc, 50+i%9*50, -50);
			mc.startMoving(i*2000);
			mc.setSpeed(3);
			this.movingConsonantsList.add(mc);
			i++;
		}
	}

	public void setMovingConsonantsPosition(MovingConsonants mc, int x, int y){
		if (y < gamePanel.getOffsetHeight()){
			gamePanel.setWidgetPosition(mc, x, y);
		}
		else{
			mc.setRemoved(true);
			removeMovingConsonants(mc);
		}
	}

	public void removeMovingConsonants(MovingConsonants mc){
		gamePanel.remove(mc);
		movingConsonantsList.remove(mc);
		if (movingConsonantsList.isEmpty()){
			gamePanel.remove(shortVowelImage);
			gamePanel.remove(this.focusPanel);
			gamePanel.add(textBoxLabel);
			gamePanel.setWidgetPosition(textBoxLabel, 170, 150);
			gamePanel.add(this.textBox);
			gamePanel.setWidgetPosition(textBox, 230, 190);
			textBox.setFocus(true);
			//doppelungGameController.endShortVowelGame();
		}
	}

	/**
	 * TODO: this has to be done in the controller
	 * @param mc
	 */
	public void checkForCollision(MovingConsonants mc){
		int imgWidth = shortVowelImage.getOffsetWidth();
		int imgHeight = this.shortVowelImage.getOffsetHeight();

		int mcWidth = mc.getOffsetWidth();
		int mcHeight = mc.getOffsetHeight();

		int posXDiff = Math.abs(shortVowelImage.getX() - mc.getX());
		int posYDiff = Math.abs(shortVowelImage.getY() - mc.getY());

		if (posXDiff < imgWidth/2+mcWidth/2 && posYDiff < imgHeight/2+mcHeight/2){
			doppelungGameController.updatePoints(mc.getConsonants());
			mc.setRemoved(true);
			removeMovingConsonants(mc);
		}
	}
}
