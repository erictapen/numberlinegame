package com.wicam.numberlineweb.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Thrown when a game should be opened but can't because the maximum number of
 * games on the server has been reached already.
 * @author timfissler
 *
 */

public class GameOpenException extends Exception implements IsSerializable{

	private static final long serialVersionUID = -6150797275041508232L;


	public GameOpenException(String msg) {
		super(msg);
	}

	public GameOpenException() {
	}

}
