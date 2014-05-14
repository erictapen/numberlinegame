package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;
import java.util.HashMap;

import com.wicam.numberlineweb.client.GameState;

/**
 * Class that carries the game state of the LeTris game that should be transfered between
 * client and server.
 * @author timfissler
 *
 */

public class LetrisGameState extends GameState {

	/**
	 * The word that is currently being displayed letter block by letter block. 
	 */
	private HashMap<Integer, String> playerId2CurrentWord = new HashMap<Integer, String>();
	/**
	 * The letter blocks that have already been set on the bottom of the playground. 
	 */
	private HashMap<Integer, ArrayList<LetrisGameLetterBlock>> playerId2StaticLetterBlocks = new HashMap<Integer, ArrayList<LetrisGameLetterBlock>>();
	/**
	 * The one letter block that currently moves from top to bottom, e.g. is being dropped.
	 */
	private HashMap<Integer, LetrisGameLetterBlock> playerId2MovingLetterBlock = new HashMap<Integer, LetrisGameLetterBlock>();
	/**
	 * The letter blocks that are waiting to be displayed.
	 */
	private HashMap<Integer, ArrayList<LetrisGameLetterBlock>> playerId2LetterBlocksToBeDisplayed = new HashMap<Integer, ArrayList<LetrisGameLetterBlock>>();
	/**
	 * The words that were build correctly by the player. 
	 */
	private HashMap<Integer, ArrayList<String>> playerId2CorrectWords = new HashMap<Integer, ArrayList<String>>();
	/**
	 * The words to be recognized. E.g. the words that are being or have been displayed
	 * but weren't build correctly by the player yet.
	 */
	private HashMap<Integer, ArrayList<String>> playerId2OutstandingWords = new HashMap<Integer, ArrayList<String>>();
	
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

	public String getCurrentWord(int playerId) {
		return playerId2CurrentWord.get(playerId);
	}
	
	public void setCurrentWord(int playerId, String currentWord) {
		this.playerId2CurrentWord.put(playerId, currentWord);
	}

	public ArrayList<LetrisGameLetterBlock> getStaticLetterBlocks(int playerId) {
		return playerId2StaticLetterBlocks.get(playerId);
	}
	
	public void setStaticLetterBlocks(int playerId, ArrayList<LetrisGameLetterBlock> staticLetterBlocks) {
		this.playerId2StaticLetterBlocks.put(playerId, staticLetterBlocks);
	}
	
	public void addStaticLetterBlock(int playerId, LetrisGameLetterBlock letterBlock) {
		if (this.playerId2StaticLetterBlocks.get(playerId) == null) {
			this.playerId2StaticLetterBlocks.put(playerId, new ArrayList<LetrisGameLetterBlock>());
		}
		this.playerId2StaticLetterBlocks.get(playerId).add(letterBlock);
	}
	
	public void removeStaticLetterBlock(int playerId, LetrisGameLetterBlock letterBlock) {
		this.playerId2StaticLetterBlocks.get(playerId).remove(letterBlock);
	}

	public LetrisGameLetterBlock getMovingLetterBlock(int playerId) {
		return playerId2MovingLetterBlock.get(playerId);
	}
	
	public void setMovingLetterBlock(int playerId, LetrisGameLetterBlock movingLetterBlock) {
		this.playerId2MovingLetterBlock.put(playerId, movingLetterBlock);
	}

	public ArrayList<LetrisGameLetterBlock> getLetterBlocksToBeDisplayed(int playerId) {
		return playerId2LetterBlocksToBeDisplayed.get(playerId);
	}
	
	public void setLetterBlocksToBeDisplayed(int playerId, ArrayList<LetrisGameLetterBlock> letterBlocksToBeDisplayed) {
		this.playerId2LetterBlocksToBeDisplayed.put(playerId, letterBlocksToBeDisplayed);
	}
	
	public void addLetterBlockToBeDisplayed(int playerId, LetrisGameLetterBlock letterBlock) {
		if (this.playerId2LetterBlocksToBeDisplayed.get(playerId) == null) {
			this.playerId2LetterBlocksToBeDisplayed.put(playerId, new ArrayList<LetrisGameLetterBlock>());
		}
		this.playerId2LetterBlocksToBeDisplayed.get(playerId).add(letterBlock);
	}
	
	public void removeLetterBlockToBeDisplayed(int playerId, LetrisGameLetterBlock letterBlock) {
		this.playerId2LetterBlocksToBeDisplayed.get(playerId).remove(letterBlock);
	}

	public ArrayList<String> getCorrectWords(int playerId) {
		return playerId2CorrectWords.get(playerId);
	}
	
	public void setCorrectWords(int playerId, ArrayList<String> correctWords) {
		this.playerId2CorrectWords.put(playerId, correctWords);
	}
	
	public void addCorrectWord(int playerId, String correctWord) {
		if (this.playerId2CorrectWords.get(playerId) == null) {
			this.playerId2CorrectWords.put(playerId, new ArrayList<String>());
		}
		this.playerId2CorrectWords.get(playerId).add(correctWord);
	}
	
	public void removeCorrectWord(int playerId, String correctWord) {
		this.playerId2CorrectWords.get(playerId).remove(correctWord);
	}

	public ArrayList<String> getOutstandingWords(int playerId) {
		return playerId2OutstandingWords.get(playerId);
	}
	
	public void setOutstandingWords(int playerId, ArrayList<String> outstandingWords) {
		this.playerId2OutstandingWords.put(playerId, outstandingWords);
	}
	
	public void addOutstandingWord(int playerId, String outstandingWord) {
		if (this.playerId2OutstandingWords.get(playerId) == null) {
			this.playerId2OutstandingWords.put(playerId, new ArrayList<String>());
		}
		this.playerId2OutstandingWords.get(playerId).add(outstandingWord);
	}
	
	public void removeOutstandingWord(int playerId, String outstandingWord) {
		this.playerId2OutstandingWords.get(playerId).remove(outstandingWord);
	}
	
	public String toString(int playerId) {
		String s = super.toString();
		s += "\ncurrent word: " + getCurrentWord(playerId);
		s += "\nstatic letter blocks: " + getStaticLetterBlocks(playerId);
		s += "\nmoving letter block: " + getMovingLetterBlock(playerId);
		s += "\nletter blocks to be displayed: " + getLetterBlocksToBeDisplayed(playerId);
		s += "\ncorrect words: " + getCorrectWords(playerId);
		s += "\noutstanding words: " + getOutstandingWords(playerId);
		return s;
	}
	
}