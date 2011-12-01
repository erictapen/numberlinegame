package com.wicam.numberlineweb.client.logging;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.wicam.numberlineweb.server.database.DatabaseConnection;

public class Logger {
	
	private DatabaseConnection databaseConnection;
	
	private final String dbHost = "localhost";
	private final String dbPort = "5432";
	private final String db = "logging";
	private final String dbUser = "log_user";
	private final String dbPassword = "ner8tiro5";
	
	private static final String STATEMENT = "INSERT INTO logs (log_user_id, log_time," +
			"log_action_id, log_action_parameters, log_action_time, log_game)" +
			" VALUES (?, ?, ?, ?, ?, ?)";
	private PreparedStatement preparedStatement;
	
	// General action data common for all games
	
	private long logActionTime;
	
	
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
					return 1;
					
			
			}
		}
	
	};
		
	private LogGame logGame;
	
	//Constants for action type equal 
	//IDs in the action table
	private static final int GAME_STARTED = 1;
	private static final int GAME_ENDED = 2;
	private static final int ROUND_STARTED = 3;
	private static final int ROUND_ENDED = 4;
	private static final int MOVE = 5;
	
	private long gameStartTime;
	private long gameEndTime;
	
	private long roundStartTime;
	private long roundEndTime;

	private long moveTime;
	
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
	
	public void writeGameStartEntry(){
		
		if(loggingActive == LoggingActive.OFF)
			return;

		try {
			//TODO Use real user id 
			this.preparedStatement.setInt(1, 1);
			//Time stamp
			this.preparedStatement.setDouble(2, System.currentTimeMillis());
			//Action type
			this.preparedStatement.setInt(3, Logger.GAME_STARTED);
			//Action parameters
			this.preparedStatement.setString(4, "");
			//Action time
			this.preparedStatement.setDouble(5, this.getGameStartTime());
			//Game type
			this.preparedStatement.setInt(6, LogGame.getIndex(this.getLogGame()));
			
			this.writeToTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeGameEndEntry(){
		
		if(loggingActive == LoggingActive.OFF)
			return;

		try {
			//TODO Use real user id 
			this.preparedStatement.setInt(1, 1);
			//Time stamp
			this.preparedStatement.setDouble(2, System.currentTimeMillis());
			//Action type
			this.preparedStatement.setInt(3, Logger.GAME_ENDED);
			//Action parameters
			this.preparedStatement.setString(4, "");
			//Action time
			this.preparedStatement.setDouble(5, this.getGameEndTime());
			//Game type
			this.preparedStatement.setInt(6, LogGame.getIndex(this.getLogGame()));
			
			this.writeToTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeRoundStartEntry(){
		
		if(loggingActive == LoggingActive.OFF)
			return;

		try {
			//TODO Use real user id 
			this.preparedStatement.setInt(1, 1);
			//Time stamp
			this.preparedStatement.setDouble(2, System.currentTimeMillis());
			//Action type
			this.preparedStatement.setInt(3, Logger.ROUND_STARTED);
			//Action parameters
			this.preparedStatement.setString(4, "");
			//Action time
			this.preparedStatement.setDouble(5, this.getRoundStartTime());
			//Game type
			this.preparedStatement.setInt(6, LogGame.getIndex(this.getLogGame()));
			
			this.writeToTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeRoundEndEntry(){
		
		if(loggingActive == LoggingActive.OFF)
			return;

		try {
			//TODO Use real user id 
			this.preparedStatement.setInt(1, 1);
			//Time stamp
			this.preparedStatement.setDouble(2, System.currentTimeMillis());
			//Action type
			this.preparedStatement.setInt(3, Logger.ROUND_ENDED);
			//Action parameters
			this.preparedStatement.setString(4, "");
			//Action time
			this.preparedStatement.setDouble(5, this.getRoundEndTime());
			//Game type
			this.preparedStatement.setInt(6, LogGame.getIndex(this.getLogGame()));
			
			this.writeToTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeMoveEntry(){
		
		if(loggingActive == LoggingActive.OFF)
			return;

		try {
			//TODO Use real user id 
			this.preparedStatement.setInt(1, 1);
			//Time stamp
			this.preparedStatement.setDouble(2, System.currentTimeMillis());
			//Action type
			this.preparedStatement.setInt(3, Logger.MOVE);
			//Action parameters
			this.preparedStatement.setString(4, "");
			//Action time
			this.preparedStatement.setDouble(5, this.getMoveTime());
			//Game type
			this.preparedStatement.setInt(6, LogGame.getIndex(this.getLogGame()));
			
			this.writeToTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void writeToTable() throws SQLException{

		this.preparedStatement.executeUpdate();
		this.databaseConnection.commit();
	
	}

	public long getLogActionTime() {
		return logActionTime;
	}

	public void setLogActionTime(long logActionTime) {
		this.logActionTime = logActionTime;
	}

	public long getRoundEndTime() {
		return roundEndTime;
	}

	public void setRoundEndTime(long roundEndTime) {
		this.roundEndTime = roundEndTime;
	}

	public long getGameEndTime() {
		return gameEndTime;
	}

	public void setGameEndTime(long gameEndTime) {
		this.gameEndTime = gameEndTime;
	}

	public long getGameStartTime() {
		return gameStartTime;
	}

	public void setGameStartTime(long gameStartTime) {
		this.gameStartTime = gameStartTime;
	}

	public long getRoundStartTime() {
		return roundStartTime;
	}

	public void setRoundStartTime(long roundStartTime) {
		this.roundStartTime = roundStartTime;
	}
	
	public void setLogGame(LogGame logGame){
		this.logGame = logGame;
	}
	
	public LogGame getLogGame(){
		return logGame;
	}

	public long getMoveTime() {
		return moveTime;
	}

	public void setMoveTime(long moveTime) {
		this.moveTime = moveTime;
	}
}
