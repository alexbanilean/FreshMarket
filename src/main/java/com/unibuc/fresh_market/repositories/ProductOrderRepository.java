package com.unibuc.fresh_market.repositories;

import com.unibuc.fresh_market.domain.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {
    List<ProductOrder> findProductOrdersByOrderId(Integer orderId);
}
