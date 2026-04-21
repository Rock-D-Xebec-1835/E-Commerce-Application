package com.cartapplication.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Robot;
import java.net.ResponseCache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cartapplication.dto.ProductRequestDTO;
import com.cartapplication.dto.ProductResponseDTO;
import com.cartapplication.entity.Product;
import com.cartapplication.exception.ResourceNotFoundException;
import com.cartapplication.repository.ProductRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class ProductServiceTest {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductService productService;
	
	@Test
	void testProductCreation() {
		ProductRequestDTO request = new ProductRequestDTO();
		
		request.setProductName("Strepsils");
		request.setDescription("Something Harsheen really needs right now!");
		request.setCategory("Medicine");
		request.setPrice(10.0);
		
		ProductResponseDTO response = productService.addProduct(request);
		
		assertNotNull(response);
	}
	
	@Test
	void testGetProductById() {
		Product product = new Product();
		product.setProductName("Strepsils");
		product.setDescription("Something Harsheen really needs right now!");
		product.setCategory("Medicine");
		product.setPrice(10.0);
		
		product = productRepository.save(product);
		
		ProductResponseDTO repsonse = productService.getProductById(product.getProductId());
		
		assertNotNull(repsonse);
		assertEquals(repsonse.getProductId(), product.getProductId());
	}
	
	@Test
	void testProductNotFound() {
		Product product = new Product();
		product.setProductName("Strepsils");
		product.setDescription("Something Harsheen really needs right now!");
		product.setCategory("Medicine");
		product.setPrice(10.0);
		
		product = productRepository.save(product);
		
		assertThrows(RuntimeException.class, () -> {
			productService.getProductById(999L);
		});
	}
	
	
}
