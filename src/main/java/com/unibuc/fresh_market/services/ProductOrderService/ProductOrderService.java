package com.unibuc.fresh_market.services.ProductOrderService;

import com.unibuc.fresh_market.domain.ProductOrder;

import java.util.List;
import java.util.Optional;

public interface ProductOrderService {
    ProductOrder createProductOrder(ProductOrder productOrder);
    List<ProductOrder> getAllProductOrders();
    Optional<ProductOrder> getProductOrderById(Integer id);
    Optional<ProductOrder> updateProductOrder(Integer id, ProductOrder productOrder);
    Optional<ProductOrder> deleteProductOrder(Integer id);

    List<ProductOrder> getProductOrdersByOrderId(Integer orderId);
    double calculateTotalOrderValue(Integer orderId);
}
