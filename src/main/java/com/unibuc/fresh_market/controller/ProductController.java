package com.unibuc.fresh_market.controller;

import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.service.ProductService.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Products controller")
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create a new product")
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors found!");
        }

        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @Operation(summary = "Get all products")
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get a product by id")
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String productId) {
        Integer id = Integer.parseInt(productId);
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update a product")
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@RequestBody @Valid Product product, BindingResult bindingResult, @PathVariable String productId) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors found!");
        }

        Integer id = Integer.parseInt(productId);
        Optional<Product> updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct.isPresent()) {
            return ResponseEntity.ok(updatedProduct.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a product")
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId) {
        Integer id = Integer.parseInt(productId);
        Optional<Product> deletedProduct = productService.deleteProduct(id);
        if (deletedProduct.isPresent()) {
            return ResponseEntity.ok(deletedProduct.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get products within a farm")
    @GetMapping("/farm/{farmId}")
    public ResponseEntity<?> getProductByFarmId(@PathVariable String farmId) {
        Integer id = Integer.parseInt(farmId);
        List<Product> products = productService.getProductsByFarmId(id);
        return ResponseEntity.ok(products);
    }
}
