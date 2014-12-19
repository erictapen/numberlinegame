package com.wicam.numberlineweb.server.MathAssessment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Stores a shuffled version of the math task items of the assessment.
 * Items may be retrieved with the popItem()-method.
 * @author timfissler
 *
 */

public class MathAssessmentItemStack implements IsSerializable {

	/**
	 * Randomizer to shuffle the list items.
	 */
	private Random rand = new Random();
	/**
	 * List that contains the items.
	 */
	private List<String> items = new LinkedList<String>();
	
	/**
	 * Create a new item stack.
	 */
	public MathAssessmentItemStack() {
		
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
	public String popItem() {
		String item = items.get(0);
		items.remove(0);
		return item;
	}
	
	/**
	 * Initially set all stack items.
	 */
	private void setItems() {
		items.add("15 × 30");
		items.add("8 ÷ 2");
		items.add("3 + 39");
		items.add("50 - 8");
	}
	
}
