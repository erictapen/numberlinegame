package com.wicam.numberlineweb.client.Resources;

import com.allen_sauer.gwt.voices.client.Sound;

public class SoundResourcesImplMp3 extends SoundResourcesImpl{
	
	@Override
	public SoundResourcesMp3 getInstance() {
		return SoundResourcesMp3.INSTANCE;
	}
	
	@Override
	public String getMimeType() {
		return Sound.MIME_TYPE_AUDIO_MPEG_MP3;
	}

}
