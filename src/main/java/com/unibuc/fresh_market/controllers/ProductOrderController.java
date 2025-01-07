package com.unibuc.fresh_market.controllers;

import com.unibuc.fresh_market.domain.ProductOrder;
import com.unibuc.fresh_market.services.ProductOrderService.ProductOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Product Order Relation controller")
@RestController
@RequestMapping("/productOrders")
public class ProductOrderController {
    private final ProductOrderService productOrderService;

    @Autowired
    public ProductOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    @Operation(summary = "Create a new product order relation")
    @PostMapping
    public ResponseEntity<?> createProductOrder(@RequestBody @Valid ProductOrder productOrder) {
        ProductOrder createdProductOrder = productOrderService.createProductOrder(productOrder);
        return ResponseEntity.ok(createdProductOrder);
    }

    @Operation(summary = "Get all product order relations")
    @GetMapping
    public ResponseEntity<?> getAllProductOrders() {
        List<ProductOrder> productOrders = productOrderService.getAllProductOrders();
        return ResponseEntity.ok(productOrders);
    }

    @Operation(summary = "Get a product order relation by id")
    @GetMapping("/{productOrderId}")
    public ResponseEntity<?> getProductOrderById(@PathVariable String productOrderId) {
        Integer id = Integer.parseInt(productOrderId);
        Optional<ProductOrder> productOrder = productOrderService.getProductOrderById(id);
        if (productOrder.isPresent()) {
            return ResponseEntity.ok(productOrder.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update a product order relation")
    @PutMapping("/{productOrderId}")
    public ResponseEntity<?> updateProductOrder(@RequestBody @Valid ProductOrder productOrder, @PathVariable String productOrderId) {
        Integer id = Integer.parseInt(productOrderId);
        Optional<ProductOrder> updatedProductOrder = productOrderService.updateProductOrder(id, productOrder);
        if (updatedProductOrder.isPresent()) {
            return ResponseEntity.ok(updatedProductOrder.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a product order relation")
    @DeleteMapping("/{productOrderId}")
    public ResponseEntity<?> deleteProductOrder(@PathVariable String productOrderId) {
        Integer id = Integer.parseInt(productOrderId);
        Optional<ProductOrder> deletedProductOrder = productOrderService.deleteProductOrder(id);
        if (deletedProductOrder.isPresent()) {
            return ResponseEntity.ok(deletedProductOrder.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get products within an order")
    @GetMapping("/orderId/{orderId}")
    public ResponseEntity<?> getProductOrderByOrderId(@PathVariable String orderId) {
        Integer id = Integer.parseInt(orderId);
        List<ProductOrder> productOrders = productOrderService.getProductOrdersByOrderId(id);
        return ResponseEntity.ok(productOrders);
    }

    @Operation(summary = "Get total amount of an order")
    @GetMapping("/orderId/{orderId}/total")
    public ResponseEntity<?> getProductOrderTotal(@PathVariable String orderId) {
        Integer id = Integer.parseInt(orderId);
        double totalValue = productOrderService.calculateTotalOrderValue(id);
        return ResponseEntity.ok(totalValue);
    }

}
