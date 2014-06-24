package com.wicam.numberlineweb.client.VowelGame.DoppelungGame;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Widget;
import com.wicam.numberlineweb.client.DirectionHandler;
import com.wicam.numberlineweb.client.GameController;

public class DoppelungGameController extends GameController implements KeyDownHandler, KeyUpHandler, DirectionHandler {

	public static final int SHORTVOWELBUTTON = 0;
	public static final int LONGVOWELBUTTON = 1;
	private boolean arrowKeysEnabled = false;

	public DoppelungGameController(DoppelungGameCoordinator coordinator) {
		super(coordinator);
	}

	@Override
	public void onMouseDown(Widget who, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseUp(Widget who, int x, int y) {
		// TODO Auto-generated method stub

	}



	@Override
	public void onMouseMove(Widget who, int x, int y) {
		// TODO Auto-generated method stub

	}

	public void onStartButtonClick(){
		((DoppelungGameCoordinator) coordinator).startButtonClicked();
	}

	public void onShortVowelButtonClick(){
		((DoppelungGameCoordinator) coordinator).vowelButtonClicked(SHORTVOWELBUTTON);
	}

	public void onLongVowelButtonClick(){
		((DoppelungGameCoordinator) coordinator).vowelButtonClicked(LONGVOWELBUTTON);
	}

	public void wordEntered(String word){
		((DoppelungGameCoordinator) coordinator).wordEntered(word);
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		if (arrowKeysEnabled){
			event.preventDefault();

			int dir = 1;

			switch(event.getNativeKeyCode()) {

			case KeyCodes.KEY_DOWN:
				dir=1;
				break;
			case KeyCodes.KEY_UP:
				dir=2;
				break;
			case KeyCodes.KEY_LEFT:
				dir=4;
				break;
			case KeyCodes.KEY_RIGHT:
				dir=3;
				break;

			}

			keyDown(dir);
		}
	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
		if (arrowKeysEnabled){
			int dir = 1;

			switch(event.getNativeKeyCode()) {

			case KeyCodes.KEY_DOWN:
				dir=1;
				break;
			case KeyCodes.KEY_UP:
				dir=2;
				break;
			case KeyCodes.KEY_LEFT:
				dir=4;
				break;
			case KeyCodes.KEY_RIGHT:
				dir=3;
				break;

			}

			keyUp(dir);
		}
	}

	public boolean isArrowKeysEnabled() {
		return arrowKeysEnabled;
	}

	public void setArrowKeysEnabled(boolean arrowKeysEnabled) {
		this.arrowKeysEnabled = arrowKeysEnabled;
	}

	@Override
	public void keyDown(int dir) {
		((DoppelungGameCoordinator) coordinator).moveImageOnGamePanel(false,dir);


	}

	@Override
	public void keyUp(int dir) {
		((DoppelungGameCoordinator) coordinator).moveImageOnGamePanel(true,dir);


	}

}
