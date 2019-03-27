package com.ava.task.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ava.task.model.impl.User;
import com.ava.task.repository.UserRepository;
import com.ava.task.security.model.AvaUserDetails;

@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;
	
	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<User> user = userRepository.findByUsername(email);
		
		user.orElseThrow(() -> new UsernameNotFoundException("User doesn't exist!"));

		return getUserDetails(user.get());
	}
	
	private AvaUserDetails getUserDetails(User user) {
		return new AvaUserDetails(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getCountry(), user.getAddress(), user.isAdmin());
	}

}