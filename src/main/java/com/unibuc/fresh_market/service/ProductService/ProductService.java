package com.unibuc.fresh_market.service.ProductService;

import com.unibuc.fresh_market.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Optional<Product> getProductById(Integer id);
    Optional<Product> updateProduct(Integer id, Product product);
    Optional<Product> deleteProduct(Integer id);

    List<Product> getProductsByFarmId(Integer farmId);
}
