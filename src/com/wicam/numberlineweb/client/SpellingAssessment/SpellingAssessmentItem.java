package com.wicam.numberlineweb.client.SpellingAssessment;

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
	 * contains the info, which audio file is to be played
	 */
	private String audioFile;
	
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
	public SpellingAssessmentItem(String sentence, String audioFile, String result){
		this.sentence = sentence;
		this.audioFile = audioFile;
		this.result = result;
	}
	
	/**
	 * Default constructor
	 */
	public SpellingAssessmentItem(){
		this.sentence = "";
		this.audioFile = "";
		this.result = "";
	}
	
	/**
	 * Get method for the sentence of the current task item
	 * @return String sentence
	 */
	public String getSentence(){
		return this.sentence;
	}
	
	/**
	 * Get method for the result, so the correct word that should have been entered
	 * @return String result
	 */
	public String result(){
		return this.result;
	}
	
	/**
	 * Get method for the audio-file that is bound to the task item
	 * @return String audio-file
	 */
	public String getAudioFile(){
		return this.audioFile;
	}
	
	/**
	 * @Overide
	 */
	public String toString(){
		String string = "";
		
		string += "Task overview:\n---------\n";
		string += "sentence:\t" + this.sentence + "\n";
		string += "audio-file:\t" + this.audioFile + "\n";
		string += "result:\t\t" + this.result + "\n";

		return string;
	}
	
	
//	public static void main(String args[]){
//		SpellingAssessmentItem myItem = new SpellingAssessmentItem("Die Banane ist _______", "Banane.wav", "krumm");
//		System.out.print(myItem.toString());
//	}
}
