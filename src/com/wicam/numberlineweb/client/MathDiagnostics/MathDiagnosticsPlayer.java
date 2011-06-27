package com.wicam.numberlineweb.client.MathDiagnostics;

import com.wicam.numberlineweb.client.Player;

public class MathDiagnosticsPlayer extends Player {
	
	private int meanRT = 0;

	public void setMeanRT(int meanRT) {
		this.meanRT = meanRT;
	}

	public int getMeanRT() {
		return meanRT;
	}

}
