package com.unibuc.fresh_market.services.CategoryService;

import com.unibuc.fresh_market.domain.Category;
import com.unibuc.fresh_market.domain.Product;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(Category category);
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Integer id);
    Optional<Category> updateCategory(Integer id, Category category);
    Optional<Category> deleteCategory(Integer id);

    List<Product> getProductsByCategoryId(Integer categoryId);
}
