package com.unibuc.fresh_market.service.DeliveryService;

import com.unibuc.fresh_market.domain.Delivery;

import java.util.List;
import java.util.Optional;

public interface DeliveryService {
    Delivery createDelivery(Delivery delivery);
    List<Delivery> getAllDeliveries();
    Optional<Delivery> getDeliveryById(Integer id);
    Optional<Delivery> updateDelivery(Integer id, Delivery delivery);
    Optional<Delivery> deleteDelivery(Integer id);

    List<Delivery> getDeliveriesByStatus(String status);
}
