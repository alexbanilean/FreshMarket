package com.unibuc.fresh_market.services.OrderService;

import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.ProductOrder;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    Order getOrderById(Integer id);
    List<Order> getAllOrders();
    Order updateOrder(Integer id, Order order);
    void deleteOrder(Integer id);

    List<Order> getOrdersByStatus(String status);
    List<ProductOrder> getOrderDetails(Integer orderId);
    void updateOrderStatus(Integer orderId, String status);
}
