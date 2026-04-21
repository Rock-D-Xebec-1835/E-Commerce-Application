package com.cartapplication.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.cartapplication.entity.Inventory;
import com.cartapplication.entity.Notification;
import com.cartapplication.entity.Product;
import com.cartapplication.enums.NotificationType;
import com.cartapplication.repository.InventoryRepository;
import com.cartapplication.repository.NotificationRepository;
import com.cartapplication.repository.ProductRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class InventoryServiceTest {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Test
	void testValidateStockSuccess() {

	    Product product = new Product();
	    product.setCategory("Electronics");
	    product.setDescription("To charge your devices");
	    product.setProductName("Charger");
	    product.setPrice(500.0);

	    Product savedProduct = productRepository.save(product);

	    Inventory inventory = new Inventory();
	    inventory.setProduct(product);
	    inventory.setAvailableQuantity(10);
	    inventory.setReorderLevel(2);

	    inventoryRepository.save(inventory);

	    assertDoesNotThrow(() -> 
	        inventoryService.validateStock(savedProduct.getProductId(), 5)
	    );
	}
	
	@Test
	void testValidateStockFailure() {

	    Product product = new Product();
	    product.setCategory("Electronics");
	    product.setDescription("To charge your devices");
	    product.setProductName("Charger");
	    product.setPrice(500.0);

	    Product savedProduct = productRepository.save(product);

	    Inventory inventory = new Inventory();
	    inventory.setProduct(product);
	    inventory.setAvailableQuantity(2);
	    inventory.setReorderLevel(1);

	    inventoryRepository.save(inventory);

	    assertThrows(RuntimeException.class, () -> {
	    	inventoryService.validateStock(savedProduct.getProductId(), 10);
	    });
	}
	
	@Test
	void testReduceStockSuccess() {
		Product product = new Product();
		product.setCategory("Electronics");
	    product.setDescription("To charge your devices");
	    product.setProductName("Charger");
	    product.setPrice(500.0);
	    
	    product = productRepository.save(product);
	    Inventory inventory = new Inventory();
	    inventory.setProduct(product);
	    inventory.setAvailableQuantity(10);
	    inventory.setReorderLevel(2);
	    inventoryRepository.save(inventory);
	    inventoryService.reduceStock(product.getProductId(), 3);
	    
	    assertEquals(7, inventoryService.getStock(product.getProductId()).getAvailableQuantity());	   
	}
	

	@Test
	void testReduceStockFailure() {
		Product product = new Product();
		product.setCategory("Electronics");
	    product.setDescription("To charge your devices");
	    product.setProductName("Charger");
	    product.setPrice(500.0);
	    
	    Product savedProduct = productRepository.save(product);
	    Inventory inventory = new Inventory();
	    inventory.setProduct(product);
	    inventory.setAvailableQuantity(2);
	    inventory.setReorderLevel(1);
	    inventoryRepository.save(inventory);
	    
	    assertThrows(RuntimeException.class, () -> {
	    	inventoryService.reduceStock(savedProduct.getProductId(), 5);
	    });
	    
	}
	
	
	@Test
	void testLowStockTriggersNotification() {
		Product product = new Product();
		product.setCategory("Electronics");
	    product.setDescription("To charge your devices");
	    product.setProductName("Charger");
	    product.setPrice(500.0);
	    
	    product = productRepository.save(product);
	    
	    Inventory inventory = new Inventory();
	    inventory.setProduct(product);
	    inventory.setAvailableQuantity(10);
	    inventory.setReorderLevel(5);
	    inventoryRepository.save(inventory);
	    
	    inventoryService.reduceStock(product.getProductId(), 6);
	    
	    List<Notification> notifications = notificationRepository.findAll();
	    Notification notification = notifications.stream()
	    		.filter(n -> n.getType() == NotificationType.LOW_STOCK )
	    		.findFirst()
	    		.orElseThrow();
	    
	    assertEquals(NotificationType.LOW_STOCK, notification.getType());
	    assertTrue(notification.getMessage().contains(String.valueOf(product.getProductId())));
	}
}
