package com.cartapplication.service;

import static org.assertj.core.api.Assertions.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.intThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.cartapplication.entity.Notification;
import com.cartapplication.entity.Order;
import com.cartapplication.entity.User;
import com.cartapplication.enums.NotificationStatus;
import com.cartapplication.enums.NotificationType;
import com.cartapplication.enums.OrderStatus;
import com.cartapplication.enums.UserRole;
import com.cartapplication.repository.NotificationRepository;
import com.cartapplication.repository.OrderRepository;
import com.cartapplication.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class NotificationServiceTest {
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderRepository orderRepository;
	
	@Test
	void testOrderSuccessNotification() {
		User user = new User();
		user.setEmail("test@gmail.com");
		user.setFirstName("Test User");
		user.setRole(UserRole.USER);
		user = userRepository.save(user);
		
		Order order = new Order();
		order.setUser(user);
		order.setTotalAmount(1000.0);
		order.setOrderStatus(OrderStatus.PLACED);
		order = orderRepository.save(order);
		
		
		notificationService.sendOrderConfirmation(order.getOrderId());
		
		List<Notification> notifications = notificationRepository.findAll();

		Notification notification = notifications.stream()
		        .filter(n -> n.getType() == NotificationType.ORDER_CONFIRMATION)
		        .findFirst()
		        .orElseThrow();

		assertEquals(NotificationType.ORDER_CONFIRMATION, notification.getType());
		assertEquals("test@gmail.com", notification.getRecipientEmail());
		assertTrue(notification.getMessage().contains("order"));
	}
	
	@Test
	void testLowStockNotification() {
		Long productId = 101L;
		int quantity = 2;
		
		// call service
		notificationService.sendLowStockAlert(productId, quantity);
		List<Notification> notifications = notificationRepository.findAll();
		
		// Find that one notification!
		Notification notification = notifications.stream()
				.filter(n-> n.getType() == NotificationType.LOW_STOCK)
				.findFirst()
				.orElseThrow();
		
		assertEquals(NotificationType.LOW_STOCK, notification.getType());
		assertEquals("admin@gmail.com", notification.getRecipientEmail());
		
		assertTrue(notification.getMessage().contains(productId.toString()));
		assertTrue(notification.getMessage().contains(String.valueOf(quantity)));
	}
	
	
}
