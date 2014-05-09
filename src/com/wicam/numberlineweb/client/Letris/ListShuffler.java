package com.wicam.numberlineweb.client.Letris;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Implementation of a shuffle method for generic lists according to
 * com.wicam.numberlineweb.server.VowelGame.TargetConsonantListCreater
 * @author timfissler
 *
 */

public class ListShuffler implements IsSerializable {
	
	public static <T> void shuffleList(ArrayList<T> list){
		for (int i = 0; i < list.size(); i++){
			int randomPosition = (int)(Math.random()*list.size());
			T tmp = list.get(i);
			list.set(i, list.get(randomPosition));
			list.set(randomPosition,tmp);
		}
	}
	
}
