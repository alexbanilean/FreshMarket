package com.unibuc.fresh_market.controller;

import com.unibuc.fresh_market.domain.Delivery;
import com.unibuc.fresh_market.service.DeliveryService.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Deliveries controller")
@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Operation(summary = "Create a new delivery")
    @PostMapping
    public ResponseEntity<?> createDelivery(@RequestBody @Valid Delivery delivery, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors found!");
        }

        Delivery createdDelivery = deliveryService.createDelivery(delivery);
        return ResponseEntity.ok(createdDelivery);
    }

    @Operation(summary = "Get all deliveries")
    @GetMapping
    public ResponseEntity<?> getAllDeliveries() {
        List<Delivery> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }

    @Operation(summary = "Get a delivery by id")
    @GetMapping("/{deliveryId}")
    public ResponseEntity<?> getDeliveryById(@PathVariable String deliveryId) {
        Integer id = Integer.parseInt(deliveryId);
        Optional<Delivery> delivery = deliveryService.getDeliveryById(id);
        if (delivery.isPresent()) {
            return ResponseEntity.ok(delivery);
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update a delivery")
    @PutMapping("/{deliveryId}")
    public ResponseEntity<?> updateDelivery(@RequestBody @Valid Delivery delivery, BindingResult bindingResult, @PathVariable String deliveryId) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors found!");
        }

        Integer id = Integer.parseInt(deliveryId);
        Optional<Delivery> updatedDelivery = deliveryService.updateDelivery(id, delivery);
        if (updatedDelivery.isPresent()) {
            return ResponseEntity.ok(updatedDelivery);
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a delivery")
    @DeleteMapping("/{deliveryId}")
    public ResponseEntity<?> deleteDelivery(@PathVariable String deliveryId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors found!");
        }

        Integer id = Integer.parseInt(deliveryId);
        Optional<Delivery> deletedDelivery = deliveryService.deleteDelivery(id);
        if (deletedDelivery.isPresent()) {
            return ResponseEntity.ok(deletedDelivery);
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get deliveries with a specific status")
    @GetMapping("/status")
    public ResponseEntity<?> getDeliveriesByStatus(@RequestBody @Valid String status, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors found!");
        }
        List<Delivery> deliveries = deliveryService.getDeliveriesByStatus(status);
        return ResponseEntity.ok(deliveries);
    }
}
