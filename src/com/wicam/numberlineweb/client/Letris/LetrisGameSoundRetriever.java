package com.wicam.numberlineweb.client.Letris;

import com.google.gwt.media.client.Audio;
import com.wicam.numberlineweb.client.VowelGame.VowelGameWord;

/**
 * Class for retrieving the wave-files
 * 
 * @author alex
 * @author timfissler
 *
 */

public class LetrisGameSoundRetriever {
	
	public static String getPath(VowelGameWord word) {
		
        String value = word.getWordString().replace("ä", "ae").replace("ö", "oe").replace("ü", "ue");
        value = value.replace("Ä", "Ae").replace("Ö", "Oe").replace("Ü", "Ue").replace("ß", "ss");
		
		String type = (word.isShortVowel()) ? "shortVowels" : "longVowels";
		
		return "Letris/" + type + "/" + value;
		
	}

	public static Audio getAudioElement(VowelGameWord word) {
		
		Audio audio = Audio.createIfSupported();
		
		if (!Audio.isSupported() || audio == null)
			return null;
			
		String path = getPath(word);
		
		audio.addSource(path + ".ogg", "audio/ogg; codecs=vorbis");
		audio.addSource(path + ".mp3", "audio/mpeg; codecs=MP3");

		return audio;
	}
	

}
