package com.cartapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartapplication.dto.OrderRequestDTO;
import com.cartapplication.dto.OrderResponseDTO;
import com.cartapplication.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class OrderController {
	@Autowired OrderService orderService;
	
	// CHECKOUT
	@PostMapping("/checkout")
	public OrderResponseDTO checkout(@Valid @RequestBody OrderRequestDTO request) {
		return orderService.checkout(request);
	}
	
	// GET ALL USERS
	@GetMapping("/orders")
	public List<OrderResponseDTO> getAllOrders(){
		return orderService.getAllOrders();
	}
	
	// GET ORDER BY ID
	@GetMapping("/orders/{id}")
	public OrderResponseDTO getOrderById(@PathVariable Long id){
		return orderService.getOrderById(id);
	}
	
	// GET ORDERS FOR USER
	@GetMapping("/orders/users/{id}")
	public List<OrderResponseDTO> getOrdersForUser(@PathVariable Long userId){
		return orderService.getOrdersByUserId(userId);
	}
	
	
}
