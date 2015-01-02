package com.wicam.numberlineweb.server.SpellingAssessment;

/**
 * The class for creating the static training item stack 
 * @author svenfillinger
 *
 */
public class SpellingAssessmentTrainingItemStackFactory extends SpellingAssessmentItemStackFactory{
	
	/**
	 * Get a new item stack.
	 * @return
	 */
	public SpellingAssessmentItemStack getItemStack() {
		SpellingAssessmentItemStack stack = new SpellingAssessmentItemStack();
		fillStack(stack);
		return stack;
	}
	
	/**
	 * Fill the stack with items.
	 * @param stack
	 */
	private void fillStack(SpellingAssessmentItemStack stack) {
		// TODO replace by real training set
		stack.pushItem(itemFactory.getItem("In den Obstsalat gehört auch eine ________.", "Orange"));
		stack.pushItem(itemFactory.getItem("In den Obstsalat gehört auch eine ________.", "Orange"));
		stack.pushItem(itemFactory.getItem("In den Obstsalat gehört auch eine ________.", "Orange"));
		
	}

}
