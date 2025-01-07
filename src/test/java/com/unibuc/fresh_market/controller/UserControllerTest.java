package com.unibuc.fresh_market.controller;

import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.Review;
import com.unibuc.fresh_market.domain.security.User;
import com.unibuc.fresh_market.service.UserService.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UserController userController;

    private User user;
    private List<User> userList;
    private List<Order> orderList;
    private List<Review> reviewList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .id(1)
                .username("john_doe")
                .email("john.doe@example.com")
                .build();

        User user2 = User.builder()
                .id(2)
                .username("jane_smith")
                .email("jane.smith@example.com")
                .build();

        userList = List.of(user, user2);

        Order order1 = Order.builder().id(1).user(user).build();
        Order order2 = Order.builder().id(2).user(user2).build();

        Review review1 = Review.builder().id(1).user(user).build();
        Review review2 = Review.builder().id(2).user(user).build();

        orderList = List.of(order1, order2);
        reviewList = List.of(review1, review2);
    }

    @Test
    void givenValidUser_whenCreateUser_thenReturnCreatedUser() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.createUser(any(User.class))).thenReturn(user);

        // When
        ResponseEntity<?> response = userController.createUser(user, bindingResult);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(user, response.getBody());
    }

    @Test
    void givenInvalidUser_whenCreateUser_thenReturnBadRequest() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = userController.createUser(user, bindingResult);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenUsers_whenGetAllUsers_thenReturnUserList() {
        // Given
        when(userService.getAllUsers()).thenReturn(userList);

        // When
        ResponseEntity<?> response = userController.getAllUsers();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(userList, response.getBody());
    }

    @Test
    void givenValidUserId_whenGetUserById_thenReturnUser() {
        // Given
        Integer userId = 1;
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        // When
        ResponseEntity<?> response = userController.getUserById(String.valueOf(userId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(user, response.getBody());
    }

    @Test
    void givenInvalidUserId_whenGetUserById_thenReturnNotFound() {
        // Given
        Integer userId = 1;
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = userController.getUserById(String.valueOf(userId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidUser_whenUpdateUser_thenReturnUpdatedUser() {
        // Given
        Integer userId = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.updateUser(eq(userId), any(User.class))).thenReturn(Optional.of(user));

        // When
        ResponseEntity<?> response = userController.updateUser(user, bindingResult, String.valueOf(userId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(user, response.getBody());
    }

    @Test
    void givenInvalidUser_whenUpdateUser_thenReturnBadRequest() {
        // Given
        Integer userId = 1;
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = userController.updateUser(user, bindingResult, String.valueOf(userId));

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenInvalidUserId_whenUpdateUser_thenReturnNotFound() {
        // Given
        Integer userId = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.updateUser(userId, user)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = userController.updateUser(user, bindingResult, String.valueOf(userId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidUserId_whenDeleteUser_thenReturnDeletedUser() {
        // Given
        Integer userId = 1;
        when(userService.deleteUser(userId)).thenReturn(Optional.of(user));

        // When
        ResponseEntity<?> response = userController.deleteUser(String.valueOf(userId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(user, response.getBody());
    }

    @Test
    void givenInvalidUserId_whenDeleteUser_thenReturnNotFound() {
        // Given
        Integer userId = 1;
        when(userService.deleteUser(userId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = userController.deleteUser(String.valueOf(userId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidUserId_whenGetOrdersByUserId_thenReturnOrderList() {
        // Given
        Integer userId = 1;
        when(userService.getOrdersByUserId(userId)).thenReturn(orderList);

        // When
        ResponseEntity<?> response = userController.getOrdersByUserId(String.valueOf(userId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(orderList, response.getBody());
    }

    @Test
    void givenValidUserId_whenGetReviewsByUserId_thenReturnReviewList() {
        // Given
        Integer userId = 1;
        when(userService.getReviewsByUserId(userId)).thenReturn(reviewList);

        // When
        ResponseEntity<?> response = userController.getReviewsByUserId(String.valueOf(userId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(reviewList, response.getBody());
    }
}
