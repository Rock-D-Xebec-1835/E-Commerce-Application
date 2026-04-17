package com.cartapplication.entity;

import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import com.cartapplication.enums.UserRole;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userId;
	
	@Column(nullable=false)
	private String firstName;
	
	private String lastName;
	
	@Column(nullable=false,unique=true)
	private String email;
	
	private String phone;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private UserRole role;
	
	public User() {
		
	}

	public User(Long userId, String firstName, String lastName, String email, String phone, UserRole role) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.role = role;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
		
}
