package com.ava.task.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ava.task.converter.Converter;
import com.ava.task.dto.impl.UserDTO;
import com.ava.task.exception.AvaException;
import com.ava.task.model.impl.User;
import com.ava.task.repository.UserRepository;
import com.ava.task.repository.specification.UserSpecification;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private Converter<User, UserDTO> converter;
	
	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, Converter<User, UserDTO> converter) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.converter = converter;
	}

	public UserDTO getUser(String email) throws AvaException {
		final Optional<User> userOpt = userRepository.findByUsername(email);
		userOpt.orElseThrow(() -> new AvaException("No user with given email!"));
		return userOpt.isPresent() ? converter.toDto(userOpt.get()) : null;
	}
	
	public UserDTO getUser(long id) throws AvaException {
		try {
			final User user = userRepository.getOne(id);
			return converter.toDto(user);	
		} catch (EntityNotFoundException e) {
			throw new AvaException(e.getMessage());
		}
	}
	
	public List<UserDTO> getUsers(UserDTO userDto) {
		final List<User> users = userRepository.findAll(new UserSpecification(userDto));
		if (CollectionUtils.isEmpty(users)) {
			return Collections.emptyList();
		}
		return users.stream().map(user -> converter.toDto(user)).collect(Collectors.toList());
	}
	
	public void createUser(UserDTO userDto) throws AvaException {
		try {
			final User user = converter.fromDto(userDto);
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userRepository.saveAndFlush(user);
		} catch (Exception e) {
			throw new AvaException(e.getMessage());
		}
	}
	
	public void updateUser(UserDTO userDto) throws AvaException {
		try {
			if (userDto.getId() == null) {
				throw new AvaException("ID can't be null!");
			}
			final User user = userRepository.getOne(userDto.getId());
			updateUser(user, userDto, true);
			userRepository.saveAndFlush(user);
		} catch (EntityNotFoundException e) {
			throw new AvaException(e.getMessage());
		}
	}
	
	public void updateUser(String email, UserDTO userDto) throws AvaException {
		try {
			final Optional<User> user = userRepository.findByUsername(email);
			userRepository.saveAndFlush(updateUser(user.get(), userDto, false));
		} catch (Exception e) {
			throw new AvaException(e.getMessage());
		}
	}
	
	private User updateUser(User user, UserDTO userDto, boolean isAdmin) {
		if (userDto.getAddress() != null) {
			user.setAddress(userDto.getAddress());
		}
		if (userDto.getCountry() != null) {
			user.setCountry(userDto.getCountry());
		}
		if (userDto.getEmail() != null) {
			user.setEmail(userDto.getEmail());
		}
		if (userDto.getFirstName() != null) {
			user.setFirstName(userDto.getFirstName());
		}
		if (userDto.getLastName() != null) {
			user.setLastName(userDto.getLastName());
		}
		if (userDto.getPassword() != null) {
			user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		}
		if (isAdmin && userDto.isAdmin() != null) {
			user.setAdmin(userDto.isAdmin());
		}
		return user;
	}
	
}
