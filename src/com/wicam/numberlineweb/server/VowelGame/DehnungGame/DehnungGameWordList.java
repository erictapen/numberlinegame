package com.wicam.numberlineweb.server.VowelGame.DehnungGame;

import java.util.ArrayList;
import java.util.Collections;

import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;


public class DehnungGameWordList {
	
	public static ArrayList<VowelGameWord> createWordList(){
		ArrayList<VowelGameWord> wordList = new ArrayList<VowelGameWord>();
		VowelGameWord gameWord = new VowelGameWord();
		gameWord.setWord("Hahn");
		gameWord.setConsonantPair("hn");
		gameWord.setShortVowel(false);
		wordList.add(gameWord);
		gameWord = new VowelGameWord();
		gameWord.setWord("Bahn");
		gameWord.setConsonantPair("hn");
		gameWord.setShortVowel(false);
		wordList.add(gameWord);
		gameWord = new VowelGameWord();
		gameWord.setWord("Vater");
		gameWord.setConsonantPair("keinh");
		gameWord.setShortVowel(false);
		wordList.add(gameWord);
		gameWord = new VowelGameWord();
		gameWord.setWord("Boden");
		gameWord.setConsonantPair("keinh");
		gameWord.setShortVowel(false);
		wordList.add(gameWord);
		
		gameWord = new VowelGameWord();
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
		
		Collections.shuffle(wordList);
		
		return wordList;
	}
}
