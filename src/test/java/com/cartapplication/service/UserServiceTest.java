package com.cartapplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cartapplication.dto.UserRequestDTO;
import com.cartapplication.dto.UserResponseDTO;
import com.cartapplication.entity.User;
import com.cartapplication.enums.UserRole;
import com.cartapplication.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@SpringBootTest
@Transactional
public class UserServiceTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	@Autowired
	private Validator validator;
	
	
	
	@Test
	void testUserCreationSuccess() {
		
		UserRequestDTO request = new UserRequestDTO();
		request.setEmail("test@gmail.com");
		request.setFirstName("Test User");
		request.setPhone("9988776655");
		request.setRole(UserRole.USER);
		
		UserResponseDTO response = userService.createUser(request);
		
		assertNotNull(response);
	}
	
	@Test
	void testInvalidUserCreation() {
		UserRequestDTO request = new UserRequestDTO();
		request.setEmail("email");
		request.setFirstName("Test User");
		request.setPhone("9988775");
		request.setRole(UserRole.USER);
		
		Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(request);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	void testUserFetchById() {
		User user = new User();
		user.setEmail("test@gmail.com");
		user.setFirstName("Test User");
		user.setPhone("9089890989");
		user.setRole(UserRole.USER);
		
		user = userRepository.save(user);
		
		UserResponseDTO response = userService.getUserById(user.getUserId());
		
		assertEquals(response.getEmail(), user.getEmail());
		assertEquals(response.getFirstName(), user.getFirstName());
		assertEquals(response.getPhone(), user.getPhone());
		assertEquals(response.getRole(), user.getRole());
	}
	
	
	
	
}
