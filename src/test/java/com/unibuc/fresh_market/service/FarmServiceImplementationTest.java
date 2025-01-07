package com.unibuc.fresh_market.service;

import com.unibuc.fresh_market.domain.Farm;
import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.domain.ProductFarm;
import com.unibuc.fresh_market.repository.FarmRepository;
import com.unibuc.fresh_market.repository.OrderRepository;
import com.unibuc.fresh_market.repository.ProductRepository;
import com.unibuc.fresh_market.service.FarmService.FarmServiceImplementation;
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
public class FarmServiceImplementationTest {

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private FarmServiceImplementation farmService;

    private Farm farm;
    private Product product;
    private Order order;

    @BeforeEach
    void setUp() {
        farm = Farm.builder().id(1).name("Farm 1").address("Address 1").build();
        product = Product.builder().id(1).name("Product 1").price(10.0).build();
        ProductFarm productFarm = ProductFarm.builder().farm(farm).product(product).quantity(100).build();
        product.setProductFarms(List.of(productFarm));
        order = Order.builder()
                .status("Pending")
                .totalAmount(100.0)
                .createdAt(new Date())
                .farm(farm)
                .build();
    }

    @Test
    void whenCreateFarm_thenReturnCreatedFarm() {
        // Given
        when(farmRepository.save(any(Farm.class))).thenReturn(farm);

        // When
        Farm createdFarm = farmService.createFarm(farm);

        // Then
        assertNotNull(createdFarm);
        assertEquals(farm.getId(), createdFarm.getId());
        assertEquals(farm.getName(), createdFarm.getName());
        assertEquals(farm.getAddress(), createdFarm.getAddress());

        verify(farmRepository, times(1)).save(any(Farm.class));
    }

    @Test
    void whenGetAllFarms_thenReturnListOfFarms() {
        // Given
        when(farmRepository.findAll()).thenReturn(List.of(farm));

        // When
        List<Farm> farms = farmService.getAllFarms();

        // Then
        assertNotNull(farms);
        assertFalse(farms.isEmpty());
        assertEquals(1, farms.size());
        assertEquals(farm.getId(), farms.getFirst().getId());

        verify(farmRepository, times(1)).findAll();
    }

    @Test
    void whenGetFarmById_thenReturnFarm() {
        // Given
        when(farmRepository.findById(1)).thenReturn(Optional.of(farm));

        // When
        Optional<Farm> result = farmService.getFarmById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(farm.getId(), result.get().getId());

        verify(farmRepository, times(1)).findById(1);
    }

    @Test
    void whenUpdateFarm_thenReturnUpdatedFarm() {
        // Given
        Farm updatedFarm = Farm.builder().id(1).name("Updated Farm").address("Updated Address").build();
        when(farmRepository.findById(1)).thenReturn(Optional.of(farm));
        when(farmRepository.save(any(Farm.class))).thenReturn(updatedFarm);

        // When
        Optional<Farm> result = farmService.updateFarm(1, updatedFarm);

        // Then
        assertTrue(result.isPresent());
        assertEquals(updatedFarm.getName(), result.get().getName());
        assertEquals(updatedFarm.getAddress(), result.get().getAddress());

        verify(farmRepository, times(1)).findById(1);
        verify(farmRepository, times(1)).save(any(Farm.class));
    }

    @Test
    void whenDeleteFarm_thenReturnDeletedFarm() {
        // Given
        when(farmRepository.findById(1)).thenReturn(Optional.of(farm));

        // When
        Optional<Farm> result = farmService.deleteFarm(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(farm.getId(), result.get().getId());

        verify(farmRepository, times(1)).findById(1);
        verify(farmRepository, times(1)).delete(farm);
    }

    @Test
    void whenGetProductsByFarmId_thenReturnListOfProducts() {
        // Given
        when(productRepository.getProductsByFarmId(1)).thenReturn(List.of(product));

        // When
        List<Product> result = farmService.getProductsByFarmId(1);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(product.getId(), result.getFirst().getId());

        verify(productRepository, times(1)).getProductsByFarmId(1);
    }

    @Test
    void whenGetOrdersByFarmId_thenReturnListOfOrders() {
        // Given
        when(orderRepository.findOrdersByFarmId(1)).thenReturn(List.of(order));

        // When
        List<Order> result = farmService.getOrdersByFarmId(1);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(order.getId(), result.getFirst().getId());

        verify(orderRepository, times(1)).findOrdersByFarmId(1);
    }

    @Test
    void whenCalculateTotalSalesByFarmId_thenReturnTotalSales() {
        // Given
        when(orderRepository.findOrdersByFarmId(1)).thenReturn(List.of(order));

        // When
        double totalSales = farmService.calculateTotalSalesByFarmId(1);

        // Then
        assertEquals(100.0, totalSales, 0.01);

        // Verify the findOrdersByFarmId method is called
        verify(orderRepository, times(1)).findOrdersByFarmId(1);
    }
}

