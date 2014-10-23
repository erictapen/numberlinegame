package com.wicam.numberlineweb.server.SpellingAssessment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * Stores the math task items of the assessment.
 * @author timfissler
 *
 */

public class SpellingAssessmentItemList implements IsSerializable{

	private Random rand = new Random();
	private ArrayList<SpellingAssessmentItem> items = new ArrayList<SpellingAssessmentItem>();
	
	public SpellingAssessmentItemList() {
		init();
	}
	
	/**
	 * Get a shuffled version of the item list.
	 * @return
	 */
	public ArrayList<SpellingAssessmentItem> getShuffledItemList() {
		rand.setSeed(System.currentTimeMillis());
		Collections.shuffle(items, rand);
		return this.items;
	}
	
	private void init() {
		items.add(new SpellingAssessmentItem("Eine Bananenschale ist ________.", "Banane.wav", "Abfall"));
		
	}
	
}
