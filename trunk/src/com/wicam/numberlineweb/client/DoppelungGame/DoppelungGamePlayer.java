package com.wicam.numberlineweb.client.DoppelungGame;

import com.wicam.numberlineweb.client.Player;

public class DoppelungGamePlayer extends Player {
	
	private boolean startButtonClicked = false;
	private boolean answer = false;
	private int soundTries = 0;
	private int wordTries = 0;
	private boolean showSoundFeedback = false;
	private boolean showWordFeedback = false;
	private boolean endedShortVowelGame = false;
	private ConsonantPoint2D playerCoords = new ConsonantPoint2D();

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

	public ConsonantPoint2D getPlayerCoords() {
		return playerCoords;
	}
}
