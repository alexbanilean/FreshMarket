package com.unibuc.fresh_market.service.CategoryService;

import com.unibuc.fresh_market.domain.Category;
import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.repository.CategoryRepository;
import com.unibuc.fresh_market.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImplementation(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return Optional.ofNullable(category);
    }

    public Optional<Category> updateCategory(Integer id, Category category) {
        Category categoryToUpdate = categoryRepository.findById(id).orElse(null);
        if (categoryToUpdate != null) {
            categoryToUpdate.setName(category.getName());
            categoryToUpdate.setDescription(category.getDescription());
            Category updatedCategory = categoryRepository.save(categoryToUpdate);
            return Optional.of(updatedCategory);
        }

        return Optional.empty();
    }

    public Optional<Category> deleteCategory(Integer id) {
        Category categoryToDelete = categoryRepository.findById(id).orElse(null);
        if (categoryToDelete != null) {
            categoryRepository.delete(categoryToDelete);
            return Optional.of(categoryToDelete);
        }

        return Optional.empty();
    }

    public List<Product> getProductsByCategoryId(Integer categoryId) {
        return productRepository.getProductsByCategoryId(categoryId);
    }
}
