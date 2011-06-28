package com.wicam.numberlineweb.client.MathDiagnostics.NumberComparison;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsController;
import com.wicam.numberlineweb.client.MathDiagnostics.isItem;

public class NumberComparisonItem implements isItem, IsSerializable {

	private int id = 0;
	private int numberTop = 0;
	private int numberBottom = 0;
	
	public void setNumberComparisonItem(int id, int number1, int number2){
		this.id = id;
		this.numberTop = number1;
		this.numberBottom = number2;
	}
	

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}


	public int getNumberTop() {
		return numberTop;
	}


	public void setNumberTop(int numberTop) {
		this.numberTop = numberTop;
	}


	public int getNumberBottom() {
		return numberBottom;
	}


	public void setNumberBottom(int numberBottom) {
		this.numberBottom = numberBottom;
	}


	@Override
	public int getCorrectSolution(){
		if (numberTop > numberBottom)
			return MathDiagnosticsController.KEYTOP;
		return MathDiagnosticsController.KEYBOTTOM;
	}
}
