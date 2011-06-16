package com.wicam.numberlineweb.client;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;

public class HistoryChangeHandler {


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
