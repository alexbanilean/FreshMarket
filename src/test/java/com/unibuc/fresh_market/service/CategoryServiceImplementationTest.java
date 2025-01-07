package com.unibuc.fresh_market.service;

import com.unibuc.fresh_market.domain.Category;
import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.repository.CategoryRepository;
import com.unibuc.fresh_market.repository.ProductRepository;
import com.unibuc.fresh_market.service.CategoryService.CategoryServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplementationTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CategoryServiceImplementation categoryService;

    private Category category;
    private List<Category> categoryList;
    private List<Product> productList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = Category.builder()
                .id(1)
                .name("Fresh Produce")
                .description("Fresh vegetables and fruits")
                .build();

        Category category2 = Category.builder()
                .id(2)
                .name("Dairy")
                .description("Dairy products")
                .build();

        categoryList = List.of(category, category2);

        Product product1 = Product.builder().id(1).name("Apple").category(category).build();
        Product product2 = Product.builder().id(2).name("Banana").category(category).build();

        productList = List.of(product1, product2);
    }

    @Test
    void whenCreateCategory_thenReturnCreatedCategory() {
        // Given
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // When
        Category createdCategory = categoryService.createCategory(category);

        // Then
        assertNotNull(createdCategory);
        assertEquals("Fresh Produce", createdCategory.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void whenGetAllCategories_thenReturnCategoryList() {
        // Given
        when(categoryRepository.findAll()).thenReturn(categoryList);

        // When
        List<Category> categories = categoryService.getAllCategories();

        // Then
        assertNotNull(categories);
        assertEquals(2, categories.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void whenGetCategoryById_thenReturnCategory() {
        // Given
        Integer categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // When
        Optional<Category> result = categoryService.getCategoryById(categoryId);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Fresh Produce", result.get().getName());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void whenGetCategoryById_notFound_thenReturnEmpty() {
        // Given
        Integer categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When
        Optional<Category> result = categoryService.getCategoryById(categoryId);

        // Then
        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void whenUpdateCategory_thenReturnUpdatedCategory() {
        // Given
        Integer categoryId = 1;
        Category updatedCategory = Category.builder()
                .id(1)
                .name("Fresh Produce Updated")
                .description("Updated description")
                .build();

        Category existingCategory = Category.builder()
                .id(1)
                .name("Fresh Produce")
                .description("Fresh vegetables and fruits")
                .build();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        // When
        Optional<Category> result = categoryService.updateCategory(categoryId, updatedCategory);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Fresh Produce Updated", result.get().getName());
        assertEquals("Updated description", result.get().getDescription());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void whenUpdateCategory_notFound_thenReturnEmpty() {
        // Given
        Integer categoryId = 1;
        Category updatedCategory = Category.builder()
                .id(1)
                .name("Fresh Produce Updated")
                .description("Updated description")
                .build();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When
        Optional<Category> result = categoryService.updateCategory(categoryId, updatedCategory);

        // Then
        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void whenDeleteCategory_thenReturnDeletedCategory() {
        // Given
        Integer categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // When
        Optional<Category> result = categoryService.deleteCategory(categoryId);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Fresh Produce", result.get().getName());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void whenDeleteCategory_notFound_thenReturnEmpty() {
        // Given
        Integer categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When
        Optional<Category> result = categoryService.deleteCategory(categoryId);

        // Then
        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void whenGetProductsByCategoryId_thenReturnProductList() {
        // Given
        Integer categoryId = 1;
        when(productRepository.getProductsByCategoryId(categoryId)).thenReturn(productList);

        // When
        List<Product> products = categoryService.getProductsByCategoryId(categoryId);

        // Then
        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).getProductsByCategoryId(categoryId);
    }
}

