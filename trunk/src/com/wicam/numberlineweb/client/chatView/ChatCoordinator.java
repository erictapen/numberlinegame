package com.wicam.numberlineweb.client.chatView;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wicam.numberlineweb.client.CommunicationServiceAsync;

/**
 * Providing the chat backbone
 * @author patrick
 *
 */

public class ChatCoordinator {


	ChatView view;
	CommunicationServiceAsync commServ;
	int gameid;
	String uname;
	boolean open=false;

	public ChatCoordinator(int gameid,ChatView view, CommunicationServiceAsync commServ) {


		this.gameid = gameid;
		this.view=view;
		this.commServ = commServ;

	}

	/**
	 * Setting the username will open the chat.
	 * @param uname
	 */

	public void setUserName(String uname) {

		this.uname=uname;
		open=true;

		Timer t = new Timer() {
			public void run() {
				update();
			}
		};

		t.scheduleRepeating(3000);

	}
	
	/**
	 * gets latest messages from the server
	 */	

	public void update() {

		commServ.getNewChatMsgs(Integer.toString(gameid) + ":" + escapeString(uname), updateCallback);

	}

	public String escapeString(String str) {

		return str.replace(":", " ");

	}

	/**
	 * send the string entered in the view
	 */

	public void send() {

		if (view.getEnteredText().equals("")) return;
		ChatMsg msg = new ChatMsg(uname,view.getEnteredText(),gameid);
		view.addLine("Du", view.getEnteredText());

		view.clearEnteredText();
		commServ.sendChatMsg(msg, msgSendCallback);

	}

	/**
	 * Write new msgs to the view
	 * @param msgs
	 */
	
	private void updateMsgs(ChatMsg[] msgs) {

		for (int i=0;i<msgs.length;i++) {
			view.addLine(msgs[i].getFrom(), msgs[i].getMsg());
		}

	}
	
	
	/**
	 * CALLBACKS
	 */

	AsyncCallback<Boolean> msgSendCallback = new AsyncCallback<Boolean>() {

		@Override
		public void onFailure(Throwable caught) {
			
		}

		@Override
		public void onSuccess(Boolean result) {


		}

	};

	AsyncCallback<ChatMsg[]> updateCallback = new AsyncCallback<ChatMsg[]>() {

		@Override
		public void onFailure(Throwable caught) {
			
		}

		@Override
		public void onSuccess(ChatMsg[] result) {

			updateMsgs(result);

		}

	};


}
