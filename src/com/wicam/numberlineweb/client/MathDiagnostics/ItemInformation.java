package com.wicam.numberlineweb.client.MathDiagnostics;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ItemInformation implements IsSerializable{

	private boolean correct = false;
	private int rt = 0;
	
	public void setItemInformation(boolean correct, int rt){
		this.setCorrect(correct);
		this.rt = rt;
	}
	
	public void setRt(int rt) {
		this.rt = rt;
	}
	
	public int getRt() {
		return rt;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public boolean isCorrect() {
		return correct;
	}
}
