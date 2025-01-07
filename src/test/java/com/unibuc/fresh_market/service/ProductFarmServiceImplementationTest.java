package com.unibuc.fresh_market.service;

import com.unibuc.fresh_market.domain.Farm;
import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.domain.ProductFarm;
import com.unibuc.fresh_market.domain.security.User;
import com.unibuc.fresh_market.repository.ProductFarmRepository;
import com.unibuc.fresh_market.service.ProductFarmService.ProductFarmServiceImplementation;
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
public class ProductFarmServiceImplementationTest {

    @Mock
    private ProductFarmRepository productFarmRepository;

    @InjectMocks
    private ProductFarmServiceImplementation productFarmService;

    private ProductFarm productFarm;
    private List<ProductFarm> productFarmList;

    @BeforeEach
    void setUp() {
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
    void whenCreateProductFarm_thenReturnCreatedProductFarm() {
        // Given
        when(productFarmRepository.save(any(ProductFarm.class))).thenReturn(productFarm);

        // When
        ProductFarm createdProductFarm = productFarmService.createProductFarm(productFarm);

        // Then
        assertNotNull(createdProductFarm);
        assertEquals(productFarm.getId(), createdProductFarm.getId());
        assertEquals(productFarm.getProduct(), createdProductFarm.getProduct());
        assertEquals(productFarm.getFarm(), createdProductFarm.getFarm());
        assertEquals(productFarm.getQuantity(), createdProductFarm.getQuantity());
        assertEquals(productFarm.getNotes(), createdProductFarm.getNotes());

        verify(productFarmRepository, times(1)).save(any(ProductFarm.class));
    }

    @Test
    void whenGetAllProductFarms_thenReturnListOfProductFarms() {
        // Given
        when(productFarmRepository.findAll()).thenReturn(productFarmList);

        // When
        List<ProductFarm> result = productFarmService.getAllProductFarms();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(productFarm.getId(), result.getFirst().getId());

        verify(productFarmRepository, times(1)).findAll();
    }

    @Test
    void whenGetProductFarmById_thenReturnProductFarm() {
        // Given
        when(productFarmRepository.findById(1)).thenReturn(Optional.of(productFarm));

        // When
        Optional<ProductFarm> result = productFarmService.getProductFarmById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(productFarm.getId(), result.get().getId());
        assertEquals(productFarm.getProduct(), result.get().getProduct());
        assertEquals(productFarm.getFarm(), result.get().getFarm());

        verify(productFarmRepository, times(1)).findById(1);
    }

    @Test
    void whenUpdateProductFarm_thenReturnUpdatedProductFarm() {
        // Given
        ProductFarm updatedProductFarm = ProductFarm.builder()
                .id(productFarm.getId())
                .product(productFarm.getProduct())
                .farm(productFarm.getFarm())
                .quantity(60)
                .notes("Updated stock")
                .build();

        when(productFarmRepository.findById(1)).thenReturn(Optional.of(productFarm));
        when(productFarmRepository.save(any(ProductFarm.class))).thenReturn(updatedProductFarm);

        // When
        Optional<ProductFarm> result = productFarmService.updateProductFarm(1, updatedProductFarm);

        // Then
        assertTrue(result.isPresent());
        assertEquals(updatedProductFarm.getQuantity(), result.get().getQuantity());
        assertEquals(updatedProductFarm.getNotes(), result.get().getNotes());

        verify(productFarmRepository, times(1)).findById(1);
        verify(productFarmRepository, times(1)).save(any(ProductFarm.class));
    }

    @Test
    void whenDeleteProductFarm_thenReturnDeletedProductFarm() {
        // Given
        when(productFarmRepository.findById(1)).thenReturn(Optional.of(productFarm));

        // When
        Optional<ProductFarm> result = productFarmService.deleteProductFarm(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(productFarm.getId(), result.get().getId());

        verify(productFarmRepository, times(1)).findById(1);
        verify(productFarmRepository, times(1)).delete(productFarm);
    }

    @Test
    void whenGetProductFarmsByFarmId_thenReturnListOfProductFarms() {
        // Given
        when(productFarmRepository.findAllByFarmId(10)).thenReturn(List.of(productFarm));

        // When
        List<ProductFarm> result = productFarmService.getProductFarmsByFarmId(10);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(productFarm.getFarm(), result.getFirst().getFarm());

        verify(productFarmRepository, times(1)).findAllByFarmId(10);
    }

    @Test
    void whenGetProductFarmsByProductId_thenReturnListOfProductFarms() {
        // Given
        when(productFarmRepository.findAllByProductId(100)).thenReturn(productFarmList);

        // When
        List<ProductFarm> result = productFarmService.getProductFarmsByProductId(100);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(productFarm.getProduct(), result.getFirst().getProduct());

        verify(productFarmRepository, times(1)).findAllByProductId(100);
    }

    @Test
    void whenCalculateProductStockAcrossFarms_thenReturnTotalStock() {
        // Given
        when(productFarmRepository.findAllByProductId(100)).thenReturn(productFarmList);

        // When
        Integer totalStock = productFarmService.calculateProductStockAcrossFarms(100);

        // Then
        assertNotNull(totalStock);
        assertEquals(250, totalStock);

        verify(productFarmRepository, times(1)).findAllByProductId(100);
    }
}

