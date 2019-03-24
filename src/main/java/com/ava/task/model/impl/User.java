package com.ava.task.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.ava.task.model.AbstractModel;

@Entity
@Table(name = "user")
public class User implements AbstractModel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	
	@NonNull
	@Column(name = "first_name")
	protected String firstName;

	@NonNull
	@Column(name = "last_name")
	protected String lastName;

	@NonNull
	@Column(name = "email")
	protected String email;

	@NonNull
	@Column(name = "password")
	protected String password;

	@Column(name = "country")
	protected String country;

	@Column(name = "address")
	protected String address;

	@Column(name = "admin")
	protected boolean admin;

	public User() { }
	
	public User(String firstName, String lastName, String email, String password, String country, String address, boolean admin) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.country = country;
		this.address = address;
		this.admin = admin;
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
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
