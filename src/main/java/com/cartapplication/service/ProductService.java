package com.cartapplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cartapplication.dto.ProductRequestDTO;
import com.cartapplication.dto.ProductResponseDTO;
import com.cartapplication.entity.Product;
import com.cartapplication.repository.ProductRepository;

@Service
public class ProductService {
	
@Autowired
private ProductRepository productRepository;

//1 CREATE
public ProductResponseDTO addProduct(ProductRequestDTO req) {
	Product product = new Product();
	product.setCategory(req.getCategory());
	product.setDescription(req.getDescription());
	product.setPrice(req.getPrice());
	product.setProductName(req.getProductName());
	Product saved = productRepository.save(product);
	return mapToDTO(saved);
}

//2 GET ALL PRODUCTS
public List<ProductResponseDTO> getProduct() {
	return productRepository.findAll().stream()
			.map(e->mapToDTO(e))
			.collect(Collectors.toList());
}

//3 GET ALL PRODUCTS BY ID
public ProductResponseDTO getProductById(Long id) {
	return productRepository.findById(id)
			.map(e->mapToDTO(e))
			.orElseThrow(()-> new RuntimeException("Product not found"));
}

//4 UPDATE PRODUCT BY ID
public ProductResponseDTO putProductById(Long id,ProductRequestDTO req) {
	Product product= productRepository.findById(id)
			.orElseThrow(()->new RuntimeException("Product not found"));
	product.setCategory(req.getCategory());
	product.setDescription(req.getDescription());
	product.setPrice(req.getPrice());
	product.setProductName(req.getProductName());
	Product savedProduct = productRepository.save(product);
	return mapToDTO(savedProduct);
}

//5 DELETE PRODUCT BY ID
public void deleteProductById(Long id) {

	if (!productRepository.existsById(id)) {
    throw new RuntimeException("Product not found");
	}
	productRepository.deleteById(id);
}


public ProductResponseDTO mapToDTO(Product product) {
	ProductResponseDTO dto = new ProductResponseDTO();
	dto.setCategory(product.getCategory());
	dto.setDescription(product.getDescription());
	dto.setPrice(product.getPrice());
	dto.setProductId(product.getProductId());
	dto.setProductName(product.getProductName());
	return dto;
}
}
