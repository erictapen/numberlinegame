package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.collections.map.HashedMap;

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
	private HashMap<Integer, ArrayList<String>> playerId2MissingWords = new HashMap<Integer, ArrayList<String>>();
	
	public LetrisGameLetterBlock getMovingLetterBlock(int playerId) {
		return playerId2MovingLetterBlock.get(playerId);
	}
	
	public void setMovingLetterBlock(int playerId, LetrisGameLetterBlock letterBlock) {
		playerId2MovingLetterBlock.put(playerId, letterBlock);
	}

	public ArrayList<LetrisGameLetterBlock> getStaticLetterBlocks(int playerId) {
		return playerId2StaticLetterBlocks.get(playerId);
	}
	
	public void setStaticLetterBlocks(int playerId, ArrayList<LetrisGameLetterBlock> letterBlocks) {
		playerId2StaticLetterBlocks.put(playerId, letterBlocks);
	}
	
	public void addStaticLetterBlock(int playerId, LetrisGameLetterBlock letterBlock) {
		playerId2StaticLetterBlocks.get(playerId).add(letterBlock);
	}
	
	public void removeStaticLetterBlock(int playerId, LetrisGameLetterBlock letterBlock) {
		playerId2StaticLetterBlocks.get(playerId).remove(letterBlock);
	}

	public ArrayList<LetrisGameLetterBlock> getLetterBlocksToBeDisplayed(int playerId) {
		return playerId2LetterBlocksToBeDisplayed.get(playerId);
	}
	
	public void setLetterBlocksToBeDisplayed(int playerId, ArrayList<LetrisGameLetterBlock> letterBlocks) {
		playerId2LetterBlocksToBeDisplayed.put(playerId, letterBlocks);
	}
	
	public void addLetterBlockToBeDisplayed(int playerId, LetrisGameLetterBlock letterBlock) {
		playerId2LetterBlocksToBeDisplayed.get(playerId).add(letterBlock);
	}
	
	public void removeLetterBlockToBeDisplayed(int playerId, LetrisGameLetterBlock letterBlock) {
		playerId2LetterBlocksToBeDisplayed.get(playerId).remove(letterBlock);
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

	public String getCurrentWord(int playerId) {
		return playerId2CurrentWord.get(playerId);
	}
	
	public void setCurrentWord(int playerId, String currentWord) {
		playerId2CurrentWord.put(playerId, currentWord);
	}
	
	public ArrayList<String> getCorrectWords(int playerId) {
		return this.playerId2CorrectWords.get(playerId);
	}
	
	public void setCorrectWords(int playerId, ArrayList<String> correctWords) {
		playerId2CorrectWords.put(playerId, correctWords);
	}
	
	public void addCorrectWord(int playerId, String correctWord) {
		playerId2CorrectWords.get(playerId).add(correctWord);
	}
	
	public void removeCorrectWord(int playerId, String correctWord) {
		playerId2CorrectWords.get(playerId).remove(correctWord);
	}
	
	public ArrayList<String> getMissingWords(int playerId) {
		return this.playerId2MissingWords.get(playerId);
	}
	
	public void setMissingWords(int playerId, ArrayList<String> missingWords) {
		playerId2MissingWords.put(playerId, missingWords);
	}
	
	public void addMissingWord(int playerId, String missingWord) {
		playerId2MissingWords.get(playerId).add(missingWord);
	}
	
	public void removeMissingWord(int playerId, String missingWord) {
		playerId2MissingWords.get(playerId).remove(missingWord);
	}
	
}
