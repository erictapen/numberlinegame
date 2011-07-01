package com.wicam.numberlineweb.server.VowelGame;

import java.util.ArrayList;

public abstract class TargetConsonantListCreater {

	public abstract ArrayList<String> createTargetConsonantList(String correctPair, int numberOfCorrectPairs, int numberOfPairs);
	
	// own shuffle implementation, because GWT does not support Collections.shuffle()
	protected static void shuffleList(ArrayList<String> list){
		for (int i = 0; i < list.size(); i++){
			int randomPosition = (int)(Math.random()*list.size());
			String tmp = list.get(i);
			list.set(i, list.get(randomPosition));
			list.set(randomPosition,tmp);
		}
	}
	
}
