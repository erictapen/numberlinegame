package com.wicam.numberlineweb.client.MathDiagnostics;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Class which contains information about the response of an user to an item
 * 
 * @author shuber
 *
 */

public class ItemInformation implements IsSerializable{

	private int rt = 0; // reaction times

	public void setRt(int rt) {
		this.rt = rt;
	}
	
	public int getRt() {
		return rt;
	}
}
