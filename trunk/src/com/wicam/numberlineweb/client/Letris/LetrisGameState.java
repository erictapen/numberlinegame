package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;
import java.util.HashMap;
import com.wicam.numberlineweb.client.GameState;

/**
 * Class that carries the game state of the LeTris game with its letter blocks.
 * @author timfissler
 *
 */

public class LetrisGameState extends GameState {

	/**
	 * The word that is currently being displayed letter block by letter block. 
	 */
	private String currentWord;
	/**
	 * The letter blocks that have already been set on the bottom of the playground. 
	 */
	private ArrayList<LetrisGameLetterBlock> staticLetterBlocks = new ArrayList<LetrisGameLetterBlock>();
	/**
	 * The one letter block that currently moves from top to bottom, e.g. is being dropped.
	 */
	private LetrisGameLetterBlock movingLetterBlock;
	/**
	 * The letter blocks that are waiting to be displayed.
	 */
	private ArrayList<LetrisGameLetterBlock> letterBlocksToBeDisplayed = new ArrayList<LetrisGameLetterBlock>();
	/**
	 * The words that were build correctly by the player. 
	 */
	private ArrayList<String> correctWords = new ArrayList<String>();
	/**
	 * The words to be recognized. E.g. the words that are being or have been displayed
	 * but weren't build correctly by the player yet.
	 */
	private ArrayList<String> missingWords = new ArrayList<String>();
	
	public LetrisGameLetterBlock getMovingLetterBlock() {
		return movingLetterBlock;
	}
	
	public void setMovingLetterBlock(LetrisGameLetterBlock letterBlock) {
		movingLetterBlock = letterBlock;
	}

	public ArrayList<LetrisGameLetterBlock> getStaticLetterBlocks() {
		return staticLetterBlocks;
	}
	
	public void setStaticLetterBlocks(ArrayList<LetrisGameLetterBlock> letterBlocks) {
		staticLetterBlocks = letterBlocks;
	}
	
	public void addStaticLetterBlock(LetrisGameLetterBlock letterBlock) {
		staticLetterBlocks.add(letterBlock);
	}
	
	public void removeStaticLetterBlock(LetrisGameLetterBlock letterBlock) {
		staticLetterBlocks.remove(letterBlock);
	}

	public ArrayList<LetrisGameLetterBlock> getLetterBlocksToBeDisplayed() {
		return letterBlocksToBeDisplayed;
	}
	
	public void setLetterBlocksToBeDisplayed(ArrayList<LetrisGameLetterBlock> letterBlocks) {
		letterBlocksToBeDisplayed = letterBlocks;
	}
	
	public void addLetterBlockToBeDisplayed(LetrisGameLetterBlock letterBlock) {
		letterBlocksToBeDisplayed.add(letterBlock);
	}
	
	public void removeLetterBlockToBeDisplayed(LetrisGameLetterBlock letterBlock) {
		letterBlocksToBeDisplayed.remove(letterBlock);
	}
	
	@Override
	public int addPlayer(String newName, int uid) {
		newName = checkDuplicateName(newName);
		LetrisGamePlayer newPlayer = new LetrisGamePlayer();
		newPlayer.setName(newName);
		newPlayer.setUid(uid);
		players.add(newPlayer);
		return players.size();
	}
	
	public void setStartButtonClicked(int playerid, boolean startButtonClicked) {
		((LetrisGamePlayer)players.get(playerid-1)).setStartButtonClicked(startButtonClicked);
	}

	public boolean hasStartButtonClicked(int playerid) {
		if (playerid-1 < players.size())
			return ((LetrisGamePlayer)players.get(playerid-1)).isStartButtonClicked();
		return false;
	}

	public String getCurrentWord() {
		return currentWord;
	}
	
	public void setCurrentWord(String currentWord) {
		this.currentWord = currentWord;
	}
	
	public ArrayList<String> getCorrectWords() {
		return correctWords;
	}
	
	public void setCorrectWords(ArrayList<String> correctWords) {
		this.correctWords = correctWords;
	}
	
	public void addCorrectWord(String correctWord) {
		correctWords.add(correctWord);
	}
	
	public void removeCorrectWord(String correctWord) {
		correctWords.remove(correctWord);
	}
	
	public ArrayList<String> getMissingWords() {
		return missingWords;
	}
	
	public void setMissingWords(ArrayList<String> missingWords) {
		this.missingWords = missingWords;
	}
	
	public void addMissingWord(String missingWord) {
		missingWords.add(missingWord);
	}
	
	public void removeMissingWord(String missingWord) {
		missingWords.remove(missingWord);
	}
	
	public String toString() {
		String s = super.toString();
		s += "\ncurrent word: " + getCurrentWord();
		s += "\nstatic letter blocks: " + getStaticLetterBlocks();
		s += "\nmoving letter block: " + getMovingLetterBlock();
		s += "\nletter blocks to be displayed: " + getLetterBlocksToBeDisplayed();
		s += "\ncorrect words: " + getCorrectWords();
		s += "\nmissing words: " + getMissingWords();
		return s;
	}
	
}
