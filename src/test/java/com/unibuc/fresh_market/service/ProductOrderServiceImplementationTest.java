package com.unibuc.fresh_market.service;

import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.domain.ProductOrder;
import com.unibuc.fresh_market.repository.ProductOrderRepository;
import com.unibuc.fresh_market.service.ProductOrderService.ProductOrderServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductOrderServiceImplementationTest {

    @Mock
    private ProductOrderRepository productOrderRepository;

    @InjectMocks
    private ProductOrderServiceImplementation productOrderService;

    private ProductOrder productOrder;
    private List<ProductOrder> productOrderList;

    @BeforeEach
    void setUp() {
        Product product1 = Product.builder().name("Apples").description("Red Apples").price(3.0).build();
        Product product2 = Product.builder().name("Carrots").description("Organic Carrots").price(2.0).build();

        productOrder = ProductOrder.builder()
                .id(1)
                .product(product1)
                .quantity(100)
                .notes("Order for 100 apples")
                .build();

        ProductOrder productOrder2 = ProductOrder.builder()
                .id(2)
                .product(product2)
                .quantity(150)
                .notes("Order for 150 carrots")
                .build();

        productOrderList = List.of(productOrder, productOrder2);
    }

    @Test
    void whenCreateProductOrder_thenReturnCreatedProductOrder() {
        // Given
        when(productOrderRepository.save(any(ProductOrder.class))).thenReturn(productOrder);

        // When
        ProductOrder createdProductOrder = productOrderService.createProductOrder(productOrder);

        // Then
        assertNotNull(createdProductOrder);
        assertEquals(productOrder.getId(), createdProductOrder.getId());
        assertEquals(productOrder.getProduct(), createdProductOrder.getProduct());
        assertEquals(productOrder.getQuantity(), createdProductOrder.getQuantity());
        assertEquals(productOrder.getNotes(), createdProductOrder.getNotes());

        verify(productOrderRepository, times(1)).save(any(ProductOrder.class));
    }

    @Test
    void whenGetAllProductOrders_thenReturnListOfProductOrders() {
        // Given
        when(productOrderRepository.findAll()).thenReturn(productOrderList);

        // When
        List<ProductOrder> result = productOrderService.getAllProductOrders();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(productOrder.getId(), result.getFirst().getId());

        verify(productOrderRepository, times(1)).findAll();
    }

    @Test
    void whenGetProductOrderById_thenReturnProductOrder() {
        // Given
        when(productOrderRepository.findById(1)).thenReturn(Optional.of(productOrder));

        // When
        Optional<ProductOrder> result = productOrderService.getProductOrderById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(productOrder.getId(), result.get().getId());
        assertEquals(productOrder.getProduct(), result.get().getProduct());
        assertEquals(productOrder.getQuantity(), result.get().getQuantity());

        verify(productOrderRepository, times(1)).findById(1);
    }

    @Test
    void whenUpdateProductOrder_thenReturnUpdatedProductOrder() {
        // Given
        ProductOrder updatedProductOrder = ProductOrder.builder()
                .id(productOrder.getId())
                .product(productOrder.getProduct())
                .quantity(120)
                .notes("Updated order for 120 apples")
                .build();

        when(productOrderRepository.findById(1)).thenReturn(Optional.of(productOrder));
        when(productOrderRepository.save(any(ProductOrder.class))).thenReturn(updatedProductOrder);

        // When
        Optional<ProductOrder> result = productOrderService.updateProductOrder(1, updatedProductOrder);

        // Then
        assertTrue(result.isPresent());
        assertEquals(updatedProductOrder.getQuantity(), result.get().getQuantity());
        assertEquals(updatedProductOrder.getNotes(), result.get().getNotes());

        verify(productOrderRepository, times(1)).findById(1);
        verify(productOrderRepository, times(1)).save(any(ProductOrder.class));
    }

    @Test
    void whenDeleteProductOrder_thenReturnDeletedProductOrder() {
        // Given
        when(productOrderRepository.findById(1)).thenReturn(Optional.of(productOrder));

        // When
        Optional<ProductOrder> result = productOrderService.deleteProductOrder(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(productOrder.getId(), result.get().getId());

        verify(productOrderRepository, times(1)).findById(1);
        verify(productOrderRepository, times(1)).delete(productOrder);
    }

    @Test
    void whenGetProductOrdersByOrderId_thenReturnListOfProductOrders() {
        // Given
        when(productOrderRepository.findProductOrdersByOrderId(1)).thenReturn(productOrderList);

        // When
        List<ProductOrder> result = productOrderService.getProductOrdersByOrderId(1);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(productOrder.getId(), result.getFirst().getId());

        verify(productOrderRepository, times(1)).findProductOrdersByOrderId(1);
    }

    @Test
    void whenCalculateTotalOrderValue_thenReturnTotalValue() {
        // Given
        when(productOrderRepository.findProductOrdersByOrderId(1)).thenReturn(productOrderList);

        // When
        double totalValue = productOrderService.calculateTotalOrderValue(1);

        // Then
        assertEquals(600.0, totalValue, 0.01);

        verify(productOrderRepository, times(1)).findProductOrdersByOrderId(1);
    }

    @Test
    void whenCalculateTotalOrderValue_thenThrowExceptionIfNoProducts() {
        // Given
        when(productOrderRepository.findProductOrdersByOrderId(1)).thenReturn(List.of());

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            productOrderService.calculateTotalOrderValue(1);
        });
        assertEquals("No products found for the given order ID: 1", thrown.getMessage());
    }

    @Test
    void whenCalculateTotalOrderValue_thenThrowExceptionIfProductNotFound() {
        // Given
        ProductOrder invalidProductOrder = ProductOrder.builder()
                .id(1)
                .product(null)  // Simulating missing product
                .quantity(100)
                .build();
        when(productOrderRepository.findProductOrdersByOrderId(1)).thenReturn(List.of(invalidProductOrder));

        // When & Then
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            productOrderService.calculateTotalOrderValue(1);
        });
        assertEquals("Product not found for ProductOrder ID: 1", thrown.getMessage());
    }
}

