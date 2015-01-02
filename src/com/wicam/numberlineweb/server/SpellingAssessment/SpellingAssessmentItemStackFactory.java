package com.wicam.numberlineweb.server.SpellingAssessment;

import java.util.Random;
import com.wicam.numberlineweb.client.SpellingAssessment.SpellingAssessmentItem;

/**
 * Class for creating a new spelling assessment item stack. Only
 * use this class for creating a new stack.
 * @author timfissler
 *
 */

public class SpellingAssessmentItemStackFactory {
	
	/**
	 * The factory to create new items.
	 */
	protected SpellingAssessmentItemFactory itemFactory;
	
	/**
	 * Randomizer to shuffle the items.
	 */
	private Random rand;
	
	/**
	 * Create a new item stack factory.
	 */
	public SpellingAssessmentItemStackFactory() {
		itemFactory = new SpellingAssessmentItemFactory();
		
		rand = new Random();
		rand.setSeed(System.currentTimeMillis());
	}
	
	/**
	 * Get a new item stack.
	 * @return
	 */
	public SpellingAssessmentItemStack getItemStack() {
		SpellingAssessmentItemStack stack = new SpellingAssessmentItemStack();
		fillStack(stack);
		stack.shuffle(rand);
		return stack;
	}
	
	/**
	 * Fill the stack with items.
	 * @param stack
	 */
	private void fillStack(SpellingAssessmentItemStack stack) {
		stack.pushItem(itemFactory.getItem("Auf der Weide steht ein kleines ________.", "Pony"));
		stack.pushItem(itemFactory.getItem("Meine Schwester ist ein Nesthäkchen, sie wird meistens ________.", "verwöhnt"));
	}

}
