package com.unibuc.fresh_market.controllers;

import com.unibuc.fresh_market.domain.ProductFarm;
import com.unibuc.fresh_market.services.ProductFarmService.ProductFarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Product Farm Relation controller")
@RestController
@RequestMapping("/productFarms")
public class ProductFarmController {
    private final ProductFarmService productFarmService;

    @Autowired
    public ProductFarmController(ProductFarmService productFarmService) {
        this.productFarmService = productFarmService;
    }

    @Operation(summary = "Create a new product farm relation")
    @PostMapping
    public ResponseEntity<?> createProductFarm(@RequestBody @Valid ProductFarm productFarm) {
        ProductFarm createdProductFarm = productFarmService.createProductFarm(productFarm);
        return ResponseEntity.ok(createdProductFarm);
    }

    @Operation(summary = "Get all product farm relations")
    @GetMapping
    public ResponseEntity<?> getAllProductFarm() {
        List<ProductFarm> productFarms = productFarmService.getAllProductFarms();
        return ResponseEntity.ok(productFarms);
    }

    @Operation(summary = "Get a product farm relation by id")
    @GetMapping("/{productFarmId}")
    public ResponseEntity<?> getProductFarmById(@PathVariable String productFarmId) {
        Integer id = Integer.parseInt(productFarmId);
        Optional<ProductFarm> productFarm = productFarmService.getProductFarmById(id);
        if (productFarm.isPresent()) {
            return ResponseEntity.ok(productFarm.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update a product farm relation")
    @PutMapping("/{productFarmId}")
    public ResponseEntity<?> updateProductFarm(@RequestBody @Valid ProductFarm productFarm, @PathVariable String productFarmId) {
        Integer id = Integer.parseInt(productFarmId);
        Optional<ProductFarm> updatedProductFarm = productFarmService.updateProductFarm(id, productFarm);
        if (updatedProductFarm.isPresent()) {
            return ResponseEntity.ok(updatedProductFarm.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a product farm relation")
    @DeleteMapping("/{productFarmId}")
    public ResponseEntity<?> deleteProductFarm(@PathVariable String productFarmId) {
        Integer id = Integer.parseInt(productFarmId);
        Optional<ProductFarm> deletedProductFarm = productFarmService.deleteProductFarm(id);
        if (deletedProductFarm.isPresent()) {
            return ResponseEntity.ok(deletedProductFarm.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get all product farm relations by farm")
    @GetMapping("/farmId/{farmId}")
    public ResponseEntity<?> getProductFarmByFarmId(@PathVariable String farmId) {
        Integer id = Integer.parseInt(farmId);
        List<ProductFarm> productFarms = productFarmService.getProductFarmsByFarmId(id);
        return ResponseEntity.ok(productFarms);
    }

    @Operation(summary = "Get all product farm relations by product")
    @GetMapping("/productId/{productId}")
    public ResponseEntity<?> getProductFarmByProductId(@PathVariable String productId) {
        Integer id = Integer.parseInt(productId);
        List<ProductFarm> productFarms = productFarmService.getProductFarmsByProductId(id);
        return ResponseEntity.ok(productFarms);
    }

    @Operation(summary = "Get product stock across all farms")
    @GetMapping("/productId/{productId}/stock")
    public ResponseEntity<?> getProductStockAcrossFarms(@PathVariable String productId) {
        Integer id = Integer.parseInt(productId);
        Integer stock = productFarmService.calculateProductStockAcrossFarms(id);
        return ResponseEntity.ok(stock);
    }
}
