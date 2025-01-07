package com.unibuc.fresh_market.repositories;

import com.unibuc.fresh_market.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT p FROM Product p JOIN p.productFarms pf WHERE pf.farm.id = :farmId")
    List<Product> getProductsByFarmId(Integer farmId);

    List<Product> getProductsByCategoryId(Integer categoryId);
}
