package com.cartapplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cartapplication.dto.NotificationRequestDTO;
import com.cartapplication.dto.NotificationResponseDTO;
import com.cartapplication.entity.Notification;
import com.cartapplication.entity.Order;
import com.cartapplication.entity.User;
import com.cartapplication.enums.NotificationStatus;
import com.cartapplication.enums.NotificationType;
import com.cartapplication.exception.ResourceNotFoundException;
import com.cartapplication.repository.NotificationRepository;
import com.cartapplication.repository.OrderRepository;
import com.cartapplication.repository.UserRepository;

@Service
public class NotificationService {
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderRepository orderRepository;
	
	// Order Confirmation
	public void sendOrderConfirmation(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
		Notification notification = new Notification();
		
		notification.setType(NotificationType.ORDER_CONFIRMATION);
		notification.setRecipientEmail(order.getUser().getEmail());
		notification.setMessage("Your order " + order.getOrderId() + " is placed successfully");
		notification.setStatus(NotificationStatus.SENT);
		notificationRepository.save(notification);
	}
	
	public void sendLowStockAlert(Long productId, Integer quantity) {		
		Notification notification = new Notification();
		
		notification.setType(NotificationType.LOW_STOCK);
		notification.setRecipientEmail("admin@gmail.com");
		notification.setMessage("Product ID " + productId + " is low on stock. Remaining: " + quantity);
		notification.setStatus(NotificationStatus.SENT);
		notificationRepository.save(notification);
	}
	
	// GET ALL NOTIFICATIONS
	
	public List<NotificationResponseDTO> getAllNotifications(){
		return notificationRepository.findAll()
				.stream()
				.map(this::toResponseDTO)
				.collect(Collectors.toList());
	}
	
	// GET NOTIFICATION BY ID
	
	public NotificationResponseDTO getNotificationById(Long id) {
		Notification notification = notificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
		return toResponseDTO(notification);
	}
	
	// GET NOTIFICATION FOR USER
	
	public List<NotificationResponseDTO> getNotificationsForUser(Long userId){
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return notificationRepository.findByRecipientEmail(user.getEmail())
				.stream()
				.map(this::toResponseDTO)
				.collect(Collectors.toList());
	}
	
	
	private NotificationResponseDTO toResponseDTO(Notification n) {
		NotificationResponseDTO dto = new NotificationResponseDTO();
		
		dto.setNotificationId(n.getNotificationId());
		dto.setStatus(n.getStatus());
		dto.setRecipientEmail(n.getRecipientEmail());
		dto.setMessage(n.getMessage());
		dto.setType(n.getType());
		
		return dto;
	}
	
	public NotificationResponseDTO createNotification(NotificationRequestDTO request) {
		Notification notification = new Notification();
		
		notification.setRecipientEmail(request.getRecipientEmail());
		notification.setMessage(request.getMessage());
		notification.setType(NotificationType.ORDER_CONFIRMATION);
		notification.setStatus(NotificationStatus.CREATED);
		
		Notification saved = notificationRepository.save(notification);
		return toResponseDTO(saved);
	}
}