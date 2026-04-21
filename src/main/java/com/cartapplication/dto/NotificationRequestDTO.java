package com.cartapplication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class NotificationRequestDTO {
	@NotBlank(message = "Recipient email cannot be blank")
	@Email(message = "Invalid email")
	private String recipientEmail;
	@NotBlank(message = "Message cannot be null")
	private String message;
	public String getRecipientEmail() {
		return recipientEmail;
	}
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
