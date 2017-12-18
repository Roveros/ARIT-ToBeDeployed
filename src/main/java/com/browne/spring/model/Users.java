package com.browne.spring.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
public class Users {

	@Id
	String username;
	
	String password;
	
	int enabled;
	
	private String reset_token;	

	public String getReset_token() {
		return reset_token;
	}

	public void setReset_token(String reset_token) {
		this.reset_token = reset_token;
	}

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

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public String toString(){
		return "username="+username+", password=\"***\", enabled="+enabled;
	}
}
