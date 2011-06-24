package com.wicam.numberlineweb.client.MathDiagnostics;

public abstract class ItemTypes {

	public final static int ADDITIONITEM = 1;
	
	public static String getTypeString(int type){
		if (type == ADDITIONITEM)
			return "Addition";
		return "";
	}
}
