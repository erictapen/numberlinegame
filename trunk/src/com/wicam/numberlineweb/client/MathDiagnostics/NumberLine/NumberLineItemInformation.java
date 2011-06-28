package com.wicam.numberlineweb.client.MathDiagnostics.NumberLine;

import com.wicam.numberlineweb.client.MathDiagnostics.ItemInformation;

public class NumberLineItemInformation extends ItemInformation {

	private int deviation = 0; // absolute deviation
	private double relDeviation = 0.0; // relative deviation: absolute deviation divided by length of the line
	
	public void setDeviation(int deviation) {
		this.deviation = deviation;
	}

	public int getDeviation() {
		return deviation;
	}

	public void setRelDeviation(double relDeviation) {
		this.relDeviation = relDeviation;
	}

	public double getRelDeviation() {
		return relDeviation;
	}
}
