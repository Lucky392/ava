package com.ava.task.converter.impl;

import org.springframework.stereotype.Service;

import com.ava.task.converter.Converter;
import com.ava.task.dto.impl.UserDTO;
import com.ava.task.model.impl.User;

@Service
public class UserConverter implements Converter<User, UserDTO> {

	@Override
	public UserDTO toDto(User user) {
		return user == null ? null : new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getCountry(), user.getAddress(), user.isAdmin());
	}

	@Override
	public User fromDto(UserDTO userDto) {
		return userDto == null ? null : new User(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), userDto.getPassword(), userDto.getCountry(), userDto.getAddress(), userDto.isAdmin());
	}

}
