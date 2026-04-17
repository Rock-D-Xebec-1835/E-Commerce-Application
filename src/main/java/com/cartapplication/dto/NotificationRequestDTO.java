package com.cartapplication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationRequestDTO {
	@NotBlank(message = "Recipient email cannot be blank")
	@Email(message = "Invalid email")
	private String recipientEmail;
	@NotBlank(message = "Message cannot be null")
	private String message;
}
