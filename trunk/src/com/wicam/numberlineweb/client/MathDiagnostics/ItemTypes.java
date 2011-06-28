package com.wicam.numberlineweb.client.MathDiagnostics;

/**
 * Abstract class for the item types
 * 
 * @author shuber
 *
 */

public abstract class ItemTypes {

	public final static int ADDITIONITEM = 1;
	public final static int NUMBERLINEITEM = 2;
	public final static int NUMBERCOMPARISON = 3;
	
	public static String getTypeString(int type){
		if (type == ADDITIONITEM)
			return "addition";
		if (type == NUMBERLINEITEM)
			return "numberline";
		if (type == NUMBERCOMPARISON)
			return "numbercomparison";
		return "";
	}
}
