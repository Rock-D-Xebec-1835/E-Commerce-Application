package com.cartapplication.validator;

import org.springframework.stereotype.Component;

import com.cartapplication.entity.User;


@Component
public class UserValidation {
	public void validate(User user) {
		if(user.getFirstName() == null) {
			throw new IllegalArgumentException("First name cannot be null");
		}
		if(user.getEmail() == null || !user.getEmail().contains("@")) {
			throw new IllegalArgumentException("Invalid email");
		}
		if((user.getPhone().length()) != 10) {
			throw new IllegalArgumentException("Invalid Phone number");
		}
		
	}
}

