package com.wicam.numberlineweb.server.Letris;

import java.util.ArrayList;
import java.util.Collections;

import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;

/**
 * Create the list of target words for the Letris game.
 * @author timfissler
 *
 */
public class LetrisGameWordList {
	
	public static ArrayList<VowelGameWord> createWordList(){
		ArrayList<VowelGameWord> wordList = new ArrayList<VowelGameWord>();
		
		wordList.add(new VowelGameWord("Abfall", "ll"));
		wordList.add(new VowelGameWord("Biss", "ss"));
		wordList.add(new VowelGameWord("Gestrüpp", "pp"));
		wordList.add(new VowelGameWord("Kette", "tt"));
		wordList.add(new VowelGameWord("Lücke", "ck"));
		wordList.add(new VowelGameWord("Päckchen", "ck"));
		wordList.add(new VowelGameWord("Ritze", "tz"));
		wordList.add(new VowelGameWord("Schiff", "ff"));
		wordList.add(new VowelGameWord("Schlüssel", "ss"));
		wordList.add(new VowelGameWord("Tritt", "tt"));
		wordList.add(new VowelGameWord("Wette", "tt"));
		wordList.add(new VowelGameWord("bitter", "tt"));
		wordList.add(new VowelGameWord("hell", "ll"));
		wordList.add(new VowelGameWord("kippt", "pp"));
		wordList.add(new VowelGameWord("rollt", "ll"));
		wordList.add(new VowelGameWord("vergisst", "ss"));
		wordList.add(new VowelGameWord("voll", "ll"));
		
		
		// Check whether there are words in the list that are longer than 15 letters.
//		for (VowelGameWord word : wordList) {
//			if (word.getWordString().length() > 15) {
//				System.out.println(word);
//			}
//		}
		
		Collections.shuffle(wordList);
		
		return wordList;
	}
}
