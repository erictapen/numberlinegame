package com.wicam.numberlineweb.server.database.drupal;

import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable{
	
	private int id;
	private String uname;
	private String pwHash;
	private String mail;
	
	public User() {
		
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the uname
	 */
	public String getUname() {
		return uname;
	}

	/**
	 * @param uname the uname to set
	 */
	public void setUname(String uname) {
		this.uname = uname;
	}

	/**
	 * @return the pwHash
	 */
	public String getPwHash() {
		return pwHash;
	}

	/**
	 * @param pwHash the pwHash to set
	 */
	public void setPwHash(String pwHash) {
		this.pwHash = pwHash;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	

}
