package com.cartapplication.dto;

import jakarta.validation.constraints.*;

public class UserRequestDTO {
	@NotBlank(message="First Name should be Blank")
	private String firstName;
	
	@NotBlank(message="last name cannot be blank")
	private String lastName;
	
	@Email(message="Invalid email")
	@NotBlank(message="Email cannot be blank")
	private String email;
	
	@Pattern(regexp="\\d{10}", message="Phone number must be 10 digits")
	@NotBlank(message="Phone number cannot be blank")
	private String phone;
	
	@NotBlank(message="Role is required")
	@Pattern(regexp="^(customer|admin)$",message="Role must be either 'customer' or 'admin'")
	private String role;
	
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	

}
