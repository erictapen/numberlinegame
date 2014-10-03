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
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.NPC;
import com.wicam.numberlineweb.server.MultiplicationInverse.MultiplicationInverseNPC;

public class MathAssessmentCommunicationServiceServlet extends
		GameCommunicationServiceServlet implements MathAssessmentCommunicationService {
	
	/*
	 * TODO Add method for sending the shuffled item list to the client.
	 */
	
	// Random generator
	protected Random rand = new Random();

	private static final long serialVersionUID = 7200332323767902482L;
	
	public MathAssessmentCommunicationServiceServlet() {
		
		super("math_assessment");
		
	}
	
    public MathAssessmentCommunicationServiceServlet(String name) {
		
		super(name);
		
	}
    
	@Override
	public GameState openGame(GameState g) throws GameOpenException {
		
		g.setServerSendTime(System.currentTimeMillis());
		GWT.log("before opening game");
		GameState retGameState = super.openGame(g);
		GWT.log("after opening game");
		
		return retGameState;

	}

	@Override
	public String getGameProperties(GameState gameState) {
		
		MultiplicationGameState numberlineGameState = (MultiplicationGameState) gameState;
		
		String gamePropertiesStr = "{";
				
		gamePropertiesStr += "num_players : " + numberlineGameState.getPlayerCount() + ", ";
		
		//TODO gameproperties
		
		gamePropertiesStr += "}";
		
		return gamePropertiesStr;
		
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
	
	
}
