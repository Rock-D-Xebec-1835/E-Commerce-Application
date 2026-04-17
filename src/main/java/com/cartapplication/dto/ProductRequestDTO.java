package com.cartapplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ProductRequestDTO {
	@NotBlank(message = "Product name cannot be null")
	private String productName;
	@NotBlank(message = "Description cannot be null")
	private String description;
	@NotBlank(message = "Category cannot be null")
	private String category;
	@Positive(message = "Price must be positive")
	private Double price;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
}
