package com.wicam.numberlineweb.client.VowelGame.DoppelungGame.Resources;

import com.allen_sauer.gwt.voices.client.Sound;

public class DoppelungGameResourcesSoundsImplMp3 extends DoppelungGameResourcesSoundsImpl{
	
	@Override
	public DoppelungGameResourcesSoundsMp3 getInstance() {
		System.out.println("MP3 used");
		return DoppelungGameResourcesSoundsMp3.INSTANCE;
		
	}
	
	@Override
	public String getMimeType(){
		return Sound.MIME_TYPE_AUDIO_MPEG_MP3;
	}

}
