package com.wicam.numberlineweb.client.chat;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChatCommunicationServiceAsync {
	
	public void sendChatMsg(ChatMsg msg, AsyncCallback<Boolean> callback);

	public void getNewChatMsgs(String ids, AsyncCallback<ChatMsg[]> callback);
}
