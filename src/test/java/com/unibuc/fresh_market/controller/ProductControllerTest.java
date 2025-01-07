package com.unibuc.fresh_market.controller;

import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.service.ProductService.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ProductController productController;

    private Product product;
    private List<Product> productList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = Product.builder()
                .id(1)
                .name("Tomatoes")
                .price(2.5)
                .build();

        Product product2 = Product.builder()
                .id(2)
                .name("Cucumbers")
                .price(1.5)
                .build();

        productList = List.of(product, product2);
    }

    @Test
    void givenValidProduct_whenCreateProduct_thenReturnCreatedProduct() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        // When
        ResponseEntity<?> response = productController.createProduct(product, bindingResult);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(product, response.getBody());
    }

    @Test
    void givenInvalidProduct_whenCreateProduct_thenReturnBadRequest() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = productController.createProduct(product, bindingResult);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenProducts_whenGetAllProducts_thenReturnProductList() {
        // Given
        when(productService.getAllProducts()).thenReturn(productList);

        // When
        ResponseEntity<?> response = productController.getAllProducts();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productList, response.getBody());
    }

    @Test
    void givenValidProductId_whenGetProductById_thenReturnProduct() {
        // Given
        Integer productId = 1;
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));

        // When
        ResponseEntity<?> response = productController.getProductById(String.valueOf(productId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(product, response.getBody());
    }

    @Test
    void givenInvalidProductId_whenGetProductById_thenReturnNotFound() {
        // Given
        Integer productId = 1;
        when(productService.getProductById(productId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = productController.getProductById(String.valueOf(productId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidProduct_whenUpdateProduct_thenReturnUpdatedProduct() {
        // Given
        Integer productId = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productService.updateProduct(eq(productId), any(Product.class))).thenReturn(Optional.of(product));

        // When
        ResponseEntity<?> response = productController.updateProduct(product, bindingResult, String.valueOf(productId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(product, response.getBody());
    }

    @Test
    void givenInvalidProduct_whenUpdateProduct_thenReturnBadRequest() {
        // Given
        Integer productId = 1;
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = productController.updateProduct(product, bindingResult, String.valueOf(productId));

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenInvalidProductId_whenUpdateProduct_thenReturnNotFound() {
        // Given
        Integer productId = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productService.updateProduct(productId, product)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = productController.updateProduct(product, bindingResult, String.valueOf(productId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidProductId_whenDeleteProduct_thenReturnDeletedProduct() {
        // Given
        Integer productId = 1;
        when(productService.deleteProduct(productId)).thenReturn(Optional.of(product));

        // When
        ResponseEntity<?> response = productController.deleteProduct(String.valueOf(productId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(product, response.getBody());
    }

    @Test
    void givenInvalidProductId_whenDeleteProduct_thenReturnNotFound() {
        // Given
        Integer productId = 1;
        when(productService.deleteProduct(productId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = productController.deleteProduct(String.valueOf(productId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidFarmId_whenGetProductsByFarmId_thenReturnProductList() {
        // Given
        Integer farmId = 1;
        when(productService.getProductsByFarmId(farmId)).thenReturn(productList);

        // When
        ResponseEntity<?> response = productController.getProductByFarmId(String.valueOf(farmId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productList, response.getBody());
    }
}
