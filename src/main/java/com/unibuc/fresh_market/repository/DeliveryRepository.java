package com.unibuc.fresh_market.repository;

import com.unibuc.fresh_market.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    List<Delivery> findDeliveriesByDeliveryStatus(String status);
}
