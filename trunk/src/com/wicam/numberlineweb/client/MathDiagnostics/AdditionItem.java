package com.wicam.numberlineweb.client.MathDiagnostics;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.wicam.numberlineweb.client.MathDiagnostics.isItem;

public class AdditionItem implements isItem, IsSerializable {

	private int addend1 = 0;
	private int addend2 = 0;
	private int solution1 = 0;
	private int solution2 = 0;
	
	public void setAdditionItem(int addend1, int addend2, int solution1, int solution2){
		this.addend1 = addend1;
		this.addend2 = addend2;
		this.solution1 = solution1;
		this.solution2 = solution2;
	}
	
	public void setAddend1(int addend1) {
		this.addend1 = addend1;
	}

	public int getAddend1() {
		return addend1;
	}

	public void setAddend2(int addend2) {
		this.addend2 = addend2;
	}

	public int getAddend2() {
		return addend2;
	}
	
	public void setSolution1(int solution1) {
		this.solution1 = solution1;
	}
	
	public int getSolution1() {
		return solution1;
	}
	
	public void setSolution2(int solution2) {
		this.solution2 = solution2;
	}
	
	public int getSolution2() {
		return solution2;
	}

}
