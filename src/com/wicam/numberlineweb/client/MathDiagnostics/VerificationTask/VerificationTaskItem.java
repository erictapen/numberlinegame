package com.wicam.numberlineweb.client.MathDiagnostics.VerificationTask;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.wicam.numberlineweb.client.MathDiagnostics.MathDiagnosticsController;
import com.wicam.numberlineweb.client.MathDiagnostics.isItem;

public class VerificationTaskItem implements isItem, IsSerializable {

	private int id = 0;
	private int number1 = 0;
	private int number2 = 0;
	private int result = 0;
	private String operator = "";
	
	public void setVerificationTaskItem(String operator, int id,  int number1, int number2, int result){
		this.setId(id);
		this.number1 = number1;
		this.number2 = number2;
		this.result = result;
		this.operator = operator;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
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

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public int getCorrectSolution() {
		if (operator.equals("x"))
			if (number1*number2 == result)
				return MathDiagnosticsController.KEYRIGHTSIDE;
			else
				return MathDiagnosticsController.KEYLEFTSIDE;
		return 0;
	}

}
