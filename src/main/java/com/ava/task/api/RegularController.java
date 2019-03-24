package com.ava.task.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ava.task.api.constants.AvaRouter;
import com.ava.task.dto.impl.ResponseDTO;
import com.ava.task.dto.impl.UserDTO;
import com.ava.task.exception.AvaException;
import com.ava.task.service.UserService;

@RestController
@RequestMapping(value=AvaRouter.REGULAR)
public class RegularController {
	
	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<UserDTO> getDetails(Authentication auth) {
		String email = (String) auth.getPrincipal();
		try {
			return ResponseEntity.ok(userService.getUser(email));
		} catch (AvaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PutMapping
	public ResponseEntity<ResponseDTO> updateDetails(@Valid @RequestBody UserDTO user, Authentication auth) {
		String email = (String) auth.getPrincipal();
		userService.updateUser(email, user);
		if (email.equals(user.getEmail())) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.ok(new ResponseDTO("Please login again!"));			
		}
	}

	@PatchMapping
	public ResponseEntity<ResponseDTO> updateDetailsParial(@RequestBody UserDTO user, Authentication auth) {
		String email = (String) auth.getPrincipal();
		if (user != null) {
			userService.updateUserPartial(email, user);
			return checkEmailChange(email, user);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	private ResponseEntity<ResponseDTO> checkEmailChange(String email, UserDTO user) {
		if (email.equals(user.getEmail())) {				
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.ok(new ResponseDTO("Please login again!"));
	}

}
