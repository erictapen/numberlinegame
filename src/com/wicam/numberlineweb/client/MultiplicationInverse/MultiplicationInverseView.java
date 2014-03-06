package com.wicam.numberlineweb.client.MultiplicationInverse;

import com.google.gwt.core.shared.GWT;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationView;

public class MultiplicationInverseView extends MultiplicationView {

	
public void setTaskText(String task) {
		GWT.log("write " + task);
		infoBox.setHTML("<div id='resultText' style='width:500px;padding:5px 20px;'>" +
				"Gesucht wird <strong style=\"font-size:30px;\">" + task + "</strong></div>");
		
	}
	
}
