package com.ava.task.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ava.task.api.constants.AvaRouter;
import com.ava.task.dto.AbstractDTO;
import com.ava.task.dto.impl.ResponseDTO;
import com.ava.task.dto.impl.UserDTO;
import com.ava.task.exception.AvaException;
import com.ava.task.service.UserService;

@RestController
@RequestMapping(value=AvaRouter.REGULAR)
public class RegularController {
	
	private UserService userService;
	
	@Autowired
	public RegularController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<AbstractDTO> getDetails(Authentication auth) {
		final String email = (String) auth.getPrincipal();
		try {
			return ResponseEntity.ok(userService.getUser(email));
		} catch (AvaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(e.getMessage()));
		}
	}
	
	@PatchMapping
	public ResponseEntity<ResponseDTO> updateDetailsPartial(@RequestBody UserDTO user, Authentication auth) {
		final String email = (String) auth.getPrincipal();
		try {
			userService.updateUser(email, user);
			return ResponseEntity.ok().build();
		} catch (AvaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(e.getMessage()));
		}
	}

}
