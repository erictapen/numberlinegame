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

	
	private final int numOfTasks = 4;
	private final int biggestInt = 50;
	private final int kindsOfOperators = 4;
	private String multSign = "*";
	private String divSign = "/";
	private String addSign = "+";
	private String subSign = "-";
	
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
	 * Ballanced occurence of all kinds of operators
	 */
	private void setItems() {
		
		for(int i=0; i<numOfTasks; i++){
			int j = rand.nextInt(biggestInt);
			int k = rand.nextInt(biggestInt);
			
			switch (i%kindsOfOperators){
			case 0:
				items.add(j+" "+addSign+" "+k);
				break;
			case 1:
				while (k>j){
					k = rand.nextInt(biggestInt);
				}
				items.add(j+" "+subSign+" "+k);
				break;
			case 2:
				while (k>10 && k<=20 && j>10 && j<=20 && j!=k || k>20 && j>20){
					k = rand.nextInt(biggestInt);
				}
				items.add(j+" "+multSign+" "+k);
				break;
			case 3:
				while (j % k != 0 || k==0){
					k = rand.nextInt(biggestInt);
				}
				items.add(j+" "+divSign+" "+k);
				break;
			}
		}
	}
	
}
