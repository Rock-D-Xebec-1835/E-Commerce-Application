package com.cartapplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cartapplication.dto.OrderItemRequestDTO;
import com.cartapplication.dto.OrderRequestDTO;
import com.cartapplication.dto.OrderResponseDTO;
import com.cartapplication.entity.Inventory;
import com.cartapplication.entity.Product;
import com.cartapplication.entity.User;
import com.cartapplication.enums.OrderStatus;
import com.cartapplication.enums.UserRole;
import com.cartapplication.exception.ResourceNotFoundException;
import com.cartapplication.repository.InventoryRepository;
import com.cartapplication.repository.ProductRepository;
import com.cartapplication.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class OrderServiceTest {
	
	@Autowired
	private OrderService orderService;
	@Autowired 
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private InventoryRepository inventoryRepository;
	
	
	@Test
	void testCheckoutSuccess() {
		// 1. Create user
		User user = new User();
		user.setFirstName("Test");
		user.setEmail("test@example.com");
		user.setRole(UserRole.USER);
		user = userRepository.save(user);
		
		// 2. Create product
		Product product = new Product();
		product.setProductName("Laptop");
		product.setPrice(50000.0);
		product = productRepository.save(product);
		
		// 3. Add Inventory
		Inventory inventory = new Inventory(product,10,2);
		inventoryRepository.save(inventory);
		
		// 4. Build Request
		OrderItemRequestDTO item = new OrderItemRequestDTO();
		item.setProductId(product.getProductId());
		item.setQuantity(2);
		
		OrderRequestDTO request = new OrderRequestDTO();
		request.setUserId(user.getUserId());
		request.setOrderItems(List.of(item));
		
		// 5. Call service
		OrderResponseDTO response = orderService.checkout(request);
		
		// 6. Assertions
		assertNotNull(response);
		assertEquals(OrderStatus.PLACED, response.getOrderStatus());
		assertEquals(1, response.getOrderItems().size());
	}
	
	@Test
	void testCheckoutFailsWhenStockInsufficient() {
		User user = new User();
		user.setFirstName("Test");
		user.setEmail("test@example.com");
		user.setRole(UserRole.USER);
		user = userRepository.save(user);
		
		Product product = new Product();
	    product.setProductName("Phone");
	    product.setPrice(1000.0);
	    product = productRepository.save(product);
	    
	    Inventory inventory = new Inventory(product,1,1);
		inventoryRepository.save(inventory);
		
		OrderItemRequestDTO item = new OrderItemRequestDTO();
		item.setProductId(product.getProductId());
		item.setQuantity(5);
		
		OrderRequestDTO request = new OrderRequestDTO();
		request.setUserId(user.getUserId());
		request.setOrderItems(List.of(item));
		
		assertThrows(RuntimeException.class, () -> {
			orderService.checkout(request);
		});
	}
	
	@Test
	void testCheckoutFailsWhenUserNotFound() {
		OrderRequestDTO request = new OrderRequestDTO();
		request.setUserId(999L);
		
		assertThrows(ResourceNotFoundException.class, () -> {
			orderService.checkout(request);
		});
	}
	
	@Test
	void testInventoryReducedAfterCheckout() {
		User user = new User();
		user.setFirstName("Test");
		user.setEmail("test@example.com");
		user.setRole(UserRole.USER);
		user = userRepository.save(user);
		
		Product product = new Product();
	    product.setProductName("Phone");
	    product.setPrice(1000.0);
	    product = productRepository.save(product);
	    
	    Inventory inventory = new Inventory(product,10,2);
		inventoryRepository.save(inventory);
		
		OrderItemRequestDTO item = new OrderItemRequestDTO();
		item.setProductId(product.getProductId());
		item.setQuantity(3);
		
		OrderRequestDTO request = new OrderRequestDTO();
		request.setUserId(user.getUserId());
		request.setOrderItems(List.of(item));
		
		orderService.checkout(request);
		
		Inventory updated = inventoryRepository.findByProduct_ProductId(product.getProductId()).orElseThrow();
		assertEquals(7, updated.getAvailableQuantity());
	}
	
	@Test
	void testCheckoutFailsWhenProductNotFound() {
		OrderRequestDTO request = new OrderRequestDTO();
		request.setUserId(1L);
		
		OrderItemRequestDTO item = new OrderItemRequestDTO();
		item.setProductId(999L);
		
		request.setOrderItems(List.of(item));
		
		assertThrows(ResourceNotFoundException.class, () -> {
			orderService.checkout(request);
		});
	}
	
	@Test
	void testCheckoutFailsWhenOrderItemsAreEmpty() {
		OrderRequestDTO request = new OrderRequestDTO();
		request.setUserId(1L);
		
		OrderItemRequestDTO item = new OrderItemRequestDTO();
		
		request.setOrderItems(List.of(item));
		
		assertThrows(RuntimeException.class, () -> {
			orderService.checkout(request);
		});
	}
	
	@Test
	void testCheckoutWithMultipleProducts() {
		User user = new User();
		user.setFirstName("Test");
		user.setEmail("test@example.com");
		user.setRole(UserRole.USER);
		user = userRepository.save(user);
		
		Product product1 = new Product();
	    product1.setProductName("Phone");
	    product1.setPrice(1000.0);
	    product1 = productRepository.save(product1);
	    
	    Product product2 = new Product();
	    product2.setProductName("Laptop");
	    product2.setPrice(10000.0);
	    product2 = productRepository.save(product2);
	    
	    Product product3 = new Product();
	    product3.setProductName("TV");
	    product3.setPrice(100000.0);
	    product3 = productRepository.save(product3);
	    
	    Inventory inventory1 = new Inventory(product1,10,2);
		inventoryRepository.save(inventory1);
		Inventory inventory2 = new Inventory(product2,10,2);
		inventoryRepository.save(inventory2);
		Inventory inventory3 = new Inventory(product3,10,2);
		inventoryRepository.save(inventory3);
		
		OrderItemRequestDTO item1 = new OrderItemRequestDTO();
		item1.setProductId(product1.getProductId());
		item1.setQuantity(1);
		OrderItemRequestDTO item2 = new OrderItemRequestDTO();
		item2.setProductId(product2.getProductId());
		item2.setQuantity(1);
		OrderItemRequestDTO item3 = new OrderItemRequestDTO();
		item3.setProductId(product3.getProductId());
		item3.setQuantity(1);
		
		OrderRequestDTO request = new OrderRequestDTO();
		request.setUserId(user.getUserId());
		request.setOrderItems(List.of(item1,item2,item3));
		
		OrderResponseDTO response = orderService.checkout(request);
		
		assertEquals(111000.0, response.getTotalAmount());
	}
	
}
