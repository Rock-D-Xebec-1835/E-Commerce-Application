package com.cartapplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cartapplication.dto.InventoryRequestDTO;
import com.cartapplication.dto.InventoryResponseDTO;
import com.cartapplication.entity.Inventory;
import com.cartapplication.entity.Product;
import com.cartapplication.repository.InventoryRepository;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // 1. Add stock
    public InventoryResponseDTO addStock(InventoryRequestDTO req) {
        Product product = new Product();
        product.setProductId(req.getProductId());

        Inventory inv = new Inventory(product, req.getAvailableQuantity(), req.getReorderLevel());
        Inventory saved = inventoryRepository.save(inv);
        return mapToDTO(saved);
    }

    // 2. Get all inventory
    public List<InventoryResponseDTO> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 3. Get inventory by productId
    public InventoryResponseDTO getStock(Long productId) {
        Product product = new Product();
        product.setProductId(productId);

        Inventory inv = inventoryRepository.findByProduct(product)
                .orElseThrow(() -> new RuntimeException("No inventory found for productId: " + productId));
        return mapToDTO(inv);
    }

    // 4. Update stock by productId
    public InventoryResponseDTO updateStock(Long productId, InventoryRequestDTO req) {
        Product product = new Product();
        product.setProductId(productId);

        Inventory inv = inventoryRepository.findByProduct(product)
                .orElseThrow(() -> new RuntimeException("No inventory found for productId: " + productId));

        // Update fields from DTO
        inv.setAvailableQuantity(req.getAvailableQuantity());
        inv.setReorderLevel(req.getReorderLevel());

        Inventory updated = inventoryRepository.save(inv);
        return mapToDTO(updated);
    }


    // 5. Get low-stock items
    public List<InventoryResponseDTO> getLowStock() {
        return inventoryRepository.findAll().stream()
                .filter(inv -> inv.getAvailableQuantity() <= inv.getReorderLevel())
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Mapper
    private InventoryResponseDTO mapToDTO(Inventory inv) {
        InventoryResponseDTO dto = new InventoryResponseDTO();
        dto.setInventoryId(inv.getInventoryId());
        dto.setProductId(inv.getProduct().getProductId());
        dto.setAvailableQuantity(inv.getAvailableQuantity());
        dto.setReorderLevel(inv.getReorderLevel());
        return dto;
    }
}
