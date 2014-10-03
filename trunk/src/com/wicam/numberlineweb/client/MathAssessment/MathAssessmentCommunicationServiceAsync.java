package com.wicam.numberlineweb.client.MathAssessment;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;

/**
 * Math assessment communication service async interface. 
 * @author timfissler
 *
 */

public interface MathAssessmentCommunicationServiceAsync extends GameCommunicationServiceAsync {

	public void itemPresented(String s, AsyncCallback<Boolean> dummyCallback);
	
	public void userAnswered(String s, AsyncCallback<Boolean> dummyCallback);
	
}
