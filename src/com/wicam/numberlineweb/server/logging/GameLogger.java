package com.wicam.numberlineweb.server.logging;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wicam.numberlineweb.server.StaticDebuggingFunctions;
import com.wicam.numberlineweb.server.database.DatabaseConnection;

public class GameLogger {
	
	public static enum LoggingActive {ON, OFF};
	
	public static LoggingActive loggingActive = LoggingActive.ON;
	
	private DatabaseConnection databaseConnection;
	
	private final String dbHost = "localhost";
	private final String dbPort = "5432";
	private final String db = "logging";
	private final String dbUser = "log_user"; //it is not clear yet, which user should be used.
	//"log_user" seems to be right for going live server
	private final String dbPassword = "ner8tiro5";
	
	private int gameInstanceId;
	
	private static final String STATEMENT_LOGS = "INSERT INTO logs (log_user_id, " +
			"log_action_id, log_action_parameters, log_action_trigger, log_game_instance, log_action_time)" +
			" VALUES (?, ?, ?, ?, ?, ?);"; //Added a semicolon at the end of the string
	private PreparedStatement preparedStatementLogs;
	
	private static final String STATEMENT_GAME_INSTANCE = "INSERT INTO game_instances (game_id," +
			"game_property)" +
			" VALUES (?, ?);"; //Added a semicolon at the end of the string
	private PreparedStatement preparedStatementGameInstance;

	private boolean userIDProvided = false;
	
	private static final Map<String, LogGame> className2GameType;
	
	static {
		
		Map<String, LogGame> tempMap = new HashMap<String, LogGame>();
		
		tempMap.put("com.wicam.numberlineweb.server.NumberLineGame." +
				"NumberLineGameCommunicationServiceServlet", LogGame.NUMBER_LINE_GAME);
		
		tempMap.put("com.wicam.numberlineweb.server.VowelGame.DehnungGame." +
				"DehnungGameCommunicationServiceServlet", LogGame.DEHNUNG_GAME);

		
		tempMap.put("com.wicam.numberlineweb.server.VowelGame.DoppelungGame." +
				"DoppelungGameCommunicationServiceServlet", LogGame.DOPPELUNG_GAME);
		
		tempMap.put("com.wicam.numberlineweb.server.MathDiagnostics." +
				"MathDiagnosticsCommunicationServiceServlet", LogGame.MATH_DIAGNOSTICS);
		
		tempMap.put("com.wicam.numberlineweb.server.Multiplication." +
				"MultiplicationGameCommunicationServiceServlet", LogGame.MULTIPLICATION);
		
		tempMap.put("com.wicam.numberlineweb.server.BuddyNumber." +
				"BuddyNumberGameCommunicationServiceServlet", LogGame.BUDDY_NUMBER);
		
		tempMap.put("com.wicam.numberlineweb.server.WordStem." +
				"WordStemGameCommunicationServiceServlet", LogGame.WORD_STEM);
		
		tempMap.put("com.wicam.numberlineweb.server.OverTen." +
				"OverTenGameCommunicationServiceServlet", LogGame.OVER_TEN);
		
		tempMap.put("com.wicam.numberlineweb.server.WordFamily." +
				"WordFamilyGameCommunicationServiceServlet", LogGame.WORD_FAMILY);

		tempMap.put("com.wicam.numberlineweb.server.MultiplicationInverse." +
				"MultiplicationInverseGameCommunicationServiceServlet", LogGame.MULTIPLICATION_INVERSE);
		
		tempMap.put("com.wicam.numberlineweb.server.Letris." +
				"LetrisGameCommunicationServiceServlet", LogGame.LETRIS);
		
		tempMap.put("com.wicam.numberlineweb.server.SpellingAssessment." +
				"SpellingAssessmentCommunicationServiceServlet", LogGame.SPELLING_ASSESSMENT);
		
		tempMap.put("com.wicam.numberlineweb.server.MathAssessment." +
				"MathAssessmentCommunicationServiceServlet", LogGame.MATH_ASSESSMENT);

		
		className2GameType = java.util.Collections.unmodifiableMap(tempMap);
		
	}
	
	public enum LogActionType {GAME_STARTED, GAME_ENDED, JOINED_GAME, LEFT_GAME,
		NUMBERLINE_SUCCESSFUL_CLICK, NUMBERLINE_POSITION_TAKEN, NUMBERLINE_NPC_GUESS,
		NUMBERLINE_NUMBER_PRESENTED, DOPPELUNGGAME_WORD_ENTERED, DOPPELUNGGAME_WORD_CATEGORIZED,
		BUDDYNUMBER_PICKED_NUMBER_PAIR, BUDDYNUMBER_NPC_PICKED_NUMBER, MULTIPLICATION_TASK_PRESENTED,
		MULTIPLICATION_USER_PICKED_ANSWER, MULTIPLICATION_NPC_PICKED_ANSWER, LETRIS_BLOCK_SET,
		LETRIS_WORD_CORRECT, LETRIS_WORD_INCORRECT, LETRIS_TARGET_WORD_PRESENTED, MATH_ASSESSMENT_ITEM_PRESENTED,
		MATH_ASSESSMENT_USER_ANSWERED, MATH_ASSESSMENT_USER_ABORTED, SPELLING_ASSESSMENT_ITEM_PRESENTED,
		SPELLING_ASSESSMENT_USER_ABORTED, SPELLING_ASSESSMENT_USER_ANSWERED, SPELLING_ASSESSMENT_USER_ANSWER_COMPLETE;


		//Get ID for game type
		public static int getIndex(LogActionType logActionType){
			
			switch(logActionType){
			
				case GAME_STARTED:
					return 1;
				case GAME_ENDED:
					return 2;
				case JOINED_GAME:
					return 3;
				case LEFT_GAME:
					return 4;
				case NUMBERLINE_SUCCESSFUL_CLICK:
					return 5;
				case NUMBERLINE_POSITION_TAKEN:
					return 6;
				case NUMBERLINE_NPC_GUESS:
					return 7;
				case NUMBERLINE_NUMBER_PRESENTED:
					return 8;
				case DOPPELUNGGAME_WORD_ENTERED:
					return 9;
				case DOPPELUNGGAME_WORD_CATEGORIZED:
					return 10;
				case BUDDYNUMBER_PICKED_NUMBER_PAIR:
					return 11;
				case BUDDYNUMBER_NPC_PICKED_NUMBER:
					return 12;
				case MULTIPLICATION_TASK_PRESENTED:
					return 13;
				case MULTIPLICATION_USER_PICKED_ANSWER:
					return 14;
				case MULTIPLICATION_NPC_PICKED_ANSWER:
					return 15;
				case LETRIS_BLOCK_SET:
					return 16;
				case LETRIS_WORD_CORRECT:
					return 17;
				case LETRIS_WORD_INCORRECT:
					return 18;
				case LETRIS_TARGET_WORD_PRESENTED:
					return 19;
				case MATH_ASSESSMENT_ITEM_PRESENTED:
					return 20;
				case MATH_ASSESSMENT_USER_ANSWERED:
					return 21;
				case MATH_ASSESSMENT_USER_ABORTED:
					return 22;
				case SPELLING_ASSESSMENT_ITEM_PRESENTED:
					return 23;
				case SPELLING_ASSESSMENT_USER_ABORTED:
					return 24;
				case SPELLING_ASSESSMENT_USER_ANSWERED:
					return 25;
				case SPELLING_ASSESSMENT_USER_ANSWER_COMPLETE:
					return 26;

				default:
					//Should not occur
					return -1;
					
			
			}
		}
	
	};
	
	public enum LogActionTrigger {USER, APPLICATION, NPC;

		//Get ID for game type
		public static String getName(LogActionTrigger logActionTrigger){
			
			switch(logActionTrigger){
			
				case USER:
					return "user";
				case APPLICATION:
					return "application";
				case NPC:
					return "npc";
				default:
					//Should not occur
					return "";
					
			
			}
		}
	
	};
	
	public enum LogGame {NUMBER_LINE_GAME, DOPPELUNG_GAME,
		DEHNUNG_GAME, MATH_DIAGNOSTICS, MULTIPLICATION, MULTIPLICATION_INVERSE, BUDDY_NUMBER,
		WORD_STEM, OVER_TEN, WORD_FAMILY, LETRIS, MATH_ASSESSMENT, SPELLING_ASSESSMENT;

		//Get ID for game type
		public static int getIndex(LogGame logGame){
			
			switch(logGame){
			
				case NUMBER_LINE_GAME:
					return 1;
				case MATH_DIAGNOSTICS:
					return 2;
				case DEHNUNG_GAME:
					return 3;
				case DOPPELUNG_GAME:
					return 4;
				case MULTIPLICATION:
					return 5;
				case BUDDY_NUMBER:
					return 6;
				case WORD_STEM:
					return 7;
				case OVER_TEN:
					return 8;
				case WORD_FAMILY:
					return 9;
				case MULTIPLICATION_INVERSE:
					return 10;
				case LETRIS:
					return 11;
				case MATH_ASSESSMENT:
					return 12;
				case SPELLING_ASSESSMENT:
					return 13;
				default:
					//Should not occur
					return -1;
					
			
			}
		}
	
	};
	
	public GameLogger(){
		
		try {
			
			this.databaseConnection = new DatabaseConnection(this.dbHost, this.dbPort, this.db, 
					this.dbUser, this.dbPassword);
			
			this.preparedStatementLogs = this.databaseConnection.prepareStmtReturnKeys(GameLogger.STATEMENT_LOGS);
			
			this.preparedStatementGameInstance = this.databaseConnection.prepareStmtReturnKeys(GameLogger.STATEMENT_GAME_INSTANCE);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void log(int gameId, long logActionTime, LogActionType logActionType, String actionParams,
			String logGameClassName, LogActionTrigger logActionTrigger){
		
		if (loggingActive == LoggingActive.OFF)
			return;
		
		try {
			
			if(!this.userIDProvided)
				this.preparedStatementLogs.setNull(1, java.sql.Types.DOUBLE);
				
			//Action type
			this.preparedStatementLogs.setInt(2, LogActionType.getIndex(logActionType));
			
			//Action parameters
			this.preparedStatementLogs.setString(3, actionParams);
			
			//Action trigger (user, application or NPC)
			this.preparedStatementLogs.setString(4, LogActionTrigger.getName(logActionTrigger));
			
			//Keep track of game IDs used by the servlets and those used for logging
			if (logActionType == LogActionType.GAME_STARTED){
				
				//Create new game instance entry
				
				LogGame game = getLogGameByClass(logGameClassName);
				
				this.preparedStatementGameInstance.setInt(1, LogGame.getIndex(game));
				
				this.preparedStatementGameInstance.setString(2, "");
				
				this.gameInstanceId = this.writeToTableGameInstances();
			}
				
			//Make reference for log entry to game instance entry
			this.preparedStatementLogs.setInt(5, this.gameInstanceId);
			
			//Action time
			this.preparedStatementLogs.setTimestamp(6, new java.sql.Timestamp(logActionTime));
			
			//print finished sql-statement
			System.out.println(this.preparedStatementLogs.toString());
			
			//Write entry to database
			// Check if connection is still open first to avoid exceptions with closed database connection.
			// TODO This may lead to unlogged user checkouts. Fix this.
			if (!this.databaseConnection.connectionClosed()) {
				this.writeToTableLogs();
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			StaticDebuggingFunctions.printSQLException(e);
		};
		
	}
	
	public void log(int gameID, long logUserID, long logActionTime, LogActionType logActionType, String actionParams,
			String logGameClassName, LogActionTrigger logActionTrigger){
		
		if (loggingActive == LoggingActive.OFF)
			return;
		
		try {
			this.preparedStatementLogs.setLong(1, logUserID);
			this.userIDProvided = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		this.log(gameID, logActionTime, logActionType, actionParams, logGameClassName, logActionTrigger);
		
	}
	
	public void commitChanges() {
		
		this.databaseConnection.commit();
	}
	
	public void rollbackChanges() {
		
		this.databaseConnection.rollback();
		
	}
	
	public void closeConnection() {
		
		this.databaseConnection.close();
	}
	
	public void updateGameProperties(int gameId, String logGameClassName, String gamePropertiesStr){
		
		String sql = "UPDATE game_instances SET game_property = ? WHERE game_instances_id=?";
		
		try {
			
			PreparedStatement preparedStatement =  this.databaseConnection.prepareStmt(sql);
			
			preparedStatement.setString(1, gamePropertiesStr);
			
			preparedStatement.setInt(2, this.gameInstanceId);
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public int getEloRating(int uid) {
		
		String stmt = "SELECT * FROM elo_rating WHERE user_id=" + uid;
		
		//Initial value for ELO - will be returned in case no ELO number is
		//found for the user in the database
		int eloNumber = 1000;
		
		try {
			List<Map<String, String>> result = this.databaseConnection.eval(stmt);
			
			//Try to retrieve ELO number for user
			if (result.size() > 0) 
				eloNumber = Integer.parseInt(result.get(0).get("elo_number"));
			
			else {
				
				//If the player is not a NPC
				if (uid != -5){
					
					stmt = "INSERT INTO elo_rating VALUES (?, ?)";
					
					PreparedStatement preparedStmt = this.databaseConnection.prepareStmt(stmt);
					preparedStmt.setInt(1, uid);
					preparedStmt.setInt(2, 1000);
					
					preparedStmt.executeUpdate();
				
				}
				
			}
			
		} catch (SQLException e) {
				e.printStackTrace();
		}
		
		return eloNumber;
		
	}
	
	public void updateEloRating(int uid, int eloNumber) {
		
		try {
	
			//The ELO value can be at most 2500
			if (eloNumber > 2500)
				return;
			
			//The ELO value can not be negative
			if (eloNumber < 0)
				eloNumber = 0;
			
			String stmt = "UPDATE elo_rating SET elo_number=? WHERE user_id=?";
			
			PreparedStatement preparedStmt = this.databaseConnection.prepareStmt(stmt);
			preparedStmt.setInt(1, eloNumber);
			preparedStmt.setInt(2, uid);
			
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
				e.printStackTrace();
		}
		
		
	}
	
	private void writeToTableLogs() throws SQLException{

		int rowcount = this.preparedStatementLogs.executeUpdate();
		
		this.userIDProvided = false;
		this.preparedStatementLogs = this.databaseConnection.prepareStmtReturnKeys(GameLogger.STATEMENT_LOGS);
		
		// commit changes
		commitChanges();
	}
	
	private int writeToTableGameInstances() throws SQLException{

		int rowcount = this.preparedStatementGameInstance.executeUpdate();
		
		// commit changes
		commitChanges();
		
		ResultSet rs = this.preparedStatementGameInstance.getGeneratedKeys();
		int id = -1;
		if(rs.next())
			id = rs.getInt("game_instances_id");
		
		
		this.preparedStatementGameInstance = this.databaseConnection.prepareStmtReturnKeys(GameLogger.STATEMENT_GAME_INSTANCE);
	
		return id;
	}

	//Get log game type for a given fully specified class name.
	private static LogGame getLogGameByClass(String className){
		
		return className2GameType.get(className);
		
	}

	public boolean connectionClosed() {
		
		return this.databaseConnection.connectionClosed();
	}
	
}
