package com.cartapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cartapplication.dto.InventoryRequestDTO;
import com.cartapplication.dto.InventoryResponseDTO;
import com.cartapplication.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // POST /api/inventory
    @PostMapping
    public ResponseEntity<InventoryResponseDTO> addInventory(@RequestBody InventoryRequestDTO req) {
        return ResponseEntity.ok(inventoryService.addStock(req));
    }

    // GET /api/inventory
    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    // GET /api/inventory/{productId}
    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponseDTO> getInventory(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getStock(productId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<InventoryResponseDTO> updateInventory(
            @PathVariable Long productId,
            @RequestBody InventoryRequestDTO req) {
        return ResponseEntity.ok(inventoryService.updateStock(productId, req));
    }

    // GET /api/inventory/low-stock
    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryResponseDTO>> getLowStock() {
        return ResponseEntity.ok(inventoryService.getLowStock());
    }
}
