package com.wicam.numberlineweb.client.MathAssessment;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameCommunicationService;

@RemoteServiceRelativePath("mathAssessmentCommunication")

/**
 * Math assessment communication service interface.
 * @author timfissler
 *
 */

public interface MathAssessmentCommunicationService extends GameCommunicationService {

	public boolean itemPresented(String s);

	public boolean userAnswered(String s);
	
	
}
