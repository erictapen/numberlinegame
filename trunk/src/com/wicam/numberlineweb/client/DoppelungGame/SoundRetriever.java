package com.wicam.numberlineweb.client.DoppelungGame;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;

/**
 * Class for retrieving the wave-files
 * 
 * @author shuber
 *
 */

public class SoundRetriever {

	public static Sound getSound(SoundController sc, String word){
		if (word.equals("Hammer"))
			return sc.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM, "doppelungGame/soundFiles/Hammer.wav");
		if (word.equals("Fehler"))
			return sc.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM, "doppelungGame/soundFiles/Fehler.wav");
		if (word.equals("Hose"))
			return sc.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM, "doppelungGame/soundFiles/Hose.wav");
		if (word.equals("Drehung"))
			return sc.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM, "doppelungGame/soundFiles/Drehung.wav");
		if (word.equals("Kamm"))
			return sc.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM, "doppelungGame/soundFiles/Kamm.wav");
		if (word.equals("Unfall"))
			return sc.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM, "doppelungGame/soundFiles/Unfall.wav");
		if (word.equals("Sieger"))
			return sc.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM, "doppelungGame/soundFiles/Sieger.wav");
		if (word.equals("Kummer"))
			return sc.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM, "doppelungGame/soundFiles/Kummer.wav");
		if (word.equals("Riss"))
			return sc.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM, "doppelungGame/soundFiles/Riss.wav");
		if (word.equals("Frühling"))
			return sc.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM, "doppelungGame/soundFiles/Fruehling.wav");
		return null;
	}
}
