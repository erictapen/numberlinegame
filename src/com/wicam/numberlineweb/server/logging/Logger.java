package com.wicam.numberlineweb.server.logging;

import java.sql.PreparedStatement;
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
	
	private static final String STATEMENT = "INSERT INTO logs (log_user_id, log_time," +
			"log_action_id, log_action_parameters, log_action_time, log_game, log_action_trigger)" +
			" VALUES (?, ?, ?, ?, ?, ?, ?)";
	private PreparedStatement preparedStatement;

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
		NUMBERLINE_NUMBER_PRESENTED;

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
			
			this.preparedStatement = this.databaseConnection.prepareStmt(Logger.STATEMENT);
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void log(long logActionTime, LogActionType logActionType, String actionParams,
			String logGameClassName, LogActionTrigger logActionTrigger){
		
		if(loggingActive == LoggingActive.OFF)
			return;
		
		try {
			
			if(!this.userIDProvided)
				this.preparedStatement.setNull(1, java.sql.Types.DOUBLE);
						
			//Time stamp
			this.preparedStatement.setDouble(2, System.currentTimeMillis());
			
			//Action type
			this.preparedStatement.setInt(3, LogActionType.getIndex(logActionType));
			
			//Action parameters
			this.preparedStatement.setString(4, actionParams);
			
			//Action time
			this.preparedStatement.setDouble(5, logActionTime);
			
			//Game type
			LogGame game = getLogGameByClass(logGameClassName);
			this.preparedStatement.setInt(6, LogGame.getIndex(game));
			
			//Action trigger (user, application or NPC)
			this.preparedStatement.setString(7, LogActionTrigger.getName(logActionTrigger));
			
			//Write entry to database
			this.writeToTable();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
	}
	
	public void log(long logUserID, long logActionTime, LogActionType logActionType, String actionParams,
			String logGameClassName, LogActionTrigger logActionTrigger){
		
		if(loggingActive == LoggingActive.OFF)
			return;
		
		try {
			this.preparedStatement.setDouble(1, logUserID);
			this.userIDProvided = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		this.log(logActionTime, logActionType, actionParams, logGameClassName, logActionTrigger);
		
	}
	
	private void writeToTable() throws SQLException{

		this.preparedStatement.executeUpdate();
		this.databaseConnection.commit();
		
		this.userIDProvided = false;
		this.preparedStatement = this.databaseConnection.prepareStmt(Logger.STATEMENT);
	
	}
	
	//Get log game type for a given fully specified class name.
	private static LogGame getLogGameByClass(String className){
		
		return className2GameType.get(className);
		
	}
}
