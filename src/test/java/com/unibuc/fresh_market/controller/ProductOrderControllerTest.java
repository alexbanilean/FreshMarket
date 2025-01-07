package com.unibuc.fresh_market.controller;

import com.unibuc.fresh_market.domain.Farm;
import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.domain.ProductOrder;
import com.unibuc.fresh_market.domain.security.User;
import com.unibuc.fresh_market.service.ProductOrderService.ProductOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductOrderControllerTest {

    @Mock
    private ProductOrderService productOrderService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ProductOrderController productOrderController;

    private ProductOrder productOrder;
    private List<ProductOrder> productOrderList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        User user1 = User.builder()
                .id(1)
                .username("john_doe")
                .email("john.doe@example.com")
                .build();

        User user2 = User.builder()
                .id(2)
                .username("jane_smith")
                .email("jane.smith@example.com")
                .build();

        Product product1 = Product.builder().name("Apples").description("Red Apples").price(3.0).build();
        Product product2 = Product.builder().name("Carrots").description("Organic Carrots").price(2.0).build();

        Farm farm1 =  Farm.builder().name("User1 Farm").address("123 Farmer's Lane").user(user1).build();
        Farm farm2 = Farm.builder().name("User2 Farm").address("456 Grower's Drive").user(user2).build();

        Order order1 = Order.builder()
                .status("Pending")
                .totalAmount(30.0)
                .createdAt(new Date())
                .user(user1)
                .farm(farm1)
                .build();
        Order order2 = Order.builder()
                .status("Shipped")
                .totalAmount(50.0)
                .createdAt(new Date())
                .user(user2)
                .farm(farm2)
                .build();

        productOrder = ProductOrder.builder()
                .id(1)
                .product(product1)
                .order(order1)
                .quantity(5)
                .notes("Test Order")
                .build();

        ProductOrder productOrder2 = ProductOrder.builder()
                .id(2)
                .product(product2)
                .order(order2)
                .quantity(10)
                .notes("Second Order")
                .build();

        productOrderList = List.of(productOrder, productOrder2);
    }

    @Test
    void givenValidProductOrder_whenCreateProductOrder_thenReturnCreatedProductOrder() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productOrderService.createProductOrder(any(ProductOrder.class))).thenReturn(productOrder);

        // When
        ResponseEntity<?> response = productOrderController.createProductOrder(productOrder, bindingResult);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productOrder, response.getBody());
    }

    @Test
    void givenInvalidProductOrder_whenCreateProductOrder_thenReturnBadRequest() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = productOrderController.createProductOrder(productOrder, bindingResult);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenProductOrders_whenGetAllProductOrders_thenReturnProductOrderList() {
        // Given
        when(productOrderService.getAllProductOrders()).thenReturn(productOrderList);

        // When
        ResponseEntity<?> response = productOrderController.getAllProductOrders();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productOrderList, response.getBody());
    }

    @Test
    void givenValidProductOrderId_whenGetProductOrderById_thenReturnProductOrder() {
        // Given
        Integer productOrderId = 1;
        when(productOrderService.getProductOrderById(productOrderId)).thenReturn(Optional.of(productOrder));

        // When
        ResponseEntity<?> response = productOrderController.getProductOrderById(String.valueOf(productOrderId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productOrder, response.getBody());
    }

    @Test
    void givenInvalidProductOrderId_whenGetProductOrderById_thenReturnNotFound() {
        // Given
        Integer productOrderId = 1;
        when(productOrderService.getProductOrderById(productOrderId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = productOrderController.getProductOrderById(String.valueOf(productOrderId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidProductOrder_whenUpdateProductOrder_thenReturnUpdatedProductOrder() {
        // Given
        Integer productOrderId = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productOrderService.updateProductOrder(eq(productOrderId), any(ProductOrder.class))).thenReturn(Optional.of(productOrder));

        // When
        ResponseEntity<?> response = productOrderController.updateProductOrder(productOrder, bindingResult, String.valueOf(productOrderId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productOrder, response.getBody());
    }

    @Test
    void givenInvalidProductOrder_whenUpdateProductOrder_thenReturnBadRequest() {
        // Given
        Integer productOrderId = 1;
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = productOrderController.updateProductOrder(productOrder, bindingResult, String.valueOf(productOrderId));

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenInvalidProductOrderId_whenUpdateProductOrder_thenReturnNotFound() {
        // Given
        Integer productOrderId = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productOrderService.updateProductOrder(productOrderId, productOrder)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = productOrderController.updateProductOrder(productOrder, bindingResult, String.valueOf(productOrderId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidProductOrderId_whenDeleteProductOrder_thenReturnDeletedProductOrder() {
        // Given
        Integer productOrderId = 1;
        when(productOrderService.deleteProductOrder(productOrderId)).thenReturn(Optional.of(productOrder));

        // When
        ResponseEntity<?> response = productOrderController.deleteProductOrder(String.valueOf(productOrderId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productOrder, response.getBody());
    }

    @Test
    void givenInvalidProductOrderId_whenDeleteProductOrder_thenReturnNotFound() {
        // Given
        Integer productOrderId = 1;
        when(productOrderService.deleteProductOrder(productOrderId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = productOrderController.deleteProductOrder(String.valueOf(productOrderId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidOrderId_whenGetProductOrderByOrderId_thenReturnProductOrderList() {
        // Given
        Integer orderId = 1;
        when(productOrderService.getProductOrdersByOrderId(orderId)).thenReturn(productOrderList);

        // When
        ResponseEntity<?> response = productOrderController.getProductOrderByOrderId(String.valueOf(orderId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productOrderList, response.getBody());
    }

    @Test
    void givenValidOrderId_whenGetProductOrderTotal_thenReturnTotalAmount() {
        // Given
        Integer orderId = 1;
        double totalValue = 100.50;
        when(productOrderService.calculateTotalOrderValue(orderId)).thenReturn(totalValue);

        // When
        ResponseEntity<?> response = productOrderController.getProductOrderTotal(String.valueOf(orderId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(totalValue, response.getBody());
    }
}
