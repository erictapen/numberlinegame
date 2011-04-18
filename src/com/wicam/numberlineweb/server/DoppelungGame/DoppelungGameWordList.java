package com.wicam.numberlineweb.server.DoppelungGame;

import java.util.ArrayList;
import java.util.Collections;

import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameWord;


public class DoppelungGameWordList {
	
	public static ArrayList<DoppelungGameWord> createWordList(){
		ArrayList<DoppelungGameWord> wordList = new ArrayList<DoppelungGameWord>();
		DoppelungGameWord gameWord = new DoppelungGameWord();
		gameWord.setWord("Hammer");
		gameWord.setConsonantPair("mm");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Kummer");
		gameWord.setConsonantPair("mm");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Kamm");
		gameWord.setConsonantPair("mm");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Unfall");
		gameWord.setConsonantPair("ll");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Riss");
		gameWord.setConsonantPair("ss");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Hose");
		gameWord.setShortVowel(false);
		wordList.add(gameWord);
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Fehler");
		gameWord.setShortVowel(false);
		wordList.add(gameWord);
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Frühling");
		gameWord.setShortVowel(false);
		wordList.add(gameWord);
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Drehung");
		gameWord.setShortVowel(false);
		wordList.add(gameWord);
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Sieger");
		gameWord.setShortVowel(false);
		wordList.add(gameWord);
		
		Collections.shuffle(wordList);
		
		return wordList;
	}
}
