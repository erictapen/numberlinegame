package com.wicam.numberlineweb.server.SpellingAssessment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.wicam.numberlineweb.client.SpellingAssessment.SpellingAssessmentItem;


/**
 * Stores items of the assessment. Items may be retrieved with the popItem()-method.
 * @author timfissler
 *
 */

public class SpellingAssessmentItemStack implements IsSerializable{

	/**
	 * List that contains the items.
	 */
	private List<SpellingAssessmentItem> items;
	
	/**
	 * Create a new item stack.
	 */
	public SpellingAssessmentItemStack() {
		items = new LinkedList<SpellingAssessmentItem>();
	}
	
	/**
	 * Shuffle the stack given a randomizer.
	 * @param rand
	 */
	public void shuffle(Random rand) {
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
	
	/**
	 * Push the given item to the first position of the stack.
	 * @param item
	 */
	public void pushItem(SpellingAssessmentItem item) {
		items.add(0, item);
	}
	
}
