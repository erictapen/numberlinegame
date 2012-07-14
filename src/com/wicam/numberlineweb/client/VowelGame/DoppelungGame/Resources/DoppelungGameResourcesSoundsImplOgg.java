package com.wicam.numberlineweb.client.VowelGame.DoppelungGame.Resources;

import com.allen_sauer.gwt.voices.client.Sound;

public class DoppelungGameResourcesSoundsImplOgg extends DoppelungGameResourcesSoundsImpl{
	
	@Override
	public DoppelungGameResourcesSounds getInstance(){
		System.out.println("Ogg used");
		return DoppelungGameResourcesSoundsOgg.INSTANCE;
	}
	
	@Override
	public String getMimeType(){
		return Sound.MIME_TYPE_AUDIO_OGG_VORBIS;
	}

}
