package com.wicam.numberlineweb.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GameJoinException extends Exception implements IsSerializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 127618086045718734L;

	public GameJoinException(String msg) {

		super(msg);


	}

	public GameJoinException() {


	}



}
