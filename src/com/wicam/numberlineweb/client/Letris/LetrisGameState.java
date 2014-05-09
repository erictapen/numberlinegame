package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;

import com.google.gwt.dev.util.collect.HashMap;
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
	 * The list of target words from that the current word is drawn randomly.
	 */
	private ArrayList<String> targetWords = new ArrayList<String>();
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

	public HashMap<Integer, String> getPlayerId2CurrentWord() {
		return playerId2CurrentWord;
	}

	public HashMap<Integer, ArrayList<LetrisGameLetterBlock>> getPlayerId2StaticLetterBlocks() {
		return playerId2StaticLetterBlocks;
	}

	public HashMap<Integer, LetrisGameLetterBlock> getPlayerId2MovingLetterBlock() {
		return playerId2MovingLetterBlock;
	}

	public HashMap<Integer, ArrayList<LetrisGameLetterBlock>> getPlayerId2LetterBlocksToBeDisplayed() {
		return playerId2LetterBlocksToBeDisplayed;
	}

	public HashMap<Integer, ArrayList<String>> getPlayerId2CorrectWords() {
		return playerId2CorrectWords;
	}

	public HashMap<Integer, ArrayList<String>> getPlayerId2OutstandingWords() {
		return playerId2OutstandingWords;
	}

	public ArrayList<String> getTargetWords() {
		return targetWords;
	}

	public void setTargetWords(ArrayList<String> targetWords) {
		this.targetWords = targetWords;
	}
	
}