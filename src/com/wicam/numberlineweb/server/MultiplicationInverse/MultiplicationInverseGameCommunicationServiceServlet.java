package com.wicam.numberlineweb.server.MultiplicationInverse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.core.shared.GWT;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.Player;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationAnswer;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameCommunicationService;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationGameState;
import com.wicam.numberlineweb.client.Multiplication.MultiplicationPlayer;
import com.wicam.numberlineweb.client.MultiplicationInverse.MultiplicationInverseGameCommunicationService;
import com.wicam.numberlineweb.server.GameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.Multiplication.MultiplicationGameCommunicationServiceServlet;
import com.wicam.numberlineweb.server.Multiplication.MultiplicationGameStateTask;
import com.wicam.numberlineweb.server.Multiplication.MultiplicationNPC;

public class MultiplicationInverseGameCommunicationServiceServlet extends
	MultiplicationGameCommunicationServiceServlet implements MultiplicationInverseGameCommunicationService  {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7757604373406891094L;
	protected ArrayList<Integer> npcIds = new ArrayList<Integer>();
	
	public MultiplicationInverseGameCommunicationServiceServlet() {
		
		super("multiplication");
		//this.handicapAdjustment = new NumberLineGameHandicap();
		
	}
	
	@Override
	public GameState openGame(GameState g) {
		
		g.setServerSendTime(System.currentTimeMillis());
		GWT.log("before opening game");
		GameState retGameState = super.openGame(g);
		GWT.log("after opening game");
		//return super.openGame(g);
		
		newResults((MultiplicationGameState) g);
		
		return retGameState;

	}
}
