package com.wicam.numberlineweb.server.DoppelungGame;

import java.util.ArrayList;
import java.util.Collections;

public class DoppelungGameConsonantPairListCreater {

	private static final String[] pairs = new String[]{"tt", "ss", "mm", "ll", "nn", "pp", "rr", "ck", "lt", "rt"};
	
	public static ArrayList<String> createConsonantPairList(String correctPair, int numberOfCorrectPairs, int numberOfPairs){
		ArrayList<String> list = new ArrayList<String>();
		// add false ones
		for (int i = 0; i < numberOfPairs-numberOfCorrectPairs; i++){
			int index = 0;
			// ensure other pairs than the correct one
			do{
				index = (int)(Math.random()*pairs.length);
			}
			while(pairs[index].equals(correctPair));
			list.add(pairs[index]);
		}
		// add the correct ones
		for (int i = 0; i < numberOfCorrectPairs; i++)
			list.add(correctPair);
		Collections.shuffle(list);
		return list;
	}
}
