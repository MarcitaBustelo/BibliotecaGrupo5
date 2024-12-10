package com.example.demo.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserModel {

	private long id;

	@NotEmpty(message = "Name must not be empty")
	private String name;

	@NotEmpty(message = "Lastname must not be empty")
	private String lastname;

	@NotEmpty(message = "Email must not be empty")
	@Email
	private String email;

	@NotEmpty(message = "Password must not be empty")
	private String password;

	private String rePassword;

	private String role;

	public UserModel(long id, @NotEmpty(message = "Name must not be empty") String name,
			@NotEmpty(message = "Lastname must not be empty") String lastname,
			@NotEmpty(message = "Email must not be empty") @Email String email,
			@NotEmpty(message = "Password must not be empty") String password, String rePassword, String role) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.rePassword = rePassword;
		this.role = role;
	}

	public UserModel() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", name=" + name + ", lastname=" + lastname + ", email=" + email + ", password="
				+ password + ", role=" + role + "]";
	}

}
