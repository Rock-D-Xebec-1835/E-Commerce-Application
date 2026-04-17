package com.cartapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartapplication.dto.ProductRequestDTO;
import com.cartapplication.dto.ProductResponseDTO;
import com.cartapplication.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
private ProductService productService;
	@PostMapping()
	public ProductResponseDTO addProduct(@RequestBody ProductRequestDTO req) {
		return productService.addProduct(req);
	}
    @GetMapping()
    public List<ProductResponseDTO>  getProduct(){
    	return productService.getProduct();
    }
    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id) {
    	return productService.getProductById(id);
    }
    @PutMapping("/{id}")
    public  ProductResponseDTO putProduct(@PathVariable Long id,@RequestBody ProductRequestDTO req) {
    	return productService.putProductById(id, req);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    	 productService.deleteProductById(id);
    }
}
