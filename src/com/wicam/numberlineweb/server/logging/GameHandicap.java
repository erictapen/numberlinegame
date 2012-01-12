package com.wicam.numberlineweb.server.logging;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.server.database.DatabaseConnection;

public abstract class GameHandicap implements IHandicap {
	
	private DatabaseConnection databaseConnection;
	
	private final String dbHost = "localhost";
	private final String dbPort = "5432";
	private final String db = "logging";
	private final String dbUser = "log_user";
	private final String dbPassword = "ner8tiro5";
	
	private static PreparedStatement preparedStmt;
	
	public enum GameType {NUMBER_LINE_GAME, DOPPELUNG_GAME,
		DEHNUNG_GAME, MATH_DIAGNOSTICS;

		//Get ID for game type
		public static int getIndex(GameType gameType){
			
			switch(gameType){
			
				case NUMBER_LINE_GAME:
					return 9;
				case MATH_DIAGNOSTICS:
					return 12;
				case DEHNUNG_GAME:
					return 11;
				case DOPPELUNG_GAME:
					return 10;
				default:
					//Should not occur
					return -1;
					
			
			}
		}
	
	};

	public GameHandicap(GameType gameType){

		try {
			
			this.databaseConnection = new DatabaseConnection(this.dbHost, this.dbPort, this.db, 
					this.dbUser, this.dbPassword);
			
			preparedStmt = this.databaseConnection.prepareStmt("SELECT * FROM logs WHERE log_action_id=" + GameType.getIndex(gameType) +
					" AND log_user_id=?");
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public double calculcateUserHandicap(int uid)
			throws NoHandicapDataException {
		List<Double> userHandicaps = new LinkedList<Double>();
		
		try {
			preparedStmt.setInt(1, uid);
			ResultSet resultSet = preparedStmt.executeQuery();
			
			while (resultSet.next()){
				
				String logActionParameters = resultSet.getString("log_action_parameters");
				double handicap = new Gson().fromJson(logActionParameters, DataObject.class).getHandicap();
				userHandicaps.add(handicap);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		double userHandicapMedian = 0.5;
		if (!userHandicaps.isEmpty()){
			
			Collections.sort(userHandicaps);
			int size = userHandicaps.size();
			if ((size % 2) != 0)
				userHandicapMedian = userHandicaps.get((size - 1) / 2);
			else
				userHandicapMedian = (userHandicaps.get((size / 2) - 1) + userHandicaps.get(size / 2)) / 2;
			
		}
		else
			throw new NoHandicapDataException();
		
		System.out.println("User handicap median: " + userHandicapMedian);
		
		return userHandicapMedian;
	}

	public abstract void adjustGameSetting(double handicap, GameState gameState);
	
	public class DataObject {
		
		private double handicap;

		public double getHandicap() {
			return handicap;
		}

		public void setHandicap(double handicap) {
			this.handicap = handicap;
		}
		
	}

}
