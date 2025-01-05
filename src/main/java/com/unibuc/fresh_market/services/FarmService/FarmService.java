package com.unibuc.fresh_market.services.FarmService;

import com.unibuc.fresh_market.domain.Farm;
import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.Product;

import java.util.List;

public interface FarmService {
    Farm createFarm(Farm farm);
    Farm getFarmById(Integer id);
    List<Farm> getAllFarms();
    Farm updateFarm(Integer id, Farm farm);
    void deleteFarm(Integer id);

    List<Product> getProductsByFarmId(Integer farmId);
    double calculateTotalSalesByFarmId(Integer farmId);
    List<Order> getOrdersByFarmId(Integer farmId);
}
