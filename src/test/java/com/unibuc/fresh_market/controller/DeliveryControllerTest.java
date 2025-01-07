package com.unibuc.fresh_market.controller;

import com.unibuc.fresh_market.domain.Delivery;
import com.unibuc.fresh_market.service.DeliveryService.DeliveryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryControllerTest {

    @Mock
    private DeliveryService deliveryService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private DeliveryController deliveryController;

    private Delivery delivery;
    private List<Delivery> deliveryList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Delivery delivery1 = Delivery.builder().id(1).deliveryStatus("Delivered").build();
        Delivery delivery2 = Delivery.builder().id(2).deliveryStatus("In progress").build();

        delivery = delivery1;
        deliveryList = List.of(delivery1, delivery2);
    }

    @Test
    void givenValidDelivery_whenCreateDelivery_thenReturnCreatedDelivery() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(false);
        when(deliveryService.createDelivery(any(Delivery.class))).thenReturn(delivery);

        // When
        ResponseEntity<?> response = deliveryController.createDelivery(delivery, bindingResult);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(delivery, response.getBody());
    }

    @Test
    void givenInvalidDelivery_whenCreateDelivery_thenReturnBadRequest() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = deliveryController.createDelivery(delivery, bindingResult);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenDeliveries_whenGetAllDeliveries_thenReturnDeliveryList() {
        // Given
        when(deliveryService.getAllDeliveries()).thenReturn(deliveryList);

        // When
        ResponseEntity<?> response = deliveryController.getAllDeliveries();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(deliveryList, response.getBody());
    }

    @Test
    void givenValidDeliveryId_whenGetDeliveryById_thenReturnDelivery() {
        // Given
        Integer deliveryId = 1;
        when(deliveryService.getDeliveryById(deliveryId)).thenReturn(Optional.of(delivery));

        // When
        ResponseEntity<?> response = deliveryController.getDeliveryById(String.valueOf(deliveryId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(delivery, response.getBody());
    }

    @Test
    void givenInvalidDeliveryId_whenGetDeliveryById_thenReturnNotFound() {
        // Given
        Integer deliveryId = 1;
        when(deliveryService.getDeliveryById(deliveryId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = deliveryController.getDeliveryById(String.valueOf(deliveryId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidDelivery_whenUpdateDelivery_thenReturnUpdatedDelivery() {
        // Given
        Integer deliveryId = 1;
        Delivery updatedDelivery = Delivery.builder().id(deliveryId).deliveryStatus("Shipped").build();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(deliveryService.updateDelivery(deliveryId, updatedDelivery)).thenReturn(Optional.of(updatedDelivery));

        // When
        ResponseEntity<?> response = deliveryController.updateDelivery(updatedDelivery, bindingResult, String.valueOf(deliveryId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(updatedDelivery, response.getBody());
    }

    @Test
    void givenInvalidDelivery_whenUpdateDelivery_thenReturnBadRequest() {
        // Given
        Integer deliveryId = 1;
        Delivery updatedDelivery = Delivery.builder().id(deliveryId).deliveryStatus("Shipped").build();

        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = deliveryController.updateDelivery(updatedDelivery, bindingResult, String.valueOf(deliveryId));

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenInvalidDeliveryId_whenUpdateDelivery_thenReturnNotFound() {
        // Given
        Integer deliveryId = 1;
        Delivery updatedDelivery = Delivery.builder().id(deliveryId).deliveryStatus("Shipped").build();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(deliveryService.updateDelivery(deliveryId, updatedDelivery)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = deliveryController.updateDelivery(updatedDelivery, bindingResult, String.valueOf(deliveryId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidDeliveryId_whenDeleteDelivery_thenReturnDeletedDelivery() {
        // Given
        Integer deliveryId = 1;
        when(deliveryService.deleteDelivery(deliveryId)).thenReturn(Optional.of(delivery));

        // When
        ResponseEntity<?> response = deliveryController.deleteDelivery(String.valueOf(deliveryId), bindingResult);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(delivery, response.getBody());
    }

    @Test
    void givenInvalidDeliveryId_whenDeleteDelivery_thenReturnNotFound() {
        // Given
        Integer deliveryId = 1;
        when(deliveryService.deleteDelivery(deliveryId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = deliveryController.deleteDelivery(String.valueOf(deliveryId), bindingResult);

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidStatus_whenGetDeliveriesByStatus_thenReturnDeliveryList() {
        // Given
        String status = "Delivered";
        when(bindingResult.hasErrors()).thenReturn(false);
        when(deliveryService.getDeliveriesByStatus(status)).thenReturn(deliveryList);

        // When
        ResponseEntity<?> response = deliveryController.getDeliveriesByStatus(status, bindingResult);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(deliveryList, response.getBody());
    }

    @Test
    void givenInvalidStatus_whenGetDeliveriesByStatus_thenReturnBadRequest() {
        // Given
        String status = "InvalidStatus";
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = deliveryController.getDeliveriesByStatus(status, bindingResult);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }
}
