package com.cartapplication.dto;

import com.cartapplication.enums.NotificationStatus;
import com.cartapplication.enums.NotificationType;

import lombok.Data;

public class NotificationResponseDTO {
	private Long notificationId;
	private NotificationType type;
	private String recipientEmail;
	private String message;
	private NotificationStatus status;
	public Long getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}
	public NotificationType getType() {
		return type;
	}
	public void setType(NotificationType type) {
		this.type = type;
	}
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
	public NotificationStatus getStatus() {
		return status;
	}
	public void setStatus(NotificationStatus status) {
		this.status = status;
	}
	
	
	
}
