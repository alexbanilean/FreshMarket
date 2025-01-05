package com.unibuc.fresh_market.services.CategoryService;

import com.unibuc.fresh_market.domain.Category;
import com.unibuc.fresh_market.domain.Product;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category getCategoryById(Integer id);
    List<Category> getAllCategories();
    Category updateCategory(Integer id, Category category);
    void deleteCategory(Integer id);

    List<Product> getProductsByCategoryId(Integer categoryId);
}
