package com.unibuc.fresh_market.repositories;

import com.unibuc.fresh_market.domain.ProductFarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFarmRepository extends JpaRepository<ProductFarm, Integer> {
    List<ProductFarm> findAllByFarmId(Integer farmId);
    List<ProductFarm> findAllByProductId(Integer productId);
}
