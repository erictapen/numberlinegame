package com.wicam.numberlineweb.client.MathDiagnostics;

import com.wicam.numberlineweb.client.MathDiagnostics.ItemInformation;

public class ChoiceTaskItemInformation extends ItemInformation {

	private boolean correct = false;

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public boolean isCorrect() {
		return correct;
	}
}
