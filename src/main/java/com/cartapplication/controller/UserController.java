package com.cartapplication.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.cartapplication.dto.UserRequestDTO;
import com.cartapplication.dto.UserResponseDTO;
import com.cartapplication.entity.User;
import com.cartapplication.service.UserService;
//import com.cartapplication.validator.UserValidation;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@PostMapping
	public UserResponseDTO createUser( @Valid @RequestBody UserRequestDTO dto) {
		return userService.createUser(dto);
	}
	
	@GetMapping("/{id}")
	public UserResponseDTO getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}
	
	@GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
	}
    
	
	@PutMapping("/{id}")
	public UserResponseDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto) {
		return userService.updateUser(id, dto);
	}
}
