package com.wicam.numberlineweb.client.VowelGame;

import com.google.gwt.media.client.Audio;

/**
 * Class for retrieving the wave-files
 * 
 * @author alex
 *
 */

public class SoundRetriever {
	
	public static String getPath(VowelGameWord word, boolean slow) {
		
        String value = word.getWordString().replace("ä", "ae").replace("ö", "oe").replace("ü", "ue");
        value = value.replace("Ä", "Ae").replace("Ö", "Oe").replace("Ü", "Ue").replace("ß", "ss");
		
		String type = (word.isShortVowel()) ? "shortVowels" : "longVowels";
		String speed = slow ? "slow" : "normal";
		
		return "doppelungGame/" + type + "/" + speed + "/" + value;
		
	}

	public static Audio getAudioElement(VowelGameWord word, boolean slow) {
		
		Audio audio = Audio.createIfSupported();
		
		if (!Audio.isSupported() || audio == null)
			return null;
			
		String path = getPath(word, slow);
		
		audio.addSource(path + ".ogg", "audio/ogg; codecs=vorbis");
		audio.addSource(path + ".mp3", "audio/mpeg; codecs=MP3");

		return audio;
	}
	

}
