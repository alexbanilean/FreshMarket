package com.unibuc.fresh_market.services.FarmService;

import com.unibuc.fresh_market.domain.Farm;
import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.Product;

import java.util.List;
import java.util.Optional;

public interface FarmService {
    Farm createFarm(Farm farm);
    List<Farm> getAllFarms();
    Optional<Farm> getFarmById(Integer id);
    Optional<Farm> updateFarm(Integer id, Farm farm);
    Optional<Farm> deleteFarm(Integer id);

    List<Product> getProductsByFarmId(Integer farmId);
    List<Order> getOrdersByFarmId(Integer farmId);
    double calculateTotalSalesByFarmId(Integer farmId);
}
