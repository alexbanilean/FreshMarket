package com.unibuc.fresh_market.service.OrderService;

import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.ProductOrder;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getAllOrders();
    Optional<Order> getOrderById(Integer id);
    Optional<Order> updateOrder(Integer id, Order order);
    Optional<Order> deleteOrder(Integer id);

    List<Order> getOrdersByStatus(String status);
    List<ProductOrder> getOrderDetails(Integer orderId);
}
