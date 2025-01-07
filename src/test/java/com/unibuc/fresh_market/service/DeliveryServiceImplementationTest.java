package com.unibuc.fresh_market.service;

import com.unibuc.fresh_market.domain.Delivery;
import com.unibuc.fresh_market.repository.DeliveryRepository;
import com.unibuc.fresh_market.service.DeliveryService.DeliveryServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceImplementationTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryServiceImplementation deliveryService;

    private Delivery delivery;

    @BeforeEach
    void setUp() {
        delivery = Delivery.builder()
                .id(1)
                .deliveryDate(new Date())
                .deliveryStatus("Delivered")
                .build();
    }

    @Test
    void whenCreateDelivery_thenReturnCreatedDelivery() {
        // Given
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(delivery);

        // When
        Delivery createdDelivery = deliveryService.createDelivery(delivery);

        // Then
        assertNotNull(createdDelivery);
        assertEquals(delivery.getId(), createdDelivery.getId());
        assertEquals(delivery.getDeliveryDate(), createdDelivery.getDeliveryDate());
        assertEquals(delivery.getDeliveryStatus(), createdDelivery.getDeliveryStatus());

        verify(deliveryRepository, times(1)).save(any(Delivery.class));
    }

    @Test
    void whenGetAllDeliveries_thenReturnListOfDeliveries() {
        // Given
        when(deliveryRepository.findAll()).thenReturn(List.of(delivery));

        // When
        List<Delivery> deliveries = deliveryService.getAllDeliveries();

        // Then
        assertNotNull(deliveries);
        assertFalse(deliveries.isEmpty());
        assertEquals(1, deliveries.size());
        assertEquals(delivery.getId(), deliveries.getFirst().getId());

        verify(deliveryRepository, times(1)).findAll();
    }

    @Test
    void whenGetDeliveryById_thenReturnDelivery() {
        // Given
        when(deliveryRepository.findById(1)).thenReturn(Optional.of(delivery));

        // When
        Optional<Delivery> result = deliveryService.getDeliveryById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(delivery.getId(), result.get().getId());

        verify(deliveryRepository, times(1)).findById(1);
    }

    @Test
    void whenUpdateDelivery_thenReturnUpdatedDelivery() {
        // Given
        Delivery updatedDelivery = Delivery.builder()
                .id(1)
                .deliveryDate(new Date())
                .deliveryStatus("Pending")
                .build();

        when(deliveryRepository.findById(1)).thenReturn(Optional.of(delivery));
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(updatedDelivery);

        // When
        Optional<Delivery> result = deliveryService.updateDelivery(1, updatedDelivery);

        // Then
        assertTrue(result.isPresent());
        assertEquals(updatedDelivery.getDeliveryDate(), result.get().getDeliveryDate());
        assertEquals(updatedDelivery.getDeliveryStatus(), result.get().getDeliveryStatus());

        verify(deliveryRepository, times(1)).findById(1);
        verify(deliveryRepository, times(1)).save(any(Delivery.class));
    }

    @Test
    void whenDeleteDelivery_thenReturnDeletedDelivery() {
        // Given
        when(deliveryRepository.findById(1)).thenReturn(Optional.of(delivery));

        // When
        Optional<Delivery> result = deliveryService.deleteDelivery(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(delivery.getId(), result.get().getId());

        verify(deliveryRepository, times(1)).findById(1);
        verify(deliveryRepository, times(1)).delete(delivery);
    }

    @Test
    void whenGetDeliveriesByStatus_thenReturnListOfDeliveries() {
        // Given
        when(deliveryRepository.findDeliveriesByDeliveryStatus("Delivered")).thenReturn(List.of(delivery));

        // When
        List<Delivery> result = deliveryService.getDeliveriesByStatus("Delivered");

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Delivered", result.getFirst().getDeliveryStatus());

        verify(deliveryRepository, times(1)).findDeliveriesByDeliveryStatus("Delivered");
    }
}
