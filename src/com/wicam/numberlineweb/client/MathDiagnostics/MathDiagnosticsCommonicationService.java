package com.wicam.numberlineweb.client.MathDiagnostics;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wicam.numberlineweb.client.GameCommunicationService;

@RemoteServiceRelativePath("mathDiagnosticsCommunication")

public interface MathDiagnosticsCommonicationService extends
		GameCommunicationService {

	public ArrayList<isItem> retrieveItemList(int gameId, int itemType);	
	public Boolean sendItemInformationList(int gameId, ArrayList<ItemInformation> itemInformationList);	
}
