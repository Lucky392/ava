package com.ava.task.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ava.task.converter.Converter;
import com.ava.task.converter.impl.UserConverter;
import com.ava.task.dto.impl.UserDTO;
import com.ava.task.exception.AvaException;
import com.ava.task.factory.AvaFactory;
import com.ava.task.model.impl.User;
import com.ava.task.repository.UserRepository;
import com.ava.task.repository.specification.UserSpecification;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private Converter<User, UserDTO> converter;
	
	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		try {
			converter = AvaFactory.getConverter(UserConverter.class);
		} catch (AvaException e) {
			//Should never happen!
			e.printStackTrace();
		}
	}

	public UserDTO getUser(String email) throws AvaException {
		Optional<User> userOpt = userRepository.findByUsername(email);
		userOpt.orElseThrow(() -> new AvaException("No user with given email!"));
		return userOpt.isPresent() ? converter.toDto(userOpt.get()) : null;
	}
	
	public UserDTO getUser(long id) throws AvaException {
		Optional<User> userOpt = userRepository.findById(id);
		userOpt.orElseThrow(() -> new AvaException("No user with given id!"));
		return userOpt.isPresent() ? converter.toDto(userOpt.get()) : null;
	}
	
	public List<UserDTO> getUsers(UserDTO userDto) {
		List<User> users = userRepository.findAll(new UserSpecification(userDto));
		if (CollectionUtils.isEmpty(users)) {
			return Collections.emptyList();
		}
		return users.stream().map(user -> converter.toDto(user)).collect(Collectors.toList());
	}
	
	public void createUser(UserDTO userDto) throws AvaException {
		try {
			User user = converter.fromDto(userDto);
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userRepository.saveAndFlush(user);
		} catch (Exception e) {
			throw new AvaException(e.getMessage());
		}
	}
	
	public void updateUser(long userId, UserDTO userDto) throws AvaException {
		final User user = converter.fromDto(userDto);
		user.setId(userId);
		userRepository.save(user);
	}
	
	public void updateUserPartial(long userId, UserDTO userDto) throws AvaException {
		Optional<User> user = userRepository.findById(userId);
		user.orElseThrow(() -> new AvaException("No user with given id!"));
		userRepository.save(updateFields(user.get(), userDto));
	}
	
	public void updateUser(String email, UserDTO userDto) {
		final Optional<User> userOpt = userRepository.findByUsername(email);
		final User user = converter.fromDto(userDto);
		user.setId(userOpt.get().getId());
		userRepository.save(user);
	}
	
	public void updateUserPartial(String email, UserDTO userDto) {
		final Optional<User> userOpt = userRepository.findByUsername(email);
		userRepository.save(updateFields(userOpt.get(), userDto));
	}
	
	private User updateFields(User user, UserDTO userDto) {
		if (userDto.isAdmin() != null) {
			user.setAdmin(userDto.isAdmin());
		}
		if (userDto.getFirstName() != null) {
			user.setFirstName(userDto.getFirstName());
		}
		if (userDto.getLastName() != null) {
			user.setLastName(userDto.getLastName());
		}
		if (userDto.getEmail() != null) {
			user.setEmail(userDto.getEmail());
		}
		if (userDto.getAddress() != null) {
			user.setAddress(userDto.getAddress());
		}
		if (userDto.getCountry() != null) {
			user.setCountry(userDto.getCountry());
		}
		if (userDto.getPassword() != null) {
			user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		}
		return user;
	}
	
}
