package com.wicam.numberlineweb.client.Letris;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.wicam.numberlineweb.client.GameView;
import com.wicam.numberlineweb.client.KeyboardDummy;
import com.wicam.numberlineweb.client.MobileDeviceChecker;
import com.wicam.numberlineweb.client.VowelGame.MovingConsonants;
import com.wicam.numberlineweb.client.VowelGame.ShortVowelImage;
import com.wicam.numberlineweb.client.VowelGame.DoppelungGame.Resources.DoppelungGameResourcesImages;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.FocusPanel;


public class LetrisGameView extends GameView {

	private final HorizontalPanel motherPanel = new HorizontalPanel();
	protected final AbsolutePanel gamePanel = new AbsolutePanel();
	private final AbsolutePanel pointsPanel = new AbsolutePanel();
	private KeyboardDummy kbd;

	protected final HTML explanationText = new HTML();
	protected final HTML feedBackText = new HTML();
//	protected final HTML feedBackText2 = new HTML();
	private final HTML wordText = new HTML();
	private final HTML canvas = new HTML("<div id='canvas' style='width:600px;height:400px;border-right:solid #333 1px'></div>");
	private final HTML canvasScore = new HTML("<div id='canvas' style='width:150px;height:30px;'></div>");
	private final HTML pointsText = new HTML("<div style='font-size:30px;color:black'>Punkte</div>");
	final FlexTable playerNamesFlexTable = new FlexTable();

	protected final Button startGameButton = new Button("Spiel Starten");
//	protected final ShortVowelImage shortVowelImage = new ShortVowelImage(DoppelungGameResourcesImages.INSTANCE.knall_small().getSafeUri().asString(), 
//            270, 330);
//	protected ShortVowelImage movingShortVowelImage;
//	protected ShortVowelImage enemyMovingShortVowelImage;

//	protected final Image feedbackImage = new Image(DoppelungGameResourcesImages.INSTANCE.beide_daumen());

//	protected final Image longVowelImage = new Image(DoppelungGameResourcesImages.INSTANCE.ziehen1().getSafeUri());
	private final FocusPanel focusPanel = new FocusPanel();
	private final HTML textBoxLabel = new HTML("<div style='font-size:18px'>Gib das zuletzt gehörte Wort ein!</div>");
	private final TextBox textBox = new TextBox();
	
	protected Audio descriptionSound = Audio.createIfSupported();

	public LetrisGameView(int numberOfPlayers, LetrisGameController doppelungGameController) {
		super(numberOfPlayers, doppelungGameController);
		init();
		sinkEvents(Event.MOUSEEVENTS);
		this.initWidget(motherPanel);
	}


	private void init() {
		
//		longVowelImage.addStyleName("vowel_img");
//		shortVowelImage.addStyleName("vowel_img");

		final LetrisGameController letrisGameController = (LetrisGameController) gameController;

		//draw everything
		gamePanel.getElement().getStyle().setPosition(Position.RELATIVE);

		startGameButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				letrisGameController.onStartButtonClick();
				try {
					descriptionSound.pause();
					descriptionSound.setCurrentTime(0);
				} catch (Exception e) {
				}
			}
		});

//		shortVowelImage.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				doppelungGameController.onShortVowelButtonClick();
//			}
//		});

//		longVowelImage.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				doppelungGameController.onLongVowelButtonClick();
//			}
//		});
		
		setExplanationText();
		gamePanel.add(explanationText);
		gamePanel.setWidgetPosition(explanationText, 0, 0);
		gamePanel.add(startGameButton);
		gamePanel.setWidgetPosition(startGameButton, 480, 350);
		gamePanel.add(canvas);
		focusPanel.setSize("600px", "400px");

//		textBox.setMaxLength(25);
//		textBox.addKeyPressHandler(new KeyPressHandler(){
//
//			@Override
//			public void onKeyPress(KeyPressEvent event) {
//				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER){
//					String enteredWord = textBox.getText().trim();
//					if (!enteredWord.equals("")){
//						gamePanel.remove(textBoxLabel);
//						gamePanel.remove(textBox);
//						letrisGameController.wordEntered(enteredWord);
//					}
//				}
//			}
//
//		});

		pointsPanel.add(pointsText);
		pointsPanel.setWidgetPosition(pointsText, 27, 10);
		pointsPanel.add(canvasScore);

		playerNamesFlexTable.setStyleName("playerList");
		playerNamesFlexTable.setCellPadding(5);


		pointsPanel.add(playerNamesFlexTable);

		focusPanel.addKeyDownHandler(letrisGameController);
		focusPanel.addKeyUpHandler(letrisGameController);

		motherPanel.add(gamePanel);
		motherPanel.add(pointsPanel);
		
		if (Audio.isSupported() && descriptionSound != null) {
			
			// TODO Change sound to actual description of the LeTris game.
			descriptionSound.addSource("desc/Doppelung.ogg", "audio/ogg; codecs=vorbis");
			descriptionSound.addSource("desc/Doppelung.mp3", "audio/mpeg; codecs=MP3");
			
			descriptionSound.play();
			
		}
	}
	
	protected void setExplanationText() {
		// TODO Enter description of the game.
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>LeTris - Beschreibung</b></div>" +
				"<div style='padding:5px 20px;font-size:12px'>" +
				"Enter description of the LeTris game here." +
		"</div>");
	}

//	public void initializeMovingShortVowelImages(int playerid){
//		if (playerid == 1){
//			movingShortVowelImage = new ShortVowelImage(DoppelungGameResourcesImages.INSTANCE.roter_knall().getSafeUri().asString(),
//					270, 330);
//			enemyMovingShortVowelImage = new ShortVowelImage(DoppelungGameResourcesImages.INSTANCE.blauer_knall().getSafeUri().asString(),
//					270, 330);
//		}
//		if (playerid == 2){
//			movingShortVowelImage = new ShortVowelImage(DoppelungGameResourcesImages.INSTANCE.blauer_knall().getSafeUri().asString(), 
//					270, 330);
//			enemyMovingShortVowelImage = new ShortVowelImage(DoppelungGameResourcesImages.INSTANCE.roter_knall().getSafeUri().asString(), 
//					270, 330);
//		}
//	}
	

	/**
	 * Plays a Word
	 * 
	 * @param wordSound       sound which should be played
	 * @param word            word as a string
	 */
	public void playWord(Audio audio, String word){
		
		if (audio == null) {
			wordText.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>" + word + "</div>");
			gamePanel.add(wordText);
			gamePanel.setWidgetPosition(wordText, 250, 33);
		}
		else
			audio.play();
		
	}


	public void showWaitingForOtherPlayer(String msg){
		feedBackText.setHTML("<div style='font-size:25px'>" + msg + "</div>");
		gamePanel.add(feedBackText);
		gamePanel.setWidgetPosition(feedBackText, 150, 180);
	}




	/**
	 * Clears the game panel
	 */
	public void clearGamePanel(){
		gamePanel.clear();
		gamePanel.add(this.canvas);
	}

	// TODO: real implementation
	public void showEndScreen(int points){
		gamePanel.clear();
	}

	public void actualizePoints(int playerid, int p,String name) {
		playerNamesFlexTable.setHTML(playerid+1, 0, "<div style='font-size:30px;color:" + playerColors[playerid-1] + "'>" + Integer.toString(p) +"<span style='font-size:14px'> " + name +"</span></div>");
	}

	public void deletePlayerFromPointList(int playerid) {
		playerNamesFlexTable.clearCell(playerid, 1);
		playerNamesFlexTable.removeCell(playerid, 1);
	}


	/**
	 * Displays the short vowel image
	 */
	public void showShortVowelGame(int playerid, int players, int startX, int startY) {
		// TODO Change the logic.
//		gamePanel.clear();
//		textBox.setText("");
//		gamePanel.add(movingShortVowelImage);
//
//		gamePanel.add(canvas);
//
//		gamePanel.add(focusPanel, 0, 0); // additionally the focus panel makes the short vowel image unclickable
//		movingShortVowelImage.setX(startX);
//		movingShortVowelImage.setY(startY);
//
//
//		if (players ==2) {
//
//			gamePanel.add(enemyMovingShortVowelImage);
//			enemyMovingShortVowelImage.setX(270);
//			enemyMovingShortVowelImage.setY(330);
//			gamePanel.setWidgetPosition(enemyMovingShortVowelImage, startX, startY);
//
//		}
//		gamePanel.setWidgetPosition(movingShortVowelImage, startX, startY);
//		
//		//Resize
//		movingShortVowelImage.setPixelSize(48, 47);
//		enemyMovingShortVowelImage.setPixelSize(48, 47);
//
//		if (MobileDeviceChecker.checkForKeyboard()) {
//			kbd = new KeyboardDummy((LetrisGameController)super.gameController);
//			gamePanel.add(kbd, 440, 240);
//		}
//
//		focusPanel.setFocus(true);

	}


	/**
	 * 
	 * @param own   true if own image is moved
	 * @return      new x-coordinate
	 */
	public int moveStepLeft(boolean own) {
		// TODO Modify that for block movement.
//		ShortVowelImage image;
//		if (own) image = movingShortVowelImage;
//		else  image = enemyMovingShortVowelImage;
//
//
//		if (image.getX()-30 > 0) {
//			image.setX(image.getX()-30);
//		}else{
//			image.setX(0);
//		}
//
//		if (image.getParent() == gamePanel)
//			gamePanel.setWidgetPosition(image, image.getX(), image.getY());
//
//		return image.getX();
		return 0;
	}


	/**
	 * 
	 * @param own   true if own image is moved
	 * @return      new x-coordinate
	 */
	public int moveStepRight(boolean own) {
		// TODO Modify that for block movement.
//		ShortVowelImage image;
//		if (own) image = movingShortVowelImage;
//		else  image = enemyMovingShortVowelImage;
//
//		int imgWidth = image.getOffsetWidth();
//
//		if (image.getX()+30+imgWidth < canvas.getOffsetWidth()-1)
//			image.setX(image.getX()+30);
//		else
//			image.setX(canvas.getOffsetWidth() - 1 - imgWidth);
//
//		if (image.getParent() == gamePanel)
//			gamePanel.setWidgetPosition(image, image.getX(), image.getY());
//
//		return image.getX();
		return 0;
	}

	/**
	 * 
	 * @param own   true if own image is moved
	 * @return      new y-coordinate
	 */
	public int moveStepDown(boolean own) {
		// TODO Modify that for block movement.
//		ShortVowelImage image;
//		if (own) image = movingShortVowelImage;
//		else  image = enemyMovingShortVowelImage;
//		int imgHeight = image.getOffsetHeight();
//
//		if (image.getY()+30+imgHeight < canvas.getOffsetHeight())
//			image.setY(image.getY()+30);
//		else
//			image.setY(canvas.getOffsetHeight() - imgHeight);
//
//		if (image.getParent() == gamePanel)
//			gamePanel.setWidgetPosition(image, image.getX(), image.getY());
//		return image.getY();
		return 0;
	}


	public boolean isOnCanvas(int y) {

		return y < gamePanel.getOffsetHeight();

	}

	public void hideMovingConsonant(MovingConsonants mc) {

		gamePanel.remove(mc);

	}


	public void showMovingConsonants(MovingConsonants mc) {
		gamePanel.add(mc);
	}

	public void updateMovingLetterBlock(LetrisGameLetterBlock letterBlock) {
		// TODO Update position and rotation of the letter block.
		gamePanel.setWidgetPosition(letterBlock, letterBlock.getX(), letterBlock.getY());
	}

}
