package com.wicam.numberlineweb.server.DoppelungGame;

import java.util.ArrayList;
import java.util.Collections;

import com.wicam.numberlineweb.client.DoppelungGame.DoppelungGameWord;

public class DoppelungGameWordList {
	
	public static ArrayList<DoppelungGameWord> createWordList(){
		ArrayList<DoppelungGameWord> wordList = new ArrayList<DoppelungGameWord>();
		DoppelungGameWord gameWord = new DoppelungGameWord();
		gameWord.setWord("Hammer");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Kummer");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Kamm");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Unfall");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Riss");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Hose");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Fehler");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Frühling");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Drehung");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		gameWord = new DoppelungGameWord();
		gameWord.setWord("Sieger");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		
		Collections.shuffle(wordList);
		
		return wordList;
	}
}
