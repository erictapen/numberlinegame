package com.wicam.numberlineweb.client.Resources;

import com.allen_sauer.gwt.voices.client.Sound;

public class SoundResourcesImplOgg extends SoundResourcesAbstract{

	@Override
	public SoundResourcesOgg getInstance() {
		return SoundResourcesOgg.INSTANCE;
	}
	
	@Override
	public String getMimeType() {
		return Sound.MIME_TYPE_AUDIO_OGG_VORBIS;
	}
	
}
