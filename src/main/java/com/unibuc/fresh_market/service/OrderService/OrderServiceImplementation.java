package com.unibuc.fresh_market.service.OrderService;

import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.ProductOrder;
import com.unibuc.fresh_market.repository.OrderRepository;
import com.unibuc.fresh_market.repository.ProductOrderRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplementation implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductOrderRepository productOrderRepository;

    public OrderServiceImplementation(OrderRepository orderRepository, ProductOrderRepository productOrderRepository) {
        this.orderRepository = orderRepository;
        this.productOrderRepository = productOrderRepository;
    }

    public Order createOrder(Order order) {
        order.setCreatedAt(new Timestamp(new Date().getTime()));
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> updateOrder(Integer id, Order order) {
        Order orderToUpdate = orderRepository.findById(id).orElse(null);

        if (orderToUpdate != null) {
            orderToUpdate.setStatus(order.getStatus());
            orderToUpdate.setTotalAmount(order.getTotalAmount());
            Order udpatedOrder = orderRepository.save(orderToUpdate);
            return Optional.of(udpatedOrder);
        }

        return Optional.empty();
    }

    public Optional<Order> deleteOrder(Integer id) {
        Order orderToDelete = orderRepository.findById(id).orElse(null);
        if (orderToDelete != null) {
            orderRepository.delete(orderToDelete);
            return Optional.of(orderToDelete);
        }

        return Optional.empty();
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findOrdersByStatus(status);
    }

    public List<ProductOrder> getOrderDetails(Integer orderId) {
        return productOrderRepository.findProductOrdersByOrderId(orderId);
    }
}
