package com.unibuc.fresh_market.controllers;

import com.unibuc.fresh_market.domain.Category;
import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.services.CategoryService.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Categories controller")
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Create a new category")
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody @Valid Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    @Operation(summary = "Get all categories")
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Get a category by id")
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable String categoryId) {
        Integer id = Integer.parseInt(categoryId);
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            return ResponseEntity.ok(category.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update a category")
    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@RequestBody @Valid Category category, @PathVariable String categoryId) {
        Integer id = Integer.parseInt(categoryId);
        Optional<Category> updatedCategory = categoryService.updateCategory(id, category);
        if (updatedCategory.isPresent()) {
            return ResponseEntity.ok(updatedCategory.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a category")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable String categoryId) {
        Integer id = Integer.parseInt(categoryId);
        Optional<Category> deletedCategory = categoryService.deleteCategory(id);
        if (deletedCategory.isPresent()) {
            return ResponseEntity.ok(deletedCategory.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get all products within a category")
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String categoryId) {
        Integer id = Integer.parseInt(categoryId);
        List<Product> products = categoryService.getProductsByCategoryId(id);
        return ResponseEntity.ok(products);
    }
}
