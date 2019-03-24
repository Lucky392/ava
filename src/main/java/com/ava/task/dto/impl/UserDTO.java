package com.ava.task.dto.impl;

import javax.validation.constraints.NotNull;

import com.ava.task.dto.AbstractDTO;

public class UserDTO implements AbstractDTO {

	private Long id;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String email;
	@NotNull
	private String password;
	private String country;
	private String address;
	@NotNull
	private Boolean isAdmin;
	
	public UserDTO() { }
	
	public UserDTO(Long id, String firstName, String lastName, String email, String password, String country, String address, boolean isAdmin) {
		this.setId(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		//Should not be sent even encrypted
//		this.password = password;
		this.country = country;
		this.address = address;
		this.isAdmin = isAdmin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
