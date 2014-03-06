package com.wicam.numberlineweb.client.MultiplicationInverse;

import java.io.Serializable;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameState;

/**
 * Extend a MultiplicationGameState with a field for storing the multiplication task.
 * @author timfissler
 *
 */

public class MultiplicationInverseGameState extends MultiplicationGameState implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9097063079301809831L;
	protected String task;

	public MultiplicationInverseGameState() {
		super();
		this.task = "";
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

}
