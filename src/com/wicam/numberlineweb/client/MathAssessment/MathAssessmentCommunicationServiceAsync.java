package com.wicam.numberlineweb.client.MathAssessment;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;
import com.wicam.numberlineweb.client.GameState;

public interface MathAssessmentCommunicationServiceAsync extends GameCommunicationServiceAsync {

	public void clickedAt(String s,AsyncCallback<GameState> callback);
}
