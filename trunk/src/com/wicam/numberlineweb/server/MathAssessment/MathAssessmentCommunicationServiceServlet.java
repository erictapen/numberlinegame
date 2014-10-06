package com.wicam.numberlineweb.server.MathAssessment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.core.shared.GWT;
import com.wicam.numberlineweb.client.GameOpenException;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;
import com.wicam.numberlineweb.client.MathAssessment.MathAssessmentCommunicationService;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationAnswer;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameCommunicationService;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameState;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationPlayer;
import com.wicam.numberlineweb.server.CustomRemoteServiceServlet;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.NPC;
import com.wicam.numberlineweb.server.MultiplicationInverse.MultiplicationInverseNPC;

/**
 * Math assessment communication service servlet.
 * @author timfissler
 *
 */

public class MathAssessmentCommunicationServiceServlet extends
	CustomRemoteServiceServlet implements MathAssessmentCommunicationService {
	
	protected Random rand = new Random();
	private static final long serialVersionUID = 7200332323767902482L;
	protected String internalName;
	
	public MathAssessmentCommunicationServiceServlet() {
		this.internalName = "math_assessment";
	}

	/**
	 * Is being called when a new math task is presented to the user. 
	 */
	@Override
	public boolean itemPresented(String s) {
		// TODO Implement this.
		System.out.println("Logging: " + s);
		return false;
	}

	/**
	 * Is being called when the user entered the possible result for the current task presented.
	 */
	@Override
	public boolean userAnswered(String s) {
		// TODO Implement this.
		System.out.println("Logging " + s);
		return false;
	}

	/**
	 * Is being called when an assessment is started by a user.
	 * Returns the player ID.
	 */
	@Override
	public int startAssessment(String s) {
		// TODO Implement this.
		System.out.println("Logging " + s);
		return 0;
	}

	/**
	 * Is being called when an assessment is ended by a user.
	 */
	@Override
	public boolean endAssessment(String s) {
		// TODO Implement this.
		System.out.println("Logging " + s);
		return false;
	}

	/**
	 * Retrieve the shuffled list of math tasks.
	 */
	@Override
	public ArrayList<String> loadShuffledItemList() {
		// TODO Implement this.
		System.out.println("Item list is being retrieved.");
		return null;
	}
	
	
}
