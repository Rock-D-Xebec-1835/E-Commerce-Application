package com.cartapplication.dto;

import com.cartapplication.enums.NotificationStatus;
import com.cartapplication.enums.NotificationType;

import lombok.Data;

@Data
public class NotificationResponseDTO {
	private Long notificationId;
	private NotificationType type;
	private String recipientEmail;
	private String message;
	private NotificationStatus status;
	
}
