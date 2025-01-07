package com.unibuc.fresh_market.service.DeliveryService;

import com.unibuc.fresh_market.domain.Delivery;
import com.unibuc.fresh_market.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImplementation implements DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public DeliveryServiceImplementation(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public Delivery createDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Optional<Delivery> getDeliveryById(Integer id) {
        Delivery delivery = deliveryRepository.findById(id).orElse(null);
        return Optional.ofNullable(delivery);
    }

    public Optional<Delivery> updateDelivery(Integer id, Delivery delivery) {
        Delivery deliveryToUpdate = deliveryRepository.findById(id).orElse(null);

        if (deliveryToUpdate != null) {
            deliveryToUpdate.setDeliveryDate(delivery.getDeliveryDate());
            deliveryToUpdate.setDeliveryStatus(delivery.getDeliveryStatus());
            Delivery updatedDelivery = deliveryRepository.save(deliveryToUpdate);
            return Optional.of(updatedDelivery);
        }

        return Optional.empty();
    }

    public Optional<Delivery> deleteDelivery(Integer id) {
        Delivery deliveryToDelete = deliveryRepository.findById(id).orElse(null);
        if (deliveryToDelete != null) {
            deliveryRepository.delete(deliveryToDelete);
            return Optional.of(deliveryToDelete);
        }

        return Optional.empty();
    }

    public List<Delivery> getDeliveriesByStatus(String status) {
        return deliveryRepository.findDeliveriesByDeliveryStatus(status);
    }

}
