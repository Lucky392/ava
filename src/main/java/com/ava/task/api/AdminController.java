package com.ava.task.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ava.task.api.constants.AvaRouter;
import com.ava.task.api.constants.RestVariable;
import com.ava.task.dto.impl.ResponseDTO;
import com.ava.task.dto.impl.UserDTO;
import com.ava.task.exception.AvaException;
import com.ava.task.service.UserService;

@RestController
@RequestMapping(value=AvaRouter.ADMIN)
public class AdminController {
	
	@Autowired
	private UserService userService;
	

	@GetMapping(value = AvaRouter.FIND_BY_ID)
	public ResponseEntity<UserDTO> getUser(@PathVariable(name = RestVariable.ID) long id) {
		try {
			return ResponseEntity.ok(userService.getUser(id));
		} catch (AvaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers(@RequestBody UserDTO userDto) {
		return ResponseEntity.ok(userService.getUsers(userDto));
	}

	
	@PostMapping
	public ResponseEntity<ResponseDTO> createUser(@Valid @RequestBody UserDTO user) {
		try {
			userService.createUser(user);
			return ResponseEntity.ok().build();
		} catch (AvaException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(e.getMessage()));
		}
	}
	
	
	@PutMapping(value = AvaRouter.FIND_BY_ID)
	public ResponseEntity<ResponseDTO> updateUser(@PathVariable(name = RestVariable.ID) long id, @Valid @RequestBody UserDTO user) {
		try {
			userService.updateUser(id, user);
			return ResponseEntity.ok().build();
		} catch (AvaException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(e.getMessage()));
		}
	}


	@PatchMapping(value = AvaRouter.FIND_BY_ID)
	public ResponseEntity<ResponseDTO> updateUserPartial(@PathVariable(name = RestVariable.ID) long id, @RequestBody UserDTO user) {
		try {
			userService.updateUserPartial(id, user);
			return ResponseEntity.ok().build();
		} catch (AvaException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(e.getMessage()));
		}
	}

}
