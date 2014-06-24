package com.wicam.numberlineweb.client.Letris;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Widget;
import com.wicam.numberlineweb.client.DirectionHandler;
import com.wicam.numberlineweb.client.GameController;

/**
 * Control the user interaction of the Letris game and fire handling methods of the coordinator.
 * @author timfissler
 *
 */

public class LetrisGameController extends GameController implements KeyDownHandler, KeyUpHandler, DirectionHandler {

	private boolean keysEnabled = false;

	public LetrisGameController(LetrisGameCoordinator coordinator) {
		super(coordinator);
	}

	@Override
	public void onMouseDown(Widget who, int x, int y) {

	}

	@Override
	public void onMouseUp(Widget who, int x, int y) {

	}

	@Override
	public void onMouseMove(Widget who, int x, int y) {

	}

	public void onStartButtonClick(){
		((LetrisGameCoordinator) coordinator).startButtonClicked();
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		if (keysEnabled){
			event.preventDefault();

			int id = 1;

			switch(event.getNativeKeyCode()) {

			case KeyCodes.KEY_DOWN:
				id=1;
				break;
			case KeyCodes.KEY_UP:
				id=2;
				break;
			case KeyCodes.KEY_LEFT:
				id=4;
				break;
			case KeyCodes.KEY_RIGHT:
				id=3;
				break;
			case KeyCodes.KEY_SPACE:
				id=5;
				break;
			case KeyCodes.KEY_W:
				id=6;
				break;
			case KeyCodes.KEY_P:
				id=7;
				break;
			}
			keyDown(id);
		}
	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
		if (keysEnabled){
			int id = 1;

			switch(event.getNativeKeyCode()) {

			case KeyCodes.KEY_DOWN:
				id=1;
				break;
			case KeyCodes.KEY_UP:
				id=2;
				break;
			case KeyCodes.KEY_LEFT:
				id=4;
				break;
			case KeyCodes.KEY_RIGHT:
				id=3;
				break;
			case KeyCodes.KEY_SPACE:
				id=5;
				break;
			case KeyCodes.KEY_W:
				id=6;
				break;
			case KeyCodes.KEY_P:
				id=7;
				break;
			}
			keyUp(id);
		}
	}

	public boolean isKeysEnabled() {
		return keysEnabled;
	}

	public void setKeysEnabled(boolean arrowKeysEnabled) {
		this.keysEnabled = arrowKeysEnabled;
	}

	@Override
	public void keyDown(int id) {
		((LetrisGameCoordinator) coordinator).handleKeyStroke(false,id);
	}

	@Override
	public void keyUp(int id) {
		((LetrisGameCoordinator) coordinator).handleKeyStroke(true,id);
	}

}
