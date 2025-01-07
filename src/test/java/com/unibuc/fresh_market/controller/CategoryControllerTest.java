package com.unibuc.fresh_market.controller;

import com.unibuc.fresh_market.domain.Category;
import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.service.CategoryService.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private CategoryController categoryController;

    private Category category;
    private List<Category> categoryList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Category category1 = Category.builder().id(1).name("Fruits").build();
        Category category2 = Category.builder().id(2).name("Dairy").build();

        category = category1;

        categoryList = List.of(category1, category2);
    }

    @Test
    void givenValidCategory_whenCreateCategory_thenReturnCreatedCategory() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(false);
        when(categoryService.createCategory(category)).thenReturn(category);

        // When
        ResponseEntity<?> response = categoryController.createCategory(category, bindingResult);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(category, response.getBody());
    }

    @Test
    void givenInvalidCategory_whenCreateCategory_thenReturnBadRequest() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = categoryController.createCategory(category, bindingResult);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenUpdatedCategory_whenUpdateCategory_thenReturnUpdatedCategory() {
        // Given
        Integer categoryId = 1;
        Category updatedCategory = new Category();
        updatedCategory.setId(categoryId);
        updatedCategory.setName("Updated Fruits");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(categoryService.updateCategory(categoryId, updatedCategory)).thenReturn(Optional.of(updatedCategory));

        // When
        ResponseEntity<?> response = categoryController.updateCategory(updatedCategory, bindingResult, String.valueOf(categoryId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(updatedCategory, response.getBody());
    }

    @Test
    void givenInvalidCategory_whenUpdateCategory_thenReturnBadRequest() {
        // Given
        Integer categoryId = 1;
        Category updatedCategory = new Category();
        updatedCategory.setId(categoryId);
        updatedCategory.setName("Updated Fruits");

        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = categoryController.updateCategory(updatedCategory, bindingResult, String.valueOf(categoryId));

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenCategories_whenGetAllCategories_thenReturnCategoryList() {
        // Given
        when(categoryService.getAllCategories()).thenReturn(categoryList);

        // When
        ResponseEntity<?> response = categoryController.getAllCategories();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(categoryList, response.getBody());
    }

    @Test
    void givenValidCategoryId_whenGetCategoryById_thenReturnCategory() {
        // Given
        Integer categoryId = 1;
        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(category));

        // When
        ResponseEntity<?> response = categoryController.getCategoryById(String.valueOf(categoryId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(category, response.getBody());
    }

    @Test
    void givenInvalidCategoryId_whenGetCategoryById_thenReturnNotFound() {
        // Given
        Integer categoryId = 1;
        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = categoryController.getCategoryById(String.valueOf(categoryId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidCategoryId_whenDeleteCategory_thenReturnDeletedCategory() {
        // Given
        Integer categoryId = 1;
        when(categoryService.deleteCategory(categoryId)).thenReturn(Optional.of(category));

        // When
        ResponseEntity<?> response = categoryController.deleteCategory(String.valueOf(categoryId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(category, response.getBody());
    }

    @Test
    void givenInvalidCategoryId_whenDeleteCategory_thenReturnNotFound() {
        // Given
        Integer categoryId = 1;
        when(categoryService.deleteCategory(categoryId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = categoryController.deleteCategory(String.valueOf(categoryId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidCategoryId_whenGetProductsByCategory_thenReturnProductList() {
        // Given
        Integer categoryId = 1;
        Product product = new Product();
        product.setId(1);
        product.setName("Apple");
        List<Product> productList = List.of(product);

        when(categoryService.getProductsByCategoryId(categoryId)).thenReturn(productList);

        // When
        ResponseEntity<?> response = categoryController.getProductsByCategory(String.valueOf(categoryId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(productList, response.getBody());
    }
}
