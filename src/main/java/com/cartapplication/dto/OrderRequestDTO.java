package com.cartapplication.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class OrderRequestDTO {
	
	@NotNull(message = "User ID cannot be null")
	@Positive(message = "User ID must be positive")
	private Long userId;
	
	@NotEmpty(message = "Order list must contain atleast one item")
	private List<OrderItemRequestDTO> orderItems;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<OrderItemRequestDTO> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemRequestDTO> orderItems) {
		this.orderItems = orderItems;
	}

	
}
