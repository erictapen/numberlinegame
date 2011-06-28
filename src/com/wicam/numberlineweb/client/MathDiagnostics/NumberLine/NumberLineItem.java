package com.wicam.numberlineweb.client.MathDiagnostics.NumberLine;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.wicam.numberlineweb.client.MathDiagnostics.isItem;

public class NumberLineItem implements isItem, IsSerializable {

	private int id = 0;
	private int leftNumber = 0;
	private int rightNumber = 0;
	private int exerciseNumber = 0;
	
	@Override
	public int getCorrectSolution(){
		return exerciseNumber;
	}

	public void setNumberLineItem(int id, int leftNumber, int rightNumber, int exerciseNumber){
		this.id = id;
		this.leftNumber = leftNumber;
		this.rightNumber = rightNumber;
		this.exerciseNumber = exerciseNumber;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setLeftNumber(int leftNumber) {
		this.leftNumber = leftNumber;
	}

	public int getLeftNumber() {
		return leftNumber;
	}

	public void setRightNumber(int rightNumber) {
		this.rightNumber = rightNumber;
	}

	public int getRightNumber() {
		return rightNumber;
	}

	public void setExerciseNumber(int exerciseNumber) {
		this.exerciseNumber = exerciseNumber;
	}

	public int getExerciseNumber() {
		return exerciseNumber;
	}
}
