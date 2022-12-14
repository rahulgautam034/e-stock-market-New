package com.userservice.dto;

import java.io.Serializable;

/**
 * authentication modal
 *
 */
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	private String username;
	private String password;

	/**
	 * need default constructor for JSON Parsing
	 */
	public JwtRequest() {

	}

	/**
	 * all args constructor
	 *
	 */
	public JwtRequest(final String username,final String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}