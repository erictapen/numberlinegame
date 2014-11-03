package com.wicam.numberlineweb.client.LetrisPush;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.wicam.numberlineweb.client.Player;

public class LetrisPushGamePlayer extends Player {
	
	// TODO Change that appropriately for the use in the Letris game.
	
	private boolean startButtonClicked = false;
	private boolean answer = false;
	private int soundTries = 0;
	private int wordTries = 0;
	private boolean showSoundFeedback = false;
	private boolean showWordFeedback = false;
	private boolean endedShortVowelGame = false;
	private int posX = 270;
	private int posY = 330;

	public void setStartButtonClicked(boolean startButtonClicked) {
		this.startButtonClicked = startButtonClicked;
	}

	public boolean isStartButtonClicked() {
		return startButtonClicked;
	}
	
	public void setAnswer(boolean answer){
		this.answer = answer;
	}
	
	public boolean hasCorrectlyAnswered(){
		return answer;
	}
	
	public void incSoundTries(){
		soundTries++;
	}
	
	public int getSoundTries(){
		return soundTries;
	}
	
	public void resetSoundTries(){
		soundTries = 0;
	}
	
	public void incWordTries(){
		wordTries++;
	}
	
	public int getWordTries(){
		return wordTries;
	}
	
	public void resetWordTries(){
		wordTries = 0;
	}
	
	public boolean isShowSoundFeedback() {
		return showSoundFeedback;
	}

	public void setShowSoundFeedback(boolean soundFeedback) {
		this.showSoundFeedback = soundFeedback;
	}
	
	public boolean isShowWordFeedback() {
		return showWordFeedback;
	}

	public void setShowWordFeedback(boolean wordFeedback) {
		this.showWordFeedback = wordFeedback;
	}

	public void setEndedShortVowelGame(boolean endedShortVowelGame) {
		this.endedShortVowelGame = endedShortVowelGame;
	}

	public boolean isEndedShortVowelGame() {
		return endedShortVowelGame;
	}

	public int getXPos(){
		return posX;
	}
	
	public int getYPos(){
		return posY;
	}
	
	public void setXPos(int x){
		this.posX = x;
	}
	
	public void setYPos(int y){
		this.posY = y;
	}
}
