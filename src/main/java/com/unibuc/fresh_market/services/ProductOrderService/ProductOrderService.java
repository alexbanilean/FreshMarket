package com.unibuc.fresh_market.services.ProductOrderService;

import com.unibuc.fresh_market.domain.ProductOrder;

import java.util.List;

public interface ProductOrderService {
    ProductOrder createProductOrder(ProductOrder productOrder);
    ProductOrder getProductOrderById(Integer id);
    List<ProductOrder> getAllProductOrders();
    ProductOrder updateProductOrder(Integer id, ProductOrder productOrder);
    void deleteProductOrder(Integer id);

    double calculateTotalOrderValue(Integer orderId);
    List<ProductOrder> getProductOrdersByOrderId(Integer orderId);
}
