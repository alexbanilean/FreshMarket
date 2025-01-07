package com.unibuc.fresh_market.service;

import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.repository.ProductRepository;
import com.unibuc.fresh_market.service.ProductService.ProductServiceImplementation;
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
public class ProductServiceImplementationTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImplementation productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1)
                .name("Product 1")
                .price(50.0)
                .description("Description of Product 1")
                .build();
    }

    @Test
    void whenCreateProduct_thenReturnCreatedProduct() {
        // Given
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        Product createdProduct = productService.createProduct(product);

        // Then
        assertNotNull(createdProduct);
        assertEquals(product.getId(), createdProduct.getId());
        assertEquals(product.getName(), createdProduct.getName());
        assertEquals(product.getPrice(), createdProduct.getPrice());
        assertEquals(product.getDescription(), createdProduct.getDescription());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void whenGetAllProducts_thenReturnListOfProducts() {
        // Given
        when(productRepository.findAll()).thenReturn(List.of(product));

        // When
        List<Product> products = productService.getAllProducts();

        // Then
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals(product.getId(), products.getFirst().getId());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void whenGetProductById_thenReturnProduct() {
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // When
        Optional<Product> result = productService.getProductById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(product.getId(), result.get().getId());

        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void whenUpdateProduct_thenReturnUpdatedProduct() {
        // Given
        Product updatedProduct = Product.builder()
                .id(1)
                .name("Updated Product")
                .price(60.0)
                .description("Updated Description")
                .build();
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // When
        Optional<Product> result = productService.updateProduct(1, updatedProduct);

        // Then
        assertTrue(result.isPresent());
        assertEquals(updatedProduct.getName(), result.get().getName());
        assertEquals(updatedProduct.getPrice(), result.get().getPrice());
        assertEquals(updatedProduct.getDescription(), result.get().getDescription());

        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void whenDeleteProduct_thenReturnDeletedProduct() {
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // When
        Optional<Product> result = productService.deleteProduct(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(product.getId(), result.get().getId());

        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void whenGetProductsByFarmId_thenReturnListOfProducts() {
        // Given
        when(productRepository.getProductsByFarmId(1)).thenReturn(List.of(product));

        // When
        List<Product> result = productService.getProductsByFarmId(1);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(product.getId(), result.getFirst().getId());

        verify(productRepository, times(1)).getProductsByFarmId(1);
    }
}

