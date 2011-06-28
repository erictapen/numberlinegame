package com.wicam.numberlineweb.client.MathDiagnostics;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wicam.numberlineweb.client.GameCommunicationServiceAsync;

public interface MathDiagnosticsCommonicationServiceAsync extends
		GameCommunicationServiceAsync {

	
	public void retrieveItemList(int gamedId, int itemType,AsyncCallback<ArrayList<isItem>> callback);
	public void sendItemInformationList(int gameId, ArrayList<ItemInformation> itemInformationList,AsyncCallback<Boolean> callback);
}
