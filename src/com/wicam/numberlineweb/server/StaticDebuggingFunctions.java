package com.wicam.numberlineweb.server;

import java.sql.SQLException;

/** This is just for taking a closer look on some SQLExceptions.
 * @author justin
 *
 */
public class StaticDebuggingFunctions {
	
	public static void printSQLException(SQLException ex) {

	    for (Throwable e : ex) {
	        if (e instanceof SQLException) {
	            if (true) {

	                e.printStackTrace(System.err);
	                System.err.println("SQLState: " +
	                    ((SQLException)e).getSQLState());

	                System.err.println("Error Code: " +
	                    ((SQLException)e).getErrorCode());

	                System.err.println("Message: " + e.getMessage());

	                Throwable t = ex.getCause();
	                while(t != null) {
	                    System.out.println("Cause: " + t);
	                    t = t.getCause();
	                }
	            }
	        }
	    }
	}
}
