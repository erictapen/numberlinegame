package com.wicam.numberlineweb.server.logging;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import com.wicam.numberlineweb.client.GameState;
import com.wicam.numberlineweb.client.NumberLineGame.NumberLineGameState;
import com.wicam.numberlineweb.server.database.DatabaseConnection;


public class NumberLineGameHandicap implements IHandicap{
	
	private DatabaseConnection databaseConnection;
	
	private final String dbHost = "localhost";
	private final String dbPort = "5432";
	private final String db = "logging";
	private final String dbUser = "log_user";
	private final String dbPassword = "ner8tiro5";
	
	private static PreparedStatement preparedStmt;
	
	public NumberLineGameHandicap(){
		
			
			try {
				
				this.databaseConnection = new DatabaseConnection(this.dbHost, this.dbPort, this.db, 
						this.dbUser, this.dbPassword);
				
				preparedStmt = this.databaseConnection.prepareStmt("SELECT * FROM logs WHERE log_action_id=9 AND log_user_id=?");
				
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		
	}

	public double calculcateUserHandicap(int uid) throws NoHandicapDataException{
		
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
	
	public void adjustGameSetting(double handicap, GameState gameState) {
		NumberLineGameState numberLineGameState = (NumberLineGameState) gameState;
		
		int minimalPointerWidth = 1;
		int maximalPointerWidth = 85;
		
		int pointerWidth = (int) (handicap * (maximalPointerWidth - minimalPointerWidth) + minimalPointerWidth);
		
		numberLineGameState.setPointerWidth(pointerWidth);
		
		System.out.println("New pointerwidth: " + pointerWidth);
		
	}
	
	
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

