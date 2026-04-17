package com.cartapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartapplication.dto.NotificationRequestDTO;
import com.cartapplication.dto.NotificationResponseDTO;
import com.cartapplication.service.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	
	@PostMapping
	public NotificationResponseDTO create(@Valid @RequestBody NotificationRequestDTO request) {
		return notificationService.createNotification(request);
	}
	
	@GetMapping
	public List<NotificationResponseDTO> getAllNotifications(){
		return notificationService.getAllNotifications();
	}
	
	@GetMapping("/{notificationId}")
	public NotificationResponseDTO getNotificationById(@PathVariable Long notificationId) {
		return notificationService.getNotificationById(notificationId);
	}
	
	@GetMapping("/user/{userId}")
	public List<NotificationResponseDTO> getNotificationsForUser(@PathVariable Long userId){
		return notificationService.getNotificationsForUser(userId);
	}
}
