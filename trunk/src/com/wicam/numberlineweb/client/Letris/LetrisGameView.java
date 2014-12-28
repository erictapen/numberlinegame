package com.wicam.numberlineweb.client.Letris;

import java.util.HashMap;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Group;
import org.vaadin.gwtgraphics.client.Line;
import org.vaadin.gwtgraphics.client.VectorObject;
import org.vaadin.gwtgraphics.client.animation.Animate;
import org.vaadin.gwtgraphics.client.shape.Circle;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wicam.numberlineweb.client.GameView;
import com.wicam.numberlineweb.client.KeyboardDummy;
import com.wicam.numberlineweb.client.MobileDeviceChecker;
import com.wicam.numberlineweb.client.VowelGame.SoundRetriever;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;
import com.google.gwt.user.client.ui.FocusPanel;

/**
 * View of the LeTris game.
 * @author timfissler
 *
 */

// TODO Is GWT.Audio capable of playing midi-files with increasing speed?
// TODO Add game sound that increases with the speed of the game.
// TODO Add dropping sound.
// TODO Add game over sound.
// TODO Add sound for wrong word.
// TODO Add sound for correct word.
// TODO Add changing background color of the playground that varies with the game level/speed.

public class LetrisGameView extends GameView {

	/**
	 * The panel to where all the other panels are linked to.
	 */
	private final HorizontalPanel motherPanel = new HorizontalPanel();
	/**
	 * The panel with the playground grid.
	 */
	private final AbsolutePanel gamePanel = new AbsolutePanel();
	/**
	 * The panel showing the players' points.
	 */
	private final AbsolutePanel pointsPanel = new AbsolutePanel();
	private KeyboardDummy kbd;
	/**
	 * Text to describe the game.
	 */
	protected final HTML explanationText = new HTML();
	/**
	 * Messages like 'Waiting for other player' are shown here.
	 */
	protected final HTML feedBackText = new HTML();
	private final HTML canvasScore = new HTML("<div id='canvas' style='width:150px;height:30px;'></div>");
	/**
	 * Text with the player points.
	 */
	private final HTML pointsText = new HTML("<div style='font-size:24px;color:black'>Punkte</div>");
	/**
	 * Table containing the player names.
	 */
	private final FlexTable playerNamesFlexTable = new FlexTable();
	/**
	 * Text containing the words 'N��chster Spielstein'.
	 */
	private final HTML nextBlockText = new HTML("<div style='font-size:24px;color:black'>N��chster</br>Stein</div>");
	/**
	 * Button to start the game.
	 */
	protected final Button startGameButton = new Button("Spiel Starten");
	/**
	 * Panel for focusing the game.
	 */
	private final FocusPanel focusPanel = new FocusPanel();
	private final TextBox textBox = new TextBox();
	/**
	 * Size of the letris view in pixels.
	 */
	private LetrisGameCoordinates viewSize = new LetrisGameCoordinates(600, 400);
	/**
	 * Game canvas where the grid with the blocks is drawn.
	 */
	private final DrawingArea canvas = new DrawingArea(viewSize.x, viewSize.y);
	/**
	 * Message displayed when game is paused.
	 */
	private final Group pauseMessage = new Group();
	/**
	 * Canvas for drawing the next letter block being shown.
	 */
	private final DrawingArea nextBlockCanvas = new DrawingArea(40, 40);
	/** 
	 * Size of the model playground in blocks.
	 */
	private LetrisGameCoordinates modelSize;
	/**
	 * Coordinate transform object to transform coordinates between model and view.
	 */
	private LetrisGameCoordinateTransform transform;
	/**
	 * Hash table containing the colors for the letters.
	 */
	private HashMap<String, String> letter2HexColor = new HashMap<String, String>();
	/**
	 * Hash table containing the letter block images given a letter block id. 
	 */
	private HashMap<Long, Group> id2LetterBlock = new HashMap<Long, Group>();
	private final int smallBlockSize = 10;
	private final int normalBlockSize = 20;
	private final int largeBlockSize = 40;
	private final int smallFontSize = 8;
	private final int normalFontSize = 17;
	private final int largeFontSize = 34;
	LetrisGameCoordinates playgroundSize;
	LetrisGameCoordinates playgroundOrigin = new LetrisGameCoordinates(200, 0);
	private VowelGameWord targetWord;
	
	// TODO Add correct sound file.
//	protected Audio descriptionSound = Audio.createIfSupported();

	/**
	 * Construct a new view.
	 * @param numberOfPlayers
	 * @param doppelungGameController
	 * @param playgroundWidth
	 * @param playgroundHeight
	 */
	public LetrisGameView(int numberOfPlayers, LetrisGameController doppelungGameController, int playgroundWidth, int playgroundHeight) {
		super(numberOfPlayers, doppelungGameController);
		this.modelSize = new LetrisGameCoordinates(playgroundWidth, playgroundHeight);
		this.transform = new LetrisGameCoordinateTransform(modelSize, viewSize);
		playgroundSize = new LetrisGameCoordinates(316, 399);
		init();
		sinkEvents(Event.MOUSEEVENTS);
		this.initWidget(motherPanel);
	}

	/**
	 * Initialize everything to construct a new view.
	 */
	private void init() {

		final LetrisGameController letrisGameController = (LetrisGameController) gameController;
		setupLetterColors();
		
		//draw everything
		gamePanel.getElement().getStyle().setPosition(Position.RELATIVE);

		startGameButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				letrisGameController.onStartButtonClick();
//				try {
//					descriptionSound.pause();
//					descriptionSound.setCurrentTime(0);
//				} catch (Exception e) {
//				}
			}
		});
		
		setExplanationText();
		gamePanel.add(explanationText);
		gamePanel.setWidgetPosition(explanationText, 0, 0);
		gamePanel.add(startGameButton);
		gamePanel.setWidgetPosition(startGameButton, 480, 350);
		gamePanel.add(canvas);
		focusPanel.setSize("600px", "400px");

		pointsPanel.setHeight("400px");
		pointsPanel.setWidth("200px");
		pointsPanel.add(pointsText);
		pointsPanel.setWidgetPosition(pointsText, 27, 10);
		pointsPanel.add(canvasScore);

		playerNamesFlexTable.setStyleName("playerList");
		playerNamesFlexTable.setCellPadding(5);

		pointsPanel.add(playerNamesFlexTable);
		
		pointsPanel.add(nextBlockText);
		pointsPanel.setWidgetPosition(nextBlockText, 27, 130);
		pointsPanel.add(nextBlockCanvas);
		pointsPanel.setWidgetPosition(nextBlockCanvas, 27, 210);
		updateNextBlock(null);

		focusPanel.addKeyDownHandler(letrisGameController);
		focusPanel.addKeyUpHandler(letrisGameController);
		
		// Setup pause message.
		
		Rectangle rect = new Rectangle(0, 0, 250, 150);
		rect.setRoundedCorners(10);
		rect.setFillColor("gray");
		rect.setFillOpacity(0.5);
		rect.setStrokeOpacity(0);
		
		// Center rectangle in canvas.
		int xOffset = (int) Math.floor(((double)playgroundSize.x - rect.getWidth()) / 2.0);
		int yOffset = (int) Math.floor(((double)playgroundSize.y - rect.getHeight()) / 2.0);
		rect.setX(playgroundOrigin.x + xOffset);
		rect.setY(playgroundOrigin.y + yOffset);
		
		Text pauseText = new Text(0, 0, "Pause");
		pauseText.setFillColor("white");
		pauseText.setStrokeOpacity(0);
		pauseText.setFillOpacity(0.5);
		pauseText.setFontSize(80);
		
		// Center text in rectangle.
		xOffset = (int) Math.floor(((double)rect.getWidth() - pauseText.getTextWidth()) / 2.0);
		yOffset = (int) Math.floor(((double)rect.getHeight() - pauseText.getTextHeight()) / 2.0) +
				pauseText.getTextHeight() - (rect.getHeight() / 10);
		pauseText.setX(rect.getX() + xOffset);
		pauseText.setY(rect.getY() + yOffset);
		
		pauseMessage.add(rect);
		pauseMessage.add(pauseText);

		motherPanel.add(gamePanel);
		motherPanel.add(pointsPanel);
		
//		if (Audio.isSupported() && descriptionSound != null) {
//			
//			// TODO Change sound to actual description of the LeTris game.
//			descriptionSound.addSource("desc/Doppelung.ogg", "audio/ogg; codecs=vorbis");
//			descriptionSound.addSource("desc/Doppelung.mp3", "audio/mpeg; codecs=MP3");
//			
//			descriptionSound.play();
//			
//		}
	}
	
	/**
	 * Show the pause message.
	 */
	public void showPauseMessage() {
		canvas.add(pauseMessage);
	}
	
	/**
	 * Hide the pause message.
	 */
	public void hidePauseMessage() {
		canvas.remove(pauseMessage);
	}
	
	/**
	 * Play the target word that should be built by the player.
	 * @param targetWord
	 */
	public void updateTargetWord(VowelGameWord targetWord) {
		this.targetWord = targetWord;
		
		// Play the target word.
		playWord(LetrisGameSoundRetriever.getAudioElement(targetWord), targetWord.getWordString());

	}
	
	/**
	 * Repeat the current target word.
	 */
	public void repeatTargetWord() {
		// Play the target word.
		playWord(LetrisGameSoundRetriever.getAudioElement(targetWord), targetWord.getWordString());
	}
	
	/**
	 * Show the next letter block that will be dropped after the current one.
	 * @param letterBlock
	 */
	public void updateNextBlock(LetrisGameLetterBlock letterBlock) {
		if (letterBlock == null) {
			nextBlockCanvas.clear();
		} else {
			Group letterBlockImage = drawLetterBlock(letterBlock, false, LetterBlockSize.LARGE);
			nextBlockCanvas.clear();
			nextBlockCanvas.add(letterBlockImage);
		}
	}
	
	/**
	 * Initialize the colors hash table.
	 */
	private void setupLetterColors() {
		letter2HexColor.put("A", "#F46943");
		letter2HexColor.put("B", "#87E3D5");
		letter2HexColor.put("C", "#8798D5");
		letter2HexColor.put("D", "#C02EB9");
		letter2HexColor.put("E", "#EF98D8");
		letter2HexColor.put("F", "#44B5DF");
		letter2HexColor.put("G", "#2A90AE");
		letter2HexColor.put("H", "#66C6A0");
		letter2HexColor.put("I", "#5749CB");
		letter2HexColor.put("J", "#8AE720");
		letter2HexColor.put("K", "#A39D46");
		letter2HexColor.put("L", "#964AB2");
		letter2HexColor.put("M", "#F53E88");
		letter2HexColor.put("N", "#4BA1ED");
		letter2HexColor.put("O", "#C3CE3A");
		letter2HexColor.put("P", "#3893A3");
		letter2HexColor.put("Q", "#E41029");
		letter2HexColor.put("R", "#D65D77");
		letter2HexColor.put("S", "#1DD099");
		letter2HexColor.put("T", "#ABCB92");
		letter2HexColor.put("U", "#F49699");
		letter2HexColor.put("V", "#E6A63E");
		letter2HexColor.put("W", "#494C62");
		letter2HexColor.put("X", "#524575");
		letter2HexColor.put("Y", "#893C77");
		letter2HexColor.put("Z", "#ED8EF8");
		letter2HexColor.put("��", "#E2306E");
		letter2HexColor.put("��", "#0A6318");
		letter2HexColor.put("��", "#8CD7DF");
		letter2HexColor.put("��", "#F4C162");
	}
	
	/**
	 * Initialize the game description.
	 */
	protected void setExplanationText() {
		explanationText.setHTML("<div style='padding:5px 20px;font-size:25px'><b>LeTris - Beschreibung</b></div>" +
				"<div style='padding:5px 20px;font-size:12px'>" +
				"In diesem Spiel werden dir W��rter vorgelesen. Versuche diese W��rter " + 
				"- genau wie im richtigen Tetris-Spiel - nachzubauen. Verwende dazu die " +
				"Pfeiltasten um einen Spielstein nach links, rechts oder unten zu bewegen. " +
				"Mit der Leertaste kannst du einen Stein schnell fallen lassen. Mit der " +
				"\"W\"-Taste wird dir das aktuelle Wort noch einmal vorgelesen. Mit der " +
				"\"P\"-Taste kannst du das Spiel pausieren.</br></br>" + 
				"Aufgepasst, es kommen nicht nur Buchstaben, die du f��r das aktuelle Wort " +
				"brauchst, sondern auch falsche Buchstaben. Versuche sie zu finden und " +
				"neben oder ��ber das Wort, das du gerade baust, zu setzen. Baust du einmal " +
				"ein Wort falsch zusammen, wird dein Platz im Spiel um eine Zeile geringer " +
				"werden. Wenn du es schaffst zwei mal hintereinander ein Wort richtig zu " +
				"bauen kannst du dir wieder eine Zeile \"zur��ck erobern\".</br></br>" + 
				"Das Spiel endet, wenn der Platz auf dem Spielfeld voll ist." +
				"</div>");
	}

	/**
	 * Change feedback test according to a given message.
	 * @param msg
	 */
	public void showWaitingForOtherPlayer(String msg){
		feedBackText.setHTML("<div style='font-size:25px'>" + msg + "</div>");
		gamePanel.add(feedBackText);
		gamePanel.setWidgetPosition(feedBackText, 150, 180);
	}
	
	/**
	 * Takes the given game state and draws the playground and the letter blocks with its information. 
	 * @param gameState
	 */
	public void updatePlayground(LetrisGameState gameState) {
		Group grid = drawPlaygroundGrid();
		
		id2LetterBlock.clear();
		
		Group movingLetterBlockImage = drawLetterBlock(gameState.getMovingLetterBlock(), true,
				LetterBlockSize.NORMAL);
		Group staticLetterBlockImages = new Group();
		for (LetrisGameLetterBlock letterBlock : gameState.getStaticLetterBlocks()) {
			Group letterBlockImage = drawLetterBlock(letterBlock, true, LetterBlockSize.NORMAL);
			staticLetterBlockImages.add(letterBlockImage);
		}
		canvas.clear();
		canvas.add(grid);
		canvas.add(staticLetterBlockImages);
		canvas.add(movingLetterBlockImage);
	}
	
	/**
	 * Update the position and the rotation of an already existing letter block in the view.
	 * @param letterBlock
	 */
	public void updateLetterBlock(LetrisGameLetterBlock letterBlock) {
		
		// Get the letter block image.
		Group letterBlockImage = id2LetterBlock.get(letterBlock.getId());
		
		// Get the new position of the rectangle in the view.
		LetrisGameCoordinates viewCoordinates = transform.transformModelToView(new LetrisGameCoordinates(letterBlock.getX(), letterBlock.getY()));
		viewCoordinates.add(playgroundOrigin);
		
		// Get the two vector objects of the group.
		Rectangle box = (Rectangle)letterBlockImage.getVectorObject(0);
		Text letter = (Text)letterBlockImage.getVectorObject(1);
		
		// Move the rectangle.
		box.setX(viewCoordinates.x);
		box.setY(viewCoordinates.y);
		
		// Rotate letter.
		switch (letterBlock.getOrientation()) {
		case EAST:
			letter.setRotation(-90);
			break;
		case WEST:
			letter.setRotation(90);
			break;
		case NORTH:
			letter.setRotation(180);
			break;
		case SOUTH:
			letter.setRotation(0);
			break;
		}

		// Center letter.
		int xOffset = (int) Math.floor(((double)normalBlockSize - letter.getTextWidth()) / 2.0);
		int yOffset = (int) Math.floor(((double)normalBlockSize - letter.getTextHeight()) / 2.0) +
				letter.getTextHeight() - (normalBlockSize / 10);
		letter.setX(viewCoordinates.x + xOffset);
		letter.setY(viewCoordinates.y + yOffset);
	}
	
	/**
	 * True, if the given letter block currently is drawn.
	 * @param letterBlock
	 * @return
	 */
	public boolean isDrawn(LetrisGameLetterBlock letterBlock) {
		return id2LetterBlock.containsKey(letterBlock.getId());
	}
	
	/**
	 * Takes the given game state and refreshes the drawing of the opponent's playgroundSize preview.
	 * @param gameState
	 */
	public void updatePreview(LetrisGameState gameState) {
		// TODO Implement this.
	}
	
	/**
	 * Draws the grid lines of the playgroundSize.
	 */
	private Group drawPlaygroundGrid() {
		
		// Create the grid and set the starting point.
		Group grid = new Group();
		
		// Draw horizontal lines.
		for (int i = 0; i <= modelSize.y; i++) {
			// Create line.
			Line l = new Line(playgroundOrigin.x, playgroundOrigin.y + (i * (normalBlockSize + 1)), 
					playgroundOrigin.x + playgroundSize.x, playgroundOrigin.y + (i * (normalBlockSize + 1)));
			// Add color.
			if (i == 0 || i == modelSize.y) {
				l.setStrokeColor("black");
			} else {
				l.setStrokeColor("gray");
			}
			// Add line to the grid.
			grid.add(l);
		}
		
		// Draw vertical lines.
		for (int i = 0; i <= modelSize.x; i++) {
			// Create line.
			Line l = new Line(playgroundOrigin.x + (i * (normalBlockSize + 1)), playgroundOrigin.y,
					playgroundOrigin.x + (i * (normalBlockSize + 1)), playgroundOrigin.y + playgroundSize.y);
			// Add color.
			if (i == 0 || i == modelSize.x) {
				l.setStrokeColor("black");
			} else {
				l.setStrokeColor("gray");
			}
			// Add line to the grid.
			grid.add(l);
		}
		
		return grid;
	}
	
	/**
	 * Possible sizes of a letter block.
	 * @author timfissler
	 *
	 */
	private enum LetterBlockSize {
		SMALL, NORMAL, LARGE
	}
	
	/**
	 * Draws the given letter block.
	 * @param letterBlock 			the letter block to be drawn
	 * @param useViewCoordinates	if true, use the coordinates of the view, else use (0,0)
	 * @return 						the group containing the letter block
	 */
	private Group drawLetterBlock(LetrisGameLetterBlock letterBlock,
			boolean useViewCoordinates,
			LetterBlockSize size) {
		
		Group letterBlockImage = new Group();
		
		// Check if the letter block is empty.
		if (letterBlock == null) {
			return letterBlockImage;
		}
		
		int blockSize = 0;
		int fontSize = 0;
		
		switch (size) {
		case SMALL:
			blockSize = smallBlockSize;
			fontSize = smallFontSize;
			break;
		case NORMAL:
			blockSize = normalBlockSize;
			fontSize = normalFontSize;
			break;
		case LARGE:
			blockSize = largeBlockSize;
			fontSize = largeFontSize;
			break;
		default:
			break;
		}
		
		String letterStr = letterBlock.getLetter();
		
		Rectangle box;
		LetrisGameCoordinates viewCoordinates = new LetrisGameCoordinates();

		if (useViewCoordinates) {
			// Calculate the appropriate coordinates for the view.
			viewCoordinates = transform.transformModelToView(new LetrisGameCoordinates(letterBlock.getX(), letterBlock.getY()));
			viewCoordinates.add(playgroundOrigin);

			// Draw box.
			box = new Rectangle(viewCoordinates.x, viewCoordinates.y, blockSize, blockSize);
		} else {
			// Draw box.
			box = new Rectangle(0, 0, blockSize, blockSize);
		}
		
		// Handle filler or letter blocks.
		if (letterStr.equals("#")) {
			
			// Filler block.
			
			// Set fill color to grey.
			box.setFillColor("grey");
			box.setFillOpacity(1.0);
			
			letterBlockImage.add(box);
			
		} else {
			
			// Letter block.

			// Get letter color.
			String colorStr = letter2HexColor.get(letterStr);

			// Draw letter.
			Text letter = new Text(0, 0, letterStr);
			letter.setFontSize(fontSize);
			letter.setFillColor(colorStr);
			letter.setStrokeColor(colorStr);
			letter.setFontFamily("Andale Mono");

			// Rotate letter.
			switch (letterBlock.getOrientation()) {
			case EAST:
				letter.setRotation(-90);
				break;
			case WEST:
				letter.setRotation(90);
				break;
			case NORTH:
				letter.setRotation(180);
				break;
			case SOUTH:
				letter.setRotation(0);
				break;
			}

			// Center letter.
			int xOffset = (int) Math.floor(((double)blockSize - letter.getTextWidth()) / 2.0);
			int yOffset = (int) Math.floor(((double)blockSize - letter.getTextHeight()) / 2.0) +
					letter.getTextHeight() - (blockSize / 10);
			if (useViewCoordinates) {
				letter.setX(viewCoordinates.x + xOffset);
				letter.setY(viewCoordinates.y + yOffset);
			} else {
				letter.setX(xOffset);
				letter.setY(yOffset);
			}

			letterBlockImage.add(box);
			letterBlockImage.add(letter);
		
		}
		
		// Insert this letter block into the has map.
		id2LetterBlock.put(letterBlock.getId(), letterBlockImage);
		
		return letterBlockImage;
	}
	
	/**
	 * Plays the audio file of a word or displays it in on the screen if
	 * no sound is available.
	 * 
	 * @param audio	       sound which should be played
	 * @param word         word as a string
	 */
	public void playWord(Audio audio, String word){
		
		if (audio == null) {
			try {
				throw new Exception("No audio found for \"" + word + "\"!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			audio.play();
		}
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
	
	/**
	 * Update the player points for a given player id, points p and player name.
	 * @param playerid
	 * @param p
	 * @param name
	 */
	public void updatePoints(int playerid, int p,String name) {
		playerNamesFlexTable.setHTML(playerid+1, 0, "<div style='font-size:30px;color:" + playerColors[playerid-1] + "'>" + Integer.toString(p) +"<span style='font-size:14px'> " + name +"</span></div>");
	}

	/**
	 * Delete a player from the points list given his id.
	 * @param playerid
	 */
	public void deletePlayerFromPointList(int playerid) {
		playerNamesFlexTable.clearCell(playerid, 1);
		playerNamesFlexTable.removeCell(playerid, 1);
	}

	/**
	 * Displays the LeTris game playground.
	 */
	public void showLetrisGame() {
		gamePanel.clear();
		textBox.setText("");
	
		gamePanel.add(canvas);
		canvas.clear();
		canvas.add(drawPlaygroundGrid());

		gamePanel.add(focusPanel, 0, 0);
		
//		if (players ==2) {
			// TODO Add preview of other player.
//		}
		
		if (MobileDeviceChecker.checkForKeyboard()) {
			kbd = new KeyboardDummy((LetrisGameController)super.gameController);
			gamePanel.add(kbd, 440, 240);
		}

		setFocused();
	}


	/**
	 * Set the current focus to the focus panel of the game view,
	 * so that the movement commands are recognized.
	 */
	public void setFocused() {
		focusPanel.setFocus(true);
	}

	/* TODO Delete this.
	public boolean isOnCanvas(int y) {
		return y < gamePanel.getOffsetHeight();
	}
	*/
}
