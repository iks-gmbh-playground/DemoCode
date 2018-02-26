package com.iksgmbh.demo.spring.boot.thymeleaf.helloworld.data;

import javax.validation.constraints.Size;

public class UserLoginData {
	
	@Size(min=2, max=8)
	private String username;
	
	@Size(min=2, max= 8)
	private String password;

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
	
}
