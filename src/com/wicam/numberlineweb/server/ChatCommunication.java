package com.wicam.numberlineweb.server;

import java.util.ArrayList;
import java.util.Iterator;

import com.wicam.numberlineweb.client.chatView.ChatMsg;

public class ChatCommunication {

	ArrayList<ChatMsg> chatMsgs = new ArrayList<ChatMsg>();
	
	ChatCommunication(){
		
	}
	
	/**
	 * CHAT FUNCTIONALITY
	 * ==================
	 * 
	 * TODO: use extra classes for this.
	 */

	public boolean sendChatMsg(ChatMsg msg) {


		chatMsgs.add(msg);

		return false;
	}

	public ChatMsg popNewMsgs(int gameid, String uname) {

		Iterator<ChatMsg> i = chatMsgs.iterator();

		while(i.hasNext()) {

			ChatMsg act = i.next();

			if (act.getGameID() == gameid && !act.getFrom().equals(uname) && !act.hasSentTo(uname)) {

				act.sentTo(uname);
				
				return act;

			}

		}

		return null;
	}


	public ChatMsg[] getNewChatMsgs(String ids) {

		String uname = ids.split(":")[1];
		int gameid = Integer.parseInt(ids.split(":")[0]);



		ArrayList<ChatMsg> temp = new ArrayList<ChatMsg>();

		ChatMsg a = popNewMsgs(gameid, uname);

		while (a != null) {

			temp.add(a);
			a = popNewMsgs(gameid, uname);

		}

		ChatMsg ret[] = new ChatMsg[temp.size()];
		ret = temp.toArray(ret);

		return ret;


	}
}
