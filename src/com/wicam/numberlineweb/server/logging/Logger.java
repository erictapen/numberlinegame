package com.wicam.numberlineweb.server.logging;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.core.java.util.Collections;
import com.wicam.numberlineweb.server.database.DatabaseConnection;

public class Logger {
	
	private DatabaseConnection databaseConnection;
	
	private final String dbHost = "localhost";
	private final String dbPort = "5432";
	private final String db = "logging";
	private final String dbUser = "log_user";
	private final String dbPassword = "ner8tiro5";
	
	private static final String STATEMENT_LOGS = "INSERT INTO logs (log_user_id, log_time," +
			"log_action_id, log_action_parameters, log_action_time, log_action_trigger, log_game_instance)" +
			" VALUES (?, ?, ?, ?, ?, ?, ?)";
	private PreparedStatement preparedStatementLogs;
	
	private static final String STATEMENT_GAME_INSTANCE = "INSERT INTO game_instances (game_id," +
			"game_property_id)" +
			" VALUES (?, ?)";
	private PreparedStatement preparedStatementGameInstance;
	
	//TODO Statement needs to be changed when more columns are added to table game_properties
	private static final String STATEMENT_GAME_PROPERTY = "INSERT INTO game_properties DEFAULT VALUES";
	private PreparedStatement preparedStatementGameProperty;

	private boolean userIDProvided = false;
	
	private static int internalGameId = 0;
	
	private static final Map<String, LogGame> className2GameType;
	
	private Map<LogGame, Map<Integer, Integer>> gameId2internalId = new HashMap<LogGame, Map<Integer, Integer>>();

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

		className2GameType = java.util.Collections.unmodifiableMap(tempMap);
		
	}
	
	// General action data common for all games
	
	public enum LogGame {NUMBER_LINE_GAME, DOPPELUNG_GAME,
		DEHNUNG_GAME, MATH_DIAGNOSTICS;

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
				default:
					//Should not occur
					return -1;
					
			
			}
		}
	
	};
	
	public enum LogActionType {GAME_STARTED, GAME_ENDED, JOINED_GAME, LEFT_GAME,
		NUMBERLINE_SUCCESSFUL_CLICK, NUMBERLINE_POSITION_TAKEN, NUMBERLINE_NPC_GUESS,
		NUMBERLINE_NUMBER_PRESENTED, NUMBERLINE_HANDICAP;

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
				case NUMBERLINE_HANDICAP:
					return 9;
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
	
	
	public static enum LoggingActive {ON, OFF};
	//Enable logging by default
	public static LoggingActive loggingActive = LoggingActive.ON;
	
	public Logger(){
		
		try {
			
			this.databaseConnection = new DatabaseConnection(this.dbHost, this.dbPort, this.db, 
					this.dbUser, this.dbPassword);
			
			this.preparedStatementLogs = this.databaseConnection.prepareStmtReturnKeys(Logger.STATEMENT_LOGS);
			
			this.preparedStatementGameInstance = this.databaseConnection.prepareStmtReturnKeys(Logger.STATEMENT_GAME_INSTANCE);
			
			this.preparedStatementGameProperty = this.databaseConnection.prepareStmtReturnKeys(Logger.STATEMENT_GAME_PROPERTY);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void log(int gameId, long logActionTime, LogActionType logActionType, String actionParams,
			String logGameClassName, LogActionTrigger logActionTrigger){
		
		if(loggingActive == LoggingActive.OFF)
			return;
		
		try {
			
			if(!this.userIDProvided)
				this.preparedStatementLogs.setNull(1, java.sql.Types.DOUBLE);
				
			//Time stamp
			this.preparedStatementLogs.setDouble(2, System.currentTimeMillis());
			
			//Action type
			this.preparedStatementLogs.setInt(3, LogActionType.getIndex(logActionType));
			
			//Action parameters
			this.preparedStatementLogs.setString(4, actionParams);
			
			//Action time
			this.preparedStatementLogs.setDouble(5, logActionTime);
			
			//Action trigger (user, application or NPC)
			this.preparedStatementLogs.setString(6, LogActionTrigger.getName(logActionTrigger));
			
			int gameInstanceId;
			//Keep track of game IDs used by the servlets and those used for logging
			if(logActionType == LogActionType.GAME_STARTED){
				if(this.gameId2internalId.get(getLogGameByClass(logGameClassName)) == null)
					this.gameId2internalId.put(getLogGameByClass(logGameClassName), new HashMap<Integer, Integer>());
				this.gameId2internalId.get(getLogGameByClass(logGameClassName)).put(gameId, internalGameId);
				
				//Create new game property entry
				
				int gamePropertyId = this.writeToTableGameProperties();

				//Create new game instance entry
				
				LogGame game = getLogGameByClass(logGameClassName);
				this.preparedStatementGameInstance.setInt(1, LogGame.getIndex(game));
				
				this.preparedStatementGameInstance.setInt(2, gamePropertyId);
				gameInstanceId = this.writeToTableGameInstances();
				
				if(this.gameId2internalId.get(getLogGameByClass(logGameClassName)) == null)
					this.gameId2internalId.put(getLogGameByClass(logGameClassName), new HashMap<Integer, Integer>());
				this.gameId2internalId.get(getLogGameByClass(logGameClassName)).put(gameId, gameInstanceId);
				
			}
			else	
				gameInstanceId = this.gameId2internalId.get(getLogGameByClass(logGameClassName)).get(gameId);
				
			//Make reference for log entry to game instance entry
			this.preparedStatementLogs.setInt(7, gameInstanceId);
			
			//Write entry to database
			this.writeToTableLogs();
		
		} catch (SQLException e) {
			e.printStackTrace();
		};
		
	}
	
	public void log(int gameID, long logUserID, long logActionTime, LogActionType logActionType, String actionParams,
			String logGameClassName, LogActionTrigger logActionTrigger){
		
		if(loggingActive == LoggingActive.OFF)
			return;
		
		try {
			this.preparedStatementLogs.setDouble(1, logUserID);
			this.userIDProvided = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		this.log(gameID, logActionTime, logActionType, actionParams, logGameClassName, logActionTrigger);
		
	}
	
	private void writeToTableLogs() throws SQLException{

		this.preparedStatementLogs.executeUpdate();
		this.databaseConnection.commit();
		
		this.userIDProvided = false;
		this.preparedStatementLogs = this.databaseConnection.prepareStmtReturnKeys(Logger.STATEMENT_LOGS);
	
	}
	
	private int writeToTableGameInstances() throws SQLException{

		this.preparedStatementGameInstance.executeUpdate();
		this.databaseConnection.commit();
		
		ResultSet rs = this.preparedStatementGameInstance.getGeneratedKeys();
		int id = -1;
		if(rs.next())
			id = rs.getInt("game_instances_id");
		
		
		this.preparedStatementGameInstance = this.databaseConnection.prepareStmtReturnKeys(Logger.STATEMENT_GAME_INSTANCE);
	
		return id;
	}
	
	private int writeToTableGameProperties() throws SQLException{

		this.preparedStatementGameProperty.executeUpdate();
		this.databaseConnection.commit();
		
		ResultSet rs = this.preparedStatementGameProperty.getGeneratedKeys();
		int id = -1;
		if(rs.next())
			id = rs.getInt("game_properties_id");
		
		this.preparedStatementGameProperty = this.databaseConnection.prepareStmtReturnKeys(Logger.STATEMENT_GAME_PROPERTY);
	
		return id;
	}
	
	//Get log game type for a given fully specified class name.
	private static LogGame getLogGameByClass(String className){
		
		return className2GameType.get(className);
		
	}
}
