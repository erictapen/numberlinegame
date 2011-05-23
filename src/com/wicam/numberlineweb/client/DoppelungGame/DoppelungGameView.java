package com.wicam.numberlineweb.client.DoppelungGame;

import java.util.ArrayList;

import com.allen_sauer.gwt.voices.client.Sound;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
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
	private final HTML feedBackText2 = new HTML();
	private final HTML wordText = new HTML();
	private final HTML canvas = new HTML("<div id='canvas' style='width:600px;height:400px;border-right:solid #333 1px'></div>");
	private final HTML canvasScore = new HTML("<div id='canvas' style='width:150px;height:30px;'></div>");
	private final HTML pointsText = new HTML("<div style='font-size:30px;color:black'>Punkte</div>");
	ArrayList<HTML> playerNames = new ArrayList<HTML>();
	final FlexTable playerNamesFlexTable = new FlexTable();
	
	protected final Button startGameButton = new Button("Spiel Starten");
	protected final ShortVowelImage shortVowelImage = new ShortVowelImage("doppelungGame/knall_small.jpg", 270, 330);
	protected final ShortVowelImage enemyShortVowelImage = new ShortVowelImage("doppelungGame/knall_small.jpg", 270, 330);
	protected final Image feedbackImage = new Image("doppelungGame/beide_daumen.gif");
	
	protected final Image longVowelImage = new Image("doppelungGame/ziehen1.jpg");
	private final FocusPanel focusPanel = new FocusPanel();
	private final HTML textBoxLabel = new HTML("<div style='font-size:18px'>Gib das zuletzt gehörte Wort ein!</div>");
	private final TextBox textBox = new TextBox();


	
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

		pointsPanel.add(pointsText);
		pointsPanel.setWidgetPosition(pointsText, 27, 10);
		pointsPanel.add(canvasScore);
		
		/*int top = 60;
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
		*/
		playerNamesFlexTable.setStyleName("playerList");
		playerNamesFlexTable.setCellPadding(5);
		

		pointsPanel.add(playerNamesFlexTable);
		
		focusPanel.addKeyDownHandler(doppelungGameController);
		focusPanel.addKeyUpHandler(doppelungGameController);
		
		motherPanel.add(gamePanel);
		motherPanel.add(pointsPanel);
	}

	/**
	 * Displays two buttons: a short vowel button and a long vowel button
	 */
	public void showVowelChoice(){
		gamePanel.clear();
		gamePanel.add(canvas);
		gamePanel.add(shortVowelImage);
		gamePanel.setWidgetPosition(shortVowelImage, 125, 165);
		gamePanel.add(longVowelImage);
		gamePanel.setWidgetPosition(longVowelImage, 385, 165);
	}

	/**
	 * Plays a Word
	 * 
	 * @param wordSound       sound which should be played
	 * @param word            word as a string
	 */
	public void playWord(Sound wordSound, String word){
		if (wordSound != null)
			wordSound.play();
		// display word only if there is no sound file
		else {
			wordText.setHTML("<div style='width:500px;padding:5px 20px;font-size:25px'>" + word + "</div>");
			gamePanel.add(wordText);
			gamePanel.setWidgetPosition(wordText, 230, 33);
		}
	}
	
	/**
	 * Displays feedback after player has clicked the short vowel or long vowel button
	 * 
	 * @param correctAnswer           true if the answer was correct
	 * @param isShortVowel            true if the word has a short vowel
	 * @param randomNumberFeedback    number which determines the feedback
	 */
	public void showSoundFeedback(boolean correctAnswer, boolean isShortVowel, int randomNumberFeedback){
		// reset feedbackImage
		feedbackImage.setUrl("");
		gamePanel.clear();
		gamePanel.add(canvas);
		if (correctAnswer)
			showCorrectSoundFeedback(isShortVowel, randomNumberFeedback);
		else
			showFalseSoundFeedback(randomNumberFeedback);
	}
	
	private void showCorrectSoundFeedback(boolean isShortVowel, int randomNumberFeedback){
		String vowelLength = isShortVowel?"kurz":"lang";
		String feedback = "";
		int imageX = 0;
		int feedbackX = 140;
		switch (randomNumberFeedback){
		case 0:
			feedback = "Klasse, der Vokal war ";
			feedbackImage.setUrl("doppelungGame/feedback/beide_daumen.gif");
			imageX = imageX - 20;
			break;
		case 1:
			feedback = "Super, der Vokal war ";
			feedbackImage.setUrl("doppelungGame/feedback/beifall.gif");
			imageX = imageX - 15;
			break;
		case 2:
			feedback = "Richtig, der Vokal war ";
			feedbackImage.setUrl("doppelungGame/feedback/beifall_1.gif");
			imageX = imageX - 15;
			break;
		case 3:
			feedback = "Genau, der Vokal war ";
			feedbackImage.setUrl("doppelungGame/feedback/jippie.gif");
			imageX = imageX - 10;
			break;
		case 4:
			feedback = "Genau, der Vokal war ";
			feedbackImage.setUrl("doppelungGame/feedback/juchhu.gif");
			imageX = imageX - 20;
			break;
		case 5:
			feedback = "Gut gemacht, der Vokal war ";
			feedbackImage.setUrl("doppelungGame/feedback/smile_1.gif");
			feedbackX = feedbackX - 10;
			break;
		case 6:
			feedback = "Richtig, der Vokal war ";
			feedbackImage.setUrl("doppelungGame/feedback/victory.gif");
			imageX = imageX - 15;
			break;
		}
		imageX = imageX + 300-feedbackImage.getOffsetWidth()/2 - 20;
		feedBackText.setHTML("<div style='font-size:25px'>" + feedback + vowelLength + "!</div>");
		gamePanel.add(feedBackText);
		gamePanel.add(feedbackImage);
		gamePanel.setWidgetPosition(feedBackText, feedbackX, 150);
		gamePanel.setWidgetPosition(feedbackImage, imageX, 230);
	}
	
	private void showFalseSoundFeedback(int randomNumberFeedback){
		String feedback = "";
		String feedback2 = "";
		int imageX = 0;
		int feedback1X = 160;
		switch (randomNumberFeedback){
		case 0:
			feedback = "Durchhalten, probiers nochmal,";
			feedback2 = "hört sich der Vokal eher lang oder eher kurz an?";
			feedbackImage.setUrl("doppelungGame/feedback/hantel.gif");
			imageX = imageX - 30;
			feedback1X = feedback1X -20;
			break;
		case 1:
			feedback = "Hmm, überleg nochmal,";
			feedback2 = "hört sich der Vokal eher lang oder eher kurz an?";
			feedbackImage.setUrl("doppelungGame/feedback/hmm_big.gif");
			feedback1X = feedback1X + 10;
			break;
		case 2:
			feedback = "Hmm, überleg nochmal,";
			feedback2 = "hört sich der Vokal eher lang oder eher kurz an?";
			feedbackImage.setUrl("doppelungGame/feedback/huch.gif");
			feedback1X = feedback1X + 10;
			break;
		case 3:
			feedback = "Ups, hör nochmal genau hin,";
			feedback2 = "hört sich der Vokal eher lang oder eher kurz an?";
			feedbackImage.setUrl("doppelungGame/feedback/oops.gif");
			imageX = imageX - 20;
			break;
		}
		imageX = imageX + 300-feedbackImage.getOffsetWidth()/2 - 20;
		feedBackText.setHTML("<div style='font-size:25px'>" + feedback + "</div>");
		feedBackText2.setHTML("<div style='font-size:25px'>" + feedback2 + "</div>");
		gamePanel.add(feedBackText);
		gamePanel.add(feedBackText2);
		gamePanel.add(feedbackImage);
		gamePanel.setWidgetPosition(feedBackText, feedback1X, 100);
		gamePanel.setWidgetPosition(feedBackText2, 30, 150);
		gamePanel.setWidgetPosition(feedbackImage, imageX, 230);
	}
	
	public void showWaitingForOtherPlayer(){
		feedBackText.setHTML("<div style='font-size:25px'>Warte auf anderen Spieler!</div>");
		gamePanel.add(feedBackText);
		gamePanel.setWidgetPosition(feedBackText, 150, 180);
	}
	
	/**
	 * Displays feedback after player has entered a word
	 * 
	 * @param correctAnswer          true if the word of the player was written correctly
	 * @param word                   the word the user had to enter
	 * @param randomNumberFeedback   number which determines the feedback
	 */
	public void showWordFeedback(boolean correctAnswer, String word, int randomNumberFeedback){
		// reset feedbackImage
		feedbackImage.setUrl("");
		gamePanel.clear();
		gamePanel.add(canvas);
		if (correctAnswer)
			showCorrectWordFeedback(randomNumberFeedback, word);
		else
			showFalseWordFeedback(randomNumberFeedback, word);
	}
	
	private void showCorrectWordFeedback(int randomNumberFeedback, String word){
		String feedback = "";
		int imageX = 0;
		switch (randomNumberFeedback){
		case 0:
			feedback = "Klasse, genau so schreibt man \"" + word + "\"! ";
			feedbackImage.setUrl("doppelungGame/feedback/beide_daumen.gif");
			imageX = imageX - 20;
			break;
		case 1:
			feedback = "Super, genau so schreibt man \"" + word + "\"!";
			feedbackImage.setUrl("doppelungGame/feedback/beifall.gif");
			imageX = imageX - 15;
			break;
		case 2:
			feedback = "Richtig, genau so schreibt man \"" + word + "\"!";
			feedbackImage.setUrl("doppelungGame/feedback/beifall_1.gif");
			imageX = imageX - 15;
			break;
		case 3:
			feedback = "Genau, genau so schreibt man \"" + word + "\"!";
			feedbackImage.setUrl("doppelungGame/feedback/jippie.gif");
			imageX = imageX - 10;
			break;
		case 4:
			feedback = "Genau, genau so schreibt man \"" + word + "\"!";
			feedbackImage.setUrl("doppelungGame/feedback/juchhu.gif");
			imageX = imageX - 20;
			break;
		case 5:
			feedback = "Gut gemacht, genau so schreibt man \"" + word + "\"!";
			feedbackImage.setUrl("doppelungGame/feedback/smile_1.gif");
			break;
		case 6:
			feedback = "Richtig, genau so schreibt man \"" + word + "\"!";
			feedbackImage.setUrl("doppelungGame/feedback/victory.gif");
			imageX = imageX - 15;
			break;
		}
		imageX = imageX + 300-feedbackImage.getOffsetWidth()/2 - 20;
		feedBackText.setHTML("<div style='font-size:25px'>" + feedback + "</div>");
		gamePanel.add(feedBackText);
		gamePanel.add(feedbackImage);
		gamePanel.setWidgetPosition(feedBackText, 80, 150);
		gamePanel.setWidgetPosition(feedbackImage, imageX, 230);
	}
	
	private void showFalseWordFeedback(int randomNumberFeedback, String word){
		String feedback = "";
		String feedback2 = word;
		int imageX = 0;
		int feedback1X = 90;
		switch (randomNumberFeedback){
		case 0:
			feedback = "Durchhalten, schau dir das Wort nochmal an:";
			feedbackImage.setUrl("doppelungGame/feedback/hantel.gif");
			imageX = imageX - 30;
			feedback1X = feedback1X - 50;
			break;
		case 1:
			feedback = "Hmm, schau dir das Wort nochmal an:";
			feedbackImage.setUrl("doppelungGame/feedback/hmm_big.gif");
			break;
		case 2:
			feedback = "Hmm, schau dir das Wort nochmal an:";
			feedbackImage.setUrl("doppelungGame/feedback/huch.gif");
			break;
		case 3:
			feedback = "Ups, schau dir das Wort nochmal an:";
			feedbackImage.setUrl("doppelungGame/feedback/oops.gif");
			break;
		}
		imageX = imageX + 300-feedbackImage.getOffsetWidth()/2 - 20;
		feedBackText.setHTML("<div style='font-size:25px'>" + feedback + "</div>");
		feedBackText2.setHTML("<div style='font-size:25px'>" + feedback2 + "</div>");
		gamePanel.add(feedBackText);
		gamePanel.add(feedBackText2);
		gamePanel.add(feedbackImage);
		gamePanel.setWidgetPosition(feedBackText, feedback1X, 100);
		gamePanel.setWidgetPosition(feedBackText2, 260, 150);
		gamePanel.setWidgetPosition(feedbackImage, imageX, 230);
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
	public void showShortVowelGame(int players, int startX, int startY) {
		
		
		gamePanel.clear();
		textBox.setText("");
		gamePanel.add(shortVowelImage);
	
		gamePanel.add(canvas);
		gamePanel.add(focusPanel, 0, 0); // additionally the focus panel makes the short vowel image unclickable
		shortVowelImage.setX(startX);
		shortVowelImage.setY(startY);
		
		GWT.log(Integer.toString(players));
		
		if (players ==2) {
			
			gamePanel.add(enemyShortVowelImage);
			enemyShortVowelImage.setX(270);
			enemyShortVowelImage.setY(330);
			gamePanel.setWidgetPosition(enemyShortVowelImage, startX, startY);
						
		}
		gamePanel.setWidgetPosition(shortVowelImage, startX, startY);
		focusPanel.setFocus(true);
		
	}
	
	
	/**
	 * 
	 * @param own   true if own image is moved
	 * @return      new x-coordinate
	 */
	public int moveStepLeft(boolean own) {
		
	    ShortVowelImage image;
		if (own) image = shortVowelImage;
		else  image = enemyShortVowelImage;
		

		if (image.getX()-30 > 0) {
			image.setX(image.getX()-30);
		}else{
			image.setX(0);
		}

		if (image.getParent() == gamePanel)
			gamePanel.setWidgetPosition(image, image.getX(), image.getY());

		return image.getX();
	}

	/**
	 * 
	 * @param own   true if own image is moved
	 * @return      new y-coordinate
	 */
	public int moveStepUp(boolean own) {
		
	    ShortVowelImage image;
		if (own) image = shortVowelImage;
		else  image = enemyShortVowelImage;

		if (image.getY()-30 > 0)
			image.setY(image.getY()-30);
		else
			image.setY(0);

		if (image.getParent() == gamePanel)
			gamePanel.setWidgetPosition(image, image.getX(), image.getY());

		return image.getY();
	}

	/**
	 * 
	 * @param own   true if own image is moved
	 * @return      new x-coordinate
	 */
	public int moveStepRight(boolean own) {
		
	    ShortVowelImage image;
		if (own) image = shortVowelImage;
		else  image = enemyShortVowelImage;

		int imgWidth = image.getOffsetWidth();

		if (image.getX()+30+imgWidth < canvas.getOffsetWidth()-1)
			image.setX(image.getX()+30);
		else
			image.setX(canvas.getOffsetWidth() - 1 - imgWidth);

		if (image.getParent() == gamePanel)
			gamePanel.setWidgetPosition(image, image.getX(), image.getY());
		
		return image.getX();
	}

	/**
	 * 
	 * @param own   true if own image is moved
	 * @return      new y-coordinate
	 */
	public int moveStepDown(boolean own) {
		
	    ShortVowelImage image;
		if (own) image = shortVowelImage;
		else  image = enemyShortVowelImage;
		int imgHeight = image.getOffsetHeight();

		if (image.getY()+30+imgHeight < canvas.getOffsetHeight())
			image.setY(image.getY()+30);
		else
			image.setY(canvas.getOffsetHeight() - imgHeight);

		if (image.getParent() == gamePanel)
			gamePanel.setWidgetPosition(image, image.getX(), image.getY());
		return image.getY();
	}
	
	public void moveTo(int x, int y) {
		
	    ShortVowelImage image = enemyShortVowelImage;

		if (image.getParent() == gamePanel)
			gamePanel.setWidgetPosition(image, x, y);

	}
	
	public boolean isOnCanvas(int y) {
		
		return y < gamePanel.getOffsetHeight();
		
	}
	
	public void hideMovingConsonant(MovingConsonants mc) {
		
		gamePanel.remove(mc);
		
	}
	
	public void showUserWordInput() {
		// reset text
		textBox.setText("");
		gamePanel.clear();
		gamePanel.add(canvas);
		gamePanel.add(textBoxLabel);
		gamePanel.setWidgetPosition(textBoxLabel, 170, 150);
		gamePanel.add(this.textBox);
		gamePanel.setWidgetPosition(textBox, 230, 190);
		textBox.setFocus(true);
	}
	
	public int[] getShortVowelImageDimension() {
		
		int[] ret = new int[2];
		
		ret[0] = shortVowelImage.getOffsetWidth();
		ret[1] = shortVowelImage.getOffsetHeight();
		
		return ret;
		
	}
	
	/**
	 * TODO: the view should NOT return the position but get it
	 * @return
	 */
	
	public int[] getShortVowelImagePosition() {
		
		int[] ret = new int[2];
		
		ret[0] = shortVowelImage.getX();
		ret[1] = shortVowelImage.getY();
		
		return ret;
		
	}
	
	
	public void showMovingConsonants(int offset, MovingConsonants mc) {
		gamePanel.add(mc);
		gamePanel.setWidgetPosition(mc, mc.getX(), mc.getY());
		mc.startMoving(offset*1000 - (int)(Math.random()*500));
		mc.setSpeed(6);
	}
	
	public void setMcPosition(MovingConsonants mc, int x, int y) {
		gamePanel.setWidgetPosition(mc, x, y);
	}


	
}
