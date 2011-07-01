package com.wicam.numberlineweb.server.VowelGame.DoppelungGame;

import java.util.ArrayList;
import java.util.Collections;

import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;


public class DoppelungGameWordList {
	
	public static ArrayList<VowelGameWord> createWordList(){
		ArrayList<VowelGameWord> wordList = new ArrayList<VowelGameWord>();
		VowelGameWord gameWord = new VowelGameWord();
		gameWord.setWord("Hammer");
		gameWord.setConsonantPair("mm");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		gameWord = new VowelGameWord();
		gameWord.setWord("Kummer");
		gameWord.setConsonantPair("mm");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		gameWord = new VowelGameWord();
		gameWord.setWord("Kamm");
		gameWord.setConsonantPair("mm");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		gameWord = new VowelGameWord();
		gameWord.setWord("Unfall");
		gameWord.setConsonantPair("ll");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		gameWord = new VowelGameWord();
		gameWord.setWord("Riss");
		gameWord.setConsonantPair("ss");
		gameWord.setShortVowel(true);
		wordList.add(gameWord);
		
		gameWord = new VowelGameWord();
		gameWord.setWord("Hose");
		gameWord.setShortVowel(false);
		wordList.add(gameWord);
		gameWord = new VowelGameWord();
		gameWord.setWord("Fehler");
		gameWord.setShortVowel(false);
		wordList.add(gameWord);
		gameWord = new VowelGameWord();
		gameWord.setWord("Frühling");
		gameWord.setShortVowel(false);
		wordList.add(gameWord);
		gameWord = new VowelGameWord();
		gameWord.setWord("Drehung");
		gameWord.setShortVowel(false);
		wordList.add(gameWord);
		gameWord = new VowelGameWord();
		gameWord.setWord("Sieger");
		gameWord.setShortVowel(false);
		wordList.add(gameWord);
		
		Collections.shuffle(wordList);
		
		return wordList;
	}
}
