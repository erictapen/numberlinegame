package com.wicam.numberlineweb.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;

public class DatabaseConnection {

	private static Connection conn = null;

	public DatabaseConnection(String dbHost, String dbPort, String db, String dbUser, String dbPassword) throws ClassNotFoundException, SQLException  {

		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection("jdbc:postgresql://" + dbHost
				+ ":" + dbPort + "/" + db, dbUser, dbPassword);

	}

	public List<Map<String, String>> eval(String qry) throws SQLException {
		List<Map<String, String>> res = null;
		QueryRunner qrun = new QueryRunner();

		conn.createStatement();
		res = qrun.query(conn, qry, new SerializableHandler());

		return res;
	}
	
	public PreparedStatement prepareStmt(String stmt) throws SQLException{
		
		conn.setAutoCommit(false);
		
		return conn.prepareStatement(stmt);
			
	}
	
	public void commit(){
		
		try {
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
