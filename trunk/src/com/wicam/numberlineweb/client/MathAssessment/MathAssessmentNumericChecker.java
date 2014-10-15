package com.wicam.numberlineweb.client.MathAssessment;

/**
 * Class for checking if a given string
 * can be interpreted as a double. 
 * @author timfissler
 *
 */

public class MathAssessmentNumericChecker {

	public MathAssessmentNumericChecker() {
		
	}
	
	/**
	 * Check if the given string can be
	 * converted to a double.
	 * @param s
	 * @return
	 */
	public boolean isDoubleConvertible(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/*
	 * Test the class.
	 */
//	public static void main(String[] args) {
//		
//		MathAssessmentNumericChecker nc = new MathAssessmentNumericChecker();
//		
//		System.out.println(nc.isDoubleConvertible("1.234566"));
//		System.out.println(nc.isDoubleConvertible("1,234566"));
//		System.out.println(nc.isDoubleConvertible("-1.234566"));
//		System.out.println(nc.isDoubleConvertible(".234566"));
//		System.out.println(nc.isDoubleConvertible("0.0"));
//		System.out.println(nc.isDoubleConvertible("Test"));
//		System.out.println(nc.isDoubleConvertible("1.234566Hallo"));
//		System.out.println(nc.isDoubleConvertible("Hallo1.234566"));
//		System.out.println(nc.isDoubleConvertible("+1.234566"));
//		System.out.println(nc.isDoubleConvertible("1.25"));
//		System.out.println(nc.isDoubleConvertible("1."));
//		System.out.println(nc.isDoubleConvertible("."));
//
//	}

}
