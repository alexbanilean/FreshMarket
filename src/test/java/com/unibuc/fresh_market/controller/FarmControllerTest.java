package com.unibuc.fresh_market.controller;

import com.unibuc.fresh_market.domain.Farm;
import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.service.FarmService.FarmService;
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

class FarmControllerTest {

    @Mock
    private FarmService farmService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private FarmController farmController;

    private Farm farm;
    private List<Farm> farmList;

    private List<Product> productList;
    private List<Order> orderList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Farm farm1 = Farm.builder().id(1).name("Sunny Farm").address("123 Green Lane").build();
        Farm farm2 = Farm.builder().id(2).name("Happy Farm").address("456 Blue Street").build();

        farm = farm1;
        farmList = List.of(farm1, farm2);

        Product product1 = Product.builder().id(1).name("Apple").build();
        Product product2 = Product.builder().id(2).name("Carrot").build();
        productList = List.of(product1, product2);

        Order order1 = Order.builder().id(1).build();
        Order order2 = Order.builder().id(2).build();
        orderList = List.of(order1, order2);
    }

    @Test
    void givenValidFarm_whenCreateFarm_thenReturnCreatedFarm() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(false);
        when(farmService.createFarm(any(Farm.class))).thenReturn(farm);

        // When
        ResponseEntity<?> response = farmController.createFarm(farm, bindingResult);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(farm, response.getBody());
    }

    @Test
    void givenInvalidFarm_whenCreateFarm_thenReturnValidationError() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = farmController.createFarm(farm, bindingResult);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenUpdatedFarm_whenUpdateFarm_thenReturnUpdatedFarm() {
        // Given
        Integer farmId = 1;
        Farm updatedFarm = Farm.builder().id(farmId).name("Updated Farm").address("789 Red Avenue").build();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(farmService.updateFarm(farmId, updatedFarm)).thenReturn(Optional.of(updatedFarm));

        // When
        ResponseEntity<?> response = farmController.updateFarm(updatedFarm, String.valueOf(farmId), bindingResult);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(updatedFarm, response.getBody());
    }

    @Test
    void givenInvalidFarm_whenUpdateFarm_thenReturnValidationError() {
        // Given
        Integer farmId = 1;
        Farm updatedFarm = Farm.builder().id(farmId).name("Updated Farm").address("789 Red Avenue").build();

        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = farmController.updateFarm(updatedFarm, String.valueOf(farmId), bindingResult);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenFarms_whenGetAllFarms_thenReturnFarmList() {
        // Given
        when(farmService.getAllFarms()).thenReturn(farmList);

        // When
        ResponseEntity<?> response = farmController.getAllFarms();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(farmList, response.getBody());
    }

    @Test
    void givenValidFarmId_whenGetFarmById_thenReturnFarm() {
        // Given
        Integer farmId = 1;
        when(farmService.getFarmById(farmId)).thenReturn(Optional.of(farm));

        // When
        ResponseEntity<?> response = farmController.getFarmById(String.valueOf(farmId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(farm, response.getBody());
    }

    @Test
    void givenInvalidFarmId_whenGetFarmById_thenReturnNotFound() {
        // Given
        Integer farmId = 1;
        when(farmService.getFarmById(farmId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = farmController.getFarmById(String.valueOf(farmId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidFarmId_whenDeleteFarm_thenReturnDeletedFarm() {
        // Given
        Integer farmId = 1;
        when(farmService.deleteFarm(farmId)).thenReturn(Optional.of(farm));

        // When
        ResponseEntity<?> response = farmController.deleteFarm(String.valueOf(farmId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(farm, response.getBody());
    }

    @Test
    void givenInvalidFarmId_whenDeleteFarm_thenReturnNotFound() {
        // Given
        Integer farmId = 1;
        when(farmService.deleteFarm(farmId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = farmController.deleteFarm(String.valueOf(farmId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidFarmId_whenGetFarmProducts_thenReturnProductList() {
        // Given
        Integer farmId = 1;
        when(farmService.getProductsByFarmId(farmId)).thenReturn(productList);

        // When
        ResponseEntity<?> response = farmController.getFarmProducts(String.valueOf(farmId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productList, response.getBody());
    }

    @Test
    void givenValidFarmId_whenGetFarmOrders_thenReturnOrderList() {
        // Given
        Integer farmId = 1;
        when(farmService.getOrdersByFarmId(farmId)).thenReturn(orderList);

        // When
        ResponseEntity<?> response = farmController.getFarmOrders(String.valueOf(farmId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(orderList, response.getBody());
    }

    @Test
    void givenValidFarmId_whenGetFarmSales_thenReturnTotalSales() {
        // Given
        Integer farmId = 1;
        double totalSales = 1500.0;
        when(farmService.calculateTotalSalesByFarmId(farmId)).thenReturn(totalSales);

        // When
        ResponseEntity<?> response = farmController.getFarmSalesByFarmId(String.valueOf(farmId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(totalSales, response.getBody());
    }
}
