package com.wicam.numberlineweb.client.SpellingAssessment;

import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;

import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.wicam.numberlineweb.client.SpellingAssessment.SpellingAssessmentSoundRetriever;

/**
 * Provides an item for the Spelling Assessment with a sentence, 
 * and the right result for the task. Never create an item using
 * its constructor. Instead use SpellingAssessmentItemFactory to
 * guarantee right setup of the playback duration in the item.
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
	 * The duration of the sentence playback in ms.
	 */
	private int sentenceDuration; // ms
	
	/**
	 * The duration of the result playback in ms.
	 */
	private int resultDuration; // ms
	
	/**
	 * The constructor for a SpellingAssessment object. Calculate time
	 * duration for result and sentence playback.
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
		this.resultDuration = 0;
		this.sentenceDuration = 0;
	}
	
	/**
	 * Setter for the duration of the result playback.
	 * @param duration
	 */
	public void setResultDuration(int duration) {
		this.resultDuration = duration;
	}
	
	/**
	 * Setter for the duration of the sentence playback.
	 * @param duration
	 */
	public void setSentenceDuration(int duration) {
		this.sentenceDuration = duration;
	}
	
	/**
	 * Getter for the duration of the sentence playback.
	 * @return
	 */
	public int getSentenceDuration() {
		return this.sentenceDuration;
	}
	
	/**
	 * Getter for the duration of the result playback.
	 * @return
	 */
	public int getResultDuration() {
		return this.resultDuration;
	}	
	
	/**
	 * Getter method for the sentence of the current task item
	 * @return String sentence
	 */
	public String getSentence(){
		return this.sentence;
	}
	
	/**
	 * Getter method for the result, e.g. the correct word that should have been entered
	 * @return String result
	 */
	public String getResult(){
		return this.result;
	}
	
	
	@Override
	public String toString(){
		String string = "Item overview:\n" +
				"sentence:\t" + this.sentence + "\n" +
				"result:\t\t" + this.result + "\n" +
				"sentence duration:\t" + this.sentenceDuration + "\n" +
				"result duration:\t" + this.resultDuration + "\n";
		return string;
	}
	
	/**
	 * Return a string for logging the item, that contains
	 * the correct result.
	 * @return
	 */
	public String logEntry() {
//		return this.sentence + " (" + this.result + ")";
		return this.result;
	}
	
}
