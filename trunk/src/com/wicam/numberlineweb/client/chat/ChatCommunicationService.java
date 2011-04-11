package com.wicam.numberlineweb.client.chat;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("chatCommunication")

public interface ChatCommunicationService extends RemoteService {

	public boolean sendChatMsg(ChatMsg msg);

	public ChatMsg[] getNewChatMsgs(String ids);
	
}
