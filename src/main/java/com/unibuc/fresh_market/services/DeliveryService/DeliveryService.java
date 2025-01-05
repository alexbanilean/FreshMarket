package com.unibuc.fresh_market.services.DeliveryService;

import com.unibuc.fresh_market.domain.Delivery;

import java.util.List;

public interface DeliveryService {
    Delivery createDelivery(Delivery delivery);
    Delivery getDeliveryById(Integer id);
    List<Delivery> getAllDeliveries();
    Delivery updateDelivery(Integer id, Delivery delivery);
    void deleteDelivery(Integer id);

    List<Delivery> getDeliveriesByStatus(String status);
    void updateDeliveryStatus(Integer deliveryId, String status);
}
