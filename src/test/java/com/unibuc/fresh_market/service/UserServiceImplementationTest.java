package com.unibuc.fresh_market.service;

import com.unibuc.fresh_market.domain.Farm;
import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.domain.Review;
import com.unibuc.fresh_market.domain.security.User;
import com.unibuc.fresh_market.repository.security.UserRepository;
import com.unibuc.fresh_market.service.UserService.UserServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplementationTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImplementation userService;

    private User user;
    private List<Order> orderList;
    private List<Review> reviewList;

    @BeforeEach
    void setUp() {
        User user1 = User.builder()
                .id(1)
                .username("john_doe")
                .email("john.doe@example.com")
                .password("password")
                .build();

        Farm farm1 =  Farm.builder().name("User1 Farm").address("123 Farmer's Lane").user(user1).build();

        Order order1 = Order.builder()
                .status("Pending")
                .totalAmount(30.0)
                .createdAt(new Date())
                .user(user1)
                .farm(farm1)
                .build();
        Order order2 = Order.builder()
                .status("Shipped")
                .totalAmount(50.0)
                .createdAt(new Date())
                .user(user1)
                .farm(farm1)
                .build();

        Review review1 = Review.builder()
                .id(2)
                .rating(3)
                .content("Okay!")
                .user(user1)
                .build();

        Review review2 = Review.builder()
                .id(2)
                .rating(5)
                .content("Excellent quality!")
                .user(user1)
                .build();

        orderList = List.of(order1, order2);
        reviewList = List.of(review1, review2);

        user1.setOrders(orderList);
        user1.setReviews(reviewList);
        user = user1;
    }

    @Test
    void whenCreateUser_thenReturnCreatedUser() {
        // Given
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        User createdUser = userService.createUser(user);

        // Then
        assertNotNull(createdUser);
        assertEquals(user.getId(), createdUser.getId());
        assertEquals(user.getUsername(), createdUser.getUsername());
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals("encodedPassword", createdUser.getPassword());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void whenGetUserById_thenReturnUser() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // When
        Optional<User> result = userService.getUserById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getId());
        assertEquals(user.getUsername(), result.get().getUsername());

        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void whenGetAllUsers_thenReturnListOfUsers() {
        // Given
        when(userRepository.findAll()).thenReturn(List.of(user));

        // When
        List<User> users = userService.getAllUsers();

        // Then
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals(user.getId(), users.getFirst().getId());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void whenUpdateUser_thenReturnUpdatedUser() {
        // Given
        User updatedUser = User.builder()
                .id(1)
                .username("john_smith")
                .email("john.smith@example.com")
                .password("newPassword")
                .build();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // When
        Optional<User> result = userService.updateUser(1, updatedUser);

        // Then
        assertTrue(result.isPresent());
        assertEquals(updatedUser.getUsername(), result.get().getUsername());
        assertEquals(updatedUser.getEmail(), result.get().getEmail());

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void whenDeleteUser_thenReturnDeletedUser() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // When
        Optional<User> result = userService.deleteUser(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getId());

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void whenGetOrdersByUserId_thenReturnOrders() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // When
        List<Order> result = userService.getOrdersByUserId(1);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(orderList.size(), result.size());

        // Verify findById method is called
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void whenGetReviewsByUserId_thenReturnReviews() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // When
        List<Review> result = userService.getReviewsByUserId(1);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(reviewList.size(), result.size());

        verify(userRepository, times(1)).findById(1);
    }
}

