package com.cartapplication.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.cartapplication.entity.User;
import com.cartapplication.service.UserService;
import com.cartapplication.validator.UserValidation;


@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;
	private UserValidation validator;
	
	public UserController(UserService userService, UserValidation validator) {
		this.userService=userService;
		this.validator=validator;
	}
	
	@PostMapping
	public User createUser(@RequestBody User user) {
		validator.validate(user);
		return userService.createUser(user);
	}
	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}
	
	@GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
	}
    
	
	@PutMapping("/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User user) {
		return userService.updateUser(id, user);
	}
}
