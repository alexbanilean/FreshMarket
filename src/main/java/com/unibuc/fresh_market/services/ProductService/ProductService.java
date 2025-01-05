package com.unibuc.fresh_market.services.ProductService;

import com.unibuc.fresh_market.domain.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product getProductById(Integer id);
    List<Product> getAllProducts();
    Product updateProduct(Integer id, Product product);
    void deleteProduct(Integer id);

    List<Product> getProductsByFarmerId(Integer farmerId);
}
