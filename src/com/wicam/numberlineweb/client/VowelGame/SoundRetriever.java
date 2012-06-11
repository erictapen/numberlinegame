package com.wicam.numberlineweb.client.VowelGame;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;

/**
 * Class for retrieving the wave-files
 * 
 * @author alex
 *
 */

public class SoundRetriever {
	

	public static Sound getSound(SoundController sc, VowelGameWord word){
		return getSound(sc, word, false);
	}

	public static Sound getSound(SoundController sc, VowelGameWord word, boolean slow) {
	
		sc.setDefaultVolume(100);
		
		String value = word.getWordString().replace("ä", "ae").replace("ö", "oe").replace("ü", "ue");
		value = value.replace("Ä", "Ae").replace("Ö", "Oe").replace("Ü", "Ue").replace("ß", "ss");
		String length = (word.isShortVowel()) ? "short" : "long";
		String speed = (slow) ? "slow" : "normal";
		String path = "doppelungGame/new_sounds/" + length + "/" + speed + "/" + value + ".wav";
		
		try {
			return sc.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM, path);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	//Preload sound by playing it silently
	public static void preloadSound(VowelGameWord word, boolean slow) {
		
		SoundController dummySoundController = new SoundController();
		
		dummySoundController.setDefaultVolume(0);
		
		String value = word.getWordString().replace("ä", "ae").replace("ö", "oe").replace("ü", "ue");
		value = value.replace("Ä", "Ae").replace("Ö", "Oe").replace("Ü", "Ue").replace("ß", "ss");
		String length = (word.isShortVowel()) ? "short" : "long";
		String speed = (slow) ? "slow" : "normal";
		String path = "doppelungGame/new_sounds/" + length + "/" + speed + "/" + value + ".wav";
		
		try {
			Sound sound = dummySoundController.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM, path);
			sound.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
