package com.wicam.numberlineweb.server.SpellingAssessment;

import java.io.File;
import java.util.HashMap;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;

import com.wicam.numberlineweb.client.SpellingAssessment.SpellingAssessmentItem;
import com.wicam.numberlineweb.client.SpellingAssessment.SpellingAssessmentSoundRetriever;

/**
 * Class for creating spelling assessment items. 
 * @author timfissler
 *
 */

public class SpellingAssessmentItemFactory {
	
	/**
	 * Store the sentence durations that already were computed.
	 */
	private HashMap<SpellingAssessmentItem, Integer> item2sentenceDuration;
	
	/**
	 * Store the result durations that already were computed.
	 */
	private HashMap<SpellingAssessmentItem, Integer> item2resultDuration;
	
	/**
	 * Create a new factory.
	 */
	public SpellingAssessmentItemFactory() {
		item2sentenceDuration = new HashMap<SpellingAssessmentItem, Integer>();
		item2resultDuration = new HashMap<SpellingAssessmentItem, Integer>();
	}
	
	/**
	 * Use this method to create new spelling assessment items.
	 * @param sentence
	 * @param result
	 * @return
	 */
	public SpellingAssessmentItem getItem(String sentence, String result) {
		SpellingAssessmentItem item = new SpellingAssessmentItem(sentence, result);
		calculateDurations(item);
		return item;
	}
	
	/**
	 * Compute the duration of sentence and result playback and store them.
	 */
	private void calculateDurations(SpellingAssessmentItem item) {
		// Get sentence duration.
		if (item2sentenceDuration.containsKey(item)) {
			item.setSentenceDuration(item2sentenceDuration.get(item));
		} else {
			try {
				File file = new File(SpellingAssessmentSoundRetriever.getPath(item, true) + ".mp3");

				AudioFile audioFile = AudioFileIO.read(file);
				int duration = (int)(((MP3AudioHeader)audioFile.getAudioHeader()).getPreciseTrackLength() * 1000);
				item.setSentenceDuration(duration);
				item2sentenceDuration.put(item, duration);

			} catch (Exception e) {
				System.out.println("Cannot calculate the sentence playback duration for \"" + item.getSentence() + "\"!");
				e.printStackTrace();
			}
		}
		// Get result duration.
		if (item2resultDuration.containsKey(item)) {
			item.setResultDuration(item2resultDuration.get(item));
		} else {
			try {
				File file = new File(SpellingAssessmentSoundRetriever.getPath(item, false) + ".mp3");

				AudioFile audioFile = AudioFileIO.read(file);
				int duration = (int)(((MP3AudioHeader)audioFile.getAudioHeader()).getPreciseTrackLength() * 1000);
				item.setResultDuration(duration);
				item2resultDuration.put(item, duration);

			} catch (Exception e) {
				System.out.println("Cannot calculate the result playback duration for \"" + item.getResult() + "\"!");
				e.printStackTrace();
			}
		}
	}

}
