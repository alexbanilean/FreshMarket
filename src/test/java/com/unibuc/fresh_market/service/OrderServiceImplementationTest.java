package com.unibuc.fresh_market.service;

import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.domain.ProductOrder;
import com.unibuc.fresh_market.repository.OrderRepository;
import com.unibuc.fresh_market.repository.ProductOrderRepository;
import com.unibuc.fresh_market.service.OrderService.OrderServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplementationTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductOrderRepository productOrderRepository;

    @InjectMocks
    private OrderServiceImplementation orderService;

    private Order order;
    private ProductOrder productOrder;

    @BeforeEach
    void setUp() {
        order = Order.builder()
                .id(1)
                .status("Pending")
                .totalAmount(100.0)
                .createdAt(new java.sql.Timestamp(System.currentTimeMillis()))
                .build();

        Product product = Product.builder().id(1).name("Product 1").price(10.0).build();

        productOrder = ProductOrder.builder()
                .id(1)
                .product(product)
                .order(order)
                .quantity(2)
                .build();
    }

    @Test
    void whenCreateOrder_thenReturnCreatedOrder() {
        // Given
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // When
        Order createdOrder = orderService.createOrder(order);

        // Then
        assertNotNull(createdOrder);
        assertEquals(order.getId(), createdOrder.getId());
        assertEquals(order.getStatus(), createdOrder.getStatus());
        assertEquals(order.getTotalAmount(), createdOrder.getTotalAmount());

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void whenGetAllOrders_thenReturnListOfOrders() {
        // Given
        when(orderRepository.findAll()).thenReturn(List.of(order));

        // When
        List<Order> orders = orderService.getAllOrders();

        // Then
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        assertEquals(1, orders.size());
        assertEquals(order.getId(), orders.getFirst().getId());

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void whenGetOrderById_thenReturnOrder() {
        // Given
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        // When
        Optional<Order> result = orderService.getOrderById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(order.getId(), result.get().getId());

        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    void whenUpdateOrder_thenReturnUpdatedOrder() {
        // Given
        Order updatedOrder = Order.builder()
                .id(1)
                .status("Shipped")
                .totalAmount(120.0)
                .createdAt(order.getCreatedAt())
                .build();
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        // When
        Optional<Order> result = orderService.updateOrder(1, updatedOrder);

        // Then
        assertTrue(result.isPresent());
        assertEquals(updatedOrder.getStatus(), result.get().getStatus());
        assertEquals(updatedOrder.getTotalAmount(), result.get().getTotalAmount());

        verify(orderRepository, times(1)).findById(1);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void whenDeleteOrder_thenReturnDeletedOrder() {
        // Given
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        // When
        Optional<Order> result = orderService.deleteOrder(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(order.getId(), result.get().getId());

        verify(orderRepository, times(1)).findById(1);
        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    void whenGetOrdersByStatus_thenReturnListOfOrders() {
        // Given
        when(orderRepository.findOrdersByStatus("Pending")).thenReturn(List.of(order));

        // When
        List<Order> result = orderService.getOrdersByStatus("Pending");

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(order.getId(), result.getFirst().getId());

        verify(orderRepository, times(1)).findOrdersByStatus("Pending");
    }

    @Test
    void whenGetOrderDetails_thenReturnListOfProductOrders() {
        // Given
        when(productOrderRepository.findProductOrdersByOrderId(1)).thenReturn(List.of(productOrder));

        // When
        List<ProductOrder> result = orderService.getOrderDetails(1);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(productOrder.getId(), result.getFirst().getId());

        verify(productOrderRepository, times(1)).findProductOrdersByOrderId(1);
    }
}

