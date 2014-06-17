package com.wicam.numberlineweb.client.Resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImageResources extends ClientBundle{
	
	public static final ImageResources INSTANCE = 
			GWT.create(ImageResources.class);

	@Source("com/wicam/numberlineweb/client/Resources/images/pre_buddyNumber.png")
	ImageResource pre_buddyNumber();

	@Source("com/wicam/numberlineweb/client/Resources/images/pre_dehnung.png")
	ImageResource pre_dehnung();

	@Source("com/wicam/numberlineweb/client/Resources/images/pre_doppelung.png")
	ImageResource pre_doppelung();

	@Source("com/wicam/numberlineweb/client/Resources/images/pre_mathDiagnostics.png")
	ImageResource pre_mathDiagnostics();

	@Source("com/wicam/numberlineweb/client/Resources/images/pre_multiplication.png")
	ImageResource pre_multiplication();

	@Source("com/wicam/numberlineweb/client/Resources/images/pre_overten.png")
	ImageResource pre_overten();

	@Source("com/wicam/numberlineweb/client/Resources/images/pre_wordfamily.png")
	ImageResource pre_wordfamily();

	@Source("com/wicam/numberlineweb/client/Resources/images/pre_wordstem.png")
	ImageResource pre_wordstem();

	@Source("com/wicam/numberlineweb/client/Resources/images/Fragezeichen.gif")
	ImageResource fragezeichen();

	@Source("com/wicam/numberlineweb/client/Resources/images/pre_backButton.png")
	ImageResource pre_backButton();
	
	@Source("com/wicam/numberlineweb/client/Resources/images/pre_letris.png")
	ImageResource pre_letris();

}
