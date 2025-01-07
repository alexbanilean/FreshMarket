package com.unibuc.fresh_market.controllers;

import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.ProductOrder;
import com.unibuc.fresh_market.services.OrderService.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Orders controller")
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Create a new order")
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    @Operation(summary = "Get all orders")
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Get an order by id")
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable String orderId) {
        Integer id = Integer.parseInt(orderId);
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update an order")
    @PutMapping("/{orderId}")
    public ResponseEntity<?> updateOrder(@RequestBody @Valid Order order, @PathVariable String orderId) {
        Integer id = Integer.parseInt(orderId);
        Optional<Order> updatedOrder = orderService.updateOrder(id, order);
        if (updatedOrder.isPresent()) {
            return ResponseEntity.ok(updatedOrder.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete an order")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable String orderId) {
        Integer id = Integer.parseInt(orderId);
        Optional<Order> deletedOrder = orderService.deleteOrder(id);
        if (deletedOrder.isPresent()) {
            return ResponseEntity.ok(deletedOrder.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get orders with a specific status")
    @GetMapping("/status")
    public ResponseEntity<?> getOrdersByStatus(@RequestBody @Valid String status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Get details of an order")
    @GetMapping("/{orderId}/details")
    public ResponseEntity<?> getOrderDetails(@PathVariable String orderId) {
        Integer id = Integer.parseInt(orderId);
        List<ProductOrder> productOrders = orderService.getOrderDetails(id);
        return ResponseEntity.ok(productOrders);
    }
}
