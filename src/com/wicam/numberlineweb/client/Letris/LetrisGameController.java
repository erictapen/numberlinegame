package com.wicam.numberlineweb.client.Letris;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.Widget;
import com.wicam.numberlineweb.client.DirectionHandler;
import com.wicam.numberlineweb.client.GameController;

/**
 * Control the user interaction of the Letris game and fire handling methods of the coordinator.
 * @author timfissler
 *
 */

public class LetrisGameController extends GameController implements KeyDownHandler, KeyUpHandler, DirectionHandler, IsSerializable {

	private boolean arrowKeysEnabled = false;

	public LetrisGameController(LetrisGameCoordinator coordinator) {
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
		((LetrisGameCoordinator) coordinator).startButtonClicked();
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		// TODO Implement listening for space-key for dropping a letter.
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

			directionDown(dir);
		}
	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
		// TODO Implement listening for space-key for dropping a letter.
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

			directionUp(dir);
		}
	}

	public boolean isArrowKeysEnabled() {
		return arrowKeysEnabled;
	}

	public void setArrowKeysEnabled(boolean arrowKeysEnabled) {
		this.arrowKeysEnabled = arrowKeysEnabled;
	}

	@Override
	public void directionDown(int dir) {
		((LetrisGameCoordinator) coordinator).moveImageOnGamePanel(false,dir);
	}

	@Override
	public void directionUp(int dir) {
		((LetrisGameCoordinator) coordinator).moveImageOnGamePanel(true,dir);
	}

}
