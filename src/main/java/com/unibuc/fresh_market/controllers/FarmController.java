package com.unibuc.fresh_market.controllers;

import com.unibuc.fresh_market.domain.Farm;
import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.services.FarmService.FarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Farms controller")
@RestController
@RequestMapping("/farms")
public class FarmController {
    private final FarmService farmService;

    @Autowired
    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    @Operation(summary = "Create a new farm")
    @PostMapping
    public ResponseEntity<?> createFarm(@RequestBody @Valid Farm farm) {
        Farm createdFarm = farmService.createFarm(farm);
        return ResponseEntity.ok(createdFarm);
    }

    @Operation(summary = "Get all farms")
    @GetMapping
    public ResponseEntity<?> getAllFarms() {
        List<Farm> farms = farmService.getAllFarms();
        return ResponseEntity.ok(farms);
    }

    @Operation(summary = "Get a farm by id")
    @GetMapping("/{farmId}")
    public ResponseEntity<?> getFarmById(@PathVariable String farmId) {
        Integer id = Integer.parseInt(farmId);
        Optional<Farm> farm = farmService.getFarmById(id);
        if (farm.isPresent()) {
            return ResponseEntity.ok(farm.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update a farm")
    @PutMapping("/{farmId}")
    public ResponseEntity<?> updateFarm(@RequestBody @Valid Farm farm, @PathVariable String farmId) {
        Integer id = Integer.parseInt(farmId);
        Optional<Farm> updatedFarm = farmService.updateFarm(id, farm);
        if (updatedFarm.isPresent()) {
            return ResponseEntity.ok(updatedFarm.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a farm")
    @DeleteMapping("/{farmId}")
    public ResponseEntity<?> deleteFarm(@PathVariable String farmId) {
        Integer id = Integer.parseInt(farmId);
        Optional<Farm> deletedFarm = farmService.deleteFarm(id);
        if (deletedFarm.isPresent()) {
            return ResponseEntity.ok(deletedFarm.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get products within a farm")
    @GetMapping("/{farmId}/products")
    public ResponseEntity<?> getFarmProducts(@PathVariable String farmId) {
        Integer id = Integer.parseInt(farmId);
        List<Product> products = farmService.getProductsByFarmId(id);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get orders within a farm")
    @GetMapping("/{farmId}/orders")
    public ResponseEntity<?> getFarmOrders(@PathVariable String farmId) {
        Integer id = Integer.parseInt(farmId);
        List<Order> orders = farmService.getOrdersByFarmId(id);
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Get total sales for a farm")
    @GetMapping("/{farmId}/sales")
    public ResponseEntity<?> getFarmSalesByFarmId(@PathVariable String farmId) {
        Integer id = Integer.parseInt(farmId);
        double totalSales = farmService.calculateTotalSalesByFarmId(id);
        return ResponseEntity.ok(totalSales);
    }
}
