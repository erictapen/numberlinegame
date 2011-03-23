package com.wicam.numberlineweb.client.chatView;

import java.io.Serializable;

/**
 * A chat message. Holds username, id and the msg itself.
 * @author patrick
 *
 */

public class ChatMsg implements  Serializable{

	/**
	 * Again, this needs to be serialized for GWT to transfer
	 * instances correctly
	 */
	private static final long serialVersionUID = -5710203576851518408L;


	private String from;
	private String msg;
	private int gameid;

	public ChatMsg() {

	}

	public ChatMsg(String from, String msg, int gameid) {
		this.from=from;
		this.msg=msg;
		this.gameid=gameid;
	}

	public String getFrom() {
		return from;
	}

	public String getMsg() {		
		return msg;
	}

	public int getGameID() {		
		return gameid;
	}

	public String toString() {
		return "From: " + from + "(" + Integer.toString(gameid) + ") Msg:" + msg; 
	}


}
