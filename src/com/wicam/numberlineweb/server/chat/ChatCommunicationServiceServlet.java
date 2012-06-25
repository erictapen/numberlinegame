package com.wicam.numberlineweb.server.chat;

import java.util.ArrayList;
import java.util.Iterator;

import com.wicam.numberlineweb.client.chat.ChatCommunicationService;
import com.wicam.numberlineweb.client.chat.ChatMsg;
import com.wicam.numberlineweb.server.CustomRemoteServiceServlet;

public class ChatCommunicationServiceServlet extends CustomRemoteServiceServlet implements ChatCommunicationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3510955814325081896L;

	ArrayList<ChatMsg> chatMsgs = new ArrayList<ChatMsg>();
	
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
