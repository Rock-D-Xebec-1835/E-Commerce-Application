package com.cartapplication.dto;

import java.util.List;

import com.cartapplication.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
	
	private Long orderId;

	private Long userId;
	
	private Double totalAmount;
	
	private OrderStatus orderStatus;
	
	private List<OrderItemResponseDTO> orderItems;
}
