package com.Authentication.api;

public class User {
	String username;
	String password;
	String email;
	
//	public User(String username, String password) {
//		super();
//		this.username = username;
//		this.password = password;
//	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}	
	
	
}
