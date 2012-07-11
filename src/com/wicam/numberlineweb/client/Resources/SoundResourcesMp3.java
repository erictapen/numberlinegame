package com.wicam.numberlineweb.client.Resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.DataResource.DoNotEmbed;
import com.google.gwt.resources.client.DataResource.MimeType;

public interface SoundResourcesMp3 extends ClientBundle, SoundResources{
	
    public static final SoundResourcesMp3 INSTANCE = 
            GWT.create(SoundResourcesMp3.class);
	
	@Source("com/wicam/numberlineweb/client/Resources/desc/BuddyNumber.mp3")
	@MimeType("audio/mpeg")
	@DoNotEmbed
	DataResource buddyNumber();

	@Source("com/wicam/numberlineweb/client/Resources/desc/Doppelung.mp3")
	@MimeType("audio/mpeg")
	@DoNotEmbed
	DataResource doppelung();

	@Source("com/wicam/numberlineweb/client/Resources/desc/Multiplication.mp3")
	@MimeType("audio/mpeg")
	@DoNotEmbed
	DataResource multiplication();

	@Source("com/wicam/numberlineweb/client/Resources/desc/OverTen.mp3")
	@MimeType("audio/mpeg")
	@DoNotEmbed
	DataResource overTen();

	@Source("com/wicam/numberlineweb/client/Resources/desc/WortbausteinespielVersion1.mp3")
	@MimeType("audio/mpeg")
	@DoNotEmbed
	DataResource wortbausteinespielVersion1();

	@Source("com/wicam/numberlineweb/client/Resources/desc/WortbausteinespielVersionDragdrop.mp3")
	@MimeType("audio/mpeg")
	@DoNotEmbed
	DataResource wortbausteinespielVersionDragdrop();

	@Source("com/wicam/numberlineweb/client/Resources/desc/Wortfamilienspiel.mp3")
	@MimeType("audio/mpgeg")
	@DoNotEmbed
	DataResource wortfamilienspiel();


}
