package com.ava.task.security.model;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ava.task.model.impl.User;
import com.ava.task.security.constants.Role;

public class AvaUserDetails extends User implements UserDetails {
	
	public AvaUserDetails() { }
	
	public AvaUserDetails(String firstName, String lastName, String email, String password, String country, String address, boolean admin) {
		super(firstName, lastName, email, password, country, address, admin);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1530712052278906529L;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return admin ? Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_ADMIN)) : Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_USER));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
