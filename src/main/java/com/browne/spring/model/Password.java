package com.browne.spring.model;

public class Password {

	private String password_confirmation;
	
	private String password;	
	
	private String reset_token;	

	public String getReset_token() {
		return reset_token;
	}

	public void setReset_token(String reset_token) {
		this.reset_token = reset_token;
	}

	public String getPassword_confirmation() {
		return password_confirmation;
	}

	public void setPassword_confirmation(String password_confirmation) {
		this.password_confirmation = password_confirmation;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
