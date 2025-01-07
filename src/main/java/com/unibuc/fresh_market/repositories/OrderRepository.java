package com.unibuc.fresh_market.repositories;

import com.unibuc.fresh_market.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrdersByStatus(String status);
    List<Order> findOrdersByFarmId(Integer farmId);
}
