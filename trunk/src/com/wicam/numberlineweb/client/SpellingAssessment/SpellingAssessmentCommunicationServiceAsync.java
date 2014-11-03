package com.wicam.numberlineweb.client.SpellingAssessment;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.SpellingAssessment.SpellingAssessmentItem;
import com.wicam.numberlineweb.server.SpellingAssessment.SpellingAssessmentItemList;

/**
 * Math assessment communication service async interface. 
 * @author timfissler
 *
 */

public interface SpellingAssessmentCommunicationServiceAsync {

	public void itemPresented(String message, AsyncCallback<Void> voidCallback);
	
	public void userAnswered(String message, AsyncCallback<Void> voidCallback);

	public void endAssessment(int assessmentID, AsyncCallback<Void> callback);

	public void startAssessment(int userID, AsyncCallback<SpellingAssessmentState> callback);

	public void loadShuffledItemList(AsyncCallback<ArrayList<SpellingAssessmentItem>> callback);
	
	public void userAborted(String message, AsyncCallback<Void> voidCallback);
	
}
