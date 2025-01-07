package com.unibuc.fresh_market.controller;

import com.unibuc.fresh_market.domain.Farm;
import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.domain.ProductFarm;
import com.unibuc.fresh_market.domain.security.User;
import com.unibuc.fresh_market.service.ProductFarmService.ProductFarmService;
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

class ProductFarmControllerTest {

    @Mock
    private ProductFarmService productFarmService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ProductFarmController productFarmController;

    private ProductFarm productFarm;
    private List<ProductFarm> productFarmList;

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

        productFarm = ProductFarm.builder()
                .id(1)
                .product(product1)
                .farm(farm1)
                .quantity(100)
                .build();

        ProductFarm productFarm2 = ProductFarm.builder()
                .id(2)
                .product(product2)
                .farm(farm2)
                .quantity(150)
                .build();

        productFarmList = List.of(productFarm, productFarm2);
    }

    @Test
    void givenValidProductFarm_whenCreateProductFarm_thenReturnCreatedProductFarm() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productFarmService.createProductFarm(any(ProductFarm.class))).thenReturn(productFarm);

        // When
        ResponseEntity<?> response = productFarmController.createProductFarm(productFarm, bindingResult);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productFarm, response.getBody());
    }

    @Test
    void givenInvalidProductFarm_whenCreateProductFarm_thenReturnBadRequest() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = productFarmController.createProductFarm(productFarm, bindingResult);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenProductFarms_whenGetAllProductFarms_thenReturnProductFarmList() {
        // Given
        when(productFarmService.getAllProductFarms()).thenReturn(productFarmList);

        // When
        ResponseEntity<?> response = productFarmController.getAllProductFarm();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productFarmList, response.getBody());
    }

    @Test
    void givenValidProductFarmId_whenGetProductFarmById_thenReturnProductFarm() {
        // Given
        Integer productFarmId = 1;
        when(productFarmService.getProductFarmById(productFarmId)).thenReturn(Optional.of(productFarm));

        // When
        ResponseEntity<?> response = productFarmController.getProductFarmById(String.valueOf(productFarmId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productFarm, response.getBody());
    }

    @Test
    void givenInvalidProductFarmId_whenGetProductFarmById_thenReturnNotFound() {
        // Given
        Integer productFarmId = 1;
        when(productFarmService.getProductFarmById(productFarmId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = productFarmController.getProductFarmById(String.valueOf(productFarmId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidProductFarm_whenUpdateProductFarm_thenReturnUpdatedProductFarm() {
        // Given
        Integer productFarmId = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productFarmService.updateProductFarm(eq(productFarmId), any(ProductFarm.class))).thenReturn(Optional.of(productFarm));

        // When
        ResponseEntity<?> response = productFarmController.updateProductFarm(productFarm, String.valueOf(productFarmId), bindingResult);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productFarm, response.getBody());
    }

    @Test
    void givenInvalidProductFarm_whenUpdateProductFarm_thenReturnBadRequest() {
        // Given
        Integer productFarmId = 1;
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = productFarmController.updateProductFarm(productFarm, String.valueOf(productFarmId), bindingResult);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenInvalidProductFarmId_whenUpdateProductFarm_thenReturnNotFound() {
        // Given
        Integer productFarmId = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productFarmService.updateProductFarm(productFarmId, productFarm)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = productFarmController.updateProductFarm(productFarm, String.valueOf(productFarmId), bindingResult);

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidProductFarmId_whenDeleteProductFarm_thenReturnDeletedProductFarm() {
        // Given
        Integer productFarmId = 1;
        when(productFarmService.deleteProductFarm(productFarmId)).thenReturn(Optional.of(productFarm));

        // When
        ResponseEntity<?> response = productFarmController.deleteProductFarm(String.valueOf(productFarmId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productFarm, response.getBody());
    }

    @Test
    void givenInvalidProductFarmId_whenDeleteProductFarm_thenReturnNotFound() {
        // Given
        Integer productFarmId = 1;
        when(productFarmService.deleteProductFarm(productFarmId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = productFarmController.deleteProductFarm(String.valueOf(productFarmId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidFarmId_whenGetProductFarmByFarmId_thenReturnProductFarmList() {
        // Given
        Integer farmId = 1;
        when(productFarmService.getProductFarmsByFarmId(farmId)).thenReturn(productFarmList);

        // When
        ResponseEntity<?> response = productFarmController.getProductFarmByFarmId(String.valueOf(farmId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productFarmList, response.getBody());
    }

    @Test
    void givenValidProductId_whenGetProductFarmByProductId_thenReturnProductFarmList() {
        // Given
        Integer productId = 1;
        when(productFarmService.getProductFarmsByProductId(productId)).thenReturn(productFarmList);

        // When
        ResponseEntity<?> response = productFarmController.getProductFarmByProductId(String.valueOf(productId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productFarmList, response.getBody());
    }

    @Test
    void givenValidProductId_whenGetProductStockAcrossFarms_thenReturnStock() {
        // Given
        Integer productId = 1;
        Integer stock = 250;
        when(productFarmService.calculateProductStockAcrossFarms(productId)).thenReturn(stock);

        // When
        ResponseEntity<?> response = productFarmController.getProductStockAcrossFarms(String.valueOf(productId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(stock, response.getBody());
    }
}
