package com.wicam.numberlineweb.client.DoppelungGame;

import com.google.gwt.user.client.ui.Widget;
import com.wicam.numberlineweb.client.GameController;

public class DoppelungGameController extends GameController {

	public static final int SHORTVOWELBUTTON = 0;
	public static final int LONGVOWELBUTTON = 1;
	
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
	
	public void endShortVowelGame(){
		((DoppelungGameCoordinator) coordinator).endShortVowelGame();
	}
	
	public void updatePoints(String consonants){
		((DoppelungGameCoordinator) coordinator).updatePoints(consonants);
	}

}
