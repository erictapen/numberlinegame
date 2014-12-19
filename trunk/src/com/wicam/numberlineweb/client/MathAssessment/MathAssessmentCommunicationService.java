package com.wicam.numberlineweb.client.MathAssessment;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameJoinException;
import com.wicam.numberlineweb.client.GameState;

@RemoteServiceRelativePath("mathAssessmentCommunication")

/**
 * Math assessment communication service interface.
 * @author timfissler
 *
 */

public interface MathAssessmentCommunicationService extends RemoteService {
	
	public void itemPresented(String message);

	public void userAnswered(String message);
	
	public MathAssessmentState startAssessment(int userID) throws GameJoinException;
	
	public void endAssessment(int assessmentID);
	
	public String getNextItem(int assessmentID);
	
	public void userAborted(String message);
	
}
