package com.wicam.numberlineweb.client.MathAssessment;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameState;

@RemoteServiceRelativePath("mathAssessmentCommunication")

/**
 * Math assessment communication service interface.
 * @author timfissler
 *
 */

public interface MathAssessmentCommunicationService extends RemoteService {
	
	public boolean itemPresented(String s);

	public boolean userAnswered(String s);
	
	public int startAssessment(String s);
	
	public boolean endAssessment(String s);
	
	public ArrayList<String> loadShuffledItemList();	
	
}
