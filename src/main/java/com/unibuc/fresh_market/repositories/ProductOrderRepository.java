package com.unibuc.fresh_market.repositories;

import com.unibuc.fresh_market.domain.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {
}
