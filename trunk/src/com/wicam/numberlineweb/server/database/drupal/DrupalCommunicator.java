package com.wicam.numberlineweb.server.database.drupal;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.wicam.numberlineweb.server.database.DatabaseConnection;

public class DrupalCommunicator {

	private DatabaseConnection c;

	public DrupalCommunicator(DatabaseConnection c) {

		this.c=c;		

	}

	public DrupalCommunicator() {

		String dbPw = "43wfsfwewf";
		String dbUser = "drupal_wiscam";
		String dbHost = "127.0.0.1";
		String db = "drupal_wiscam";
	
		try {
			this.c= new DatabaseConnection(dbHost, "5432", db, dbUser, dbPw);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public User getUser(int id) throws UserNotFoundException {

		User ret = new User();

		try {
			List<Map<String, String>> res = c.eval("SELECT uid,name,pass,mail FROM users WHERE uid = " + id);

			if (res.size() == 0) throw new UserNotFoundException();
			ret.setId(Integer.parseInt(res.get(0).get("uid")));
			ret.setMail(res.get(0).get("mail"));
			ret.setPwHash(res.get(0).get("pass"));
			ret.setUname(res.get(0).get("name"));

		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserNotFoundException();
		}		

		return ret;

	}

}
