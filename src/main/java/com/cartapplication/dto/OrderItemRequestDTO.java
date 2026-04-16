package com.cartapplication.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {
	@NotNull(message = "Product ID cannot be null")
	@Positive(message = "Product ID must be positive")
	private Long productId;
	@Positive(message = "Quantity must be positive")
	private Integer quantity;
}
