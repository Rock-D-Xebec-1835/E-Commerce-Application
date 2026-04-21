package com.cartapplication.entity;

import com.cartapplication.enums.NotificationStatus;
import com.cartapplication.enums.NotificationType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")

public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notificationId;
	
	@Enumerated(EnumType.STRING)
	private NotificationType type;
	
	private String recipientEmail;
	@Column(length = 1000)
	private String message;
	
	@Enumerated(EnumType.STRING)
	private NotificationStatus status;
	
	public Notification() {}
	
	

	public Notification(NotificationType type, String recipientEmail, String message, NotificationStatus status) {
		this.type = type;
		this.recipientEmail = recipientEmail;
		this.message = message;
		this.status = status;
	}



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


