package com.unibuc.fresh_market.controller;

import com.unibuc.fresh_market.domain.Delivery;
import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.ProductOrder;
import com.unibuc.fresh_market.service.OrderService.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private OrderController orderController;

    private Order order;
    private List<Order> orderList;

    private Delivery delivery;

    private List<ProductOrder> productOrderList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Delivery delivery1 = Delivery.builder().id(1).deliveryStatus("Delivered").build();
        Delivery delivery2 = Delivery.builder().id(2).deliveryStatus("In progress").build();

        delivery = delivery1;

        Order order1 = Order.builder().id(1).status("Pending").totalAmount(100.0).createdAt(new Date()).delivery(delivery1).build();
        Order order2 = Order.builder().id(2).status("Shipped").totalAmount(200.0).createdAt(new Date()).delivery(delivery2).build();

        order = order1;
        orderList = List.of(order1, order2);

        ProductOrder productOrder1 = ProductOrder.builder().id(1).quantity(2).build();
        ProductOrder productOrder2 = ProductOrder.builder().id(2).quantity(3).build();
        productOrderList = List.of(productOrder1, productOrder2);
    }

    @Test
    void givenValidOrder_whenCreateOrder_thenReturnCreatedOrder() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(false);
        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        // When
        ResponseEntity<?> response = orderController.createOrder(order, bindingResult);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(order, response.getBody());
    }

    @Test
    void givenOrders_whenGetAllOrders_thenReturnOrderList() {
        // Given
        when(orderService.getAllOrders()).thenReturn(orderList);

        // When
        ResponseEntity<?> response = orderController.getAllOrders();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(orderList, response.getBody());
    }

    @Test
    void givenValidOrderId_whenGetOrderById_thenReturnOrder() {
        // Given
        Integer orderId = 1;
        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));

        // When
        ResponseEntity<?> response = orderController.getOrderById(String.valueOf(orderId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(order, response.getBody());
    }

    @Test
    void givenInvalidOrder_whenCreateOrder_thenReturnBadRequest() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = orderController.createOrder(order, bindingResult);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenInvalidOrderId_whenGetOrderById_thenReturnNotFound() {
        // Given
        Integer orderId = 1;
        when(orderService.getOrderById(orderId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = orderController.getOrderById(String.valueOf(orderId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenUpdatedOrder_whenUpdateOrder_thenReturnUpdatedOrder() {
        // Given
        Integer orderId = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        Order updatedOrder = Order.builder().id(orderId).status("Shipped").totalAmount(150.0).createdAt(new Date()).delivery(delivery).build();

        when(orderService.updateOrder(orderId, updatedOrder)).thenReturn(Optional.of(updatedOrder));

        // When
        ResponseEntity<?> response = orderController.updateOrder(updatedOrder, bindingResult, String.valueOf(orderId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(updatedOrder, response.getBody());
    }

    @Test
    void givenValidOrderId_whenDeleteOrder_thenReturnDeletedOrder() {
        // Given
        Integer orderId = 1;
        when(orderService.deleteOrder(orderId)).thenReturn(Optional.of(order));

        // When
        ResponseEntity<?> response = orderController.deleteOrder(String.valueOf(orderId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(order, response.getBody());
    }

    @Test
    void givenInvalidOrderId_whenDeleteOrder_thenReturnNotFound() {
        // Given
        Integer orderId = 1;
        when(orderService.deleteOrder(orderId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = orderController.deleteOrder(String.valueOf(orderId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidOrderId_whenGetOrderDetails_thenReturnProductOrderList() {
        // Given
        Integer orderId = 1;
        when(orderService.getOrderDetails(orderId)).thenReturn(productOrderList);

        // When
        ResponseEntity<?> response = orderController.getOrderDetails(String.valueOf(orderId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productOrderList, response.getBody());
    }

    @Test
    void givenInvalidUpdatedOrder_whenUpdateOrder_thenReturnBadRequest() {
        // Given
        Integer orderId = 1;
        when(bindingResult.hasErrors()).thenReturn(true);

        Order updatedOrder = Order.builder().id(orderId).status("Shipped").totalAmount(150.0).createdAt(new Date()).delivery(delivery).build();

        // When
        ResponseEntity<?> response = orderController.updateOrder(updatedOrder, bindingResult, String.valueOf(orderId));

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }
}
