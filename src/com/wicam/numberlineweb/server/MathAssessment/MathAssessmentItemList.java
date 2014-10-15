package com.wicam.numberlineweb.server.MathAssessment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Stores the math task items of the assessment.
 * @author timfissler
 *
 */

public class MathAssessmentItemList {

	private Random rand = new Random();
	private ArrayList<String> items = new ArrayList<String>();
	
	public MathAssessmentItemList() {
		init();
	}
	
	/**
	 * Get a shuffled version of the item list.
	 * @return
	 */
	public ArrayList<String> getShuffledItemList() {
		rand.setSeed(System.currentTimeMillis());
		Collections.shuffle(items, rand);
		return this.items;
	}
	
	private void init() {
		items.add("15 × 30");
		items.add("8 ÷ 2");
		items.add("3 + 39");
		items.add("50 - 8");
	}
	
}
