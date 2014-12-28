package com.wicam.numberlineweb.client.SpellingAssessment;

import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Provides an item for the Spelling Assessment with a sentence, 
 * an audio file that will be played, and the right result for the task
 * @author svenfillinger
 *
 */
public class SpellingAssessmentItem implements IsSerializable{

	/**
	 * contains the sentence for this item that shall be displayed
	 */
	private String sentence;
	
	
	/**
	 * contains the right result for this task
	 */
	private String result;
	
	/**
	 * The constructor for a SpellingAssessment object
	 * @param sentence
	 * @param audioFile
	 * @param result
	 */
	public SpellingAssessmentItem(String sentence, String result){
		this.sentence = sentence;
		this.result = result;
	}
	
	/**
	 * Default constructor
	 */
	public SpellingAssessmentItem(){
		this.sentence = "";
		this.result = "";
	}
	
	/**
	 * gets an audio object of the item word (result)
	 * @return
	 */
	public Audio getResultAudio(){
		return SpellingAssessmentSoundRetriever.getAudioElement(this, false);
	}
	
	/**
	 * gets an audio object of the item sentence
	 * @return
	 */
	public Audio getSentenceAudio(){
		return SpellingAssessmentSoundRetriever.getAudioElement(this, true);
	}
	
	
	/**
	 * Get method for the sentence of the current task item
	 * @return String sentence
	 */
	public String getSentence(){
		return this.sentence;
	}
	
	/**
	 * Get method for the result, e.g. the correct word that should have been entered
	 * @return String result
	 */
	public String getResult(){
		return this.result;
	}
	
	
	@Override
	public String toString(){
		String string = "";
		
		string += "Item overview:\n";
		string += "sentence:\t" + this.sentence + "\n";
		string += "result:\t\t" + this.result + "\n";

		return string;
	}
	
	/**
	 * Return a string for logging the item, that contains
	 * the sentence and the correct result.
	 * @return
	 */
	public String logEntry() {
		return this.sentence + " (" + this.result + ")";
	}
	
	
//	public static void main(String args[]){
//		SpellingAssessmentItem myItem = new SpellingAssessmentItem("Die Banane ist _______", "Banane.wav", "krumm");
//		System.out.print(myItem.toString());
//		System.out.print(myItem.logEntry());
//	}
}
