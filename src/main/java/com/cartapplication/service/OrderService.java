package com.cartapplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cartapplication.dto.OrderItemRequestDTO;
import com.cartapplication.dto.OrderItemResponseDTO;
import com.cartapplication.dto.OrderRequestDTO;
import com.cartapplication.dto.OrderResponseDTO;
import com.cartapplication.entity.Order;
import com.cartapplication.entity.OrderItem;
import com.cartapplication.entity.Product;
import com.cartapplication.entity.User;
import com.cartapplication.enums.OrderStatus;
import com.cartapplication.exception.ResourceNotFoundException;
import com.cartapplication.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
	
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired 
	private UserService userService;
	@Autowired
	private ProductService productService;
	
	// ORDER CHECKOUT
	
	@Transactional
	public OrderResponseDTO checkout(OrderRequestDTO orderRequest) {
		User user = userService.getUserEntityById(orderRequest.getUserId());
		Order order = new Order();
		order.setUser(user);
		
		order.setOrderStatus(OrderStatus.CREATED);
		
		List<OrderItem> orderItems = new ArrayList<>();
		double totalAmount = 0;
		
		for(OrderItemRequestDTO itemDTO : orderRequest.getOrderItems()) {
			Product product = productService.getProductEntityById(itemDTO.getProductId());
			inventoryService.validateStock(itemDTO.getProductId(), itemDTO.getQuantity());
			
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(product);
			orderItem.setQuantity(itemDTO.getQuantity());
			orderItem.setUnitPrice(product.getPrice());
			
			
			totalAmount += product.getPrice() * itemDTO.getQuantity();
			orderItems.add(orderItem);
		}
		
		order.setItems(orderItems);
		order.setTotalAmount(totalAmount);
		order.setOrderStatus(OrderStatus.PLACED);
		
		Order savedOrder = orderRepository.save(order);
		
		for(OrderItem orderItem : orderItems) {
			inventoryService.reduceStock(orderItem.getProduct().getProductId(), orderItem.getQuantity());			
		}
		
		// Notification for successful order
		
		return toResponseDTO(savedOrder);
	}
	
	// GET ORDERS
	public List<OrderResponseDTO> getAllOrders(){
		return orderRepository.findAll()
				.stream()
				.map(this::toResponseDTO)
				.collect(Collectors.toList());
	}
	
	// GET ORDER BY ID
	public OrderResponseDTO getOrderById(Long orderId) {
		Order order =  orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
		return toResponseDTO(order);
	}
	
	// GET ORDERS BY USER ID
	public List<OrderResponseDTO> getOrdersByUserId(Long userId){
		return orderRepository.findByUser_UserId(userId)
				.stream()
				.map(this::toResponseDTO)
				.collect(Collectors.toList());
	}
	
	// Mapping
	private OrderResponseDTO toResponseDTO(Order order) {
		OrderResponseDTO dto = new OrderResponseDTO();
		
		dto.setOrderId(order.getOrderId());
		dto.setUserId(order.getUser().getUserId());
		dto.setTotalAmount(order.getTotalAmount());
		dto.setOrderStatus(order.getOrderStatus());
	
		List<OrderItemResponseDTO> items = order.getItems()
				.stream()
				.map(item -> {
					OrderItemResponseDTO i = new OrderItemResponseDTO();
					i.setProductId(item.getProduct().getProductId());
					i.setQuantity(item.getQuantity());
					i.setUnitPrice(item.getUnitPrice());
					return i;
				}).collect(Collectors.toList());
		
		dto.setOrderItems(items);
		return dto;
	}
	

}
