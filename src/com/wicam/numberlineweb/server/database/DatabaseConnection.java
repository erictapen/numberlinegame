package com.wicam.numberlineweb.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;

public class DatabaseConnection {

	private Connection conn = null;

	public DatabaseConnection(String dbHost, String dbPort, String db, String dbUser, String dbPassword) throws ClassNotFoundException, SQLException  {

		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection("jdbc:postgresql://" + dbHost
				+ ":" + dbPort + "/" + db, dbUser, dbPassword);
		
		conn.setAutoCommit(false);

	}

	public List<Map<String, String>> eval(String qry) throws SQLException {
		List<Map<String, String>> res = null;
		QueryRunner qrun = new QueryRunner();

		conn.createStatement();
		res = qrun.query(conn, qry, new SerializableHandler());
		
		return res;
	}
	
	public PreparedStatement prepareStmtReturnKeys(String stmt) throws SQLException{
		
		return conn.prepareStatement(stmt, PreparedStatement.RETURN_GENERATED_KEYS);
			
	}
	
	
	public PreparedStatement prepareStmt(String stmt) throws SQLException{
		
		return conn.prepareStatement(stmt);
			
	}
	
	
	public void commit() {
		
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void rollback() {
		
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public boolean connectionClosed() {
		
		try {
			return conn.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

}
