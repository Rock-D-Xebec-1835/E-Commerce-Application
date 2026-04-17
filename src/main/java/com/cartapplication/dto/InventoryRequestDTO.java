package com.cartapplication.dto;

import jakarta.validation.constraints.Positive;

public class InventoryRequestDTO {
	@Positive(message = "Product ID must be positive")
    private Long productId;
	@Positive(message = "Available quantity must be positive")
    private Integer availableQuantity;
	@Positive(message = "Re-order level must be positive")
    private Integer reorderLevel;

    // getters and setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(Integer availableQuantity) { this.availableQuantity = availableQuantity; }

    public Integer getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; }
}
