package com.unibuc.fresh_market.service.FarmService;

import com.unibuc.fresh_market.domain.Farm;
import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.repository.FarmRepository;
import com.unibuc.fresh_market.repository.OrderRepository;
import com.unibuc.fresh_market.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FarmServiceImplementation implements FarmService {
    private final FarmRepository farmRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public FarmServiceImplementation(FarmRepository farmRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.farmRepository = farmRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public Farm createFarm(Farm farm) {
        return farmRepository.save(farm);
    }

    public List<Farm> getAllFarms() {
        return farmRepository.findAll();
    }

    public Optional<Farm> getFarmById(Integer id) {
        Farm farm = farmRepository.findById(id).orElse(null);
        return Optional.ofNullable(farm);
    }

    public Optional<Farm> updateFarm(Integer id, Farm farm) {
        Farm farmToUpdate = farmRepository.findById(id).orElse(null);
        if (farmToUpdate != null) {
            farmToUpdate.setName(farm.getName());
            farmToUpdate.setAddress(farm.getAddress());
            Farm updatedFarm = farmRepository.save(farmToUpdate);
            return Optional.of(updatedFarm);
        }

        return Optional.empty();
    }

    public Optional<Farm> deleteFarm(Integer id) {
        Farm farmToDelete = farmRepository.findById(id).orElse(null);
        if (farmToDelete != null) {
            farmRepository.delete(farmToDelete);
            return Optional.of(farmToDelete);
        }

        return Optional.empty();
    }

    public List<Product> getProductsByFarmId(Integer farmId) {
        return productRepository.getProductsByFarmId(farmId);
    }

    public List<Order> getOrdersByFarmId(Integer farmId) {
        return orderRepository.findOrdersByFarmId(farmId);
    }

    public double calculateTotalSalesByFarmId(Integer farmId) {
        List<Order> orders = orderRepository.findOrdersByFarmId(farmId);
        return orders.stream().mapToDouble(Order::getTotalAmount).sum();
    }
}
