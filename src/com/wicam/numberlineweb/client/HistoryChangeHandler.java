package com.wicam.numberlineweb.client;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;

public class HistoryChangeHandler {
	
	// TODO Delete this class if the new history handling with the ValueChangeHandler works.


	@SuppressWarnings("deprecation")
	private static HistoryListener h;



	@SuppressWarnings("deprecation")
	public static void setHistoryListener(HistoryListener hl) {

		if (h != null) {
			History.removeHistoryListener(h);
		}
		h=hl;
		History.addHistoryListener(h);

	}

	@SuppressWarnings("deprecation")
	public static void clear() {

		if (h != null) {
			History.removeHistoryListener(h);
			h=null;
		}


	}




}
