package com.wicam.numberlineweb.server.SpellingAssessment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.wicam.numberlineweb.client.GameJoinException;
import com.wicam.numberlineweb.client.MathAssessment.MathAssessmentCommunicationService;
import com.wicam.numberlineweb.client.MathAssessment.MathAssessmentState;
import com.wicam.numberlineweb.client.SpellingAssessment.SpellingAssessmentCommunicationService;
import com.wicam.numberlineweb.client.SpellingAssessment.SpellingAssessmentItem;
import com.wicam.numberlineweb.client.SpellingAssessment.SpellingAssessmentState;
import com.wicam.numberlineweb.server.CustomRemoteServiceServlet;
import com.wicam.numberlineweb.server.MathAssessment.MathAssessmentItemStack;
import com.wicam.numberlineweb.server.database.drupal.DrupalCommunicator;
import com.wicam.numberlineweb.server.database.drupal.UserNotFoundException;
import com.wicam.numberlineweb.server.logging.GameLogger;
import com.wicam.numberlineweb.server.logging.GameLogger.LogActionTrigger;
import com.wicam.numberlineweb.server.logging.GameLogger.LogActionType;

/**
 * Math assessment communication service servlet.
 * @author timfissler
 *
 */

public class SpellingAssessmentCommunicationServiceServlet extends
	CustomRemoteServiceServlet implements SpellingAssessmentCommunicationService {
	
	private static final long serialVersionUID = 7200332323767902482L;
	protected String internalName;
	/**
	 * Stores the logging objects for all open assessments on the server.
	 */
	protected Map<Integer, GameLogger> assessmentId2Logger;
	/**
	 * Stores the assessment states for all open assessments on the server.
	 */
	protected Map<Integer, SpellingAssessmentState> assessmentId2openAssessments;
	/**
	 * Stores the item lists for all open assessments on the server.
	 */
	protected Map<Integer, SpellingAssessmentItemStack> assessmentId2ItemStack;
	/**
	 * Stores the current item id to allow each new assessment to get an individual id.
	 */
	protected int currentAssessmentID;
	
	public SpellingAssessmentCommunicationServiceServlet() {
		this.internalName = "spelling_assessment";
		this.assessmentId2Logger = new HashMap<Integer, GameLogger>();
		this.assessmentId2openAssessments = new HashMap<Integer, SpellingAssessmentState>();
		this.assessmentId2ItemStack = new HashMap<Integer, SpellingAssessmentItemStack>();
		this.currentAssessmentID = 0;
	}

	/**
	 * Is being called when a new math taskText is presented to the user. Logs the presented item.
	 */
	@Override
	public synchronized void itemPresented(String message) {

		System.out.println("Item presented.");
		System.out.println("Logging: " + message);
		
		// Decode the message string.
		int assessmentID = Integer.parseInt(message.split(":")[0]);
		String item = message.split(":")[1];
		long timestamp = Long.parseLong(message.split(":")[2]);
		
		// Log the item.
		if (this.assessmentId2Logger.containsKey(assessmentID))
		this.assessmentId2Logger.get(assessmentID).log(assessmentID, 
				this.assessmentId2openAssessments.get(assessmentID).getUserID(),
				timestamp, LogActionType.MATH_ASSESSMENT_ITEM_PRESENTED,
				"{\"taskItem\" : " + item + "}", 
				this.getClass().getName(), LogActionTrigger.APPLICATION);
		
	}

	/**
	 * Is being called when the user entered the possible result for the current taskText presented.
	 * Logs the user answer.
	 */
	@Override
	public synchronized void userAnswered(String message) {

		System.out.println("User answered.");
		System.out.println("Logging: " + message);
		
		// Decode the message string.
		int assessmentID = Integer.parseInt(message.split(":")[0]);
		String item = message.split(":")[1];
		String answer = message.split(":")[2];
		boolean isCorrect = Boolean.parseBoolean(message.split(":")[3]);
		long reactionTime = Long.parseLong(message.split(":")[4]);
		long timestamp = Long.parseLong(message.split(":")[5]);

		// Log the item.
		if (this.assessmentId2Logger.containsKey(assessmentID))
			this.assessmentId2Logger.get(assessmentID).log(assessmentID, 
					this.assessmentId2openAssessments.get(assessmentID).getUserID(),
					timestamp, LogActionType.MATH_ASSESSMENT_USER_ANSWERED, "{\"taskItem\" : " + item +
					", \"answer\" : " + answer + ", \"isCorrect\" : " + isCorrect + 
					", \"reactionTime\" : " + reactionTime + "}",
					this.getClass().getName(), LogActionTrigger.APPLICATION);
		
	}

	/**
	 * Is being called when an assessment is started by a user.
	 * Returns the assessment state with the assessment id and the player name.
	 * @throws GameJoinException 
	 */
	@Override
	public synchronized SpellingAssessmentState startAssessment(int userID) throws GameJoinException {
		
		SpellingAssessmentState state = new SpellingAssessmentState();
		
		// Set user ID.
		state.setUserID(userID);
		
		// Retrieve the user's name.
//		if (userID == -2) {
			state.setPlayerName("Gast");
//		}
//		else{
//			DrupalCommunicator dc = new DrupalCommunicator();
//			try {
//				state.setPlayerName(dc.getUser(userID).getUname());
//			} catch (UserNotFoundException e) {
//				throw new GameJoinException("User with id =" + userID + " could not be found.");
//			}
//		}
		
		// Set assessment id.
		state.setAssessmentID(currentAssessmentID);
		currentAssessmentID++;
		
		// Create new shuffled item list.
		this.assessmentId2ItemStack.put(state.getAssessmentID(), new SpellingAssessmentItemStack());
		
		// Create new logger.
		this.assessmentId2Logger.put(state.getAssessmentID(), new GameLogger());
		
		// Add state to open assessments.
		assessmentId2openAssessments.put(state.getAssessmentID(), state);
		
		// Log the start of the assessment.
		this.assessmentId2Logger.get(state.getAssessmentID()).log(state.getAssessmentID(), state.getUserID(),
				System.currentTimeMillis(), LogActionType.GAME_STARTED, "", 
				this.getClass().getName(), LogActionTrigger.APPLICATION);
		
		System.out.println("Started assessment " + state.getAssessmentID());
		System.out.println("User ID: " + state.getUserID());
		
		return state;
	}

	/**
	 * Is being called when an assessment is ended by a user.
	 */
	@Override
	public synchronized void endAssessment(int assessmentID) {
		
		// Log the end of the assessment.
		if (this.assessmentId2Logger.containsKey(assessmentID)) {
			this.assessmentId2Logger.get(assessmentID).log(assessmentID,
					this.assessmentId2openAssessments.get(assessmentID).getUserID(),
					System.currentTimeMillis(), LogActionType.GAME_ENDED, "", 
					this.getClass().getName(), LogActionTrigger.APPLICATION);
		}

		// Delete the assessment and the logger.
		this.assessmentId2openAssessments.remove(assessmentID);
		this.assessmentId2Logger.remove(assessmentID);
		this.assessmentId2ItemStack.remove(assessmentID);
		
		System.out.println("Ended assessment " + assessmentID);
		
	}

	/**
	 * Return the next item for the given assessmentID.
	 * @param assessmentID
	 * @return
	 */
	@Override
	public synchronized SpellingAssessmentItem getNextItem(int assessmentID) {
		
		SpellingAssessmentItem nextItem = null;
		// If there is at least one item left return it and remove it from the list.
		if (!assessmentId2ItemStack.get(assessmentID).isEmpty()) {
			nextItem = assessmentId2ItemStack.get(assessmentID).popItem();
		}

		// Else return null.
		
		return nextItem;
	}

	/**
	 * Log the item and the abort time.
	 */
	@Override
	public void userAborted(String message) {

		System.out.println("User aborted.");
		System.out.println("Logging: " + message);
		
		// Decode the message string.
		int assessmentID = Integer.parseInt(message.split(":")[0]);
		String item = message.split(":")[1];
		long timestamp = Long.parseLong(message.split(":")[2]);
		
		// Log the item.
		if (this.assessmentId2Logger.containsKey(assessmentID))
		this.assessmentId2Logger.get(assessmentID).log(assessmentID, 
				this.assessmentId2openAssessments.get(assessmentID).getUserID(),
				timestamp, LogActionType.MATH_ASSESSMENT_USER_ABORTED,
				"{\"taskItem\" : " + item + "}", 
				this.getClass().getName(), LogActionTrigger.APPLICATION);
		
	}
	
	
}
