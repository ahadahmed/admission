package com.progoti.surecash.admission.domain;

public class User {

	private String userName;
	private String password;
	private String email;
	private String role;
	private University univ;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public University getUniv() {
		return univ;
	}

	public void setUniv(University univ) {
		this.univ = univ;
	}

}
