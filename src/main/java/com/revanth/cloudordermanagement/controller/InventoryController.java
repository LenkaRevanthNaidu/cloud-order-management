
package com.revanth.cloudordermanagement.controller;

import com.revanth.cloudordermanagement.dto.UpdateInventoryRequest;
import com.revanth.cloudordermanagement.entity.Product;
import com.revanth.cloudordermanagement.service.InventoryService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{productId}")
    public Product getInventory(@PathVariable Long productId) {
        return inventoryService.getInventory(productId);
    }

    @PutMapping("/{productId}")
    public Product updateInventory(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateInventoryRequest request) {

        return inventoryService.updateInventory(
                productId, request.getQuantity());
    }
}
