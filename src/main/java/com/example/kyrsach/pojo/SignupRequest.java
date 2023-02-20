package com.example.kyrsach.pojo;

import javax.validation.constraints.Size;
import java.util.Set;

public class SignupRequest {
	@Size(min = 4,max=50)
	private String username;
	@Size(min = 4,max=50)
	private String email;
	private Set<String> roles;
	@Size(min = 4,max=50)
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
