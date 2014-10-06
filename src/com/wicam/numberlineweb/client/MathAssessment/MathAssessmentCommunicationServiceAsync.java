package com.wicam.numberlineweb.client.MathAssessment;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;

/**
 * Math assessment communication service async interface. 
 * @author timfissler
 *
 */

public interface MathAssessmentCommunicationServiceAsync {

	public void itemPresented(String s, AsyncCallback<Boolean> dummyCallback);
	
	public void userAnswered(String s, AsyncCallback<Boolean> dummyCallback);

	public void endAssessment(String s, AsyncCallback<Boolean> callback);

	public void startAssessment(String s, AsyncCallback<Integer> callback);

	public void loadShuffledItemList(AsyncCallback<ArrayList<String>> callback);
	
}
