package com.wicam.numberlineweb.client.Resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.DataResource.DoNotEmbed;
import com.google.gwt.resources.client.DataResource.MimeType;

public interface SoundResources extends ClientBundle{
	
	public static final SoundResources INSTANCE = 
			GWT.create(SoundResources.class);

	@Source("com/wicam/numberlineweb/client/Resources/desc/BuddyNumber.ogg")
	@MimeType("audio/vorbis")
	@DoNotEmbed
	DataResource buddyNumber();

	@Source("com/wicam/numberlineweb/client/Resources/desc/Doppelung.ogg")
	@MimeType("audio/vorbis")
	@DoNotEmbed
	DataResource doppelung();

	@Source("com/wicam/numberlineweb/client/Resources/desc/Multiplication.ogg")
	@MimeType("audio/vorbis")
	@DoNotEmbed
	DataResource multiplication();

	@Source("com/wicam/numberlineweb/client/Resources/desc/OverTen.ogg")
	@MimeType("audio/vorbis")
	@DoNotEmbed
	DataResource overTen();

	@Source("com/wicam/numberlineweb/client/Resources/desc/WortbausteinespielVersion1.ogg")
	@MimeType("audio/vorbis")
	@DoNotEmbed
	DataResource wortbausteinespielVersion1();

	@Source("com/wicam/numberlineweb/client/Resources/desc/WortbausteinespielVersionDragdrop.ogg")
	@MimeType("audio/vorbis")
	@DoNotEmbed
	DataResource wortbausteinespielVersionDragdrop();

	@Source("com/wicam/numberlineweb/client/Resources/desc/Wortfamilienspiel.ogg")
	@MimeType("audio/vorbis")
	@DoNotEmbed
	DataResource wortfamilienspiel();

}
