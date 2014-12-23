package com.wicam.numberlineweb.server.SpellingAssessment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.wicam.numberlineweb.client.SpellingAssessment.SpellingAssessmentItem;


/**
 * Stores a shuffled version of the math task items of the assessment.
 * Items may be retrieved with the popItem()-method.
 * @author timfissler
 *
 */

public class SpellingAssessmentItemStack implements IsSerializable{

	/**
	 * Randomizer to shuffle the list items.
	 */
	private Random rand = new Random();
	/**
	 * List that contains the items.
	 */
	private List<SpellingAssessmentItem> items = new LinkedList<SpellingAssessmentItem>();
	
	/**
	 * Create a new item stack.
	 */
	public SpellingAssessmentItemStack() {
		
		// Set all items.
		setItems();

		// Shuffle the items.
		rand.setSeed(System.currentTimeMillis());
		Collections.shuffle(items, rand);
		
	}
	
	/**
	 * Check whether the item stack is empty.
	 * @return
	 */
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	/**
	 * Pop the first item of the stack. (Return
	 * and remove it from the stack.)
	 * @return
	 */
	public SpellingAssessmentItem popItem() {
		SpellingAssessmentItem item = items.get(0);
		items.remove(0);
		return item;
	}
	
	private void setItems() {
		items.add(new SpellingAssessmentItem("Eine Bananenschale ist ________.", "Banane.wav", "Abfall"));
		items.add(new SpellingAssessmentItem("Sonne, Mond und ________.", "Sterne.wav", "Sterne"));
		items.add(new SpellingAssessmentItem("Hoch auf den gelben ________.", "Wagen.wav", "Wagen"));
		
	}
	
}
