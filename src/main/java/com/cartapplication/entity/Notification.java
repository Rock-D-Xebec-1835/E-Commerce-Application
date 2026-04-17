package com.cartapplication.entity;

import com.cartapplication.enums.NotificationStatus;
import com.cartapplication.enums.NotificationType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
	
}
