package com.wicam.numberlineweb.client.MathDiagnostics.ChoiceReactionTask;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsController;
import com.wicam.numberlineweb.client.MathDiagnostics.isItem;

public class ChoiceReactionTaskItem implements isItem, IsSerializable {

	private int id = 0;
	private int number1 = 0;
	private int number2 = 0;
	private int solution1 = 0;
	private int solution2 = 0;
	private String operator = "";
	
	public void setArithmeticItem(String operator, int id, int number1, int number2, int solution1, int solution2){
		this.operator = operator;
		this.id = id;
		this.number1 = number1;
		this.number2 = number2;
		this.solution1 = solution1;
		this.solution2 = solution2;
	}
	
	public int getNumber1() {
		return number1;
	}

	public void setNumber1(int number1) {
		this.number1 = number1;
	}

	public int getNumber2() {
		return number2;
	}

	public void setNumber2(int number2) {
		this.number2 = number2;
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

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperator() {
		return operator;
	}

	@Override
	public int getCorrectSolution(){
		if ((number1 + number2) == solution1)
			return MathDiagnosticsController.KEYLEFTSIDE;
		return MathDiagnosticsController.KEYRIGHTSIDE;
	}
}
