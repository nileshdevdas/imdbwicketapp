package com.vinsys.app;

import java.io.Serializable;

// ADD A POJO WITH USERNAME/PASSWORD FIELDS WHICH IS SERIALIZABLE
public class User implements Serializable {
	private String username;
	private String password;

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
