package com.wicam.numberlineweb.server.VowelGame.DehnungGame;

import java.util.ArrayList;

import com.wicam.numberlineweb.server.VowelGame.TargetConsonantListCreater;

public class DehnungGameTargetConsonantListCreater extends TargetConsonantListCreater{

	private static final String[] pairs = new String[]{"hn", "hm", "hl", "hr", "keinh"};
	
	@Override
	public ArrayList<String> createTargetConsonantList(String correctPair, int numberOfCorrectPairs, int numberOfPairs){
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
		shuffleList(list);
		return list;
	}
	
}
