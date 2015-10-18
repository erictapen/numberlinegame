package com.wicam.numberlineweb.client.MathAssessment;

/**
 * Class for parsing simple string math tasks like
 * "a + b", "a - b", "a × b" or "a ÷ b",
 * solving them and for checking if a given result solves a given task.
 * @author timfissler
 *
 */

/*
 * TODO Add error handling with exceptions.
 */

public class MathAssessmentTaskResultParser {

	/**
	 * The strings representing the math operations.
	 */
	private String multSign = "*";
	private String divSign = "/";
	private String addSign = "+";
	private String subSign = "-";
	private double a = 0;
	private double b = 0;
	private String operatorString = "";
	private double result = 0;
	private MathOperator operator = MathOperator.ADD;
	
	/**
	 * Create a new parser object.
	 */
	public MathAssessmentTaskResultParser() {
	}
	
	private enum MathOperator {
		ADD, SUBTRACT, MULTIPLY, DIVIDE
	}
	
	/**
	 * Solve a given task and return the result. 
	 * @param task
	 * @return
	 */
	public double solveTask(String task) {
		
		parseTask(task);
		switch (operator) {
		case ADD:
			result = a + b;		
			break;
		case SUBTRACT:
			result = a - b;		
			break;
		case MULTIPLY:
			result = a * b;		
			break;
		case DIVIDE:
			// TODO Catch division by 0.
			result = a / b;		
			break;
		}
		
		return result;
	}
	
	/**
	 * Check whether a given result is correct, given
	 * the task.
	 * @param task
	 * @param result
	 * @return
	 */
	public boolean checkTaskResult(String task, String result) {
		return checkTaskResult(task, Double.parseDouble(result));
	}
	
	/**
	 * Check whether a given result is correct, given
	 * the task.
	 * @param task
	 * @param result
	 * @return
	 */
	public boolean checkTaskResult(String task, double result) {
		solveTask(task);
		return (this.result == result);
	}
	
	/**
	 * Finds the operatorString in the given task.
	 * @param task
	 */
	private void findOperator(String task) {
		if (task.contains(addSign)) {
			this.operatorString = addSign;
			this.operator = MathOperator.ADD;
		}
		else if (task.contains(subSign)) {
			this.operatorString = subSign;
			this.operator = MathOperator.SUBTRACT;
		}
		else if (task.contains(multSign)) {
			this.operatorString = multSign;
			this.operator = MathOperator.MULTIPLY;
		}
		else if (task.contains(divSign)) {
			this.operatorString = divSign;
			this.operator = MathOperator.DIVIDE;
		}
		else {
			// TODO Throw exception.
		}
	}
	
	/**
	 * Divide the given task into the first argument 'a',
	 * the operatorString and the second argument 'b'.
	 */
	private void parseTask(String task) {
		findOperator(task);
		String[] taskArray = task.split("\\" + operatorString);
		if (taskArray.length != 2) {
			// TODO Throw exception.
		}
		a = Double.parseDouble(taskArray[0].trim());
		b = Double.parseDouble(taskArray[1].trim());
	}
	
	/*
	 * Test the class.
	 */
//	public static void main(String[] args) {
//		MathAssessmentTaskResultParser parser = new MathAssessmentTaskResultParser();
//		System.out.println(parser.checkTaskResult("15 * 30", "450"));
//		System.out.println(parser.checkTaskResult("10 / 8", "1.25"));
//		System.out.println(parser.checkTaskResult("3 + 39", "42"));
//		System.out.println(parser.checkTaskResult("50 - 8", "42"));
//	}

}
