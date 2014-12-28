package com.wicam.numberlineweb.client.SpellingAssessment;

import com.google.gwt.media.client.Audio;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;

/**
 * Class for retrieving the wave-files
 * 
 * @author alex
 * @author timfissler
 *
 */

public class SpellingAssessmentSoundRetriever {
	
	private static String getPath(SpellingAssessmentItem item, boolean getSentence) {
		
		String type;
		String value = item.getResult().replace("??", "ae").replace("??", "oe").replace("??", "ue");
		value = value.replace("??", "Ae").replace("??", "Oe").replace("??", "Ue").replace("??", "ss");
		
		if(getSentence){
			type = "sentences";
			value += "_s";
		} else{
			type = "item";
			value += "_i";
		}
		
		return "SpellingAssessment/audio_files/" + type + "/" + value;
		
	}

	public static Audio getAudioElement(SpellingAssessmentItem item, boolean getSentence) {
		
		Audio audio = Audio.createIfSupported();
		
		if (!Audio.isSupported() || audio == null)
			return null;
			
		String path = getPath(item, getSentence);
		
		audio.addSource(path + ".ogg", "audio/ogg; codecs=vorbis");
		audio.addSource(path + ".mp3", "audio/mpeg; codecs=MP3");

		return audio;
	}
	

}
