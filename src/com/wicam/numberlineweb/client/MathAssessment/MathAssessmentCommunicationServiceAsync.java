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

	public void itemPresented(String message, AsyncCallback<Void> voidCallback);
	
	public void userAnswered(String message, AsyncCallback<Void> voidCallback);

	public void endAssessment(int assessmentID, AsyncCallback<Void> callback);

	public void startAssessment(int userID, AsyncCallback<MathAssessmentState> callback);

	public void getNextItem(int assessmentID, AsyncCallback<String> callback);
	
	public void userAborted(String message, AsyncCallback<Void> voidCallback);
	
}
