package com.unibuc.fresh_market.services.ProductService;

import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImplementation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer productId) {
        return productRepository.findById(productId);
    }

    public Optional<Product> updateProduct(Integer productId, Product product) {
        Product productToUpdate = productRepository.findById(productId).orElse(null);

        if (productToUpdate != null) {
            productToUpdate.setName(product.getName());
            productToUpdate.setPrice(product.getPrice());
            productToUpdate.setDescription(product.getDescription());
            productToUpdate.setCategory(product.getCategory());

            Product updatedProduct = productRepository.save(productToUpdate);
            return Optional.of(updatedProduct);
        }

        return Optional.empty();
    }

    public Optional<Product> deleteProduct(Integer productId) {
        Product productToDelete = productRepository.findById(productId).orElse(null);
        if (productToDelete != null) {
            productRepository.delete(productToDelete);
            return Optional.of(productToDelete);
        }

        return Optional.empty();
    }

    public List<Product> getProductsByFarmId(Integer farmId) {
        return productRepository.getProductsByFarmId(farmId);
    }
}
